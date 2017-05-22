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
import com.vdoc.intellij.run.ui.VDocConfigurable;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by famaridon on 11/05/17.
 */
public class VDoc14RunConfiguration extends ApplicationConfiguration {
	private final Project project;
	private final VDocConfigurable configurable;
	private Path vdocHome;
	private String xmx;
	private String maxPermSize;
	
	public VDoc14RunConfiguration(Project project, ConfigurationFactory configurationFactory) {
		super("VDoc14+", project, configurationFactory);
		this.project = project;
		this.configurable = new VDocConfigurable();
	}
	
	@Override
	public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {
		MAIN_CLASS_NAME = "org.jboss.Main";
		VM_PARAMETERS = "-classpath \"" + vdocHome + "\\JBoss\\bin\\run.jar\" -server -Xmx" + xmx + " -XX:MaxPermSize=" + maxPermSize + "";
		PROGRAM_PARAMETERS = "-c all -b 0.0.0.0";
		WORKING_DIRECTORY = "D:\\developements\\process\\process-addon\\moovapps-workplace";
		
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
	
	@Override
	public void readExternal(Element element) throws InvalidDataException {
		super.readExternal(element);
		Element home = element.getChild("vdocHome");
		if (home != null && StringUtils.isNotEmpty(home.getText())) {
			this.vdocHome = Paths.get(home.getText());
		}
		Element xmx = element.getChild("xmx");
		if (xmx != null && StringUtils.isNotEmpty(xmx.getText())) {
			this.xmx = xmx.getText();
		}
		Element maxPermSize = element.getChild("maxPermSize");
		if (maxPermSize != null && StringUtils.isNotEmpty(maxPermSize.getText())) {
			this.maxPermSize = maxPermSize.getText();
		}
	}
	
	@Override
	public void writeExternal(Element element) throws WriteExternalException {
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
		if (this.maxPermSize != null) {
			Element maxPermSize = new Element("maxPermSize");
			maxPermSize.setText(this.maxPermSize);
			element.addContent(maxPermSize);
		}
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
	 * get {@link VDoc14RunConfiguration#xmx} property
	 *
	 * @return get the xmx property
	 **/
	public String getXmx() {
		return xmx;
	}
	
	/**
	 * set {@link VDoc14RunConfiguration#xmx} property
	 *
	 * @param xmx set the xmx property
	 **/
	public void setXmx(String xmx) {
		this.xmx = xmx;
	}
	
	/**
	 * get {@link VDoc14RunConfiguration#maxPermSize} property
	 *
	 * @return get the maxPermSize property
	 **/
	public String getMaxPermSize() {
		return maxPermSize;
	}
	
	/**
	 * set {@link VDoc14RunConfiguration#maxPermSize} property
	 *
	 * @param maxPermSize set the maxPermSize property
	 **/
	public void setMaxPermSize(String maxPermSize) {
		this.maxPermSize = maxPermSize;
	}
}
