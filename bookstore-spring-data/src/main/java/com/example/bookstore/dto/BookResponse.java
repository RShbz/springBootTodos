package com.example.bookstore.dto;

import java.util.Objects;

//public record BookResponse (
//	Long id,
//	 String isbn,
//	 String author,
//	 String title,
//	 int pages,
//	 int year,
//	 double price,
//	 String cover){
//}
//

public class BookResponse {
	private Long id;
	private String isbn;
	private String author;
	private String title;
	private int pages;
	 private int year;
	private double price;
	private String cover;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookResponse other = (BookResponse) obj;
		return Objects.equals(isbn, other.isbn);
	}
	public BookResponse(Long id, String isbn, String author, String title, int pages, int year, double price,
			String cover) {
	
		this.id = id;
		this.isbn = isbn;
		this.author = author;
		this.title = title;
		this.pages = pages;
		this.year = year;
		this.price = price;
		this.cover = cover;
	}
	public BookResponse() {
	
	}

	
	
	
}

