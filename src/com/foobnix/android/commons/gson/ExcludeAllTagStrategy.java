package com.foobnix.android.commons.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public final class ExcludeAllTagStrategy implements ExclusionStrategy {
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		ExcludeServer annotation = f.getAnnotation(ExcludeServer.class);
		if (annotation == null) {
			return false;
		}
		return true;
	}
}