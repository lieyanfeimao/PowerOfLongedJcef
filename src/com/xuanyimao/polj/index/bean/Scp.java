/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月3日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

/**
 * @Description: 脚本对象
 * @author liuming
 */
public class Scp {
	/**脚本内容*/
	private String content;
	
	/**动态嵌入脚本内容前的部分*/
	private String activeStart;
	
	/**动态嵌入脚本内容末尾的部分*/
	private String activeEnd;

	/**
	 * content
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置 content
	 * @param content content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * activeStart
	 * @return activeStart
	 */
	public String getActiveStart() {
		return activeStart;
	}

	/**
	 * 设置 activeStart
	 * @param activeStart activeStart
	 */
	public void setActiveStart(String activeStart) {
		this.activeStart = activeStart;
	}

	/**
	 * activeEnd
	 * @return activeEnd
	 */
	public String getActiveEnd() {
		return activeEnd;
	}

	/**
	 * 设置 activeEnd
	 * @param activeEnd activeEnd
	 */
	public void setActiveEnd(String activeEnd) {
		this.activeEnd = activeEnd;
	}
	/**
	 * 获取脚本内容
	 * @author:liuming
	 * @return
	 */
	public String getScript() {
		StringBuffer sbuf=new StringBuffer();
		if(activeStart!=null) {
			sbuf.append(activeStart);
		}
		if(content!=null) {
			sbuf.append(content);
		}
		if(activeEnd!=null) {
			sbuf.append(activeEnd);
		}
		return sbuf.toString();
	}
}
