package com.volunteam.mobilebijb.merchandise.pojo;

public class MerchsItem{
	private String image;
	private String harga;
	private String name;
	private String description;
	private String kategori;
	private String id;
	private String stok;

	public MerchsItem(String image, String harga, String name, String description, String kategori, String id, String stok) {
		this.image = image;
		this.harga = harga;
		this.name = name;
		this.description = description;
		this.kategori = kategori;
		this.id = id;
		this.stok = stok;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setStok(String stok){
		this.stok = stok;
	}

	public String getStok(){
		return stok;
	}

	@Override
 	public String toString(){
		return 
			"MerchsItem{" + 
			"image = '" + image + '\'' + 
			",harga = '" + harga + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",id = '" + id + '\'' + 
			",stok = '" + stok + '\'' + 
			"}";
		}
}
