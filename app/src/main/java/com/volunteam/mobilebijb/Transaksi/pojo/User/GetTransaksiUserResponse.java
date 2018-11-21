package com.volunteam.mobilebijb.Transaksi.pojo.User;

import java.util.List;

public class GetTransaksiUserResponse{
	private List<TransaksiItem> transaksi;
	private int statusCode;
	private String status;

	public void setTransaksi(List<TransaksiItem> transaksi){
		this.transaksi = transaksi;
	}

	public List<TransaksiItem> getTransaksi(){
		return transaksi;
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
			"GetTransaksiUserResponse{" + 
			"transaksi = '" + transaksi + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}