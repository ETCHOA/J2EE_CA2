package com.example.CA2assignment.controller;


import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;
import databaseAccess.*;

@RestController
public class ApplicationController {
	@RequestMapping(
			path="/getBook/{id}",
			method = RequestMethod.GET)
	public Book getBook(@PathVariable String id) {
		Book book = new Book();
		
		try {
			DBbooks db = new DBbooks();
			book = db.getBookById(id);
			System.out.println("___getBookById___Function___Called___");
		} catch(Exception e) {
			System.out.println(e);
		}
		return book;
	}
	
	@RequestMapping(
			path="/getBooks",
			method = RequestMethod.GET)
	public ArrayList<Book> getBooks(@RequestParam("limit") String limitStr,@RequestParam("offset") String offsetStr) {
		ArrayList<Book> book_list = new ArrayList<Book>();
		try {
			DBbooks db = new DBbooks();
			int limit = Integer.parseInt(offsetStr);
			int offset = Integer.parseInt(limitStr);
			book_list = db.getBooks(limit,offset);
			System.out.println("___getBooks___Function___Called___");
		} catch(Exception e) {
			System.out.println(e);
		}
		return book_list;
	}
	
	@RequestMapping(
			path="/createBook",
			consumes = "application/json",
			method = RequestMethod.POST)
	public String getBooks(@RequestBody Book book) {
		String statement = "";
		try {
			DBbooks db = new DBbooks();
			
			int rowsChanged = db.createBook(book);
			System.out.println("___createBook___Function___Called___");
			if(rowsChanged > 0) {
				statement = "Books successfully added";
			} else {
				statement = "Some error";
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return statement;
	}
	
	@RequestMapping(
			path="/updateBook/{idStr}",
			consumes = "application/json",
			method = RequestMethod.PUT)
	public String updateBook(@RequestBody Book book,@PathVariable String idStr) {
		String statement = "";
		try {
			DBbooks db = new DBbooks();
			int id = Integer.parseInt(idStr);
			int rowsChanged = db.updateBook(book,id);
			System.out.println("___updateBook___Function___Called___");
			if(rowsChanged > 0) {
				statement = "Books successfully updated";
			} else {
				statement = "Some error";
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		return statement;
	}
	
	@RequestMapping(
			path="/deleteBook/{idStr}",
			method = RequestMethod.DELETE)
	public String deleteBook(@PathVariable String idStr) {
		String statement = "";
		try {
			DBbooks db = new DBbooks();
			int id = Integer.parseInt(idStr);
			int rowsChanged = db.deleteBook(id);
			System.out.println("___deleteBook___Function___Called___");
			if(rowsChanged > 0) {
				statement = "Books successfully deleted";
			} else {
				statement = "Some error";
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		return statement;
	}
	
	@RequestMapping(
			path="/getTopBooks",
			method = RequestMethod.GET)
	public ArrayList<Book> getTopBooks(@RequestParam("limit") String limitStr){
		ArrayList<Book> book_list = new ArrayList<Book>();
		try {
			DBbooks db = new DBbooks();
			int limit = Integer.parseInt(limitStr);
			book_list = db.getTopBooksSales(limit);
			System.out.println("___getTopTenBooksSales___Function___Called___");
		} catch(Exception e) {
			System.out.println(e);
		}
		return book_list;
	}
	
	@RequestMapping(
			path="/checkStockLevel/{levelStr}",
			method = RequestMethod.GET)
	public ArrayList<Book> checkStockLevel(@PathVariable String levelStr,@RequestParam("offset") String offsetStr){
		ArrayList<Book> book_list = new ArrayList<Book>();
		//System.out.println("___checkStockLevels___Function___Called___2");
		try {
			DBbooks db = new DBbooks();
			int level = Integer.parseInt(levelStr);
			int offset = Integer.parseInt(offsetStr);
			book_list = db.checkStockLevel(level, offset);
			System.out.println("___checkStockLevels___Function___Called___");
		} catch(Exception e) {
			System.out.println(e);
		}
		return book_list;
	}
	
	
}
