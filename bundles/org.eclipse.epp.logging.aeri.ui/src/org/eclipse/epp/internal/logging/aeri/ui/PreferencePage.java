/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Daniel Haftstein - initial implementation
 */
package org.eclipse.epp.internal.logging.aeri.ui;

import static org.eclipse.epp.internal.logging.aeri.ui.Constants.*;
import static org.eclipse.epp.internal.logging.aeri.ui.model.PreferenceInitializer.*;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.Messages;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Browsers;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    private static final Point TOOLTIP_DISPLACEMENT = new Point(5, 20);
    private static int TOOLTIP_MS_HIDE_DELAY = 20000;

    public PreferencePage() {
        super(GRID);
    }

    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, Constants.PLUGIN_ID));
    }

    @Override
    protected void createFieldEditors() {
        addField(new StringFieldEditor(PROP_SERVER, Messages.FIELD_LABEL_SERVER, getFieldEditorParent()));

        addField(createStringFieldEditorAndToolTip(PROP_NAME, Messages.FIELD_LABEL_NAME, Messages.FIELD_MESSAGE_NAME));
        addField(createStringFieldEditorAndToolTip(PROP_EMAIL, Messages.FIELD_LABEL_EMAIL,
                Messages.FIELD_MESSAGE_EMAIL + " \n" + Messages.FIELD_DESC_EMAIL)); //$NON-NLS-1$

        addField(new ComboFieldEditor(PROP_SEND_ACTION, Messages.FIELD_LABEL_ACTION, createModeLabelAndValues(),
                getFieldEditorParent()));

        addField(createBooleanFieldEditorAndToolTip(PROP_SKIP_SIMILAR_ERRORS, Messages.FIELD_LABEL_SKIP_SIMILAR_ERRORS,
                Messages.TOOLTIP_SKIP_SIMILAR));
        addField(createBooleanFieldEditorAndToolTip(PROP_ANONYMIZE_STACKTRACES,
                Messages.FIELD_LABEL_ANONYMIZE_STACKTRACES, Messages.TOOLTIP_MAKE_STACKTRACE_ANONYMOUS));
        addField(createBooleanFieldEditorAndToolTip(PROP_ANONYMIZE_MESSAGES, Messages.FIELD_LABEL_ANONYMIZE_MESSAGES,
                Messages.TOOLTIP_MAKE_MESSAGES_ANONYMOUS));

        addLinks(getFieldEditorParent());
        Dialog.applyDialogFont(getFieldEditorParent());
    }

    private StringFieldEditor createStringFieldEditorAndToolTip(String name, String labelText, String toolTipText) {
        StringFieldEditor stringFieldEditor = new StringFieldEditor(name, labelText, getFieldEditorParent());
        DefaultToolTip toolTip = new DefaultToolTip(stringFieldEditor.getLabelControl(getFieldEditorParent()));
        calibrateTooltip(toolTip, toolTipText);

        return stringFieldEditor;
    }

    private BooleanFieldEditor createBooleanFieldEditorAndToolTip(String fieldEditorName, String fieldEditorLabel,
            String toolTipText) {
        BooleanFieldEditor booleanFieldEditor = new BooleanFieldEditor(fieldEditorName, fieldEditorLabel,
                getFieldEditorParent());
        DefaultToolTip toolTip = new DefaultToolTip(booleanFieldEditor.getDescriptionControl(getFieldEditorParent()));
        calibrateTooltip(toolTip, toolTipText);

        return booleanFieldEditor;
    }

    private void calibrateTooltip(DefaultToolTip toolTip, String toolTipText) {
        toolTip.setText(toolTipText);
        toolTip.setFont(JFaceResources.getDialogFont());
        toolTip.setShift(TOOLTIP_DISPLACEMENT);
        toolTip.setHideDelay(TOOLTIP_MS_HIDE_DELAY);
    }

    private void addLinks(Composite parent) {
        Composite links = new Composite(parent, SWT.NONE);
        links.setLayout(new RowLayout(SWT.VERTICAL));
        GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(links);

        createNotificationsLabelAndLink(links);

        // placeholder
        new Label(links, SWT.NONE);

        createAndConfigureLink(links, Messages.LINK_LEARN_MORE, HELP_URL);
        createAndConfigureLink(links, Messages.LINK_PROVIDE_FEEDBACK, FEEDBACK_FORM_URL);
    }

    private void createNotificationsLabelAndLink(Composite links) {
        Link link = new Link(links, SWT.NONE);
        link.setText("Notifications are configured on the <a>Notifications</a> preference page.");
        link.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                PreferencesUtil.createPreferenceDialogOn(getShell(),
                        "org.eclipse.mylyn.commons.notifications.preferencePages.Notifications", null, null);
            }
        });

    }

    private Link createAndConfigureLink(Composite parent, String text, final String url) {
        Link link = new Link(parent, SWT.NONE);
        link.setText(text);
        link.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Browsers.openInExternalBrowser(url);
            }
        });
        return link;
    }

    private static String[][] createModeLabelAndValues() {
        SendAction[] modes = SendAction.values();
        String[][] labelAndValues = new String[modes.length][2];
        for (int i = 0; i < modes.length; i++) {
            SendAction mode = modes[i];
            labelAndValues[i][0] = descriptionForMode(mode);
            labelAndValues[i][1] = mode.name();
        }
        return labelAndValues;
    }

    private static String descriptionForMode(SendAction mode) {
        switch (mode) {
        case ASK:
            return Messages.PREFERENCEPAGE_ASK_LABEL;
        case IGNORE:
            return Messages.FIELD_LABEL_ACTION_REPORT_NEVER;
        case SILENT:
            return Messages.FIELD_LABEL_ACTION_REPORT_ALWAYS;
        default:
            return mode.name();
        }
    }

}
