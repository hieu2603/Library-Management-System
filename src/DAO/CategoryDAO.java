/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.CategoryDTO;
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
public class CategoryDAO {
    
    public ArrayList<CategoryDTO> getAll() {
        ArrayList<CategoryDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM `category`";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("name");

                CategoryDTO category = new CategoryDTO(id, name);

                list.add(category);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    
    public CategoryDTO getById(int id) {
        CategoryDTO category = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM `category` WHERE category_id = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id); 
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int id1 = rs.getInt("category_id");
                String name = rs.getString("name");

                category = new CategoryDTO(id1, name);
            }
            
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return category;
    }
    
    public int add(CategoryDTO category) {
        int result = 0;
        try {
            Connection conn = Database.getConnection();
            String query = "INSERT INTO category (name) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, category.getName());

            result = ps.executeUpdate();
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    
    public int update(CategoryDTO category) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE category SET name = ? WHERE category_id = ?";
        
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            
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
            String checkQuery = "SELECT COUNT(*) FROM book WHERE category_id = ?";
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
            String deleteQuery = "DELETE FROM category WHERE category_id = ? ";
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
