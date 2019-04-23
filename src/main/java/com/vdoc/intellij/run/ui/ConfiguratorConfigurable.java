package com.vdoc.intellij.run.ui;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.vdoc.intellij.bundle.VdocExecutionBundle;
import com.vdoc.intellij.run.configuration.ConfiguratorRunConfiguration;
import com.vdoc.intellij.run.configuration.VDoc14RunConfiguration;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

/**
 * Created by famaridon on 12/05/17.
 */
public class ConfiguratorConfigurable extends SettingsEditor<ConfiguratorRunConfiguration> {
	
	private JPanel panel;
	private JLabel vdocHomeLabel;
	private JTextField vdocHome;
	private TextFieldWithBrowseButton browseButton;
	private JCheckBox apply;
	private JLabel applyLabel;
	
	@Override
	protected void resetEditorFrom(@NotNull ConfiguratorRunConfiguration runConfiguration) {
		if (runConfiguration.getVdocHome() != null) {
			vdocHome.setText(runConfiguration.getVdocHome().toString());
		}
		if (runConfiguration.isApply() != null) {
			apply.setSelected(runConfiguration.isApply());
		}
	}
	
	@Override
	protected void applyEditorTo(@NotNull ConfiguratorRunConfiguration runConfiguration) throws ConfigurationException {
		if (StringUtils.isNotEmpty(vdocHome.getText())) {
			runConfiguration.setVdocHome(Paths.get(vdocHome.getText()));
		}
		runConfiguration.setApply(apply.isSelected());
	}
	
	@NotNull
	@Override
	protected JComponent createEditor() {
		this.panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));
		vdocHome = new JTextField();
		browseButton = new TextFieldWithBrowseButton(vdocHome);
		apply = new JCheckBox();
		browseButton.addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(false, true, false, false, false, false)));
		
		vdocHomeLabel = new JLabel(VdocExecutionBundle.message("run.vdoc.home"));
		vdocHomeLabel.setLabelFor(vdocHome);
		applyLabel = new JLabel(VdocExecutionBundle.message("run.apply"));
		applyLabel.setLabelFor(apply);
		
		panel.add(vdocHomeLabel);
		panel.add(browseButton);
		panel.add(applyLabel);
		panel.add(apply);
		return panel;
	}
	
}
