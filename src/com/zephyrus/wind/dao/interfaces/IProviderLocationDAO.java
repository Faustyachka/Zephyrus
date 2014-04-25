package com.zephyrus.wind.dao.interfaces;

import java.sql.Date;
import java.util.ArrayList;

import com.zephyrus.wind.model.ProviderLocation;
import com.zephyrus.wind.reports.ProfitabilityByMonth;

public interface IProviderLocationDAO extends IDAO<ProviderLocation> {

	ArrayList<ProfitabilityByMonth> getProfitByMonth(Date month)
			throws Exception;

}
