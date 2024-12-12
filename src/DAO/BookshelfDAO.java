/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.BookshelfDTO;
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
public class BookshelfDAO {
    public ArrayList<BookshelfDTO> getAll() {
        ArrayList<BookshelfDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM `bookshelf`";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("bookshelf_id");
                String name = rs.getString("name");

                BookshelfDTO bookshelf = new BookshelfDTO(id, name);

                list.add(bookshelf);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    
    public BookshelfDTO getById(int id) {
        BookshelfDTO bookshelf = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM `bookshelf` WHERE bookshelf_id = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id); 
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int bookshelf_id = rs.getInt("bookshelf_id");
                String name = rs.getString("name");

                bookshelf = new BookshelfDTO(bookshelf_id, name);
            }
            
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return bookshelf;
    }
    
    public int update(BookshelfDTO bookshelf) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE `bookshelf` SET name = ? WHERE bookshelf_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, bookshelf.getName());
            ps.setInt(2, bookshelf.getId());
            
            result = ps.executeUpdate();
            
            Database.closeConnection(connection);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return result;
    }
    
    public int add(BookshelfDTO bookshelf) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "INSERT INTO `bookshelf` (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, bookshelf.getName());

            result = ps.executeUpdate();

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }
    
    public boolean checkDelete(int id) {
        boolean result = false;
        try {
            Connection conn = Database.getConnection();
            String checkQuery = "SELECT COUNT(*) FROM book WHERE bookshelf_id = ?";
            PreparedStatement ps = conn.prepareStatement(checkQuery);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                result = true;
            }
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public int delete(int id) {
        int result = 0;
        try {
            Connection conn = Database.getConnection();
            String deleteQuery = "DELETE FROM bookshelf WHERE bookshelf_id = ? ";
            PreparedStatement ps = conn.prepareStatement(deleteQuery);

            ps.setInt(1, id);
            result = ps.executeUpdate();

            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    
}
