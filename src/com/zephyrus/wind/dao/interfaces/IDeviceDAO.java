package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.reports.MostProfitableRouterReport;
import com.zephyrus.wind.reports.RouterUtil;
																									// REVIEW: documentation expected
public interface IDeviceDAO extends IDAO<Device> {

	ArrayList<RouterUtil> getRouterUtil() throws Exception;

}
