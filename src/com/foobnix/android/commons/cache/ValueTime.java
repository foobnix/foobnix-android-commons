package com.foobnix.android.commons.cache;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ValueTime<T> implements Serializable {

    private T value;
    private long deadTime;

    public ValueTime(T value, long liveTime) {
        if (liveTime < 0) {
            throw new IllegalArgumentException("live time should be more then 0");
        }
        this.value = value;
        this.deadTime = liveTime + System.currentTimeMillis();
    }

    public T getObject() {
        return value;
    }

    public void setObject(T object) {
        this.value = object;
    }

    public long getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(long time) {
        this.deadTime = time;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return hashCode() == o.hashCode();
    }
}