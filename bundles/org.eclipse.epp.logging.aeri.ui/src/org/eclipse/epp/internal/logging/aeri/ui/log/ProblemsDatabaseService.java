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

import static com.google.common.base.Optional.absent;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.lucene.index.IndexReader.openIfChanged;
import static org.eclipse.epp.internal.logging.aeri.ui.Constants.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.WARN_STATUS_INDEX_NOT_AVAILABLE;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus.RequiredAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.AbstractIdleService;

public class ProblemsDatabaseService extends AbstractIdleService {

    private static final String F_MESSAGE = "message";
    private static final String F_ACTION = "action";
    private static final String F_BUG_ID = "bugId";
    private static final String F_BUG_URL = "bugUrl";
    private static final String F_PROBLEM_URL = "problemUrl";
    private static final String F_FINGERPRINT = "fingerprint";
    private File indexDirectory;
    private Directory index;
    private IndexReader reader;
    private IndexSearcher searcher;

    public ProblemsDatabaseService(File indexDirectory) {
        this.indexDirectory = indexDirectory;
    }

    public Optional<ProblemStatus> seen(ErrorReport report) {
        if (!isRunning()) {
            return absent();
        }
        String fingerprint = Reports.traceIdentityHash(report);
        return seen(new TermQuery(new Term(F_FINGERPRINT, fingerprint)));
    }

    private Optional<ProblemStatus> seen(Query q) {
        try {
            renewReaderAndSearcher();
            TopDocs results = searcher.search(q, 1);
            if (results.totalHits > 0) {
                int doc = results.scoreDocs[0].doc;
                Document d = reader.document(doc);
                ProblemStatus status = loadStatus(d);
                return Optional.of(status);
            }
        } catch (Exception e) {
            log(WARN_STATUS_INDEX_NOT_AVAILABLE, e);
        }
        return Optional.absent();
    }

    private ProblemStatus loadStatus(Document d) {
        ProblemStatus status = new ProblemStatus();
        {
            String stacktraceFingerprint = d.get(F_FINGERPRINT);
            status.setIncidentFingerprint(stacktraceFingerprint);
        }
        {
            String bugId = d.get(F_BUG_ID);
            if (isNotBlank(bugId)) {
                int id = Integer.parseInt(bugId);
                String url = d.get(F_BUG_URL);
                status.setBugId(id);
                status.setBugUrl(url);
            }
        }
        {
            String problemUrl = d.get(F_PROBLEM_URL);
            status.setProblemUrl(problemUrl);
        }
        {
            RequiredAction action = RequiredAction.valueOf(d.get(F_ACTION));
            status.setAction(action);
        }
        {
            String message = d.get(F_MESSAGE);
            if (isNotBlank(message)) {
                status.setMessage(message);
            }
        }
        return status;
    }

    @Override
    protected void startUp() throws Exception {
        index = createIndexDirectory();
        createReaderAndSearcher();
    }

    @VisibleForTesting
    protected Directory createIndexDirectory() throws IOException {
        indexDirectory.mkdirs();
        FSDirectory directory = FSDirectory.open(indexDirectory);
        if (!IndexReader.indexExists(directory)) {
            createInitialIndex(directory);
        }
        return directory;
    }

    private void createInitialIndex(Directory directory) throws IOException {
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, new KeywordAnalyzer());
        conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
        try (IndexWriter writer = new IndexWriter(directory, conf)) {
            Document meta = new Document();
            meta.add(new Field(F_VERSION, VERSION, Store.YES, Index.NO));
            writer.addDocument(meta);
            writer.commit();
        }
    }

    private void createReaderAndSearcher() throws CorruptIndexException, IOException {
        reader = IndexReader.open(index);
        searcher = new IndexSearcher(reader);
    }

    protected void renewReaderAndSearcher() throws IOException {
        IndexReader tmp = openIfChanged(reader);
        if (tmp != null) {
            IOUtils.close(reader, searcher);
            searcher = new IndexSearcher(tmp);
            reader = tmp;
        }
    }

    @Override
    protected void shutDown() throws Exception {
        IOUtils.close(searcher, reader, index);
    }

    public void replaceContent(File tempDir) throws IOException {
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, new KeywordAnalyzer());
        conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
        try (IndexWriter writer = new IndexWriter(index, conf); FSDirectory newContent = FSDirectory.open(tempDir);) {
            writer.deleteAll();
            writer.addIndexes(newContent);
            writer.commit();
        }
    }

    public File getIndexDirectory() {
        return indexDirectory;
    }

}
