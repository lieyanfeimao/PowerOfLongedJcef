/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月14日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

/**
 * @Description: 应用文件
 * @author liuming
 */
public class FileConfig {
	/**原文件路径*/
	private String fileName;
	/**解压的目录*/
	private String exportPath;
	/**是否为目录*/
	private Boolean isFolder;
	/**在Zip文件中的名字**/
	private String zipFileName;
	/**
	 * isFolder
	 * @return isFolder
	 */
	public Boolean getIsFolder() {
		return isFolder;
	}
	/**
	 * 设置 isFolder
	 * @param isFolder isFolder
	 */
	public void setIsFolder(Boolean isFolder) {
		this.isFolder = isFolder;
	}
	/**
	 * zipFileName
	 * @return zipFileName
	 */
	public String getZipFileName() {
		return zipFileName;
	}
	/**
	 * 设置 zipFileName
	 * @param zipFileName zipFileName
	 */
	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}
	/**
	 * 设置 原文件路径
	 * @return fileName 原文件路径
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 设置 原文件路径
	 * @param fileName 原文件路径
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取 解压的目录
	 * @return exportPath 解压的目录
	 */
	public String getExportPath() {
		return exportPath;
	}
	/**
	 * 设置 解压的目录
	 * @param exportPath 解压的目录
	 */
	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}
}
