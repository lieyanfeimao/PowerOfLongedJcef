/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月8日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

/**
 * @Description: 方法参数
 * @author liuming
 */
public class MethodParam {
	/**参数类型*/
	private Class<?> cls;
	/**参数名*/
	private String name;
	/**
	 * cls
	 * @return cls
	 */
	public Class<?> getCls() {
		return cls;
	}
	/**
	 * 设置 cls
	 * @param cls cls
	 */
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	/**
	 * name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 name
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 */
	public MethodParam() {
		super();
	}
	/**
	 * @param cls
	 * @param name
	 */
	public MethodParam(Class<?> cls, String name) {
		super();
		this.cls = cls;
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MethodParam [cls=" + cls + ", name=" + name + "]";
	}
	
}
