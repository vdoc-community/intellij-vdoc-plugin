/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vdoc.intellij.postfix.completion.template.macro;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.template.*;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

public class ModuleMacro extends Macro {
	
	private static String[] getNames(final ExpressionContext context) {
		//we need to improve this ugly thing
		LinkedList<String> namesList = new LinkedList<>();
		namesList.add("getPortalModule()");
		namesList.add("getWorkflowModule()");
		namesList.add("getLibraryModule()");
		namesList.add("getProjectModule()");
		namesList.add("portalModule");
		namesList.add("workflowModule");
		namesList.add("libraryModule");
		namesList.add("projectModule");
		
		return ArrayUtil.toStringArray(namesList);
	}
	
	@Override
	public String getName() {
		return "module";
	}
	
	@Override
	public String getPresentableName() {
		return "module";
	}
	
	@Override
	@NotNull
	public String getDefaultValue() {
		return "a";
	}
	
	@Override
	public Result calculateResult(@NotNull Expression[] params, ExpressionContext context) {
		String[] names = getNames(context);
		if (names.length == 0) {
			return null;
		}
		return new TextResult(names[0]);
	}
	
	@Nullable
	@Override
	public Result calculateQuickResult(@NotNull Expression[] params, ExpressionContext context) {
		return this.calculateResult(params, context);
	}
	
	@Override
	public LookupElement[] calculateLookupItems(@NotNull Expression[] params, final ExpressionContext context) {
		String[] names = getNames(context);
		if (names.length < 2) {
			return null;
		}
		LookupElement[] items = new LookupElement[names.length];
		for (int i = 0; i < names.length; i++) {
			items[i] = LookupElementBuilder.create(names[i]);
		}
		return items;
	}
	
	@Override
	public boolean isAcceptableInContext(TemplateContextType context) {
		return context instanceof JavaCodeContextType;
	}
	
}
