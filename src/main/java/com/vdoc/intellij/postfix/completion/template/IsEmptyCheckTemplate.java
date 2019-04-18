package com.vdoc.intellij.postfix.completion.template;

import com.intellij.codeInsight.generation.surroundWith.JavaWithIfExpressionSurrounder;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateExpressionSelector;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplatePsiInfo;
import com.intellij.codeInsight.template.postfix.templates.SurroundPostfixTemplateBase;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.lang.surroundWith.Surrounder;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.annotations.NotNull;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.JAVA_PSI_INFO;

/**
 * Created by wtoscer on 12/05/2017.
 */
public class IsEmptyCheckTemplate extends SurroundPostfixTemplateBase {
	
	public static final Condition<PsiElement> IS_COLLECTION = new Condition<PsiElement>()
	{
		@Override
		public boolean value(PsiElement element)
		{
			return InheritanceUtil.isInheritor(((PsiExpression)element).getType(), "java.util.Collection");
		}
	};
	
	public IsEmptyCheckTemplate() {
		this("empty", "if (expr.isEmpty())");
	}
	
	public IsEmptyCheckTemplate(@NotNull String name, @NotNull String descr) {
		super(name, descr, JAVA_PSI_INFO, JavaPostfixTemplatesUtils.selectorAllExpressionsWithCurrentOffset(IS_COLLECTION));
	}
	
	@NotNull
	@Override
	protected String getTail() {
		return ".isEmpty()";
	}
	
	@NotNull
	@Override
	protected Surrounder getSurrounder()  {
		return new JavaWithIfExpressionSurrounder();
	}
}
