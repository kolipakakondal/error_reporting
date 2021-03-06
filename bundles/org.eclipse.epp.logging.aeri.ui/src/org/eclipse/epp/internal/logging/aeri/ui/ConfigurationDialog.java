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
package org.eclipse.epp.internal.logging.aeri.ui;

import static org.eclipse.emf.databinding.EMFProperties.value;
import static org.eclipse.jface.databinding.swt.WidgetProperties.*;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.Messages;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Browsers;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ConfigurationDialog extends TitleAreaDialog {

    /**
     * Return code to indicate a cancel using the esc-button.
     */
    public static final int ESC_CANCEL = 42 + 42;
    public static final ImageDescriptor TITLE_IMAGE_DESC = ImageDescriptor.createFromFile(ConfigurationDialog.class,
            "/icons/wizban/stacktraces_wiz.png"); //$NON-NLS-1$

    private static final Point TOOLTIP_DISPLACEMENT = new Point(5, 20);
    private static int TOOLTIP_MS_HIDE_DELAY = 20000;
    private Text emailText;
    private Text nameText;
    private Button anonymizeStacktracesButton;
    private Button clearMessagesButton;

    private Settings settings;
    private ServerConfiguration configuration;

    public ConfigurationDialog(Shell parentShell, final Settings settings, ServerConfiguration configuration) {
        super(parentShell);
        this.settings = settings;
        this.configuration = configuration;
        setHelpAvailable(false);
        setShellStyle(getShellStyle() | SWT.RESIZE);
    }

    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("Automated Error Reporting Configuration");
        shell.addListener(SWT.Traverse, new Listener() {
            @Override
            public void handleEvent(Event e) {
                if (e.detail == SWT.TRAVERSE_ESCAPE) {
                    e.doit = false;
                    setReturnCode(ESC_CANCEL);
                    close();
                }
            }
        });
    }

    @Override
    public void create() {
        super.create();
        setTitle("Thank you for using the Automated Error Reporter");
        setMessage("Please take a moment for the initial configuration. All values can be changed in the preferences at any time.");

        // move focus away from first text-field to show its message-hint
        anonymizeStacktracesButton.setFocus();
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, Messages.CONFIGURATIONDIALOG_ENABLE, true);
        createButton(parent, IDialogConstants.CANCEL_ID, Messages.CONFIGURATIONDIALOG_DISABLE, false);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        setTitleImage(TITLE_IMAGE_DESC.createImage());
        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayout(new GridLayout());
        GridDataFactory.fillDefaults().grab(true, true).applyTo(container);

        Composite personalGroup = createPersonalGroup(container);
        GridDataFactory.fillDefaults().indent(10, 10).grab(true, false).applyTo(personalGroup);

        Group makeAnonymousGroup = makeAnonymousGroup(container);
        GridDataFactory.fillDefaults().applyTo(makeAnonymousGroup);

        Composite linksComposite = createLinksComposite(container);
        GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(linksComposite);

        createDataBindingContext();
        Dialog.applyDialogFont(container);
        return container;
    }

    private Composite createPersonalGroup(Composite container) {
        Composite personalGroup = new Composite(container, SWT.NONE);
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(personalGroup);
        nameText = createLabelledText(personalGroup, Messages.FIELD_LABEL_NAME, Messages.FIELD_MESSAGE_NAME, Messages.FIELD_MESSAGE_NAME);
        String emailTooltip = Messages.FIELD_MESSAGE_EMAIL + '\n' + Messages.FIELD_DESC_EMAIL;
        emailText = createLabelledText(personalGroup, Messages.FIELD_LABEL_EMAIL, Messages.FIELD_MESSAGE_EMAIL, emailTooltip);
        return personalGroup;
    }

    private Text createLabelledText(Composite parent, String labelString, String messageString, String tooltipString) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(labelString);
        calibrateTooltip(new DefaultToolTip(label), tooltipString);
        Text text = new Text(parent, SWT.BORDER);
        text.setMessage(messageString);
        calibrateTooltip(new DefaultToolTip(text), tooltipString);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(text);
        return text;
    }

    private Group makeAnonymousGroup(Composite container) {
        Group makeAnonymousGroup = new Group(container, SWT.SHADOW_ETCHED_IN | SWT.SHADOW_ETCHED_OUT | SWT.SHADOW_IN | SWT.SHADOW_OUT);
        makeAnonymousGroup.setLayout(new RowLayout(SWT.VERTICAL));
        makeAnonymousGroup.setText(Messages.CONFIGURATIONDIALOG_ANONYMIZATION);

        anonymizeStacktracesButton = createGroupCheckButton(makeAnonymousGroup, Messages.FIELD_LABEL_ANONYMIZE_STACKTRACES,
                Messages.TOOLTIP_MAKE_STACKTRACE_ANONYMOUS);
        clearMessagesButton = createGroupCheckButton(makeAnonymousGroup, Messages.FIELD_LABEL_ANONYMIZE_MESSAGES,
                Messages.TOOLTIP_MAKE_MESSAGES_ANONYMOUS);
        return makeAnonymousGroup;
    }

    private Button createGroupCheckButton(Group group, String buttonText, String toolTipText) {
        Button button = new Button(group, SWT.CHECK);
        button.setText(buttonText);
        calibrateTooltip(new DefaultToolTip(button), toolTipText);

        return button;
    }

    private Composite createLinksComposite(Composite container) {
        Composite linksComposite = new Composite(container, SWT.NONE);
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(linksComposite);
        Link learnMoreLink = new Link(linksComposite, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, false).align(SWT.BEGINNING, SWT.END).applyTo(learnMoreLink);
        learnMoreLink.setText(Messages.LINK_LEARN_MORE);
        learnMoreLink.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Browsers.openInExternalBrowser(configuration.getHelpUrl());
            }
        });
        Link feedbackLink = new Link(linksComposite, SWT.NONE);
        GridDataFactory.fillDefaults().align(SWT.END, SWT.END).applyTo(feedbackLink);
        feedbackLink.setText(Messages.LINK_PROVIDE_FEEDBACK);
        feedbackLink.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Browsers.openInExternalBrowser(configuration.getFeedbackUrl());
            }
        });
        return linksComposite;
    }

    private void createDataBindingContext() {
        DataBindingContext context = new DataBindingContext();

        ModelPackage pkg = ModelPackage.eINSTANCE;

        IObservableValue ovTxtName = text(SWT.Modify).observe(nameText);
        IObservableValue ovSetName = value(pkg.getSettings_Name()).observe(settings);
        context.bindValue(ovTxtName, ovSetName, null, null);

        IObservableValue ovTxtEmail = text(SWT.Modify).observe(emailText);
        IObservableValue ovSetEmail = value(pkg.getSettings_Email()).observe(settings);
        context.bindValue(ovTxtEmail, ovSetEmail, null, null);

        IObservableValue ovBtnAnonSt = selection().observe(anonymizeStacktracesButton);
        IObservableValue ovSetAnonSt = value(pkg.getSettings_AnonymizeStrackTraceElements()).observe(settings);
        context.bindValue(ovBtnAnonSt, ovSetAnonSt, null, null);

        IObservableValue ovBtnAnonMsg = selection().observe(clearMessagesButton);
        IObservableValue ovSetAnonMsg = value(pkg.getSettings_AnonymizeMessages()).observe(settings);
        context.bindValue(ovBtnAnonMsg, ovSetAnonMsg, null, null);
    }

    private void calibrateTooltip(DefaultToolTip toolTip, String toolTipText) {
        toolTip.setText(toolTipText);
        toolTip.setFont(JFaceResources.getDialogFont());
        toolTip.setShift(TOOLTIP_DISPLACEMENT);
        toolTip.setHideDelay(TOOLTIP_MS_HIDE_DELAY);
    }
}
