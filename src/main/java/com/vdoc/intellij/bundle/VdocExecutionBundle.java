package com.vdoc.intellij.bundle;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/**
 * Created by wtoscer on 30/05/2017.
 */
public class VdocExecutionBundle extends AbstractBundle {
	
	public static final String PATH_TO_BUNDLE = "messages.execution";
	private static final AbstractBundle ourInstance = new VdocExecutionBundle();
	
	protected VdocExecutionBundle() {
		super(PATH_TO_BUNDLE);
	}
	
	public static String message(@NotNull @PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key, @NotNull Object... params) {
		return ourInstance.getMessage(key, params);
	}
}
