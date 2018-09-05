package com.wzlab.smartcity.po;





/**
 * <b>alarm_log[alarm_log]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Administrator
 * @date 2018-08-06 22:34:53
 */
public class AlarmLog {



	private String user_address;
	/**
	 * 报警序号
	 */
	private String alarm_id;
	
	/**
	 * 设备id
	 */
	private String device_id;
	
	/**
	 * 用户手机号
	 */
	private String user_phone;
	
	/**
	 * 报警时间
	 */
	private String alarm_time;
	
	/**
	 * 出警时间
	 */
	private String response_time;
	
	/**
	 * 报警方式
	 */
	private String type;
	
	/**
	 * 处理者
	 */
	private String handler_;
	
	/**
	 * 处理者电话
	 */
	private String handler_phone;
	
	/**
	 * 报警原因
	 */
	private String reason;
	
	/**
	 * 报警解除
	 */
	private String alarm_release;
	
	/**
	 * 取消报警
	 */
	private String is_cancel;
	
	/**
	 * 备用1
	 */
	private String beiyong1_;
	
	/**
	 * 备用2
	 */
	private String beiyong2_;
	
	/**
	 * 备用3
	 */
	private String beiyong3_;


	// wzlab
	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}



	/**
	 * 报警序号
	 * 
	 * @return alarm_id
	 */
	public String getAlarm_id() {
		return alarm_id;
	}
	
	/**
	 * 设备id
	 * 
	 * @return device_id
	 */
	public String getDevice_id() {
		return device_id;
	}
	
	/**
	 * 用户手机号
	 * 
	 * @return user_phone
	 */
	public String getUser_phone() {
		return user_phone;
	}
	
	/**
	 * 报警时间
	 * 
	 * @return alarm_time
	 */
	public String getAlarm_time() {
		return alarm_time;
	}
	
	/**
	 * 出警时间
	 * 
	 * @return response_time
	 */
	public String getResponse_time() {
		return response_time;
	}
	
	/**
	 * 报警方式
	 * 
	 * @return type
	 */
	public String getType_() {
		return type;
	}
	
	/**
	 * 处理者
	 * 
	 * @return handler_
	 */
	public String getHandler_() {
		return handler_;
	}
	
	/**
	 * 处理者电话
	 * 
	 * @return handler_phone
	 */
	public String getHandler_phone() {
		return handler_phone;
	}
	
	/**
	 * 报警原因
	 * 
	 * @return reason
	 */
	public String getReason() {
		return reason;
	}
	
	/**
	 * 报警解除
	 * 
	 * @return alarm_release
	 */
	public String getAlarm_release() {
		return alarm_release;
	}
	
	/**
	 * 取消报警
	 * 
	 * @return is_cancel
	 */
	public String getIs_cancel() {
		return is_cancel;
	}
	
	/**
	 * 备用1
	 * 
	 * @return beiyong1_
	 */
	public String getBeiyong1_() {
		return beiyong1_;
	}
	
	/**
	 * 备用2
	 * 
	 * @return beiyong2_
	 */
	public String getBeiyong2_() {
		return beiyong2_;
	}
	
	/**
	 * 备用3
	 * 
	 * @return beiyong3_
	 */
	public String getBeiyong3_() {
		return beiyong3_;
	}
	

	/**
	 * 报警序号
	 * 
	 * @param alarm_id
	 */
	public void setAlarm_id(String alarm_id) {
		this.alarm_id = alarm_id;
	}
	
	/**
	 * 设备id
	 * 
	 * @param device_id
	 */
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	
	/**
	 * 用户手机号
	 * 
	 * @param user_phone
	 */
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	
	/**
	 * 报警时间
	 * 
	 * @param alarm_time
	 */
	public void setAlarm_time(String alarm_time) {
		this.alarm_time = alarm_time;
	}
	
	/**
	 * 出警时间
	 * 
	 * @param response_time
	 */
	public void setResponse_time(String response_time) {
		this.response_time = response_time;
	}
	
	/**
	 * 报警方式
	 * 
	 * @param type
	 */
	public void setType_(String type) {
		this.type = type;
	}
	
	/**
	 * 处理者
	 * 
	 * @param handler_
	 */
	public void setHandler_(String handler_) {
		this.handler_ = handler_;
	}
	
	/**
	 * 处理者电话
	 * 
	 * @param handler_phone
	 */
	public void setHandler_phone(String handler_phone) {
		this.handler_phone = handler_phone;
	}
	
	/**
	 * 报警原因
	 * 
	 * @param reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	/**
	 * 报警解除
	 * 
	 * @param alarm_release
	 */
	public void setAlarm_release(String alarm_release) {
		this.alarm_release = alarm_release;
	}
	
	/**
	 * 取消报警
	 * 
	 * @param is_cancel
	 */
	public void setIs_cancel(String is_cancel) {
		this.is_cancel = is_cancel;
	}
	
	/**
	 * 备用1
	 * 
	 * @param beiyong1_
	 */
	public void setBeiyong1_(String beiyong1_) {
		this.beiyong1_ = beiyong1_;
	}
	
	/**
	 * 备用2
	 * 
	 * @param beiyong2_
	 */
	public void setBeiyong2_(String beiyong2_) {
		this.beiyong2_ = beiyong2_;
	}
	
	/**
	 * 备用3
	 * 
	 * @param beiyong3_
	 */
	public void setBeiyong3_(String beiyong3_) {
		this.beiyong3_ = beiyong3_;
	}
	

}