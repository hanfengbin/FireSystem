package com.fire.service;

import java.sql.SQLException;
import java.util.Map;

import com.fire.dao.FireOrderProcessDao;
import com.fire.util.CacheDataBase;

public class FaultOrderProcessService {
	public String receiveOrderProcess(Map<String, String[]> map) {
		String faultId = map.get("faultId")[0];
		String dealPerson = map.get("dealPerson")[0];
		FireOrderProcessDao fopDao = new FireOrderProcessDao();
		try {
			fopDao.receiveOrderProcessMethod(faultId, dealPerson);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";

	}

	public String completeOrderProcess(Map<String, String[]> map) {
		String faultId = map.get("faultId")[0];
		String faultCode = map.get("faultCode")[0];
		FireOrderProcessDao fopDao = new FireOrderProcessDao();
		try {
			fopDao.completeOrderProcessMethod(faultId);
			if (faultCode.contains("h")) {
				int index = faultCode.indexOf("h");
				String buildId = faultCode.substring(index + 2, index + 6);
				CacheDataBase.hostFaultData.get(buildId).remove(faultCode);
			} else if (faultCode.contains("w")) {
				int index = faultCode.indexOf("w");
				String buildId = faultCode.substring(index + 2, index + 6);
				CacheDataBase.warningParam.get(buildId).remove(faultCode);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
}
