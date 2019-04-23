package com.vdoc.intellij.run.ui;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.vdoc.intellij.bundle.VdocExecutionBundle;
import com.vdoc.intellij.run.configuration.VDoc14RunConfiguration;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

/**
 * Created by famaridon on 12/05/17.
 */
public class VDocConfigurable extends SettingsEditor<VDoc14RunConfiguration> {
	
	private JPanel panel;
	private JLabel vdocHomeLabel;
	private JTextField vdocHome;
	private TextFieldWithBrowseButton browseButton;
	private JLabel xmxLabel;
	private JTextField xmx;
	private JLabel maxPermSizeLabel;
	private JTextField maxPermSize;
	private JCheckBox useDCEVM;
	private JLabel useDCEVMLabel;
	
	@Override
	protected void resetEditorFrom(@NotNull VDoc14RunConfiguration runConfiguration) {
		if (runConfiguration.getVdocHome() != null) {
			this.getVdocHome().setText(runConfiguration.getVdocHome().toString());
		}
		if (runConfiguration.getXmx() != null) {
			this.getXmx().setText(runConfiguration.getXmx());
		}
		if (runConfiguration.getMaxPermSize() != null) {
			this.getMaxPermSize().setText(runConfiguration.getMaxPermSize());
		}
		if (runConfiguration.isUseDCEVM() != null) {
			this.getUseDCEVM().setSelected(runConfiguration.isUseDCEVM());
		}
	}
	
	@Override
	protected void applyEditorTo(@NotNull VDoc14RunConfiguration runConfiguration) throws ConfigurationException {
		if (StringUtils.isNotEmpty(this.getVdocHome().getText())) {
			runConfiguration.setVdocHome(Paths.get(this.getVdocHome().getText()));
		}
		if (StringUtils.isNotEmpty(this.getXmx().getText())) {
			runConfiguration.setXmx(this.getXmx().getText());
		}
		if (StringUtils.isNotEmpty(this.getMaxPermSize().getText())) {
			runConfiguration.setMaxPermSize(this.getMaxPermSize().getText());
		}
		runConfiguration.setUseDCEVM(this.getUseDCEVM().isSelected());
	}
	
	@NotNull
	@Override
	protected JComponent createEditor() {
		this.setPanel(new JPanel());
		this.getPanel().setLayout(new GridLayout(4, 2));
		this.setVdocHome(new JTextField());
		this.setXmx(new JTextField());
		this.setMaxPermSize(new JTextField());
		this.setBrowseButton(new TextFieldWithBrowseButton(this.getVdocHome()));
		this.setUseDCEVM(new JCheckBox());
		this.getBrowseButton().addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(false, true, false, false, false, false)));
		
		this.setVdocHomeLabel(new JLabel(VdocExecutionBundle.message("run.vdoc.home")));
		this.getVdocHomeLabel().setLabelFor(this.getVdocHome());
		this.setXmxLabel(new JLabel(VdocExecutionBundle.message("run.xmx")));
		this.getXmxLabel().setLabelFor(this.getXmx());
		this.setMaxPermSizeLabel(new JLabel(VdocExecutionBundle.message("run.maxPermSize")));
		this.getMaxPermSizeLabel().setLabelFor(this.getMaxPermSize());
		this.setUseDCEVMLabel(new JLabel(VdocExecutionBundle.message("run.useDCEVM")));
		this.getUseDCEVMLabel().setLabelFor(this.getUseDCEVM());
		
		this.getPanel().add(this.getVdocHomeLabel());
		this.getPanel().add(this.getBrowseButton());
		this.getPanel().add(this.getXmxLabel());
		this.getPanel().add(this.getXmx());
		this.getPanel().add(this.getMaxPermSizeLabel());
		this.getPanel().add(this.getMaxPermSize());
		this.getPanel().add(this.getUseDCEVMLabel());
		this.getPanel().add(this.getUseDCEVM());
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
	
	public JLabel getXmxLabel() {
		return this.xmxLabel;
	}
	
	public void setXmxLabel(JLabel xmxLabel) {
		this.xmxLabel = xmxLabel;
	}
	
	public JTextField getXmx() {
		return this.xmx;
	}
	
	public void setXmx(JTextField xmx) {
		this.xmx = xmx;
	}
	
	public JLabel getMaxPermSizeLabel() {
		return this.maxPermSizeLabel;
	}
	
	public void setMaxPermSizeLabel(JLabel maxPermSizeLabel) {
		this.maxPermSizeLabel = maxPermSizeLabel;
	}
	
	public JTextField getMaxPermSize() {
		return this.maxPermSize;
	}
	
	public void setMaxPermSize(JTextField maxPermSize) {
		this.maxPermSize = maxPermSize;
	}
	
	public JCheckBox getUseDCEVM() {
		return this.useDCEVM;
	}
	
	public void setUseDCEVM(JCheckBox useDCEVM) {
		this.useDCEVM = useDCEVM;
	}
	
	public JLabel getUseDCEVMLabel() {
		return this.useDCEVMLabel;
	}
	
	public void setUseDCEVMLabel(JLabel useDCEVMLabel) {
		this.useDCEVMLabel = useDCEVMLabel;
	}
}
