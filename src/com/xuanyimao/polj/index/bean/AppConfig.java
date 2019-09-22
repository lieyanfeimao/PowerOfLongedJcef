/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月10日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

import com.xuanyimao.polj.index.util.ToolUtil;

/**
 * @Description: 软件配置文件对象
 * @author liuming
 */
public class AppConfig {
	/**
	 * @Description:应用类型,
	 * 				如果应用类型为jar/exe，表示它会以独立的应用方式启动，不附加在软件界面中，如果未配置启动命令，则根据主程序路径以普通方式打开
	 * @author liuming
	 */
	public enum AppType{
		exe,
		html,
		web,
		jar
	}
	
	/** 应用ID */
	private String id;
	/**应用名称*/
	private String name;
	/**版本号*/
	private String version;
	/**版本数字，用于更新*/
	private int versionNum;
	/**应用图标*/
	private String icon;
	/**应用类型*/
	private AppType type;
	/**应用启动文件*/
	private String path;
	/**需要扫描的包*/
	private String scannerpkg;
	/**启动命令，cmd命令行命令，程序会对相关变量替换后再执行*/
	private String command;
	/**程序需要加载的dll文件*/
	private String lib;
	/**程序需要加载的jar文件*/
	private String libjar;
	
	/**
	 * @return 应用ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 应用ID
	 * @param id 应用ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return 应用名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 应用名称
	 * @param name 应用名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return 版本号
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置 版本号
	 * @param version 版本号
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return 版本数字，用于更新
	 */
	public int getVersionNum() {
		return versionNum;
	}
	/**
	 * 设置 版本数字，用于更新
	 * @param versionNum 版本数字，用于更新
	 */
	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}
	/**
	 * @return 应用图标
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置 应用图标
	 * @param icon 应用图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @return 应用类型
	 */
	public AppType getType() {
		return type;
	}
	/**
	 * 设置 应用类型
	 * @param type 应用类型
	 */
	public void setType(AppType type) {
		this.type = type;
	}
	/**
	 * @return 应用启动文件
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置 应用启动文件
	 * @param path 应用启动文件
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return 需要扫描的包
	 */
	public String getScannerpkg() {
		return scannerpkg;
	}
	/**
	 * 设置 需要扫描的包
	 * @param scannerpkg 需要扫描的包
	 */
	public void setScannerpkg(String scannerpkg) {
		this.scannerpkg = scannerpkg;
	}
	/**
	 * @return 启动命令，cmd命令行命令，程序会对相关变量替换后再执行
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * 设置 启动命令，cmd命令行命令，程序会对相关变量替换后再执行
	 * @param command 启动命令，cmd命令行命令，程序会对相关变量替换后再执行
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * @return 程序需要加载的dll文件
	 */
	public String getLib() {
		return lib;
	}
	/**
	 * 设置 程序需要加载的dll文件
	 * @param lib 程序需要加载的dll文件
	 */
	public void setLib(String lib) {
		this.lib = lib;
	}
	/**
	 * @return 程序需要加载的jar文件
	 */
	public String getLibjar() {
		return libjar;
	}
	/**
	 * 设置 程序需要加载的jar文件
	 * @param libjar 程序需要加载的jar文件
	 */
	public void setLibjar(String libjar) {
		this.libjar = libjar;
	}
	/**
	 * @param id
	 * @param name
	 * @param version
	 * @param versionNum
	 */
	public AppConfig(String id, String name, String version, int versionNum) {
		super();
		this.id = id;
		this.name = name;
		this.version = version;
		this.versionNum = versionNum;
	}
	
	public AppConfig() {}
	/**获取APP真实路径*/
	public String getRealPath() {
		return ToolUtil.getTransData(this.path, id);
	}
	/**
	 * 获取图标的真实路径
	 * @author:liuming
	 * @return
	 */
	public String getRealIcon() {
		return ToolUtil.getTransData(this.icon, id);
	}
}
