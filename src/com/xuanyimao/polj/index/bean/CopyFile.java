/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月15日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

/**
 * @Description: 用于APP安装时的文件类
 * @author liuming
 */
public class CopyFile {
	/**文件路径*/
	private String srcPath;
	/**文件名*/
	private String fileName;
	/**是否为目录*/
	private Boolean isFolder=false;
	/**
	 * 文件路径
	 * @return srcPath 文件路径
	 */
	public String getSrcPath() {
		return srcPath;
	}
	/**
	 * 设置 文件路径
	 * @param srcPath 文件路径
	 */
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	/**
	 * 文件名
	 * @return fileName 文件名
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 设置 文件名
	 * @param fileName 文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 是否为目录
	 * @return isFolder 是否为目录
	 */
	public Boolean getIsFolder() {
		return isFolder;
	}
	/**
	 * 设置 是否为目录
	 * @param isFolder 是否为目录
	 */
	public void setIsFolder(Boolean isFolder) {
		this.isFolder = isFolder;
	}
}
