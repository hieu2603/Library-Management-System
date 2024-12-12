/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.Database;
import DTO.PermissionDTO;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author Duc3m
 */
public class PermissionDAO {
    
    public ArrayList<PermissionDTO> getAll() {
        ArrayList<PermissionDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM `permission`";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("permission_id");
                String name = rs.getString("permission_name");

                PermissionDTO permission = new PermissionDTO(id, name);

                list.add(permission);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    
    public PermissionDTO getById(int id) {
        PermissionDTO permission = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM `permission` WHERE permission_id = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id); 
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int permission_id = rs.getInt("permission_id");
                String permission_name = rs.getString("permission_name");

                permission = new PermissionDTO(permission_id, permission_name);
            }
            
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return permission;
    }
    
    public int update(PermissionDTO permission) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE `permission` SET permission_name = ? WHERE permission_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, permission.getName());
            ps.setInt(2, permission.getId());
            
            result = ps.executeUpdate();
            
            Database.closeConnection(connection);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return result;
    }
      
    public int add(PermissionDTO permission) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "INSERT INTO `permission` (permission_name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, permission.getName());

            result = ps.executeUpdate();

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }
    
    public int addReturnId(PermissionDTO permission) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "INSERT INTO `permission` (`permission_name`) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, permission.getName());

            ps.executeUpdate();

            query = "SELECT * FROM `permission` WHERE `permission_id` = LAST_INSERT_ID();";
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                result = rs.getInt("permission_id");
            }

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
            String checkQuery = "SELECT COUNT(*) FROM account WHERE permission_id = ?";
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
            String deleteQuery = "DELETE FROM permission_details WHERE permission_id = ? ";
            PreparedStatement ps = conn.prepareStatement(deleteQuery);

            ps.setInt(1, id);
            result += ps.executeUpdate();
            
            deleteQuery = "DELETE FROM permission WHERE permission_id = ? ";
            ps = conn.prepareStatement(deleteQuery);
            ps.setInt(1, id);
            result += ps.executeUpdate();

            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    
}
