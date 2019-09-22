/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月8日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Description:注解方法对象
 * @author liuming
 */
public class AnnoMethod {
	
	/**方法对象*/
	private Method method;
	
	/**类对象*/
	private AnnoClass annoClass;
	
	/** 方法参数对象列表 */
	private List<MethodParam> methodParam;

	/**
	 * method
	 * @return method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * 设置 method
	 * @param method method
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	/**
	 * annoClass
	 * @return annoClass
	 */
	public AnnoClass getAnnoClass() {
		return annoClass;
	}

	/**
	 * 设置 annoClass
	 * @param annoClass annoClass
	 */
	public void setAnnoClass(AnnoClass annoClass) {
		this.annoClass = annoClass;
	}

	/**
	 * methodParam
	 * @return methodParam
	 */
	public List<MethodParam> getMethodParam() {
		return methodParam;
	}

	/**
	 * 设置 methodParam
	 * @param methodParam methodParam
	 */
	public void setMethodParam(List<MethodParam> methodParam) {
		this.methodParam = methodParam;
	}

	/**
	 * 
	 */
	public AnnoMethod() {
		super();
	}

	/**
	 * @param method
	 * @param annoClass
	 * @param methodParam
	 */
	public AnnoMethod(Method method, AnnoClass annoClass, List<MethodParam> methodParam) {
		super();
		this.method = method;
		this.annoClass = annoClass;
		this.methodParam = methodParam;
	}

	/**
	 * @param method
	 * @param annoClass
	 */
	public AnnoMethod(Method method, AnnoClass annoClass) {
		super();
		this.method = method;
		this.annoClass = annoClass;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnnoMethod [method=" + method + ", annoClass=" + annoClass + ", methodParam=" + methodParam + "]";
	}
}
