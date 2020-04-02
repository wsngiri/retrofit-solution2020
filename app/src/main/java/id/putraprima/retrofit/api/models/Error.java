package id.putraprima.retrofit.api.models;

import java.util.List;

public class Error{
	private int statusCode;
	private Error error;
	private List<String> password;
	private List<String> name;
	private List<String> email;

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

	public void setPassword(List<String> password){
		this.password = password;
	}

	public List<String> getPassword(){
		return password;
	}

	public void setName(List<String> name){
		this.name = name;
	}

	public List<String> getName(){
		return name;
	}

	public void setEmail(List<String> email){
		this.email = email;
	}

	public List<String> getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return
			"Error{" +
			"status_code = '" + statusCode + '\'' +
			",error = '" + error + '\'' +
			",password = '" + password + '\'' +
			",name = '" + name + '\'' +
			",email = '" + email + '\'' +
			"}";
		}
}
