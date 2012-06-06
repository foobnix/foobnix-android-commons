/**
 * 
 */
package com.foobnix.android.commons.cache;

/**
 * @author iivanenko
 * 
 */
public interface Cache {

    public <T> T getObject(String key, Class<T> clazz);

    public void putObject(String key, Object object, long leavTime);

    public void putObject(String key, Object object);

    public void clear();

}
