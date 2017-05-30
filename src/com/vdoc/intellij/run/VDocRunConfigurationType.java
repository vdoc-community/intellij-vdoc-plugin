package com.vdoc.intellij.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.vdoc.intellij.run.factories.VDoc14ConfigurationFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by famaridon on 11/05/17.
 */
public class VDocRunConfigurationType implements ConfigurationType
{

	private final ConfigurationFactory[] factories;
	
	public static final String VDOC_RUN_CONFIGURATION_ID = "VDocRunConfiguration";
	
	public VDocRunConfigurationType()
	{
		this.factories = new ConfigurationFactory[1];
		this.factories[0] = new VDoc14ConfigurationFactory(this);
	}
	
	/**
	 * Returns the display name of the configuration type. This is used, for example, to represent the configuration type in the run
	 * configurations tree, and also as the name of the action used to create the configuration.
	 *
	 * @return the display name of the configuration type.
	 */
	@Override
	public String getDisplayName()
	{
		return "Process";
	}
	
	/**
	 * Returns the description of the configuration type. You may return the same text as the display name of the configuration type.
	 *
	 * @return the description of the configuration type.
	 */
	@Override
	public String getConfigurationTypeDescription()
	{
		return this.getDisplayName();
	}
	
	/**
	 * Returns the 16x16 icon used to represent the configuration type.
	 *
	 * @return the icon
	 */
	@Override
	public Icon getIcon()
	{
		return new ImageIcon(VDocRunConfigurationType.class.getResource("/images/icon/icon16x16.png"));
	}
	
	/**
	 * Returns the ID of the configuration type. The ID is used to store run configuration settings in a project or workspace file and
	 * must not change between plugin versions.
	 *
	 * @return the configuration type ID.
	 */
	@NotNull
	@Override
	public String getId()
	{
		return VDOC_RUN_CONFIGURATION_ID;
	}
	
	/**
	 * Returns the configuration factories used by this configuration type. Normally each configuration type provides just a single factory.
	 * You can return multiple factories if your configurations can be created in multiple variants (for example, local and remote for an
	 * application server).
	 *
	 * @return the run configuration factories.
	 */
	@Override
	public ConfigurationFactory[] getConfigurationFactories()
	{
		return this.factories;
	}
}
