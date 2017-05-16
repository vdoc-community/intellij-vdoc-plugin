package com.vdoc.intellij.run.configuration;

import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationBase;
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
import com.vdoc.intellij.run.ui.VDocConfigurable;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by famaridon on 11/05/17.
 */
public class VDoc14RunConfiguration extends RunConfigurationBase {
	private final Project project;
	private Path vdocHome;
	private final VDocConfigurable configurable;
	
	public VDoc14RunConfiguration(Project project, ConfigurationFactory configurationFactory) {
		super(project, configurationFactory, "VDoc14+");
		this.project = project;
		this.configurable = new VDocConfigurable();
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
		SettingsEditorGroup<VDoc14RunConfiguration> group = new SettingsEditorGroup<>();
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
	}
	
	/**
	 * get {@link VDoc14RunConfiguration#vdocHome} property
	 *
	 * @return get the vdocHome property
	 **/
	public Path getVdocHome() {
		return vdocHome;
	}
	
	/**
	 * set {@link VDoc14RunConfiguration#vdocHome} property
	 *
	 * @param vdocHome set the vdocHome property
	 **/
	public void setVdocHome(Path vdocHome) {
		this.vdocHome = vdocHome;
	}
	
	/**
	 * Prepares for executing a specific instance of the run configuration.
	 *
	 * @param executor    the execution mode selected by the user (run, debug, profile etc.)
	 * @param environment the environment object containing additional settings for executing the configuration.
	 * @return the RunProfileState describing the process which is about to be started, or null if it's impossible to start the process.
	 */
	@Nullable
	@Override
	public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
		throw new NotImplementedException();
	}
	
	@Override
	public void writeExternal(Element element) throws WriteExternalException {
		super.writeExternal(element);
		if(this.vdocHome != null) {
			Element home = new Element("vdocHome");
			home.setText(this.vdocHome.toString());
			element.addContent(home);
		}
	}
	
	@Override
	public void readExternal(Element element) throws InvalidDataException {
		super.readExternal(element);
		Element home = element.getChild("vdocHome");
		if(home != null && StringUtils.isNotEmpty(home.getText())) {
			this.vdocHome = Paths.get(home.getText());
		}
	}
}
