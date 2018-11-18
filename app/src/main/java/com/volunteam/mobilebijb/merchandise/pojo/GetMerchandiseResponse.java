package com.volunteam.mobilebijb.merchandise.pojo;

import java.util.List;

public class GetMerchandiseResponse{
	private List<MerchsItem> merchs;
	private int statusCode;
	private String status;

	public void setMerchs(List<MerchsItem> merchs){
		this.merchs = merchs;
	}

	public List<MerchsItem> getMerchs(){
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
			"GetMerchandiseResponse{" + 
			"merchs = '" + merchs + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}