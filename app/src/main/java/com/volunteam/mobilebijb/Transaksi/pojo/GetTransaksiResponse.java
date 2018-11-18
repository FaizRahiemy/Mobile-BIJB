package com.volunteam.mobilebijb.Transaksi.pojo;

import java.util.List;

public class GetTransaksiResponse{
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
			"GetTransaksiResponse{" + 
			"transaksi = '" + transaksi + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}