/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui.log;

import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.WARN_HISTORY_NOT_AVAILABLE;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;
import static org.eclipse.epp.internal.logging.aeri.ui.model.Reports.*;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.AbstractIdleService;

public class ReportHistory extends AbstractIdleService {

    private static final String F_VERSION = "version";
    private static final String F_IDENTITY = "identity";
    private static final String F_IDENTITY_TRACE = "identity-trace";
    private Directory index;
    protected IndexWriter writer;
    private SearcherManager manager;

    protected String getIndexDirectoryName() {
        return "history";
    }

    public boolean seen(ErrorReport report) {
        return seen(new TermQuery(new Term(F_IDENTITY, exactIdentityHash(report))));
    }

    public boolean seenSimilar(ErrorReport report) {
        return seen(new TermQuery(new Term(F_IDENTITY_TRACE, traceIdentityHash(report))));
    }

    private boolean seen(Query q) {
        IndexSearcher searcher = manager.acquire();
        try {
            TopDocs results = searcher.search(q, 1);
            boolean foundIdenticalReport = results.totalHits > 0;
            return foundIdenticalReport;
        } catch (Exception e) {
            log(WARN_HISTORY_NOT_AVAILABLE, e);
            return false;
        } finally {
            try {
                manager.release(searcher);
            } catch (IOException e) {
                log(WARN_HISTORY_NOT_AVAILABLE, e);
            }
        }
    }

    public void remember(Iterable<ErrorReport> reports) {
        for (ErrorReport report : reports) {
            remember(report);
        }
    }

    public void remember(ErrorReport report) {
        if (seen(report)) {
            return;
        }
        Document doc = new Document();
        Field field = new Field(F_IDENTITY, exactIdentityHash(report), Store.NO, Index.NOT_ANALYZED);
        doc.add(field);
        if (report.isIgnoreSimilar()) {
            field = new Field(F_IDENTITY_TRACE, traceIdentityHash(report), Store.NO, Index.NOT_ANALYZED);
            doc.add(field);
        }
        try {
            writer.addDocument(doc);
            writer.commit();
        } catch (Exception e) {
            log(WARN_HISTORY_NOT_AVAILABLE, e);
        }
        reopen();
    }

    private void reopen() {
        try {
            manager.maybeReopen();
        } catch (IOException e) {
            log(WARN_HISTORY_NOT_AVAILABLE, e);
        }
    }

    @VisibleForTesting
    protected Directory createIndexDirectory() throws IOException {
        Bundle bundle = FrameworkUtil.getBundle(getClass());
        IPath stateLocation = Platform.getStateLocation(bundle);
        File indexdir = new File(stateLocation.toFile(), getIndexDirectoryName());
        indexdir.mkdirs();
        return FSDirectory.open(indexdir);
    }

    @Override
    protected void startUp() throws Exception {
        index = createIndexDirectory();
        createWriter();
        manager = new SearcherManager(index, null, null);
    }

    private void createWriter() throws CorruptIndexException, LockObtainFailedException, IOException {
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, new KeywordAnalyzer());
        conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
        writer = new IndexWriter(index, conf);
        // to build an initial index if empty:
        if (writer.numDocs() == 0) {
            buildInitialIndex();
        }
    }

    private void buildInitialIndex() throws CorruptIndexException, IOException {
        Document meta = new Document();
        meta.add(new Field(F_VERSION, Constants.VERSION, Store.YES, Index.NO));
        writer.addDocument(meta);
        writer.commit();
    }

    @Override
    protected void shutDown() throws Exception {
        IOUtils.close(writer, index);
        manager.close();
    }
}
