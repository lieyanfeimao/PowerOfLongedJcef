package com.xuanyimao.polj.index.bean;

import java.io.Serializable;


/**
 * 通用的消息返回信息类
 * @author liuming
 * @since 2017-12-3
 */
public class Message implements Serializable{
    /**
     * id
     */
    private static final long serialVersionUID = -5690781835233764327L;


    /**响应*/
    private int code;
    
    
    /**返回信息描述*/
    private String msg;
    /**额外数据*/
    private Object result;
    public Message(){
    }
    public Message(int code,String msg,Object result){
        this.code=code;
        this.msg=msg;
        this.result=result;
    }
    
   
    
 
    /**  
     * 获取响应  
     * @return code 响应  
     */
    public int getCode() {
        return code;
    }
    /**  
     * 设置响应  
     * @param code 响应  
     */
    public void setCode(int code) {
        this.code = code;
    }
    /**  
     * 获取返回信息描述  
     * @return msg 返回信息描述  
     */
    public String getMsg() {
        return msg;
    }
    /**  
     * 设置返回信息描述  
     * @param msg 返回信息描述  
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    /**  
     * 获取额外数据  
     * @return extrDatas 额外数据  
     */
    public Object getResult() {
        return result;
    }
    /**  
     * 设置额外数据  
     * @param extrDatas 额外数据  
     */
    public void setResult(Object result) {
        this.result = result;
    }
    /**
     * 请求成功
     * @parameter msg  描述
     * @parameter map  返回的数据对象
     * @return
     */
    public static Message success(String msg,Object obj){
        return new Message(MessageCode.CODE_SUCCESS,msg,obj);
    }
    /**
     * 请求成功
     * @parameter msg  描述
     * @return
     */
    public static Message success(String msg){
        return success(msg,null);
    }
    /**
     * 请求成功
     * @return
     */
    public static Message success(){
        return success(MessageCode.MSG_SUCCESS,null);
    }
    
    /**
     * 请求错误
     * @parameter msg  描述
     * @parameter map  返回的数据对象
     * @return
     */
    public static Message error(String msg,Object obj){
        return new Message(MessageCode.CODE_ERROR,msg,obj);
    }
    /**
     * 请求错误
     * @parameter msg  描述
     * @return
     */
    public static Message error(String msg){
        return error(msg,null);
    }
    /**
     * 请求错误
     * @return
     */
    public static Message error(){
        return error(MessageCode.MSG_ERROR,null);
    }
}
