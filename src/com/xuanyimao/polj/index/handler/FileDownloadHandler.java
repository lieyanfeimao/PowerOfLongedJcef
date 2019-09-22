/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月22日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.handler;

import org.cef.browser.CefBrowser;
import org.cef.callback.CefBeforeDownloadCallback;
import org.cef.callback.CefDownloadItem;
import org.cef.handler.CefDownloadHandlerAdapter;

/**
 * @Description: 文件下载，作用不大
 * @author liuming
 */
public class FileDownloadHandler extends CefDownloadHandlerAdapter {

	/* (non-Javadoc)
	 * @see org.cef.handler.CefDownloadHandlerAdapter#onBeforeDownload(org.cef.browser.CefBrowser, org.cef.callback.CefDownloadItem, java.lang.String, org.cef.callback.CefBeforeDownloadCallback)
	 */
	@Override
	public void onBeforeDownload(CefBrowser browser, CefDownloadItem downloadItem, java.lang.String suggestedName, CefBeforeDownloadCallback callback) {
		System.out.println("准备文件下载...");
		callback.Continue(null, true);
	}
}
