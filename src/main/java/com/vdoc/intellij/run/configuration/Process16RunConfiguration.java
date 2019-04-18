package com.vdoc.intellij.run.configuration;

import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configuration.AbstractRunConfiguration;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationModule;
import com.intellij.execution.configurations.RunConfigurationWithSuppressedDefaultDebugAction;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RuntimeConfigurationError;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.impl.CheckableRunConfigurationEditor;
import com.intellij.execution.process.KillableColoredProcessHandler;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.vdoc.intellij.bundle.VdocExecutionBundle;
import com.vdoc.intellij.run.ui.Process16Configurable;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Created by famaridon on 11/05/17.
 */
public class Process16RunConfiguration extends AbstractRunConfiguration implements RunConfigurationWithSuppressedDefaultDebugAction {
	private final Project project;
	private final Process16Configurable configurable;
	private Path vdocHome;
	
	public Process16RunConfiguration(String name, RunConfigurationModule module, ConfigurationFactory configurationFactory) {
		super(name, module, configurationFactory);
		this.project = this.getProject();
		this.configurable = new Process16Configurable();
	}
	
	@Override
	public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {
		
		return new CommandLineState(env) {
			@NotNull
			@Override
			protected ProcessHandler startProcess() throws ExecutionException {
				GeneralCommandLine commandLine = new GeneralCommandLine();
				commandLine.setExePath("cmd.exe");
				commandLine.getParametersList().addParametersString("/c");
				commandLine.addParameter(vdocHome.resolve("scripts/start.bat").toString());
				commandLine.getParametersList().addParametersString(vdocHome.toString() + "/");
				commandLine.withWorkDirectory(vdocHome.toFile());
				commandLine.withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.SYSTEM);
				commandLine.withEnvironment(Process16RunConfiguration.this.getEnvs());
				OSProcessHandler osProcessHandler = new KillableColoredProcessHandler(commandLine.createProcess(), commandLine.getCommandLineString());
				ProcessTerminatedListener.attach(osProcessHandler, project);
				return osProcessHandler;
			}
		};
		
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
		SettingsEditorGroup<Process16RunConfiguration> group = new SettingsEditorGroup<>();
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
		if (vdocHome != null) {
			Path jboss = vdocHome.resolve("wildfly");
			if (!jboss.toFile().exists()) {
				throw new RuntimeConfigurationError(VdocExecutionBundle.message("run.error.vdoc.home.no.wildfly", vdocHome));
			}
		}
	}
	
	@Override
	public void readExternal(@NotNull Element element) throws InvalidDataException {
		super.readExternal(element);
		Element home = element.getChild("vdocHome");
		if (home != null && StringUtils.isNotEmpty(home.getText())) {
			this.vdocHome = Paths.get(home.getText());
		}
	}
	
	@Override
	public void writeExternal(@NotNull Element element) throws WriteExternalException {
		super.writeExternal(element);
		if (this.vdocHome != null) {
			Element home = new Element("vdocHome");
			home.setText(this.vdocHome.toString());
			element.addContent(home);
		}
	}
	
	/**
	 * get {@link Process16RunConfiguration#vdocHome} property
	 *
	 * @return get the vdocHome property
	 **/
	public Path getVdocHome() {
		return vdocHome;
	}
	
	/**
	 * set {@link Process16RunConfiguration#vdocHome} property
	 *
	 * @param vdocHome set the vdocHome property
	 **/
	public void setVdocHome(Path vdocHome) {
		this.vdocHome = vdocHome;
	}
	
	@Override
	public Collection<Module> getValidModules() {
		return this.getAllModules();
	}
}
