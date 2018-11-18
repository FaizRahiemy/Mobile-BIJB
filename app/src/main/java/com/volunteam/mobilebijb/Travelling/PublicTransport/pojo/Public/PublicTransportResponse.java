package com.volunteam.mobilebijb.Travelling.PublicTransport.pojo.Public;

import java.util.List;

public class PublicTransportResponse{
	private List<TransportItem> transport;
	private int statusCode;
	private String status;

	public void setTransport(List<TransportItem> transport){
		this.transport = transport;
	}

	public List<TransportItem> getTransport(){
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
			"PublicTransportResponse{" + 
			"transport = '" + transport + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}