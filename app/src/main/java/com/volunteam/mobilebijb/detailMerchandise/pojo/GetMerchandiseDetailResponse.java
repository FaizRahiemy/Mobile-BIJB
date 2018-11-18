package com.volunteam.mobilebijb.detailMerchandise.pojo;

public class GetMerchandiseDetailResponse{
	private Merchs merchs;
	private int statusCode;
	private String status;

	public void setMerchs(Merchs merchs){
		this.merchs = merchs;
	}

	public Merchs getMerchs(){
		return merchs;
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
			"GetMerchandiseDetailResponse{" + 
			"merchs = '" + merchs + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
