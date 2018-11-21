package com.volunteam.mobilebijb.Transaksi.pojo.User;

import java.util.List;

public class TransaksiItem{
	private String date;
	private String id;
	private String user;
	private String status;
	private List<ProductsItem> products;
	private int jumlah;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUser(String user){
		this.user = user;
	}

	public String getUser(){
		return user;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setProducts(List<ProductsItem> products){
		this.products = products;
	}

	public List<ProductsItem> getProducts(){
		return products;
	}

	public int getJumlah() {
		return jumlah;
	}

	public void setJumlah(int jumlah) {
		this.jumlah = jumlah;
	}

	@Override
 	public String toString(){
		return 
			"TransaksiItem{" + 
			"date = '" + date + '\'' + 
			",id = '" + id + '\'' + 
			",user = '" + user + '\'' + 
			",status = '" + status + '\'' + 
			",products = '" + products + '\'' + 
			"}";
		}
}