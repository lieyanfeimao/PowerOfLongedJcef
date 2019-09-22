/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月10日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuanyimao.polj.index.bean.AppConfig.AppType;
import com.xuanyimao.polj.index.util.ToolUtil;

/**
 * @Description: 软件配置数据仓库，储存配置文件相关信息等，软件启动时加载各个应用的配置
 * @author liuming
 */
public class DevRepertory {
	
	private final static DevRepertory devRepertory=new DevRepertory();
	
	private DevRepertory() {}
	/**
	 * 获取软件配置数据仓库实例
	 * @author:liuming
	 * @return
	 */
	public static DevRepertory getInstance() {
		return devRepertory;
	}
	/**本软件在磁盘的目录*/
	private String path;
	/**应用列表*/
	private List<AppConfig> appList;
	
//	private final static String APP_FOLDER="/app";
	
	/**
	 * 返回本软件在磁盘的目录
	 * @return 本软件在磁盘的目录
	 */
	public String getPath() {
		return path;
	}
	/***
	 * 获取应用主目录
	 * @author:liuming
	 * @return
	 */
	public String getAppPath() {
		return path+File.separator+Constants.APPFOLDER;
	}
	/**
	 * 设置 本软件在磁盘的目录
	 * @param path 本软件在磁盘的目录
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 应用列表
	 * @return 应用列表
	 */
	public List<AppConfig> getAppList() {
		return appList;
	}
	/**
	 * 设置 应用列表
	 * @param appList 应用列表
	 */
	public void setAppList(List<AppConfig> appList) {
		this.appList = appList;
	}
	/**
	 * 加载应用信息
	 * @author:liuming
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void loadAppConfig() throws FileNotFoundException, IOException {
		//读取config文件，如果不存在，创建新的config文件，并赋予默认值
		String configFilePath=this.path+File.separator+Constants.CONFIGFILE;
		File file=new File(configFilePath);
		if(!file.exists()) {
			createDefaultAppConfig(configFilePath);
		}else {//读取配置文件
			FileInputStream fis=new FileInputStream(file);
			String content=IOUtils.toString(fis,"UTF-8");
			IOUtils.closeQuietly(fis);
			if(StringUtils.isBlank(content)) {
				createDefaultAppConfig(configFilePath);
			}else {
				appList=new Gson().fromJson(content, new TypeToken<List<AppConfig>>(){}.getType());
			}
		}
//		for(AppConfig ac:appList) {
//			ac.setPath(ac.getPath()==null?"":ToolUtil.getTransData(ac.getPath(), ac.getId()));
//			ac.setIcon(ac.getIcon()==null?"":ToolUtil.getTransData(ac.getIcon(), ac.getId()));
//		}
	}
	
	/**
	 * 创建默认的配置信息
	 * @author:liuming
	 * @throws IOException 
	 */
	private void createDefaultAppConfig(String configFilePath) throws IOException {
		appList=new ArrayList<AppConfig>();
		//代码生成器（模板配置）
		AppConfig appConfig=new AppConfig("modelDeal","代码模板生成器","V1.0",1);
		appConfig.setIcon("");//图标
		appConfig.setType(AppType.html);
//		appConfig.setScannerpkg("com.xuanyimao.modeldeal.jsimpl");//扫描包
		appConfig.setPath("{appPath}/index.html");
		appConfig.setLibjar("{appPath}/lib/modeldeal.jar");
		appList.add(appConfig);
		
//		chm制作器
//		appConfig=new AppConfig("chmmake","chm制作器","V1.0",1);
//		appConfig.setIcon("");//图标
//		appConfig.setType(AppType.html);
//		appConfig.setScannerpkg("");//扫描包
//		appConfig.setPath("{appPath}/index.html");
//		appList.add(appConfig);
		
		//帮助文档
		appConfig=new AppConfig("helpdoc","JCEF开发文档","V1.0",1);
		appConfig.setIcon("{appPath}/icon.png");//图标
		appConfig.setType(AppType.html);
		appConfig.setPath("{appPath}/index.html");
		appList.add(appConfig);
		
		FileOutputStream fos=new FileOutputStream(new File(configFilePath));
		IOUtils.write(new Gson().toJson(appList), fos,"UTF-8");
		IOUtils.closeQuietly(fos);
	}
	/**
	 * 保存App列表
	 * @author:liuming
	 * @throws IOException
	 */
	public void saveAppList() throws IOException{
		String configFilePath=this.path+File.separator+Constants.CONFIGFILE;
		FileOutputStream fos=new FileOutputStream(new File(configFilePath));
		IOUtils.write(new Gson().toJson(appList), fos,"UTF-8");
		IOUtils.closeQuietly(fos);
//		for(AppConfig ac:appList) {
//			ac.setPath(ac.getPath()==null?"":ToolUtil.getTransData(ac.getPath(), ac.getId()));
//			ac.setIcon(ac.getIcon()==null?"":ToolUtil.getTransData(ac.getIcon(), ac.getId()));
//		}
	}
	/**
	 * 删除一个应用
	 * @author:liuming
	 * @param id
	 * @throws IOException
	 */
	public void deleteApp(String id) throws IOException {
		for(int i=0;i<appList.size();i++) {
			if(appList.get(i).getId().equals(id)) {
				appList.remove(i);
				break;
			}
		}
		saveAppList();
	}
}
