/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月21日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JDialog;

import org.cef.browser.CefBrowser;

/**
 * @Description: 从Demo复制来的 开发者工具弹窗。不建议使用，容易让程序崩溃
 * @author liuming
 */
public class DevToolsDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4115973232808017898L;
	
	private final CefBrowser devTools_;
    public DevToolsDialog(Frame owner, String title, CefBrowser browser) {
        this(owner, title, browser, null);
    }

    public DevToolsDialog(Frame owner, String title, CefBrowser browser, Point inspectAt) {
        super(owner, title, false);

        setLayout(new BorderLayout());
        setSize(1000, 800);
        setLocation(owner.getLocation().x + 20, owner.getLocation().y + 20);

        devTools_ = browser.getDevTools(inspectAt);
        add(devTools_.getUIComponent());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                dispose();
            }
        });
    }

    @Override
    public void dispose() {
        devTools_.close(true);
        super.dispose();
    }
}
