package com.homihq.manager.support;

public final class NameUtils {

	private NameUtils() {
		throw new AssertionError("Must not instantiate utility class.");
	}

	/**
	 * Generated name prefix.
	 */
	public static final String GENERATED_NAME_PREFIX = "_genkey_";


	public static String generateName(int i) {
		return GENERATED_NAME_PREFIX + i;
	}
}
