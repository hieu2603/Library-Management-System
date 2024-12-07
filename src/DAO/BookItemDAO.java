/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.BookItemDTO;
import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Duc3m
 */
public class BookItemDAO {
    
    public static BookItemDAO getInstance() {
        return new BookItemDAO();
    }
    
    public ArrayList<BookItemDTO> getByBookId(int id) {
        ArrayList<BookItemDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM bookitem WHERE book_id = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String isbn = rs.getString("isbn");
                int book_id = rs.getInt("book_id");
                int purchaseticket_id = rs.getInt("purchaseticket_id");
                String status = rs.getString("status");
                long price = rs.getLong("price");
                
                BookItemDTO bookItem = new BookItemDTO(isbn, book_id, purchaseticket_id, status, price);

                list.add(bookItem);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    
    public int getAllCount() {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT COUNT(*) count FROM bookitem";

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
    
    public BookItemDTO getByISBN(String isbn) {
        BookItemDTO bookItem = null;

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM bookitem WHERE isbn LIKE ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, isbn);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String isbn1 = rs.getString("isbn");
                int book_id = rs.getInt("book_id");
                int purchaseticket_id = rs.getInt("purchaseticket_id");
                String status = rs.getString("status");
                long price = rs.getLong("price");
                
                bookItem = new BookItemDTO(isbn1, book_id, purchaseticket_id, status, price);

            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return bookItem;
    }
    
    public int addList(ArrayList<BookItemDTO> list) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            for (BookItemDTO i : list) {
                String query = "INSERT INTO bookitem (isbn, book_id, purchaseticket_id, status, price) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(query);

                ps.setString(1, i.getIsbn());
                ps.setInt(2, i.getBook_id());
                ps.setInt(3, i.getPurchaseticket_id());
                ps.setString(4, i.getStatus());
                ps.setLong(5, i.getPrice());

                result = ps.executeUpdate();
            }

            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }
    
    public int changeStatus(BookItemDTO bookItem, String status) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE bookitem SET status = ? WHERE isbn LIKE ?";
        
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, status);
            ps.setString(2, bookItem.getIsbn());
            
            result = ps.executeUpdate();
            
            Database.closeConnection(connection);
        
        } catch (SQLException e) {
            System.out.println(e); 
        }
        
        return result;
    }
    
}
