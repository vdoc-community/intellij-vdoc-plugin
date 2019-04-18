package com.vdoc.intellij.run.factories;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationModule;
import com.intellij.openapi.project.Project;
import com.vdoc.intellij.run.configuration.Process16RunConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * Created by famaridon on 11/05/17.
 */
public class Process16ConfigurationFactory extends ConfigurationFactory
{
	
	public Process16ConfigurationFactory(@NotNull ConfigurationType type)
	{
		super(type);
	}
	
	/**
	 * Creates a new template run configuration within the context of the specified project.
	 *
	 * @param project the project in which the run configuration will be used
	 * @return the run configuration instance.
	 */
	@NotNull
	@Override
	public RunConfiguration createTemplateConfiguration(@NotNull Project project)
	{
		return new Process16RunConfiguration(getName(), new RunConfigurationModule(project), this);
	}
	
	@NotNull
	@Override
	public String getName()
	{
		return "Process16+";
	}
}
