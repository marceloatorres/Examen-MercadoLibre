package response;

public class Response {
	private boolean success;
	private String message;
	//private Ip ip;
	
	public Response(boolean success, String message){//, Ip ip) {
		this.success = success;
		this.message = message;
		//this.ip = ip;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSucess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/*public Ip getIp() {
		return ip;
	}
	public void setIp(Ip ip) {
		this.ip = ip;
	}*/
	
}
