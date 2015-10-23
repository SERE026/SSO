package com.bs.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bs.api.model.Book;
import com.bs.api.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	public List<Book> findBooks() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format_new = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date_book = new Date();
		format.format(new Date());
		
		
		List<Book> books = new ArrayList<Book>();
		books.add(new Book("Practical API Design", "Jaroslav Tulach", "Apress", date_book, 75, 85));
		
		books.add(new Book("Effective Java", "Joshua Bloch", "Addison-Wesley Professional", format.parse("2008-05-28"), 55, 70));
		
		books.add(new Book("Java Concurrency in Practice", "Doug Lea", "Addison-Wesley Professional", date_book, 60, 60));
		
		books.add(new Book("Java Programming Language", "James Gosling", "Prentice Hall", format.parse("2005-08-27"), 65, 75));
		
		books.add(new Book("Domain-Driven Design", "Eric Evans", "Addison-Wesley Professional", format.parse("2003-08-30"), 70, 80));
		
		books.add(new Book("Agile Project Management with Scrum", "Ken Schwaber", "Microsoft Press", format.parse("2004-03-10"), 40, 80));
		
		books.add(new Book("J2EE Development without EJB", "Rod Johnson", "Wrox", format.parse("2011-09-17"), 40, 70));
		
		books.add(new Book("Design Patterns", "Erich Gamma", "Addison-Wesley Professional", format.parse("1994-11-10"), 60, 80));
		
		books.add(new Book("Agile Software Development, Principles, Patterns, and Practices", " Robert C. Martin", "Prentice Hall", format.parse("2002-10-25"), 80, 75));
		
		books.add(new Book("Design by Contract, by Example", "Richard Mitchell", "Addison-Wesley Publishing Company", format.parse("2001-10-22"), 50, 85));
		return books;
	}

}
