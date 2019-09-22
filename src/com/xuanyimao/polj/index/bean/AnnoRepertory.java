/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月8日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

import java.util.List;
import java.util.Map;

/**
 * @Description:注解仓库对象
 * @author liuming
 */
public class AnnoRepertory {
	
	private final static AnnoRepertory annoRepertory=new AnnoRepertory();
	
	private AnnoRepertory() {}
	/**
	 * 获取注解仓库实例
	 * @return
	 */
	public static AnnoRepertory getInstance() {
		return annoRepertory;
	}
	
	/** 注解扫描包配置，多个包以;号隔开  */
    private String scannerPackage;
    /**引入的jar文件列表*/
    private List<String> extraJars;
    /** 注解类对象集合 */
    private List<AnnoClass> annoClassList;
    /** 方法集合 */
    private Map<String,AnnoMethod> methodMap;
    
    /**
	 * 方法集合
	 * @return methodMap
	 */
	public Map<String, AnnoMethod> getMethodMap() {
		return methodMap;
	}
	/**
	 * 设置 方法集合
	 * @param methodMap 方法集合
	 */
	public void setMethodMap(Map<String, AnnoMethod> methodMap) {
		this.methodMap = methodMap;
	}
	/**
	 * 注解类对象集合
	 * @return annoClassList 注解类对象集合
	 */
	public List<AnnoClass> getAnnoClassList() {
		return annoClassList;
	}
	
	/**
	 * 设置 注解类对象集合
	 * @param annoClassList 注解类对象集合
	 */
	public void setAnnoClassList(List<AnnoClass> annoClassList) {
		this.annoClassList = annoClassList;
	}
	/**  
     * 获取注解扫描包配置，多个包以;号隔开  
     * @return scannerPackage 注解扫描包配置，多个包以;号隔开  
     */
    public String getScannerPackage() {
        return scannerPackage;
    }
    /**  
     * 设置注解扫描包配置，多个包以;号隔开  
     * @param scannerPackage 注解扫描包配置，多个包以;号隔开  
     */
    public void setScannerPackage(String scannerPackage) {
        this.scannerPackage = scannerPackage;
    }
	/**
	 * 获取引入的jar文件列表
	 * @return extraJars 引入的jar文件列表
	 */
	public List<String> getExtraJars() {
		return extraJars;
	}
	/**
	 * 设置 引入的jar文件列表
	 * @param extraJars 引入的jar文件列表
	 */
	public void setExtraJars(List<String> extraJars) {
		this.extraJars = extraJars;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnnoRepertory [scannerPackage=" + scannerPackage + ", annoClassList=" + annoClassList + ", methodMap="
				+ methodMap + "]";
	}
}
