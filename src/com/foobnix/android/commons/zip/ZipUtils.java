package com.foobnix.android.commons.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.foobnix.android.commons.util.LOG;

public class ZipUtils {

	@SuppressWarnings("unchecked")
	public static <T> T decompress(byte[] bytes, Class<T> clazz) {
		try {
			ByteArrayInputStream input = new ByteArrayInputStream(bytes);
			GZIPInputStream gs = new GZIPInputStream(input);
			ObjectInputStream ois = new ObjectInputStream(gs);
			T result = (T) ois.readObject();
			ois.close();
			input.close();
			return result;
		} catch (Exception e) {
			LOG.e(e);
		}
		return null;

	}

	public static byte[] compress(Serializable object) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream gz = new GZIPOutputStream(out);
			ObjectOutputStream oos = new ObjectOutputStream(gz);
			oos.writeObject(object);
			oos.flush();
			oos.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			LOG.e(e);
		}
		return null;
	}

}
