/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月4日
 * @version V1.0 
 */
package com.xuanyimao.polj.index;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.commons.lang.StringUtils;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.browser.CefMessageRouter.CefMessageRouterConfig;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefAppHandlerAdapter;
import org.cef.handler.CefMessageRouterHandler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xuanyimao.polj.index.bean.HandlerObject;
import com.xuanyimao.polj.index.bean.TabBrowser;
import com.xuanyimao.polj.index.handler.ContextMenuHandler;
import com.xuanyimao.polj.index.handler.DisplayHandler;
import com.xuanyimao.polj.index.handler.FileDownloadHandler;
import com.xuanyimao.polj.index.handler.LifeSpanHandler;
import com.xuanyimao.polj.index.handler.LoadHandler;
import com.xuanyimao.polj.index.listener.TabCloseListener;
import com.xuanyimao.polj.index.scanner.AnnotationScanner;

/**
 * @Description: cef对象管理类
 * 				“做人一定要脚踏实地，不要老想着和别人攀比，比来比去又得到了什么，让自己白白受累。百年后不过是一坯黄土，自己踏踏实实过好自己就行了。”
 * 				“还是你心态好啊~”
 * 				“也不是，主要是比不过。”
 * @author liuming
 */
public class CefManager{
	
	private static CefManager mg;
	
