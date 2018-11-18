package com.volunteam.mobilebijb.BookTicket.model;

import java.util.List;

public class GetBandaraResponse{
	private int statusCode;
	private String status;
	private List<AirportsItem> airports;

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

	public void setAirports(List<AirportsItem> airports){
		this.airports = airports;
	}

	public List<AirportsItem> getAirports(){
		return airports;
	}

	@Override
 	public String toString(){
		return 
			"GetBandaraResponse{" + 
			"statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			",airports = '" + airports + '\'' + 
			"}";
		}
}