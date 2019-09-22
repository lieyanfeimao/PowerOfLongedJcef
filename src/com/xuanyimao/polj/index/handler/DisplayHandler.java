/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月4日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.handler;

import org.cef.browser.CefBrowser;
import org.cef.handler.CefDisplayHandlerAdapter;

import com.xuanyimao.polj.index.CefManager;

/**
 * @Description:
 * @author liuming
 */
public class DisplayHandler extends CefDisplayHandlerAdapter {

	/* (non-Javadoc)
	 * @see org.cef.handler.CefDisplayHandlerAdapter#onStatusMessage(org.cef.browser.CefBrowser, java.lang.String)
	 */
	@Override
	public void onStatusMessage(CefBrowser browser, String value) {
//		System.out.println("状态消息:"+value);
		super.onStatusMessage(browser, value);
	}

	/* (non-Javadoc)
	 * @see org.cef.handler.CefDisplayHandlerAdapter#onTitleChange(org.cef.browser.CefBrowser, java.lang.String)
	 */
	@Override
	public void onTitleChange(CefBrowser browser, String title) {
//		System.out.println("标题改变："+title);
		//在此处更改tab栏标题
		CefManager.getInstance().updateTabTitle(browser, title);
		super.onTitleChange(browser, title);
	}
	
	
}
