package com.zephyrus.wind.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class manages locks which allow to achieve continuous code execution.
 * It provides functionality of obtaining lock for specified object.
 * @author Igor Litvinenko
 */
public class LockManager {
	
	/** Map of key-objects and corresponding Locks */ 
	private static final Map<Object, Lock> lockMap = new HashMap<Object, Lock>();
	
	/**
	 * This method allows to obtain Lock for specified object.
	 * If no lock exist for given key new Lock associated with is created.
	 * @param key
	 * @return
	 */
	public static Lock getLock(Object key) {
		if(key == null) {
			throw new IllegalArgumentException("Lock key cannot be null");
		}
		
		if(!lockMap.containsKey(key)) {
			lockMap.put(key, new ReentrantLock());
		}
		
		return lockMap.get(key);
	}
	
	public static void removeLock(Object key) {
		if(key == null) {
			throw new IllegalArgumentException("Lock key cannot be null");
		}
		lockMap.remove(key);
	}
}
