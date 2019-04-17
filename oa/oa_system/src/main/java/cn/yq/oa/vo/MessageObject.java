package cn.yq.oa.vo;

public class MessageObject {
	Integer code;//状态码：1 表示成功 0，失败
	String message;//消息
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "MessageObject [code=" + code + ", message=" + message + "]";
	}
	public MessageObject(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	
}
