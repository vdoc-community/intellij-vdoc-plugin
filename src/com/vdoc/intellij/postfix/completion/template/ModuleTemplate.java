package com.vdoc.intellij.postfix.completion.template;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateEditingAdapter;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateExpressionSelector;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateWithExpressionSelector;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by famaridon on 07/07/2016.
 */
public class ModuleTemplate extends PostfixTemplateWithExpressionSelector
{

	public static final Condition<PsiElement> IS_MODULE = new Condition<PsiElement>()
	{
		@Override
		public boolean value(PsiElement element)
		{
			return InheritanceUtil.isInheritor(((PsiExpression)element).getType(), "com.axemble.vdoc.sdk.modules.IModule");
		}
	};

	public ModuleTemplate()
	{
		this("module");
	}

	public ModuleTemplate(@NotNull String alias)
	{
		super(alias, "toto", JavaPostfixTemplatesUtils.selectorAllExpressionsWithCurrentOffset(IS_MODULE));
	}

	protected ModuleTemplate(@NotNull String name, @NotNull String key, @NotNull String example, @NotNull PostfixTemplateExpressionSelector selector)
	{
		super(name, key, example, selector);
	}

	protected ModuleTemplate(@NotNull String name, @NotNull String example, @NotNull PostfixTemplateExpressionSelector selector)
	{
		super(name, example, selector);
	}

	@Override
	protected void expandForChooseExpression(@NotNull PsiElement expression, @NotNull Editor editor)
	{
		Project project = expression.getProject();
		Document document = editor.getDocument();
		//document.deleteString(expression.getTextRange().getStartOffset(), expression.getTextRange().getEndOffset());
		final TemplateManager manager = TemplateManager.getInstance(project);

		String templateString = "try{}catch(com.axemble.vdoc.sdk.exceptions.ModuleException e){//todo}";

		Template template = createTemplate(project, manager, templateString);

		//		if (shouldAddExpressionToContext()) {
		addExprVariable(expression, template);
		//		}

		//		setVariables(template, expression);

		manager.startTemplate(editor, template, new TemplateEditingAdapter()
		{
			@Override
			public void templateFinished(Template template, boolean brokenOff)
			{
//				onTemplateFinished(manager, editor, template);
				System.out.println("template = [" + template + "], brokenOff = [" + brokenOff + "]");
			}
		});
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
