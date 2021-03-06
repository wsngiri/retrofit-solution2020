package id.putraprima.retrofit.api.models;

public class ErrorPassword{
	private int statusCode;
	private Error error;

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	public void setError(Error error){
		this.error = error;
	}

	public Error getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"ErrorPassword{" + 
			"status_code = '" + statusCode + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}
