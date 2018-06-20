package com.fire.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fire.mina.MinaProcessServer;
import com.fire.netty.NettyServer;
import com.fire.test.CreateCurrentData;
import com.fire.test.CreateFireDoorData;
import com.fire.test.CreateGasData;
import com.fire.test.CreatePowerData;
import com.fire.util.CacheDataBase;
import com.fire.util.CurrentReceiveData;
import com.fire.util.FireDoorReceiveData;
import com.fire.util.GasReceiveData;
import com.fire.util.PowerReceiveData;

public class InitializeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InitializeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		CacheDataBase.log.info("system init");
		try {
			CacheDataBase.main(null);
			// CreateCurrentData.main(null);
			// CreatePowerData.main(null);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CurrentReceiveData.main(null);
		PowerReceiveData.main(null);
		GasReceiveData.main(null);
		FireDoorReceiveData.main(null);
		try {
			MinaProcessServer.init();
			CreateCurrentData.main(null);
			CreateFireDoorData.main(null);
			CreateGasData.main(null);
			CreatePowerData.main(null);
			CacheDataBase.service.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					NettyServer.init();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
