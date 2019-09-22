/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月5日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefContextMenuParams;
import org.cef.callback.CefMenuModel;
import org.cef.callback.CefMenuModel.MenuId;
import org.cef.handler.CefContextMenuHandler;
import com.xuanyimao.polj.index.CefManager;
import com.xuanyimao.polj.index.bean.DevRepertory;
import com.xuanyimao.polj.index.bean.Injection;
import com.xuanyimao.polj.index.bean.Scp;
import com.xuanyimao.polj.index.bean.ScpConfig;
import com.xuanyimao.polj.index.dialog.DevToolsDialog;

/**
 * @Description:
 * @author liuming
 */
public class ContextMenuHandler implements CefContextMenuHandler{
	/**注入ID*/
	private final static int MENU_ID_INJECTION=10000;
	/**首页ID*/
	private final static int MENU_ID_INDEX=9999;
	/**开发者工具条**/
	private final static int MENU_ID_DEVTOOL=9998;
	/**查看源码**/
	private final static int MENU_ID_VIEWSOURCE=9997;
	
	/**初始索引，不断叠加*/
	private static int menuIndex=10000;
	
	/**自定义子菜单**/
	private static Map<Integer, ScpConfig> scpmenu=new HashMap<Integer, ScpConfig>();
	
	/**菜单ID与frame对应**/
	private static Map<Integer, CefFrame> menufrm=new HashMap<Integer, CefFrame>();
	
	/* 
	 * 在显示上下文菜单之前调用
	 * @see org.cef.handler.CefContextMenuHandler#onBeforeContextMenu(org.cef.browser.CefBrowser, org.cef.browser.CefFrame, org.cef.callback.CefContextMenuParams, org.cef.callback.CefMenuModel)
	 */
	@Override
	public void onBeforeContextMenu(CefBrowser browser, CefFrame frame, CefContextMenuParams params, CefMenuModel model) {
		//清除菜单项
		model.clear();
		
		//剪切、复制、粘贴
		model.addItem(MenuId.MENU_ID_COPY, "复制");
		model.addItem(MenuId.MENU_ID_CUT, "剪切");
		model.addItem(MenuId.MENU_ID_PASTE, "粘贴");
		model.addSeparator();
		
		model.addItem(MenuId.MENU_ID_BACK, "返回");
		model.setEnabled(MenuId.MENU_ID_BACK, browser.canGoBack());
		
		model.addItem(MenuId.MENU_ID_FORWARD, "前进");
        model.setEnabled(MenuId.MENU_ID_FORWARD, browser.canGoForward());
        
        model.addItem(MenuId.MENU_ID_RELOAD, "刷新");
        
        model.addSeparator();
        
        model.addItem(MENU_ID_INDEX, "首页");
        model.addItem(MENU_ID_DEVTOOL, "开发者工具");
        model.addItem(MENU_ID_VIEWSOURCE, "查看页面源码");
        
        model.addSeparator();
        CefMenuModel cmodel=model.addSubMenu(MENU_ID_INJECTION, "脚本注入");
//        cmodel.addItem(MenuId.MENU_ID_COPY, "复制");
        Injection inj=Injection.getInstance();
        List<ScpConfig> configs = inj.getConfigs();
        if(configs!=null && !configs.isEmpty()) {
        	scpmenu=new HashMap<Integer, ScpConfig>();
        	menufrm=new HashMap<Integer, CefFrame>();
        	for(ScpConfig sc:configs) {
        		if(StringUtils.isNotBlank(sc.getName())) {
//        			cmodel.addItem(menuIndex, sc.getName());
//        			scpmenu.put(menuIndex++, sc);
        			CefMenuModel cmm=cmodel.addSubMenu(menuIndex++,sc.getName());
        			
        			Vector<Long> fids = browser.getFrameIdentifiers();
        			for(Long ids:fids) {
        				CefFrame cf=browser.getFrame(ids);
        				if(cf!=null) {
        					cmm.addItem(menuIndex, cf.getURL());
        					menufrm.put(menuIndex, cf);
        					scpmenu.put(menuIndex++, sc);
        				}
        			}
        			
        		}
        	}
        }
	}

	/* 
	 * @see org.cef.handler.CefContextMenuHandler#onContextMenuCommand(org.cef.browser.CefBrowser, org.cef.browser.CefFrame, org.cef.callback.CefContextMenuParams, int, int)
	 */
	@Override
	public boolean onContextMenuCommand(CefBrowser browser, CefFrame frame, CefContextMenuParams params, int commandId, int eventFlags) {
		if(commandId==MenuId.MENU_ID_RELOAD) {
			browser.reload();
			return true;
		}else if(commandId==MENU_ID_INDEX) {
			CefManager.getInstance().createBrowser("file:///"+DevRepertory.getInstance().getAppPath()+"/index.html");
			return true;
		}else if(commandId==MENU_ID_DEVTOOL) {
			DevToolsDialog devToolsDlg = new DevToolsDialog(CefManager.getInstance().getFrame(), "开发者工具-"+browser.getMainFrame().getName(), browser);
			devToolsDlg.setVisible(true);
			return true;
		}else if(commandId==MENU_ID_VIEWSOURCE) {
			browser.viewSource();
			return true;
		}else {//脚本注入
			ScpConfig sc=scpmenu.get(commandId);
			if(sc!=null) {
				Scp scp = Injection.getInstance().getScpInfo().get(sc.getFile());
				if(scp!=null) {
					//执行js脚本
					CefFrame cf = menufrm.get(commandId);
					if(cf!=null) {
						cf.executeJavaScript(scp.getScript(), null, 0);
					}else {
						browser.executeJavaScript(scp.getScript(), null, 0);
					}
					System.out.println("手动注入脚本...");
				}
			}
		}
//		switch (commandId) {
//		case MenuId.MENU_ID_RELOAD:
//			browser.reload();
//			return true;
//		}
		return false;
	}

	/* 
	 * 无论菜单是空还是选择了命令，都会在关闭上下文菜单时调用
	 * @see org.cef.handler.CefContextMenuHandler#onContextMenuDismissed(org.cef.browser.CefBrowser, org.cef.browser.CefFrame)
	 */
	@Override
	public void onContextMenuDismissed(CefBrowser browser, CefFrame frame) {
		// TODO Auto-generated method stub
		
	}

}
