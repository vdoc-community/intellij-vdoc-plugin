package com.vdoc.intellij.run;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.vdoc.intellij.run.factories.ConfiguratorConfigurationFactory;
import com.vdoc.intellij.run.factories.VDoc14ConfigurationFactory;

import javax.swing.*;

/**
 * Created by famaridon on 11/05/17.
 */
public class VDocRunConfigurationType extends ConfigurationTypeBase {
	
	public static final String VDOC_RUN_CONFIGURATION_ID = "VDocRunConfiguration";
	public static final String VDOC_RUN_CONFIGURATION_DISPLAY_NAME = "Process";
	
	public VDocRunConfigurationType() {
		super(VDOC_RUN_CONFIGURATION_ID, VDOC_RUN_CONFIGURATION_DISPLAY_NAME, VDOC_RUN_CONFIGURATION_DISPLAY_NAME, new ImageIcon(VDocRunConfigurationType.class.getResource("/images/icon/icon16x16.png")));
		this.addFactory(new VDoc14ConfigurationFactory(this));
		this.addFactory(new ConfiguratorConfigurationFactory(this));
	}
}
