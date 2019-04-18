package com.vdoc.intellij.run.ui;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.vdoc.intellij.bundle.VdocExecutionBundle;
import com.vdoc.intellij.run.configuration.Process16RunConfiguration;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

/**
 * Created by famaridon on 12/05/17.
 */
public class Process16Configurable extends SettingsEditor<Process16RunConfiguration> {
	
	private JPanel panel;
	private JLabel vdocHomeLabel;
	private JTextField vdocHome;
	private TextFieldWithBrowseButton browseButton;
	
	@Override
	protected void resetEditorFrom(@NotNull Process16RunConfiguration runConfiguration) {
		if (runConfiguration.getVdocHome() != null) {
			vdocHome.setText(runConfiguration.getVdocHome().toString());
		}
	}
	
	@Override
	protected void applyEditorTo(@NotNull Process16RunConfiguration runConfiguration) throws ConfigurationException {
		if (StringUtils.isNotEmpty(vdocHome.getText())) {
			runConfiguration.setVdocHome(Paths.get(vdocHome.getText()));
		}
	}
	
	@NotNull
	@Override
	protected JComponent createEditor() {
		this.panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));
		vdocHome = new JTextField();
		browseButton = new TextFieldWithBrowseButton(vdocHome);
		browseButton.addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(false, true, false, false, false, false)));
		
		vdocHomeLabel = new JLabel(VdocExecutionBundle.message("run.vdoc.home"));
		vdocHomeLabel.setLabelFor(vdocHome);
		
		panel.add(vdocHomeLabel);
		panel.add(browseButton);
		return panel;
	}
	
}
