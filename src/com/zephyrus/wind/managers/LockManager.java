package com.zephyrus.wind.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {
	
	private static final Map<Object, Lock> lockMap = new HashMap<Object, Lock>();
	
	public static void addLock(Object key) {
		lockMap.put(key, new ReentrantLock());
	}
	
	public static void removeLock(Object key) {
		lockMap.remove(key);
	}
	
	public static Lock getLock(Object key) {
		return lockMap.get(key);
	}
}
