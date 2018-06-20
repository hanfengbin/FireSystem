package com.fire.controller;

import org.junit.Test;

import com.fire.service.TableItemService;

public class ControllerTest {

	@Test
	public void test() {
		TableItemService tis=new TableItemService();
		System.out.println(tis.getTableItemJsonObject("0"));
	}

}
