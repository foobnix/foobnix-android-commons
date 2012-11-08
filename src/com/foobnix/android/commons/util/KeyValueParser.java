package com.foobnix.android.commons.util;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;

public class KeyValueParser {

	private final String line;
	private final String divToken;
	private final String eqToken;
	private Map<String, String> map = new HashMap<String, String>();

	// user_uid=224620; pass=e10adc3949ba59abbe56e057f20f883e;
	public KeyValueParser(String line, String divToken, String eqToken) {
		this.line = line;
		this.divToken = divToken;
		this.eqToken = eqToken;
		proccess();
	}

	public void proccess() {
		if (Strings.isNullOrEmpty(line) || Strings.isNullOrEmpty(divToken) || Strings.isNullOrEmpty(eqToken)) {
			return;
		}
		for (String div : line.split(divToken)) {
			if (Strings.isNullOrEmpty(div)) {
				continue;
			}
			String[] split = div.split(eqToken);
			if (split.length != 2) {
				continue;
			}
			String key = split[0];
			String value = split[1];
			if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(value)) {
				continue;
			}
			map.put(key.trim(), value.trim());
		}

	}

	public String getValue(String key) {
		return map.get(key);
	}
}
