package com.foobnix.android.commons.xml;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.foobnix.android.commons.util.LOG;

public class XmlUtils {

	public static <T> T toModel(String xml, Class<T> clazz) {
		Serializer serializer = new Persister();
		try {
			return (T) serializer.read(clazz, xml, false);
		} catch (Exception e) {
			LOG.e(e);
			try {
				return clazz.newInstance();
			} catch (Exception e1) {
				LOG.e(e1);
			}
		}
		return null;
	}

}
