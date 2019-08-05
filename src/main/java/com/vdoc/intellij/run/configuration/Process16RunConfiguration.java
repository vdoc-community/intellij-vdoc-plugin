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
import com.vdoc.intellij.run.ui.Process16Configurable;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by famaridon on 11/05/17.
 */
public class Process16RunConfiguration extends ApplicationConfiguration {
	private final Project project;
	private final Process16Configurable configurable;
	private Path vdocHome;
	private String xmx;
	private Boolean useDCEVM;
	private String xms;
	
	public Process16RunConfiguration(Project project, ConfigurationFactory configurationFactory) {
		super("Process16+", project, configurationFactory);
		this.project = project;
		this.configurable = new Process16Configurable();
	}
	
	@Override
	public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {
		
		setMainClassName("org.jboss.modules.Main");
		Path jar = vdocHome.resolve("wildfly/jboss-modules.jar");
		Path modulePath = vdocHome.resolve("wildfly/modules");
		Path wildflyPath = vdocHome.resolve("wildfly");
		
		StringBuilder parameterBuilder = new StringBuilder();
		parameterBuilder.append("-classpath \"");
		parameterBuilder.append(jar.toString());
		parameterBuilder.append("\" -server -Xmx");
		parameterBuilder.append(xmx);
		parameterBuilder.append(" -Xms");
		parameterBuilder.append(xms);
		parameterBuilder.append(" -Djava.net.preferIPv4Stack=true -Djboss.modules.system.pkgs=org.jboss.byteman");
		if (useDCEVM != null && useDCEVM) {
			parameterBuilder.append(" -XXaltjvm=dcevm");
		}
		setVMParameters(parameterBuilder.toString());
		setProgramParameters("-mp \"" + modulePath.toString() + "\" org.jboss.as.standalone \"-Djboss.home.dir=" + wildflyPath.toString() + "\" -c standalone-process.xml -b 0.0.0.0");
		setWorkingDirectory(vdocHome.toString());
		Map<String, String> envs = this.getEnvs();
		envs.put("JBOSS_HOME", wildflyPath.toString());
		setEnvs(envs);
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
		Element xmx = element.getChild("xmx");
		if (xmx != null && StringUtils.isNotEmpty(xmx.getText())) {
			this.xmx = xmx.getText();
		}
		Element xms = element.getChild("xms");
		if (xms != null && StringUtils.isNotEmpty(xms.getText())) {
			this.xms = xms.getText();
		}
		Element useDCEVM = element.getChild("useDCEVM");
		if (useDCEVM != null && StringUtils.isNotEmpty(useDCEVM.getText())) {
			this.useDCEVM = Boolean.parseBoolean(useDCEVM.getText());
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
		if (this.xmx != null) {
			Element xmx = new Element("xmx");
			xmx.setText(this.xmx);
			element.addContent(xmx);
		}
		if (this.xms != null) {
			Element xms = new Element("xms");
			xms.setText(this.xms);
			element.addContent(xms);
		}
		if (this.useDCEVM != null) {
			Element useDCEVM = new Element("useDCEVM");
			useDCEVM.setText(this.useDCEVM.toString());
			element.addContent(useDCEVM);
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
	
	/**
	 * get {@link Process16RunConfiguration#xmx} property
	 *
	 * @return get the xmx property
	 **/
	public String getXmx() {
		return xmx;
	}
	
	/**
	 * set {@link Process16RunConfiguration#xmx} property
	 *
	 * @param xmx set the xmx property
	 **/
	public void setXmx(String xmx) {
		this.xmx = xmx;
	}
	
	/**
	 * get {@link Process16RunConfiguration#xms} property
	 *
	 * @return get the xms property
	 **/
	public String getXms() {
		return xms;
	}
	
	/**
	 * set {@link Process16RunConfiguration#xms} property
	 *
	 * @param xms set the xms property
	 **/
	public void setXms(String xms) {
		this.xms = xms;
	}
	
	/**
	 * get {@link Process16RunConfiguration#useDCEVM} property
	 *
	 * @return get the useDCEVM property
	 **/
	public Boolean isUseDCEVM() {
		return useDCEVM;
	}
	
	/**
	 * set {@link Process16RunConfiguration#useDCEVM} property
	 *
	 * @param useDCEVM set the useDCEVM property
	 **/
	public Process16RunConfiguration setUseDCEVM(Boolean useDCEVM) {
		this.useDCEVM = useDCEVM;
		return this;
	}
}
