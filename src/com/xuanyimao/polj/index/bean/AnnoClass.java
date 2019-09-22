/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月8日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

/**
 * @Description:注解类对象
 * @author liuming
 */
public class AnnoClass {
	/**类文件对象*/
	private Class<?> cls;
	/**类实例对象*/
	private Object obj;
	/**类实例名称，如果注解未指定，则用类名（小写开头）*/
	private String name;
	/**完整类名，包名.类名*/
	private String clsName;
	
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
	 * obj
	 * @return obj
	 */
	public Object getObj() {
		return obj;
	}
	/**
	 * 设置 obj
	 * @param obj obj
	 */
	public void setObj(Object obj) {
		this.obj = obj;
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
	 * clsName
	 * @return clsName
	 */
	public String getClsName() {
		return clsName;
	}
	/**
	 * 设置 clsName
	 * @param clsName clsName
	 */
	public void setClsName(String clsName) {
		this.clsName = clsName;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnnoClass [cls=" + cls + ", obj=" + obj + ", name=" + name + ", clsName=" + clsName + "]";
	}
	public AnnoClass() {}
	/**
	 * @param classes
	 * @param obj
	 * @param name
	 */
	public AnnoClass(Class<?> cls, Object obj, String name) {
		super();
		this.cls = cls;
		this.obj = obj;
		this.name = name;
	}
	/**
	 * @param cls
	 * @param obj
	 * @param name
	 * @param clsName
	 */
	public AnnoClass(Class<?> cls, Object obj, String name, String clsName) {
		super();
		this.cls = cls;
		this.obj = obj;
		this.name = name;
		this.clsName = clsName;
	}
}
