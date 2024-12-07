package DAO;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import config.Database;
import DTO.BookDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hieun
 */
public class BookDAO {
   
    public static BookDAO getInstance() {
        return new BookDAO();
    }
    
    public int add(BookDTO book) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
           
            String query = "INSERT INTO book (book_id, title, author, publisher_id, year_publish, category_id, quantity, book_image, bookshelf_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setInt(1, book.getId());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setInt(4, book.getPublisherId());
            ps.setInt(5, book.getYearPublish());
            ps.setInt(6, book.getCategoryId());
            ps.setInt(7, book.getQuantity());
            ps.setString(8, book.getImage());
            ps.setInt(9, book.getBookshelf_id());
            result = ps.executeUpdate();
            
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return result;
    }
    
    public int update(BookDTO book) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE `book` SET title = ?, author = ?, publisher_id = ?, year_publish = ?, category_id = ?, quantity = ?, book_image = ?, bookshelf_id = ? WHERE book_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, book.getTitle()); 
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublisherId());
            ps.setInt(4, book.getYearPublish());
            ps.setInt(5, book.getCategoryId());
            ps.setInt(6, book.getQuantity());
            ps.setString(7, book.getImage());
            ps.setInt(8, book.getBookshelf_id());
            ps.setInt(9, book.getId());
            
            result = ps.executeUpdate();
            
            Database.closeConnection(connection);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return result;
    }
    
    public int increaseQuantity(int id, int quantity) {
        BookDTO book = this.getById(id);
        int newQuantity = book.getQuantity() + quantity;
        int result = 0;
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE `book` SET quantity = ? WHERE book_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, newQuantity);
            ps.setInt(2, book.getId());
            
            result = ps.executeUpdate();
            
            Database.closeConnection(connection);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return result;
    }
    
    public ArrayList<BookDTO> getAll() {
        ArrayList<BookDTO> list = new ArrayList<>();
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM book";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {                
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int publisher_id = rs.getInt("publisher_id");
                int year = rs.getInt("year_publish");
                int category_id = rs.getInt("category_id");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("book_image");
                int bookshelf_id = rs.getInt("bookshelf_id");
                BookDTO book = new BookDTO(id, title, author, publisher_id, year, category_id, quantity, image, bookshelf_id);
                list.add(book);
            }
            
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return list;
    }
    
    public ArrayList<BookDTO> getByBookshelfID(int bookshelfID) {
        ArrayList<BookDTO> list = new ArrayList<>();
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM book WHERE bookshelf_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, bookshelfID);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {                
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int publisher_id = rs.getInt("publisher_id");
                int year = rs.getInt("year_publish");
                int category_id = rs.getInt("category_id");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("book_image");
                int bookshelf_id = rs.getInt("bookshelf_id");
                BookDTO book = new BookDTO(id, title, author, publisher_id, year, category_id, quantity, image, bookshelf_id);
                list.add(book);
            }
            
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return list;
    }
    
    public BookDTO getById(int id) {
        BookDTO book = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM `book` WHERE book_id = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id); 
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int id1 = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int publisher_id = rs.getInt("publisher_id");
                int year = rs.getInt("year_publish");
                int category_id = rs.getInt("category_id");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("book_image");
                int bookshelf_id = rs.getInt("bookshelf_id");
                book = new BookDTO(id1, title, author, publisher_id, year, category_id, quantity, image, bookshelf_id);
            }
            
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return book;
    }
    
    public BookDTO getByISBN(String isbn) {
        BookDTO book = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM `book`, `bookitem` WHERE isbn LIKE ? AND book.book_id = bookitem.book_id";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, isbn); 
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int publisher_id = rs.getInt("publisher_id");
                int year = rs.getInt("year_publish");
                int category_id = rs.getInt("category_id");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("book_image");
                int bookshelf_id = rs.getInt("bookshelf_id");
                book = new BookDTO(id, title, author, publisher_id, year, category_id, quantity, image, bookshelf_id);
            }
            
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return book;
    }
    
    public int getAllCount() {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT COUNT(*) count FROM book";

            PreparedStatement ps = connection.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result = rs.getInt("count");
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    
}
