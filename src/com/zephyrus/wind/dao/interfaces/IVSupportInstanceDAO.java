package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.VSupportInstance;

public interface IVSupportInstanceDAO extends IDAO<VSupportInstance>{
		ArrayList<VSupportInstance> getInstancesByUserId(int id) throws Exception;
}
