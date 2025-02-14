package io.cubyz.utils.language;

import java.util.HashMap;

import io.cubyz.utils.log.Log;

/**
 * A translation of all cubyz texts to a certain language.
 */

public class Language {

	private HashMap<String, String> keyValues = new HashMap<>();
	private String locale;
	
	public Language(String locale) {
		this.locale = locale;
	}
	
	public void add(String key, String text) {
		keyValues.put(key, text);
	}
	
	public void remove(String key) {
		keyValues.remove(key);
	}
	
	public String get(String key) {
		return keyValues.get(key);
	}
	
	public void translate(TextKey key) {
		if (keyValues.containsKey(key.getTranslateKey())) {
			key.translation = keyValues.get(key.getTranslateKey());
			return;
		}
		if(key.getTranslateKey().contains("."))
			Log.warning("Unable to translate key "+key.getTranslateKey()+" in language "+locale+".");
		key.translation = key.getTranslateKey();
	}
	
	public String getLocale() {
		return locale;
	}
	
}