	private CefManager(boolean useOSR,boolean isTransparent,JFrame frame) {
		
		String[] args=new String[] {
			"--enable-system-flash=true"//启用flash
			,"--disable-web-security"//开启跨域
		};
		
		CefApp.addAppHandler(new CefAppHandlerAdapter(args) {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });
		this.useOSR=useOSR;
		this.isTransparent=isTransparent;
		CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = useOSR;
        cefApp= CefApp.getInstance(args,settings);
        client = cefApp.createClient();
        //注册一些handler
        client.addLoadHandler(new LoadHandler());
        client.addLifeSpanHandler(new LifeSpanHandler());
        client.addDisplayHandler(new DisplayHandler());
        client.addContextMenuHandler(new ContextMenuHandler());
        client.addDownloadHandler(new FileDownloadHandler());
        //添加js交互
        jsActive();
        
        tabbedPane=new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
        this.frame=frame;
        
    	this.frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    	this.frame.pack();
    	this.frame.setSize(800, 600);
    	this.frame.setVisible(true);
    	this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    	this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option= JOptionPane.showConfirmDialog(
                		frame, "确定退出系统? ", "提示 ",JOptionPane.YES_NO_OPTION);
            	if(option==JOptionPane.YES_OPTION) {
            		closeAllBrowser();
            		CefApp.getInstance().dispose();
            		frame.dispose();
            	}else {
            		return;
            	}
            }
        });
	}
	
	private CefManager() {}
	/**
	 * 获取CefManager对象实例
	 * @author:liuming
	 * @param useOSR
	 * @param isTransparent
	 * @param frame
	 * @return
	 */
	public static CefManager getInstance(boolean useOSR,boolean isTransparent,JFrame frame) {
		if(mg==null) {
			mg=new CefManager( useOSR, isTransparent,frame);
		}
		return mg;
	}
	/**
	 * 获取CefManager对象实例，在此方法之前必须在任意位置调用过getInstance(boolean useOSR,boolean isTransparent,JFrame frame)
	 * @author:liuming
	 * @return
	 */
	public static CefManager getInstance() {
		return mg;
	}
	
	private static CefApp cefApp;
	
	private static CefClient client;
	
	private JFrame frame;
	
	private boolean useOSR;
	
	private boolean isTransparent;
	
	private static JTabbedPane tabbedPane;
	
	private List<TabBrowser> tbList=new Vector<TabBrowser>();
	
	private int tbIndex=0; 
	
	private final static String TITLE_INFO="正在载入...";
	
	
	
	/**
	 * 获取CefClient对象
	 * @return client
	 */
	public static CefClient getClient() {
		return client;
	}

	/**
	 * 获取Frame窗口对象
	 * @return frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * 获取TabBrowser对象集合
	 * @return tbList
	 */
	public List<TabBrowser> getTbList() {
		return tbList;
	}
	/**
	 * 关闭所有浏览器窗口
	 * @author:liuming
	 */
	public void closeAllBrowser() {
		for(int i=tbList.size()-1;i>=0;i--) {
			TabBrowser tb=tbList.get(i);
			tb.getBrowser().close(true);
			tabbedPane.removeTabAt(i);
			System.out.println("移除索引为"+i+"的tab...");
		}
	}
	
	/**
	 * 根据url创建一个新的tab页
	 * @author:liuming
	 * @param url
	 * @return 最后一个tab的索引
	 */
	public int createBrowser(String url) {
		CefBrowser browser = client.createBrowser(url, useOSR, isTransparent);
		tabbedPane.addTab(".", browser.getUIComponent());
		int lastIndex=tabbedPane.getTabCount()-1;
		tbIndex++;
		
		//创建自定义tab栏
		JPanel jp=new JPanel();
		//设置panel为卡片布局
//		jp.setLayout(new GridLayout(1, 1, 10, 0));
		
		JLabel ltitle=new JLabel(TITLE_INFO);
		JLabel lclose=new JLabel("X");
		jp.setOpaque(false);
		ltitle.setHorizontalAlignment(JLabel.LEFT);
		lclose.setHorizontalAlignment(JLabel.RIGHT);
		jp.add(ltitle);
		jp.add(lclose);
		lclose.addMouseListener(new TabCloseListener(tbIndex));
		
		tabbedPane.setTabComponentAt(lastIndex, jp);
		
		TabBrowser tb=new TabBrowser(tbIndex, browser, ltitle);
		tbList.add(tb);
		
		tabbedPane.setSelectedIndex(lastIndex);
		return lastIndex;
	}
	
	/**
	 * 修改标题
	 * @author:liuming
	 * @param browser
	 * @param title
	 */
	public void updateTabTitle(CefBrowser browser,String title) {
		if(StringUtils.isNotBlank(title)) {
			if(title.length()>12) title=title.substring(0, 12)+"...";
			for(TabBrowser tb:tbList) {
				if(tb.getBrowser()==browser) {
					tb.getTitle().setText(title);
					break;
				}
			}
		}
	}
	/**
	 * 移除tab
	 * @author:liuming
	 * @param browser
	 * @param index
	 */
	public void removeTab(CefBrowser browser,int index) {
		if(browser!=null) {
			for(int i=0;i<tbList.size();i++) {
				TabBrowser tb=tbList.get(i);
				if(tb.getBrowser()==browser) {
					tb.getBrowser().close(true);
					tabbedPane.removeTabAt(i);
					tbList.remove(i);
//					System.out.println("移除索引为"+i+"的tab");
					break;
				}
			}
			
		}else {
			
			for(int i=0;i<tbList.size();i++) {
				TabBrowser tb=tbList.get(i);
				if(tb.getIndex()==index) {
					tb.getBrowser().close(true);
					tabbedPane.removeTabAt(i);
					tbList.remove(i);
//					System.out.println("移除索引为"+i+"的tab");
					break;
				}
			}
		}
	}
	
	
	/**
	 * 添加js交互
     * @author:liuming
     */
    public void jsActive() {
    	 //配置一个查询路由
		 CefMessageRouterConfig cmrc=new CefMessageRouterConfig("java","javaCancel");
		 //创建查询路由
		 CefMessageRouter cmr=CefMessageRouter.create(cmrc);
		 cmr.addHandler(new CefMessageRouterHandler() {
			
			@Override
			public void setNativeRef(String str, long val) {
				System.out.println(str+"  "+val);
			}
			
			@Override
			public long getNativeRef(String str) {
				System.out.println(str);
				return 0;
			}
			
			@Override
			public void onQueryCanceled(CefBrowser browser, CefFrame frame, long query_id) {
				System.out.println("取消查询:"+query_id);
			}
			
			@Override
			public boolean onQuery(CefBrowser browser, CefFrame frame, long query_id, String request, boolean persistent,
					CefQueryCallback callback) {
				int i1=request.indexOf(":");
				String action=request;
				String param="";
				if(i1!=-1) {
					action=request.substring(0,i1);
					param=request.substring(i1+1);
				}
				System.out.println("action:"+action+"  param"+param);
				//创建js交互数据重新封装的对象
				HandlerObject ho=new HandlerObject(browser, frame, query_id, request, persistent, callback);
				JsonObject jobj=null;
				if(StringUtils.isNotBlank(param)) {//如果存在参数
					JsonParser parser=new JsonParser();
					jobj=parser.parse(param).getAsJsonObject();
				}
				
				AnnotationScanner.execMain(action, jobj, ho);
				return true;
			}
		}, true);
		client.addMessageRouter(cmr);
	}
}
