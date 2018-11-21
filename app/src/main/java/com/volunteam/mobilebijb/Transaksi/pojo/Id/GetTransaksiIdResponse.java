package com.volunteam.mobilebijb.Transaksi.pojo.Id;

public class GetTransaksiIdResponse{
	private Transaksi transaksi;
	private int statusCode;
	private String status;

	public void setTransaksi(Transaksi transaksi){
		this.transaksi = transaksi;
	}

	public Transaksi getTransaksi(){
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
			"GetTransaksiIdResponse{" + 
			"transaksi = '" + transaksi + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
