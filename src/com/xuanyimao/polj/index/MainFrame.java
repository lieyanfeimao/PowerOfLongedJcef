package com.xuanyimao.polj.index;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import org.cef.OS;
import com.xuanyimao.polj.index.anno.JsClass;
import com.xuanyimao.polj.index.bean.DevRepertory;

/**
 * @Description: 启动窗口
 * @author liuming
 */
@JsClass
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5550525843316817638L;
	
	public MainFrame() {
		CefManager cmg=CefManager.getInstance(OS.isLinux(), false, this);
    	cmg.createBrowser("file:///"+DevRepertory.getInstance().getAppPath()+"/index.html");
    	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	this.setTitle("PowerOfLongedJcef-玄翼猫-V1.0");
    	ImageIcon icon=new ImageIcon(DevRepertory.getInstance().getAppPath()+File.separator+"logo.png");
    	this.setIconImage(icon.getImage());
	}
}
