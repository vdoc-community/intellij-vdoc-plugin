package com.vdoc.intellij.postfix.completion.template;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateExpressionSelector;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateWithExpressionSelector;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by wtoscer on 03/08/2016.
 */
public abstract class VDocPostfixTemplateWithExpressionSelector extends PostfixTemplateWithExpressionSelector
{
		public static final Condition<PsiElement> IS_MODULE = new Condition<PsiElement>()
		{
				@Override
				public boolean value(PsiElement element)
				{
						return InheritanceUtil.isInheritor(((PsiExpression)element).getType(), "com.axemble.vdoc.sdk.modules.IModule");
				}
		};
		public static final Condition<PsiElement> IS_STRING = new Condition<PsiElement>()
		{
				@Override
				public boolean value(PsiElement element)
				{
						return InheritanceUtil.isInheritor(((PsiExpression)element).getType(), "java.lang.String");
				}
		};
	
	protected VDocPostfixTemplateWithExpressionSelector(@Nullable String id, @NotNull String name, @NotNull String example, @NotNull PostfixTemplateExpressionSelector selector, @Nullable PostfixTemplateProvider provider) {
		super(id, name, example, selector, provider);
	}
	
	protected VDocPostfixTemplateWithExpressionSelector(@Nullable String id, @NotNull String name, @NotNull String key, @NotNull String example, @NotNull PostfixTemplateExpressionSelector selector, @Nullable PostfixTemplateProvider provider) {
		super(id, name, key, example, selector, provider);
	}
	
	/**
		 * Create a new instance of a code template for the current postfix template.
		 *
		 * @param project        The current project
		 * @param manager        The template manager
		 * @param templateString The template string
		 */
		protected Template createTemplate(Project project, TemplateManager manager, String templateString)
		{
				Template template = manager.createTemplate("", "", templateString);
				template.setToReformat(true);
				template.setValue(Template.Property.USE_STATIC_IMPORT_IF_POSSIBLE, false);
				return template;
		}

		protected void addExprVariable(@NotNull PsiElement expr, Template template)
		{
				template.addVariable("expr", new TextExpression(expr.getText()), false);
		}
}
