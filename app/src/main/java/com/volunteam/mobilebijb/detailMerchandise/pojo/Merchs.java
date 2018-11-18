package com.volunteam.mobilebijb.detailMerchandise.pojo;

public class Merchs{
	private String image;
	private String harga;
	private String name;
	private String description;
	private String kategori;
	private String id;
	private String stok;

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
			"Merchs{" + 
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
