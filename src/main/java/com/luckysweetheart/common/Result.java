package com.luckysweetheart.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangxin
 */
public class Result<T> implements Serializable {

	private static final long serialVersionUID = -1L;

	private boolean success; // 操作是否成功
	private String msg; // 操作失败的原因
	private List<Object> otherInfoList; // 其他信息

	private String resultCode;//返回码
	
	private T data;//返回对象
	
	/**
	 * 异常对象不作序列化传输
	 */
	private transient Exception exception;//异常对象
	
	public Result(){}
	
	public Result<T> resultFrom(Result<?> result){
		this.success = result.isSuccess();
		this.msg = result.getMsg();
		return this;
	}
	
	public static <T> Result<T> create(Class<T> cls){
		return new Result<T>();
	}
	
	public static Result<Void> create(){
		return new Result<Void>();
	}
	
	public static Result<Void> createFail(Exception e){
		Result<Void> result = new Result<Void>();
		result.fail(e);
		return result;
	}
	
	public static Result<Void> createFail(String msg, Exception e){
		Result<Void> result = new Result<Void>();
		result.fail(msg,e);
		return result;
	}
	
	public Result<T> success(){
		this.success = true;
		return this;
	}
	
	public Result<T> success(T data){
		this.success = true;
		this.data = data;
		return this;
	}
	
	public Result<T> fail(){
		this.success = false;
		return this;
	}
	
	public Result<T> fail(String msg){
		this.success = false;
		this.msg = msg;
		return this;
	}
	
	public Result<T> fail(Exception e){
		this.success = false;
		if(e != null){
			this.exception = e;
			this.msg = e.getMessage();
		}
		return this;
	}
	
	public Result<T> fail(String msg, Exception e){
		this.success = false;
		this.msg = msg;
		if(e != null){
			this.exception = e;
		}
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public Result<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Result<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	
	public Result<T> appendMsg(String msg) {
		if(msg == null) {
			return this;
		}
		if(this.msg == null){
			this.msg = msg;
		}else{
			this.msg += msg;
		}
		this.msg += "\n";
		return this;
	}

	public List<Object> getOtherInfoList() {
		return otherInfoList;
	}

	public Result<T> setOtherInfoList(List<Object> otherInfoList) {
		this.otherInfoList = otherInfoList;
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public T getData() {
		return data;
	}

	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}
	
	public Result<T> setData(T data, boolean success, String msg) {
		this.data = data;
		this.success = success;
		this.msg = msg;
		return this;
	}

	public Exception getException() {
		return exception;
	}

	public Result<T> setException(Exception exception) {
		this.exception = exception;
		return this;
	}

	public String getResultCode() {
		return resultCode;
	}

	public Result<T> setResultCode(String resultCode) {
		this.resultCode = resultCode;
		return this;
	}
}
