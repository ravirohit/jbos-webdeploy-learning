package com.learn.commonutil;

import java.util.ArrayList;
import java.util.List;

public class AppUtilForAllModue {

	public static List<String> list = new ArrayList<>();
	static{
		System.out.println("<<<<< this is static block");
		list.add("from static block");
	}
	public List<String> display(String name){
		System.out.println("common jar get called");
		list.add(name);
		return list;
	}

}
