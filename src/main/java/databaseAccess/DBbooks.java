package databaseAccess;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DBbooks {
	public Book getBookById(String idStr) {
		Book book = new Book();
		try {
			int id = Integer.parseInt(idStr);
			Connection conn = DBConnection.getConnection();
			String sqlStr = "SELECT * FROM books WHERE ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, id);
			ResultSet resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				book.setId(resultSet.getInt("id"));
				book.setTitle(resultSet.getString("title"));
				book.setAuthor(resultSet.getString("author"));
				book.setPrice(resultSet.getDouble("price"));
				book.setQuantity(resultSet.getInt("quantity"));
				book.setPublisher(resultSet.getString("publisher"));
				book.setPublication_date(resultSet.getString("publication_date"));
				book.setIsbn(resultSet.getString("isbn"));
				book.setGenre(resultSet.getString("genre"));
				book.setRating(resultSet.getDouble("rating"));
				book.setDescription(resultSet.getString("description"));
				book.setImage(resultSet.getString("image"));
			}
			conn.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		return book;
	}
	
	public ArrayList<Book> getBooks(int offset,int limit){
		ArrayList<Book> book_list = new ArrayList<Book>();
		try {
			Connection conn = DBConnection.getConnection();
			String sqlStr = "SELECT * FROM books LIMIT ?,?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1,offset);
			pstmt.setInt(2,limit);
			ResultSet resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				Book book = new Book();
				book.setTitle(resultSet.getString("title"));
				book.setAuthor(resultSet.getString("author"));
				book.setPrice(resultSet.getDouble("price"));
				book.setQuantity(resultSet.getInt("quantity"));
				book.setPublisher(resultSet.getString("publisher"));
				book.setPublication_date(resultSet.getString("publication_date"));
				book.setIsbn(resultSet.getString("isbn"));
				book.setGenre(resultSet.getString("genre"));
				book.setRating(resultSet.getDouble("rating"));
				book.setDescription(resultSet.getString("description"));
				book.setImage(resultSet.getString("image"));
				book_list.add(book);
			}
			conn.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		return book_list;
	}
	
	public int createBook(Book book) {
		int rowsChanged = 0;
		try {
			Connection conn = DBConnection.getConnection();
			String sqlStr = "INSERT INTO BOOKS(title, author, price, quantity, publisher, publication_date, isbn, genre, rating, description, image) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getAuthor());
			pstmt.setDouble(3, book.getPrice());
			pstmt.setInt(4, book.getQuantity());
			pstmt.setString(5, book.getPublisher());
			pstmt.setString(6, book.getPublication_date());
			pstmt.setString(7, book.getIsbn());
			pstmt.setString(8, book.getGenre());
			pstmt.setDouble(9, book.getRating());
			pstmt.setString(10, book.getDescription());
			pstmt.setString(11, book.getImage());
			rowsChanged = pstmt.executeUpdate();
			conn.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		//return rowsChanged;
		return rowsChanged;
	} 
	
	public int updateBook(Book book,int id) {
		int rowsChanged = 0;
		try {
			Connection conn = DBConnection.getConnection();
			String sqlStr = "UPDATE books SET title=?, author=?, price=?, quantity=?, publisher=?, publication_date=?, isbn=?, genre=?, rating=?, description=?, image=? WHERE id=?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getAuthor());
			pstmt.setDouble(3, book.getPrice());
			pstmt.setInt(4, book.getQuantity());
			pstmt.setString(5, book.getPublisher());
			pstmt.setString(6, book.getPublication_date());
			pstmt.setString(7, book.getIsbn());
			pstmt.setString(8, book.getGenre());
			pstmt.setDouble(9, book.getRating());
			pstmt.setString(10, book.getDescription());
			pstmt.setString(11, book.getImage());
			pstmt.setInt(12, id);
			rowsChanged = pstmt.executeUpdate();
			conn.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		return rowsChanged;
	}
	
	public int deleteBook(int id) {
		int rowsChanged = 0;
		try {
			Connection conn = DBConnection.getConnection();
			String sqlStr = "DELETE FROM books WHERE id=?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, id);
			rowsChanged = pstmt.executeUpdate();
			conn.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		return rowsChanged;
	}
	
	public ArrayList<Book> getTopBooksSales(int limit){
		ArrayList<Book> book_list = new ArrayList<Book>();
		try {
			Connection conn = DBConnection.getConnection();
			String sqlStr = "SELECT books.*, SUM(books_transaction.quantity) AS total_quantity FROM books AS books LEFT JOIN books_transaction AS books_transaction ON books.id = books_transaction.book_id GROUP BY books.id HAVING total_quantity != 0 ORDER BY total_quantity DESC LIMIT ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, limit);
			ResultSet resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				//rating, description, image, total_quantity
				Book book = new Book();
				book.setId(resultSet.getInt("id"));
				book.setTitle(resultSet.getString("title"));
				book.setAuthor(resultSet.getString("author"));
				book.setPrice(resultSet.getDouble("price"));
				book.setQuantity(resultSet.getInt("quantity"));
				book.setPublisher(resultSet.getString("publisher"));
				book.setPublication_date(resultSet.getString("publication_date"));				book.setIsbn(resultSet.getString("isbn"));
				book.setGenre(resultSet.getString("genre"));
				book.setRating(resultSet.getDouble("rating"));
				book.setDescription(resultSet.getString("description"));
				book.setImage(resultSet.getString("image"));
				book.setMaxQuantity(resultSet.getInt("total_quantity"));
				book_list.add(book);
			}
			conn.close();
		} catch(Exception e) {
			
		}
		return book_list;
	}
	
	public ArrayList<Book> checkStockLevel(int level,int offset){
		ArrayList<Book> book_list = new ArrayList<Book>();
		try {
			Connection conn = DBConnection.getConnection();
			String sqlStr = "";
			sqlStr = "SELECT id,title,price,rating,genre,image,quantity FROM books where quantity < ? LIMIT ?,200 ";
			PreparedStatement ps = conn.prepareStatement(sqlStr);
			ps.setInt(1,level);
			ps.setInt(2, offset);
			ResultSet resultSet	= ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				double price = resultSet.getDouble("price");
				double rating = resultSet.getDouble("rating");
				String genre = resultSet.getString("genre");
				String image = resultSet.getString("image");
				int quantity = resultSet.getInt("quantity");
				//System.out.println(id);
				Book bookBean = new Book();
				bookBean.setId(id);
				bookBean.setTitle(title);
				bookBean.setPrice(price);
				bookBean.setRating(rating);
				bookBean.setGenre(genre);
				bookBean.setImage(image);
				bookBean.setQuantity(quantity);
				book_list.add(bookBean);
			}
			conn.close();
		} catch(Exception e) {
			
		}
		return book_list;
	}
}

