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
	
	/** Lock that used to manage access to lock obtaining method */
	private static final Lock accessLock = new ReentrantLock();
	
	/**
	 * This method allows to obtain Lock for specified object.
	 * If no lock exist for given key new Lock associated with is created.
	 * @param key object associated with Lock
	 * @return Lock for given key
	 */
	public static Lock getLock(Object key) {
		if(key == null) {
			throw new IllegalArgumentException("Lock key cannot be null");
		}
		
		accessLock.lock();
		
		if(!lockMap.containsKey(key)) {
			lockMap.put(key, new ReentrantLock());
		}
		
		accessLock.unlock();
		
		return lockMap.get(key);
	}
	
	/**
	 * This method removes Lock, associated with given key, from map
	 * @param key object associated with Lock
	 */
	public static void removeLock(Object key) {
		if(key == null) {
			throw new IllegalArgumentException("Lock key cannot be null");
		}
		lockMap.remove(key);
	}
}
