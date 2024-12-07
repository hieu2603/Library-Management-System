/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.Database;
import DTO.StaffDTO;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author hieun
 */
public class StaffDAO { 

    public int add(StaffDTO staff) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "INSERT INTO staff (full_name, email, phone, gender, birthday, address, hire_date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, staff.getFullName());
            ps.setString(2, staff.getEmail());
            ps.setString(3, staff.getPhone());
            ps.setString(4, staff.getGender());
            ps.setTimestamp(5, staff.getBirthday());
            ps.setString(6, staff.getAddress());
            ps.setTimestamp(7, staff.getHireDate());
            ps.setInt(8, 1); 

            result = ps.executeUpdate();

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    public int update(StaffDTO staff) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE staff SET full_name = ?, email = ?, phone = ?, gender = ?, birthday = ?, address = ?, status = ? WHERE staff_id = ?";
        
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, staff.getFullName());
            ps.setString(2, staff.getEmail());
            ps.setString(3, staff.getPhone());
            ps.setString(4, staff.getGender());
            ps.setTimestamp(5, staff.getBirthday());
            ps.setString(6, staff.getAddress());
            ps.setString(7, staff.getStatus());
            ps.setInt(8, staff.getId());
            
            result = ps.executeUpdate();
            
            Database.closeConnection(connection);
        
        } catch (SQLException e) {
            System.out.println(e); 
        }
        
        return result;
    }

    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public StaffDTO getById(int id) {
        StaffDTO staff = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM staff WHERE staff_id = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id); 
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int staff_id = rs.getInt("staff_id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                Timestamp birthday = rs.getTimestamp("birthday");
                String address = rs.getString("address");
                Timestamp hire_date = rs.getTimestamp("hire_date");
                String status = rs.getString("status");
                
                staff = new StaffDTO(staff_id, fullName, email, phone, gender, birthday, address, hire_date, status);
            }
            
            Database.closeConnection(connection); 
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return staff;
    }
    
    public ArrayList<StaffDTO> getByStatus(int status) {
        ArrayList<StaffDTO> list = new ArrayList<>();
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM staff WHERE status = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, status);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int staff_id = rs.getInt("staff_id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                Timestamp birthday = rs.getTimestamp("birthday");
                String address = rs.getString("address");
                Timestamp hire_date = rs.getTimestamp("hire_date");
                String status1 = rs.getString("status");
                
                StaffDTO staff = new StaffDTO(staff_id, fullName, email, phone, gender, birthday, address, hire_date, status1);
                
                list.add(staff);
            }
            
            Database.closeConnection(connection);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return list;
    }

    public ArrayList<StaffDTO> getAll() {
        ArrayList<StaffDTO> list = new ArrayList<>(); 
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM staff";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int staff_id = rs.getInt("staff_id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                Timestamp birthday = rs.getTimestamp("birthday");
                String address = rs.getString("address");
                Timestamp hire_date = rs.getTimestamp("hire_date");
                String status = rs.getString("status");
                
                StaffDTO staff = new StaffDTO(staff_id, fullName, email, phone, gender, birthday, address, hire_date, status);
                
                list.add(staff);
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

            String query = "SELECT COUNT(*) count FROM staff";

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
