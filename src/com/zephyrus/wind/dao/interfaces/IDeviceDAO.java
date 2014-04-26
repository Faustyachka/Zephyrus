package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.reports.MostProfitableRouter;
import com.zephyrus.wind.reports.RouterUtil;
																									// REVIEW: documentation expected
public interface IDeviceDAO extends IDAO<Device> {

	ArrayList<MostProfitableRouter> getProfitRouter() throws Exception;

	ArrayList<RouterUtil> getRouterUtil() throws Exception;

}
