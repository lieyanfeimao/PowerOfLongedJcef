package com.xuanyimao.polj.modeldeal.bean;

/**
 * 处理的消息对象
 * @author liuming
 *
 */
public class ModelDealMessage {
	/**
	 * 状态枚举类
	 * @author liuming
	 */
	public enum Status{
		//成功
        SUCCESS,
        //失败
        FAIL
    }
	
	/**消息状态*/
	private Status status;
	
	/**消息*/
	private String msg;

	/**
	 * @return 消息状态
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * 设置 消息状态
	 * @param status 消息状态
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return 消息
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置 消息
	 * @param msg 消息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ModelDealMessage(Status status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public ModelDealMessage(Status status) {
		super();
		this.status = status;
	}
	/***
	 * 默认构造器，返回成功状态
	 */
	public ModelDealMessage(){
		this.status=Status.SUCCESS;
		this.msg="操作成功";
	}
}

