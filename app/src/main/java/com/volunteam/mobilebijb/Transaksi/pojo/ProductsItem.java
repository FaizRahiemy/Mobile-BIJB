package com.volunteam.mobilebijb.Transaksi.pojo;

public class ProductsItem{
	private String transaksi;
	private String produk;
	private String jumlah;
	private String id;

	public void setTransaksi(String transaksi){
		this.transaksi = transaksi;
	}

	public String getTransaksi(){
		return transaksi;
	}

	public void setProduk(String produk){
		this.produk = produk;
	}

	public String getProduk(){
		return produk;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"ProductsItem{" + 
			"transaksi = '" + transaksi + '\'' + 
			",produk = '" + produk + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
