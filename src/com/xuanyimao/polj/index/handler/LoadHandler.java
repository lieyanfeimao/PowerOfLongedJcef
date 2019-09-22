/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月2日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.handler;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandlerAdapter;

import com.xuanyimao.polj.index.bean.Injection;
import com.xuanyimao.polj.index.bean.Scp;
import com.xuanyimao.polj.index.bean.ScpConfig;

/**
 * @Description:
 * @author liuming
 */
public class LoadHandler extends CefLoadHandlerAdapter {
	
	/* (non-Javadoc)
	 * @see org.cef.handler.CefLoadHandlerAdapter#onLoadEnd(org.cef.browser.CefBrowser, org.cef.browser.CefFrame, int)
	 */
	@Override
	public void onLoadEnd(CefBrowser browser, CefFrame frame, int httpStatusCode) {
		System.out.println("加载完成："+browser.getURL()+">>>>"+frame.getURL());
		//用来做正则匹配的url
		String url=frame.getURL();
		
		Injection inj=Injection.getInstance();
		List<ScpConfig> clist=inj.getConfigs();
		if(clist!=null && !clist.isEmpty()) {
			for(ScpConfig sc:clist) {
				System.out.println(url+"////"+sc.getUrl()+"..."+sc.getType()+"---"+url.matches(sc.getUrl())+"+++"+url.matches("https://www.baidu.com"));
				if("1".equals(sc.getType()) && StringUtils.isNotBlank(sc.getUrl()) && url.matches(sc.getUrl()) ) {
					Scp scp = inj.getScpInfo().get(sc.getFile());
					if(scp!=null) {
						//执行js脚本
						frame.executeJavaScript(scp.getScript(), null, 0);
						System.out.println(url+">>>"+sc.getUrl()+"  执行注入脚本...");
					}
				}
			}
		}
		super.onLoadEnd(browser, frame, httpStatusCode);
	}
	
	
}