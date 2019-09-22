/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月9日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xuanyimao.polj.index.bean.Constants;
import com.xuanyimao.polj.index.bean.DevRepertory;
import com.xuanyimao.polj.index.bean.HandlerObject;
import com.xuanyimao.polj.index.bean.Message;

/**
 * @Description: 工具类
 * @author liuming
 */
public class ToolUtil {
	//gson对象
	private final static Gson gson=new GsonBuilder().serializeNulls().create();
	/**
	 * 返回操作成功的信息
	 * @author:liuming
	 * @param ho
	 * @param obj
	 */
	public static void successMessage(HandlerObject ho,Object obj) {
		if(ho==null || ho.getCallback()==null) return;
		if(obj instanceof Message) {
			ho.getCallback().success(gson.toJson(obj));
		}else {
			ho.getCallback().success(gson.toJson(Message.success("操作成功", obj)));
		}
	}
	/**
	 * 获取异常的详细信息
	 * @author:liuming
	 * @param e
	 * @return
	 */
	public static String getExceptionMessage(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true); 
		e.fillInStackTrace().printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
	/**
	 * 获取转换后的字符串，将 {path}、{appPath}、{appId}转换为指定路径
	 * @author:liuming
	 * @param data 数据
	 * @param appId 如果需要转换{appId}或{appPath}，此值必填
	 * @return
	 */
	public static String getTransData(String data,String appId) {
		return data.replace("{path}", DevRepertory.getInstance().getPath())
				.replace("{appPath}", DevRepertory.getInstance().getAppPath()+File.separator+appId)
				.replace("{appId}", appId);
	}
	
//	/**
//	 * 获取24小时后的时间
//	 * @return
//	 */
//	public static Long getLastDayTime() {
//		try {
//			Thread.sleep(1000*3600*24);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		return System.currentTimeMillis();
//	}
//	public static String getAppPath(String appId) {
//		return Constants.APPPATH.replace("{appId}", appId);
//	}
}
