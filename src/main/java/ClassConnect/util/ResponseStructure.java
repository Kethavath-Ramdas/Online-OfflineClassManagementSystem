package ClassConnect.util;


	import org.springframework.stereotype.Component;

	import com.fasterxml.jackson.annotation.JsonPropertyOrder;

	@Component
	@JsonPropertyOrder({ "statusCode", "message", "data" })
	public class ResponseStructure <T>{
		private int statusCode;
		private String message;
		private T data;
		public int getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		
	}


