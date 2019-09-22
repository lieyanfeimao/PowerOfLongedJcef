/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月14日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

import java.util.List;

/**
 * @Description: APP压缩包
 * @author liuming
 */
public class AppPackage {
	/**文件配置列表*/
	private List<FileConfig> fileConfigs;
	/**应用配置*/
	private AppConfig appConfig;
	/**脚本配置列表*/
	private List<ScpConfig> scpConfigs;
	/**应用打包文件输出路径*/
	private String appPkg;
	
	/**
	 * 应用打包文件输出路径
	 * @return appPkg 应用打包文件输出路径
	 */
	public String getAppPkg() {
		return appPkg;
	}
	/**
	 * 设置 应用打包文件输出路径
	 * @param appPkg 应用打包文件输出路径
	 */
	public void setAppPkg(String appPkg) {
		this.appPkg = appPkg;
	}
	/**
	 * 文件配置列表
	 * @return fileConfigs 文件配置列表
	 */
	public List<FileConfig> getFileConfigs() {
		return fileConfigs;
	}
	/**
	 * 设置 文件配置列表
	 * @param fileConfigs 文件配置列表
	 */
	public void setFileConfigs(List<FileConfig> fileConfigs) {
		this.fileConfigs = fileConfigs;
	}
	/**
	 * 应用配置
	 * @return appConfig 应用配置
	 */
	public AppConfig getAppConfig() {
		return appConfig;
	}
	/**
	 * 设置 应用配置
	 * @param appConfig 应用配置
	 */
	public void setAppConfig(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	/**
	 * 脚本配置列表
	 * @return scpConfigs 脚本配置列表
	 */
	public List<ScpConfig> getScpConfigs() {
		return scpConfigs;
	}
	/**
	 * 设置 脚本配置列表
	 * @param scpConfigs 脚本配置列表
	 */
	public void setScpConfigs(List<ScpConfig> scpConfigs) {
		this.scpConfigs = scpConfigs;
	}
}
