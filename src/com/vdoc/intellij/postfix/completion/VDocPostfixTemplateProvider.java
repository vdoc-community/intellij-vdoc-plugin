package com.vdoc.intellij.postfix.completion;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.util.containers.ContainerUtil;
import com.vdoc.intellij.postfix.completion.template.ModuleTemplate;
import com.vdoc.intellij.postfix.completion.template.ProtocolUriTemplate;
import com.vdoc.intellij.postfix.completion.template.TransactionTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by famaridon on 07/07/2016.
 */
public class VDocPostfixTemplateProvider extends JavaPostfixTemplateProvider
{
		private final Set<PostfixTemplate> templates;

		public VDocPostfixTemplateProvider()
		{
				templates = ContainerUtil.<PostfixTemplate>newHashSet(new ModuleTemplate(), new ProtocolUriTemplate(), new TransactionTemplate());
		}

		@NotNull
		@Override
		public Set<PostfixTemplate> getTemplates()
		{
				return templates;
		}
}
