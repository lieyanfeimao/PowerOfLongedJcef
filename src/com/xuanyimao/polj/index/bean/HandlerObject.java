/**
 * 
 */
package com.xuanyimao.polj.index.bean;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefQueryCallback;

/**
 * js交互数据重新封装的对象
 * @author liuming
 */
public class HandlerObject {
	/**浏览器对象*/
	private CefBrowser browser;
	/**frame对象*/
	private CefFrame frame;
	/**查询ID*/
	private long query_id;
	/**原始数据*/
	private String request;
	/**是否持久化*/
	private boolean persistent;
	/**回调对象*/
	private CefQueryCallback callback;
	
	public HandlerObject() {}
	
	
	/**
	 * 构造方法
	 * @param browser
	 * @param frame
	 * @param query_id
	 * @param request
	 * @param persistent
	 * @param callback
	 */
	public HandlerObject(CefBrowser browser, CefFrame frame, long query_id, String request, boolean persistent,
			CefQueryCallback callback) {
		super();
		this.browser = browser;
		this.frame = frame;
		this.query_id = query_id;
		this.request = request;
		this.persistent = persistent;
		this.callback = callback;
	}

	/**
	 * browser
	 * @return browser
	 */
	public CefBrowser getBrowser() {
		return browser;
	}
	/**
	 * 设置 browser
	 * @param browser browser
	 */
	public void setBrowser(CefBrowser browser) {
		this.browser = browser;
	}
	/**
	 * frame
	 * @return frame
	 */
	public CefFrame getFrame() {
		return frame;
	}
	/**
	 * 设置 frame
	 * @param frame frame
	 */
	public void setFrame(CefFrame frame) {
		this.frame = frame;
	}
	/**
	 * query_id
	 * @return query_id
	 */
	public long getQuery_id() {
		return query_id;
	}
	/**
	 * 设置 query_id
	 * @param query_id query_id
	 */
	public void setQuery_id(long query_id) {
		this.query_id = query_id;
	}
	/**
	 * request
	 * @return request
	 */
	public String getRequest() {
		return request;
	}
	/**
	 * 设置 request
	 * @param request request
	 */
	public void setRequest(String request) {
		this.request = request;
	}
	/**
	 * persistent
	 * @return persistent
	 */
	public boolean isPersistent() {
		return persistent;
	}
	/**
	 * 设置 persistent
	 * @param persistent persistent
	 */
	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}
	/**
	 * callback
	 * @return callback
	 */
	public CefQueryCallback getCallback() {
		return callback;
	}
	/**
	 * 设置 callback
	 * @param callback callback
	 */
	public void setCallback(CefQueryCallback callback) {
		this.callback = callback;
	}
}
