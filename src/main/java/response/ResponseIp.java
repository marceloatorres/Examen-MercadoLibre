package response;

import model.Ip;

public class ResponseIp extends Response implements IResponse {
	private Ip ip;
	
	public ResponseIp(boolean success, String message, Ip ip) {
		super(success, message);
		this.ip = ip;
	}

	public Ip getIp() {
		return ip;
	}

	public void setIp(Ip ip) {
		this.ip = ip;
	}
}
