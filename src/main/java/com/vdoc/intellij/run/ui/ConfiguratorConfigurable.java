package com.vdoc.intellij.run.ui;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.vdoc.intellij.bundle.VdocExecutionBundle;
import com.vdoc.intellij.run.configuration.ConfiguratorRunConfiguration;
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
			this.getVdocHome().setText(runConfiguration.getVdocHome().toString());
		}
		if (runConfiguration.isApply() != null) {
			this.getApply().setSelected(runConfiguration.isApply());
		}
	}
	
	@Override
	protected void applyEditorTo(@NotNull ConfiguratorRunConfiguration runConfiguration) throws ConfigurationException {
		if (StringUtils.isNotEmpty(this.getVdocHome().getText())) {
			runConfiguration.setVdocHome(Paths.get(this.getVdocHome().getText()));
		}
		runConfiguration.setApply(this.getApply().isSelected());
	}
	
	@NotNull
	@Override
	protected JComponent createEditor() {
		this.setPanel(new JPanel());
		this.getPanel().setLayout(new GridLayout(4, 2));
		this.setVdocHome(new JTextField());
		this.setBrowseButton(new TextFieldWithBrowseButton(this.getVdocHome()));
		this.setApply(new JCheckBox());
		this.getBrowseButton().addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(false, true, false, false, false, false)));
		
		this.setVdocHomeLabel(new JLabel(VdocExecutionBundle.message("run.vdoc.home")));
		this.getVdocHomeLabel().setLabelFor(this.getVdocHome());
		this.setApplyLabel(new JLabel(VdocExecutionBundle.message("run.apply")));
		this.getApplyLabel().setLabelFor(this.getApply());
		
		this.getPanel().add(this.getVdocHomeLabel());
		this.getPanel().add(this.getBrowseButton());
		this.getPanel().add(this.getApplyLabel());
		this.getPanel().add(this.getApply());
		return this.getPanel();
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	public JLabel getVdocHomeLabel() {
		return this.vdocHomeLabel;
	}
	
	public void setVdocHomeLabel(JLabel vdocHomeLabel) {
		this.vdocHomeLabel = vdocHomeLabel;
	}
	
	public JTextField getVdocHome() {
		return this.vdocHome;
	}
	
	public void setVdocHome(JTextField vdocHome) {
		this.vdocHome = vdocHome;
	}
	
	public TextFieldWithBrowseButton getBrowseButton() {
		return this.browseButton;
	}
	
	public void setBrowseButton(TextFieldWithBrowseButton browseButton) {
		this.browseButton = browseButton;
	}
	
	public JCheckBox getApply() {
		return this.apply;
	}
	
	public void setApply(JCheckBox apply) {
		this.apply = apply;
	}
	
	public JLabel getApplyLabel() {
		return this.applyLabel;
	}
	
	public void setApplyLabel(JLabel applyLabel) {
		this.applyLabel = applyLabel;
	}
}
