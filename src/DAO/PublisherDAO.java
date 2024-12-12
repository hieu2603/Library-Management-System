/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.PublisherDTO;
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
public class PublisherDAO {
    
    public ArrayList<PublisherDTO> getAll() {
        ArrayList<PublisherDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM `publisher`";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("publisher_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                PublisherDTO publisher = new PublisherDTO(id, name, address, phone);

                list.add(publisher);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    
    public PublisherDTO getById(int id) {
        PublisherDTO publisher = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM `publisher` WHERE publisher_id = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id); 
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int id1 = rs.getInt("publisher_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                publisher = new PublisherDTO(id1, name, address, phone);
            }
            
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return publisher;
    }
    
    public int add(PublisherDTO publisher) {
        int result = 0;
        try {
            Connection conn = Database.getConnection();
            String query = "INSERT INTO publisher (name, address, phone) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, publisher.getName());
            ps.setString(2, publisher.getAddress());
            ps.setString(3, publisher.getPhone());

            result = ps.executeUpdate();
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    
    public int update(PublisherDTO publisher) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE publisher SET name = ?, address = ?, phone = ? WHERE publisher_id = ?";
        
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, publisher.getName());
            ps.setString(2, publisher.getAddress());
            ps.setString(3, publisher.getPhone());
            ps.setInt(4, publisher.getId());
            
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
            String checkQuery = "SELECT COUNT(*) FROM book WHERE supplier_id = ?";
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
            String deleteQuery = "DELETE FROM publisher WHERE publisher_id = ? ";
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
