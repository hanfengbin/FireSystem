package com.fire.util;

import java.sql.SQLException;
import java.util.Map.Entry;

import com.fire.dao.NodeOrHostUpdateDao;

public class UpdateNodeOrHostState implements Runnable {
	private NodeOrHostUpdateDao updateStateDao = new NodeOrHostUpdateDao();

	@Override
	public void run() {
		// System.err.println("定时更新节点、主机开始");
		String[] nodeUpdateSql = new String[2];
		String[] hostUpdateSql = new String[2];
		StringBuffer nodeNormalBuffer = new StringBuffer("('0',");
		StringBuffer nodeFaultBuffer = new StringBuffer("('0',");
		for (Entry<String, Integer> entry : CacheDataBase.subSystemNodeState.entrySet()) {
			if (entry.getValue() == 1) {
				nodeFaultBuffer.append("'" + entry.getKey()).append("'").append(",");
			} else {
				nodeNormalBuffer.append("'" + entry.getKey()).append("'").append(",");
			}
		}
		nodeNormalBuffer.deleteCharAt(nodeNormalBuffer.length() - 1);
		nodeNormalBuffer.append(")");
		nodeUpdateSql[1] = nodeNormalBuffer.toString();
		nodeFaultBuffer.deleteCharAt(nodeFaultBuffer.length() - 1);
		nodeFaultBuffer.append(")");
		nodeUpdateSql[0] = nodeFaultBuffer.toString();
		StringBuffer hostFaultBuffer = new StringBuffer("('0',");
		StringBuffer hostNormalBuffer = new StringBuffer("('0',");
		for (Entry<String, Integer> entry : CacheDataBase.subSystemHostState.entrySet()) {
			if (entry.getValue() == 1) {
				hostFaultBuffer.append("'" + entry.getKey()).append("'").append(",");
			} else {
				hostNormalBuffer.append("'" + entry.getKey()).append("'").append(",");
			}
		}
		hostFaultBuffer.deleteCharAt(hostFaultBuffer.length() - 1);
		hostFaultBuffer.append(")");
		hostUpdateSql[0] = hostFaultBuffer.toString();
		hostNormalBuffer.deleteCharAt(hostNormalBuffer.length() - 1);
		hostNormalBuffer.append(")");
		hostUpdateSql[1] = hostNormalBuffer.toString();
		try {
			updateStateDao.updateNodeState(nodeUpdateSql);
			updateStateDao.updateHostState(hostUpdateSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
