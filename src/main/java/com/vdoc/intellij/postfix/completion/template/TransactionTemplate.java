package com.vdoc.intellij.postfix.completion.template;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateEditingAdapter;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateExpressionSelector;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by wtoscer on 03/08/2016.
 */
public class TransactionTemplate extends VDocPostfixTemplateWithExpressionSelector {
	public TransactionTemplate() {
		this("transaction");
	}
	
	public TransactionTemplate(@NotNull String alias) {
		super(alias, "manage transaction", JavaPostfixTemplatesUtils.selectorAllExpressionsWithCurrentOffset(IS_MODULE));
	}
	
	protected TransactionTemplate(@NotNull String name, @NotNull String key, @NotNull String example, @NotNull PostfixTemplateExpressionSelector selector) {
		super(name, key, example, selector);
	}
	
	protected TransactionTemplate(@NotNull String name, @NotNull String example, @NotNull PostfixTemplateExpressionSelector selector) {
		super(name, example, selector);
	}
	
	@Override
	protected void expandForChooseExpression(@NotNull PsiElement expression, @NotNull Editor editor) {
		Project project = expression.getProject();
		Document document = editor.getDocument();
		document.deleteString(expression.getTextRange().getStartOffset(), expression.getTextRange().getEndOffset());
		final TemplateManager manager = TemplateManager.getInstance(project);
		
		String templateString = "$expr$.beginTransaction();try{\n$END$\n$expr$.commitTransaction();}catch(com.axemble.vdoc.sdk.exceptions.ModuleException e){LOG.error(e);}finally{if ($expr$.isTransactionActive()){$expr$.rollbackTransaction();}}";
		
		Template template = this.createTemplate(project, manager, templateString);
		
		//		if (shouldAddExpressionToContext()) {
		this.addExprVariable(expression, template);
		//		}
		
		//		setVariables(template, expression);
		
		manager.startTemplate(editor, template, new TemplateEditingAdapter() {
			@Override
			public void templateFinished(@NotNull Template template, boolean brokenOff) {
				//				onTemplateFinished(manager, editor, template);
				System.out.println("template = [" + template + "], brokenOff = [" + brokenOff + "]");
			}
		});
	}
}
