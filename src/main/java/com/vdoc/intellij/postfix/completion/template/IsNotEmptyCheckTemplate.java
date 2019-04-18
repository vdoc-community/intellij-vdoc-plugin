package com.vdoc.intellij.postfix.completion.template;

import org.jetbrains.annotations.NotNull;

/**
 * Created by wtoscer on 12/05/2017.
 */
public class IsNotEmptyCheckTemplate extends IsEmptyCheckTemplate {
	
	public IsNotEmptyCheckTemplate() {
		super("nempty", "if (!expr.isEmpty())");
	}
	
	@NotNull
	@Override
	protected String getHead() {
		return "!";
	}
}
