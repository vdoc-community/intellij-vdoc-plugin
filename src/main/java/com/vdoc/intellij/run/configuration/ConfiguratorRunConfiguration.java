package com.vdoc.intellij.run.configuration;

import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RuntimeConfigurationError;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.impl.CheckableRunConfigurationEditor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.vdoc.intellij.bundle.VdocExecutionBundle;
import com.vdoc.intellij.run.ui.ConfiguratorConfigurable;
import com.vdoc.intellij.run.ui.VDocConfigurable;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by famaridon on 11/05/17.
 */
public class ConfiguratorRunConfiguration extends ApplicationConfiguration {
	private final ConfiguratorConfigurable configurable;
	private Path vdocHome;
	private Boolean apply;
	
	public ConfiguratorRunConfiguration(Project project, ConfigurationFactory configurationFactory) {
		super("Configurator", project, configurationFactory);
		this.configurable = new ConfiguratorConfigurable();
	}
	
	@Override
	public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {
		
		this.setMainClassName("com.axemble.vdoc.configurator.Configurator");
		Path jar = this.getVdocHome().resolve("configurator/Configurator-suite.jar");
		StringBuilder parameterBuilder = new StringBuilder();
		parameterBuilder.append("-classpath \"");
		parameterBuilder.append(jar.toString());
		setVMParameters(parameterBuilder.toString());
		if (this.isApply() != null && this.isApply()) {
			this.setProgramParameters("console");
		}
		setWorkingDirectory(this.getVdocHome().toString() + "/configurator");
		return super.getState(executor, env);
	}
	
	/**
	 * Returns the UI control for editing the run configuration settings. If additional control over validation is required, the object
	 * returned from this method may also implement {@link CheckableRunConfigurationEditor}. The returned object
	 * can also implement {@link SettingsEditorGroup} if the settings it provides need to be displayed in
	 * multiple tabs.
	 *
	 * @return the settings editor component.
	 */
	@NotNull
	@Override
	public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
		SettingsEditorGroup<ConfiguratorRunConfiguration> group = new SettingsEditorGroup<>();
		group.addEditor(ExecutionBundle.message("run.configuration.configuration.tab.title"), this.configurable);
		return group;
	}
	
	/**
	 * Checks whether the run configuration settings are valid.
	 * Note that this check may be invoked on every change (i.e. after each character typed in an input field).
	 *
	 * @throws RuntimeConfigurationException if the configuration settings contain a non-fatal problem which the user should be warned about
	 *                                       but the execution should still be allowed.
	 * @throws RuntimeConfigurationError     if the configuration settings contain a fatal problem which makes it impossible
	 *                                       to execute the run configuration.
	 */
	@Override
	public void checkConfiguration() throws RuntimeConfigurationException {
		if (this.getVdocHome() != null) {
			Path configurator = this.getVdocHome().resolve("configurator");
			if (!configurator.toFile().exists()) {
				throw new RuntimeConfigurationError(VdocExecutionBundle.message("run.error.vdoc.home.no.configurator", this.getVdocHome()));
			}
		}
	}
	
	@Override
	public void readExternal(Element element) throws InvalidDataException {
		super.readExternal(element);
		Element home = element.getChild("vdocHome");
		if (home != null && StringUtils.isNotEmpty(home.getText())) {
			this.setVdocHome(Paths.get(home.getText()));
		}
		Element apply = element.getChild("apply");
		if (apply != null && StringUtils.isNotEmpty(apply.getText())) {
			this.setApply(Boolean.parseBoolean(apply.getText()));
		}
	}
	
	@Override
	public void writeExternal(Element element) throws WriteExternalException {
		super.writeExternal(element);
		if (this.getVdocHome() != null) {
			Element home = new Element("vdocHome");
			home.setText(this.getVdocHome().toString());
			element.addContent(home);
		}
		if (this.isApply() != null) {
			Element apply = new Element("apply");
			apply.setText(this.isApply().toString());
			element.addContent(apply);
		}
	}
	
	/**
	 * get {@link ConfiguratorRunConfiguration#vdocHome} property
	 *
	 * @return get the vdocHome property
	 **/
	public Path getVdocHome() {
		return this.vdocHome;
	}
	
	/**
	 * set {@link ConfiguratorRunConfiguration#vdocHome} property
	 *
	 * @param vdocHome set the vdocHome property
	 **/
	public void setVdocHome(Path vdocHome) {
		this.vdocHome = vdocHome;
	}
	
	/**
	 * get {@link ConfiguratorRunConfiguration#apply} property
	 *
	 * @return get the apply property
	 **/
	public Boolean isApply() {
		return this.apply;
	}
	
	/**
	 * set {@link ConfiguratorRunConfiguration#apply} property
	 *
	 * @param apply set the apply property
	 **/
	public void setApply(Boolean apply) {
		this.apply = apply;
	}
}
