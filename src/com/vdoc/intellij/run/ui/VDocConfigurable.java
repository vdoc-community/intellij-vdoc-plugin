package com.vdoc.intellij.run.ui;

import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.vdoc.intellij.run.configuration.VDoc14RunConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by famaridon on 12/05/17.
 */
public class VDocConfigurable extends SettingsEditor<VDoc14RunConfiguration>
{
	
	@Override
	protected void resetEditorFrom(@NotNull VDoc14RunConfiguration s)
	{
	}
	
	@Override
	protected void applyEditorTo(@NotNull VDoc14RunConfiguration s) throws ConfigurationException
	{
	}
	
	@NotNull
	@Override
	protected JComponent createEditor()
	{
		return new JPanel();
	}
}
