/**
 * 
 */
package com.foobnix.android.commons.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.foobnix.android.commons.util.LOG;

/**
 * @author iivanenko
 * 
 */
public class MemCache {

	public static final int SEC = 1000;
	public static final int MIN = SEC * 60;
	public static final int HOUR = MIN * 60;
	public static final int DAY = HOUR * 24;

	private static Map<String, ValueTime> cacheMap = Collections.synchronizedMap(new HashMap<String, ValueTime>());

	public <T> T getObject(String key, boolean online) {
		T result = null;
		if (cacheMap.containsKey(key)) {
			ValueTime<T> valueTime = cacheMap.get(key);
			// offline
			if (!online) {
				LOG.d("offline");
				return valueTime.getObject();
			}
			if (valueTime != null && valueTime.getDeadTime() > System.currentTimeMillis()) {
				result = valueTime.getObject();
				LOG.d("get object from cache", key);
			} else {
				LOG.d("Remove expired key", key);
				cacheMap.remove(key);
			}
		}
		return result;
	}

	public <T> T getOrUpdate(String key, long leavTime, CacheTask<T> task) {
		T result = getObject(key, false);
		if (result == null) {
			result = task.run();
			putObject(key, result, leavTime);
		}
		return result;

	}

	public <T> T getOrUpdate(String key, long leavTime, CacheTask<T> task, boolean online) {
		T result = getObject(key, online);

		if (result == null) {
			result = task.run();
			putObject(key, result, leavTime);
		}

		return result;

	}

	public void remove(String key) {
		cacheMap.remove(key);
	}

	public void putObject(String key, Object object, long leavTime) {
		if (leavTime < 0) {
			throw new IllegalArgumentException("Live time should be > 0");
		}
		LOG.d("Put key value to cache", key, leavTime);
		cacheMap.put(key, new ValueTime(object, leavTime));
	}

	public <T> T putTaskObject(String key, long leavTime, CacheTask<T> task) {
		if (leavTime < 0) {
			throw new IllegalArgumentException("Live time should be > 0");
		}
		LOG.d("Put key value to cache", key, leavTime);
		T result = task.run();
		cacheMap.put(key, new ValueTime(result, leavTime));
		return result;
	}

	public void putObject(String key, Object object) {
		LOG.d("Put key value to cache", key);
		cacheMap.put(key, new ValueTime(object, Long.MAX_VALUE - System.currentTimeMillis() - 1000));
	}

	public void clear() {
		cacheMap.clear();
	}

	public void setCacheMap(Map<String, ValueTime> currentMap) {
		cacheMap = new ConcurrentHashMap<String, ValueTime>(currentMap);
	}

	public Map<String, ValueTime> getCacheMap() {
		return cacheMap;
	}

}
