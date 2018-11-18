package com.volunteam.mobilebijb.Travelling.PublicTransport.pojo.detail;

public class TransportDetailResponse{
	private Transport transport;
	private int statusCode;
	private String status;

	public void setTransport(Transport transport){
		this.transport = transport;
	}

	public Transport getTransport(){
		return transport;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"TransportDetailResponse{" + 
			"transport = '" + transport + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
