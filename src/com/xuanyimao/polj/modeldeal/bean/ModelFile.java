package com.xuanyimao.polj.modeldeal.bean;

/**
 * 模板文件实体类
 * @author liuming
 *
 */
public class ModelFile {
	/**ID**/
	private Integer id;
	
	/**源文件路径*/
	private String sourcePath;
	
	/** 源文件名称 **/
	private String sourcePathName;
	
	/**目标文件路径*/
	private String objPath;
	
	/** 目标文件绝对路径 **/
	private String objAbsPath;
	
	/**源文件编码,默认:UTF-8*/
	private String sourceCode="UTF-8";
	
	/**目标文件编码,默认:UTF-8*/
	private String objCode="UTF-8";

	/**
	 * @return 源文件路径
	 */
	public String getSourcePath() {
		return sourcePath;
	}

	/**
	 * 设置 源文件路径
	 * @param sourcePath 源文件路径
	 */
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	/**
	 * @return 目标文件路径
	 */
	public String getObjPath() {
		return objPath;
	}

	/**
	 * 设置 目标文件路径
	 * @param objPath 目标文件路径
	 */
	public void setObjPath(String objPath) {
		this.objPath = objPath;
	}

	/**
	 * @return 源文件编码默认:UTF-8
	 */
	public String getSourceCode() {
		return sourceCode;
	}

	/**
	 * 设置 源文件编码默认:UTF-8
	 * @param sourceCode 源文件编码默认:UTF-8
	 */
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	/**
	 * @return 目标文件编码默认:UTF-8
	 */
	public String getObjCode() {
		return objCode;
	}

	/**
	 * 设置 目标文件编码默认:UTF-8
	 * @param objCode 目标文件编码默认:UTF-8
	 */
	public void setObjCode(String objCode) {
		this.objCode = objCode;
	}

	
	/**
	 * id
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置 id
	 * @param id id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 源文件名称
	 * @return 源文件名称
	 */
	public String getSourcePathName() {
		return sourcePathName;
	}

	/**
	 * 设置 源文件名称
	 * @param sourcePathName 源文件名称
	 */
	public void setSourcePathName(String sourcePathName) {
		this.sourcePathName = sourcePathName;
	}
	
	/**
	 * 目标文件绝对路径
	 * @return 目标文件绝对路径
	 */
	public String getObjAbsPath() {
		return objAbsPath;
	}

	/**
	 * 设置 目标文件绝对路径
	 * @param objAbsPath 目标文件绝对路径
	 */
	public void setObjAbsPath(String objAbsPath) {
		this.objAbsPath = objAbsPath;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModelFile [id=" + id + ", sourcePath=" + sourcePath + ", sourcePathName=" + sourcePathName
				+ ", objPath=" + objPath + ", objAbsPath=" + objAbsPath + ", sourceCode=" + sourceCode + ", objCode="
				+ objCode + "]";
	}
}
