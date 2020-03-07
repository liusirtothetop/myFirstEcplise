package com.liusir.bookstore.domain;

import java.util.Date;

public class Book {
	private Integer Id;
	private String Author;
	
	private String Title;
	private float Price;
	
	private Date PublishingDate;
	private int SalesAmount;
	
	private int StoreNumber;
	private String Remark;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}
	public Date getPublishingDate() {
		return PublishingDate;
	}
	public void setPublishingDate(Date publishingDate) {
		PublishingDate = publishingDate;
	}
	public int getSalesAmount() {
		return SalesAmount;
	}
	public void setSalesAmount(int salesAmount) {
		SalesAmount = salesAmount;
	}
	public int getStoreNumber() {
		return StoreNumber;
	}
	public void setStoreNumber(int storeNumber) {
		StoreNumber = storeNumber;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	@Override
	public String toString() {
		return "Book [Id=" + Id + ", Author=" + Author + ", Title=" + Title + ", Price=" + Price + ", PublishingDate="
				+ PublishingDate + ", SalesAmount=" + SalesAmount + ", StoreNumber=" + StoreNumber + ", Remark="
				+ Remark + "]";
	}
	

}
