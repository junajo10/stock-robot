package model.android;

public class NetworkObject {
	String networkCode = "";
	String data;
	public NetworkObject(String code, String data){
		this.networkCode = code;
		this.data = data;
	}
	
	public String getCode(){
		return networkCode;
	}
	
	public String getData(){
		return data;
	}
	
	public String getMessage(){
		return networkCode + data;
	}
}
