/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月4日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.handler;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLifeSpanHandlerAdapter;
import com.xuanyimao.polj.index.CefManager;

/**
 * @Description:生命周期
 * @author liuming
 */
public class LifeSpanHandler extends CefLifeSpanHandlerAdapter {

	/* (non-Javadoc)
	 * @see org.cef.handler.CefLifeSpanHandlerAdapter#onBeforePopup(org.cef.browser.CefBrowser, org.cef.browser.CefFrame, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean onBeforePopup(CefBrowser browser, CefFrame frame, String target_url, String target_frame_name) {
//		MainFrame.addTable(target_url);
		CefManager.getInstance().createBrowser(target_url);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.cef.handler.CefLifeSpanHandlerAdapter#doClose(org.cef.browser.CefBrowser)
	 */
	@Override
	public boolean doClose(CefBrowser arg0) {
		System.out.println("关闭前浏览器对象...");
		return super.doClose(arg0);
	}

	/* (non-Javadoc)
	 * @see org.cef.handler.CefLifeSpanHandlerAdapter#onAfterCreated(org.cef.browser.CefBrowser)
	 */
	@Override
	public void onAfterCreated(CefBrowser arg0) {
		System.out.println("创建后调用...");
		super.onAfterCreated(arg0);
	}

	/* (non-Javadoc)
	 * @see org.cef.handler.CefLifeSpanHandlerAdapter#onBeforeClose(org.cef.browser.CefBrowser)
	 */
	@Override
	public void onBeforeClose(CefBrowser arg0) {
		System.out.println("关闭前调用...");
		super.onBeforeClose(arg0);
	}
	
	
}
