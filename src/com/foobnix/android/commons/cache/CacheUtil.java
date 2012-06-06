/**
 * 
 */
package com.foobnix.android.commons.cache;

import java.util.Iterator;
import java.util.Map;

import com.foobnix.android.commons.util.LOG;

/**
 * @author iivanenko
 * 
 */
public class CacheUtil {
	public static Map<String, ValueTime> cleanDeadElements(Map<String, ValueTime> map) {
		Iterator<String> iterator = map.keySet().iterator();
		long curTime = System.currentTimeMillis();
		while (iterator.hasNext()) {
			String key = iterator.next();
			ValueTime keyValue = map.get(key);
			if (curTime > keyValue.getDeadTime()) {
				LOG.d("Current, keyTime", curTime, keyValue.getDeadTime());
				iterator.remove();
			}
		}
		return map;
	}
}
