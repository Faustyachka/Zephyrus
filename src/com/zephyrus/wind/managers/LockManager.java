package com.zephyrus.wind.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {
	
	private static final Map<Object, Lock> lockMap = new HashMap<Object, Lock>();
	
	public static Lock getLock(Object key) {
		if(key == null) {
			throw new IllegalArgumentException("Lock key cannot be null");
		}
		ReentrantLock lock = new ReentrantLock();
		lockMap.put(key, lock);
		return lock;
	}
	
	public static void removeLock(Object key) {
		if(key == null) {
			throw new IllegalArgumentException("Lock key cannot be null");
		}
		lockMap.remove(key);
	}
}
