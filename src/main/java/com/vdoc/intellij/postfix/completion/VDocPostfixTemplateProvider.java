package com.vdoc.intellij.postfix.completion;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.util.containers.ContainerUtil;
import com.vdoc.intellij.postfix.completion.template.IsEmptyCheckTemplate;
import com.vdoc.intellij.postfix.completion.template.IsNotEmptyCheckTemplate;
import com.vdoc.intellij.postfix.completion.template.ProtocolUriTemplate;
import com.vdoc.intellij.postfix.completion.template.TransactionTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by famaridon on 07/07/2016.
 */
public class VDocPostfixTemplateProvider extends JavaPostfixTemplateProvider {
	private final Set<PostfixTemplate> templates;
	
	public VDocPostfixTemplateProvider() {
		this.templates = ContainerUtil.newHashSet(new ProtocolUriTemplate(this), new TransactionTemplate(this), new IsEmptyCheckTemplate(), new IsNotEmptyCheckTemplate());
	}
	
	@NotNull
	@Override
	public Set<PostfixTemplate> getTemplates() {
		return this.templates;
	}
	
	
}
