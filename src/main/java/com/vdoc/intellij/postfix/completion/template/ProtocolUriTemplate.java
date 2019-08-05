package com.vdoc.intellij.postfix.completion.template;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateEditingAdapter;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.MacroCallNode;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.codeInsight.template.macro.ExpectedTypeMacro;
import com.intellij.codeInsight.template.macro.SubtypesMacro;
import com.intellij.codeInsight.template.macro.SuggestVariableNameMacro;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateExpressionSelector;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.vdoc.intellij.postfix.completion.template.macro.ModuleMacro;
import org.jetbrains.annotations.NotNull;

/**
 * Created by wtoscer on 03/08/2016.
 */
public class ProtocolUriTemplate extends VDocPostfixTemplateWithExpressionSelector
{
		public ProtocolUriTemplate()
		{
				this("uri");
		}

		public ProtocolUriTemplate(@NotNull String alias)
		{
				super(alias, "get element by protocol uri", JavaPostfixTemplatesUtils.selectorAllExpressionsWithCurrentOffset(IS_STRING));
		}

		protected ProtocolUriTemplate(@NotNull String name, @NotNull String key, @NotNull String example, @NotNull PostfixTemplateExpressionSelector selector)
		{
				super(name, key, example, selector);
		}

		protected ProtocolUriTemplate(@NotNull String name, @NotNull String example, @NotNull PostfixTemplateExpressionSelector selector)
		{
				super(name, example, selector);
		}

		@Override
		protected void expandForChooseExpression(@NotNull PsiElement expression, @NotNull Editor editor)
		{
				Project project = expression.getProject();
				Document document = editor.getDocument();
				document.deleteString(expression.getTextRange().getStartOffset(), expression.getTextRange().getEndOffset());
				final TemplateManager manager = TemplateManager.getInstance(project);

				String templateString = "$type$ $name$ = ($type$)$module$.getElementByProtocolURI($expr$);$END$";

				Template template = createTemplate(project, manager, templateString);

				//		if (shouldAddExpressionToContext()) {
				addExprVariable(expression, template);
				MacroCallNode type = new MacroCallNode(new ExpectedTypeMacro());
				type.addParameter(new TextExpression("com.axemble.vdoc.sdk.interfaces.IProtocolSupport"));
				template.addVariable("type", type, type, true);
				MacroCallNode name = new MacroCallNode(new SuggestVariableNameMacro());
				template.addVariable("name", name, name, true);
				MacroCallNode module = new MacroCallNode(new ModuleMacro());
				template.addVariable("module", module, module, true);
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
}
