/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月4日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

import javax.swing.JLabel;

import org.cef.browser.CefBrowser;

/**
 * @Description: 保存tab信息
 * @author liuming
 */
public class TabBrowser {
	
	private int index;
	
	private CefBrowser browser;
	
	private JLabel title;

	/**
	 * index
	 * @return index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置 index
	 * @param index index
	 */
	public void setIndex(int index) {
		this.index = index;
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
	 * title
	 * @return title
	 */
	public JLabel getTitle() {
		return title;
	}

	/**
	 * 设置 title
	 * @param title title
	 */
	public void setTitle(JLabel title) {
		this.title = title;
	}

	/**
	 * @param index
	 * @param browser
	 * @param title
	 */
	public TabBrowser(int index, CefBrowser browser, JLabel title) {
		super();
		this.index = index;
		this.browser = browser;
		this.title = title;
	}
}
