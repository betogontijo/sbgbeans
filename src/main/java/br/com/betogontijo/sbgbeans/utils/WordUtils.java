package br.com.betogontijo.sbgbeans.utils;

import java.text.Normalizer;

public class WordUtils {
	public static String normalize(String string) {
		if (string != null) {
			string = string.toLowerCase();
			string = Normalizer.normalize(string, Normalizer.Form.NFD);
			string = string.replaceAll("[^a-z0-9\\s]", "");
		}
		return string;
	}
}
