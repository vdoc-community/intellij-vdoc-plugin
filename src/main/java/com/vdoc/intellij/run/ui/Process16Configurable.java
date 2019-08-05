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
	private JLabel xmxLabel;
	private JTextField xmx;
	private JLabel xmsLabel;
	private JTextField xms;
	private JCheckBox useDCEVM;
	private JLabel useDCEVMLabel;
	
	@Override
	protected void resetEditorFrom(@NotNull Process16RunConfiguration runConfiguration) {
		if (runConfiguration.getVdocHome() != null) {
			vdocHome.setText(runConfiguration.getVdocHome().toString());
		}
		if (runConfiguration.getXmx() != null) {
			xmx.setText(runConfiguration.getXmx());
		}
		if (runConfiguration.getXms() != null) {
			xms.setText(runConfiguration.getXms());
		}
		if (runConfiguration.isUseDCEVM() != null) {
			useDCEVM.setSelected(runConfiguration.isUseDCEVM());
		}
	}
	
	@Override
	protected void applyEditorTo(@NotNull Process16RunConfiguration runConfiguration) throws ConfigurationException {
		if (StringUtils.isNotEmpty(vdocHome.getText())) {
			runConfiguration.setVdocHome(Paths.get(vdocHome.getText()));
		}
		if (StringUtils.isNotEmpty(xmx.getText())) {
			runConfiguration.setXmx(xmx.getText());
		}
		if (StringUtils.isNotEmpty(xms.getText())) {
			runConfiguration.setXms(xms.getText());
		}
		runConfiguration.setUseDCEVM(useDCEVM.isSelected());
	}
	
	@NotNull
	@Override
	protected JComponent createEditor() {
		this.panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));
		vdocHome = new JTextField();
		xmx = new JTextField();
		xms = new JTextField();
		browseButton = new TextFieldWithBrowseButton(vdocHome);
		useDCEVM = new JCheckBox();
		browseButton.addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(false, true, false, false, false, false)));
		
		vdocHomeLabel = new JLabel(VdocExecutionBundle.message("run.vdoc.home"));
		vdocHomeLabel.setLabelFor(vdocHome);
		xmxLabel = new JLabel(VdocExecutionBundle.message("run.xmx"));
		xmxLabel.setLabelFor(xmx);
		xmsLabel = new JLabel(VdocExecutionBundle.message("run.xms"));
		xmsLabel.setLabelFor(xms);
		useDCEVMLabel = new JLabel(VdocExecutionBundle.message("run.useDCEVM"));
		useDCEVMLabel.setLabelFor(useDCEVM);
		
		panel.add(vdocHomeLabel);
		panel.add(browseButton);
		panel.add(xmxLabel);
		panel.add(xmx);
		panel.add(xmsLabel);
		panel.add(xms);
		panel.add(useDCEVMLabel);
		panel.add(useDCEVM);
		return panel;
	}
	
}
