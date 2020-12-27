package response;

public class ResponseLong extends Response implements IResponse{
	private long result;

	public ResponseLong(boolean success, String message, long result) {
		super(success, message);
		this.result = result;
	}

	public long getResult() {
		return result;
	}

	public void setResult(long value) {
		this.result = value;
	}
}
