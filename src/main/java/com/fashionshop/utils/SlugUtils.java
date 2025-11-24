package com.fashionshop.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugUtils {
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	public static String toSlug(String input) {
		if (input == null) {
			throw new IllegalArgumentException();
		}
		String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
		String slug = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]+", "");
		return NONLATIN.matcher(slug).replaceAll("").toLowerCase(Locale.ENGLISH);
	}
}
