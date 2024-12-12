/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.Database;
import DTO.AccountDTO;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author hieun
 */
public class AccountDAO {

    public int add(AccountDTO account) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "INSERT INTO account (username, password, permission_id, status, staff_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setInt(3, account.getPermission_id());
            ps.setInt(4, 1);  
            ps.setInt(5, account.getStaff_id());

            result = ps.executeUpdate();

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }
    
    public int update(AccountDTO account) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE account SET username = ?, password = ?, permission_id = ?, status = ?, staff_id = ? WHERE account_id = ?";
        
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setInt(3, account.getPermission_id());
            ps.setString(4, account.getStatus());
            ps.setInt(5, account.getStaff_id());
            ps.setInt(6, account.getId());
            
            result = ps.executeUpdate();
            
            Database.closeConnection(connection);
        
        } catch (SQLException e) {
            System.out.println(e); 
        }
        
        return result;
    }
    
    public int editStatus(int id, String status) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE account SET status = ? WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, status);
            ps.setInt(2, id);
            
            result = ps.executeUpdate();
            
            Database.closeConnection(connection);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return result;
    }

    public boolean checkLogIn(String username, String password) {
        boolean result = false;

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT username, password, status FROM account WHERE username = ? AND password = ? ";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password); 

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if(rs.getString("status").equals("Ngừng hoạt động")) {
                    JOptionPane.showMessageDialog(null, "Tài khoản đang bị khóa");
                    result = false;
                } else {
                    result = true;
                }
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }
    
    public AccountDTO getByUsernameAndPassword(String username, String password) {
        AccountDTO account = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM account WHERE username = ? AND password = ? ";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password); 
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int account_id = rs.getInt("account_id");
                String username1 = rs.getString("username");
                String password1 = rs.getString("password");
                int permission_id = rs.getInt("permission_id");
                String status = rs.getString("status");
                int staff_id = rs.getInt("staff_id");
                
                account = new AccountDTO(account_id, username1, password1, permission_id, status, staff_id);
            }
            
            Database.closeConnection(connection); 
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return account;
    }

    public boolean searchAccount(String username) {
        boolean result = false;

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT username FROM account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = true;
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }
    
    public ArrayList<AccountDTO> getAll() {
        ArrayList<AccountDTO> list = new ArrayList<>();
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM account";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {                
                int id = rs.getInt("account_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String status = rs.getString("status");
                int permission_id = rs.getInt("permission_id");
                int staff_id = rs.getInt("staff_id");
                AccountDTO account = new AccountDTO(id, username, password, permission_id, status, staff_id);
                
                list.add(account);
            }
            
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return list;
    }
    
    public AccountDTO getById(int id) {
        AccountDTO account = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM account WHERE account_id = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id); 
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int account_id = rs.getInt("account_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int permission_id = rs.getInt("permission_id");
                String status = rs.getString("status");
                int staff_id = rs.getInt("staff_id");
                
                account = new AccountDTO(account_id, username, password, permission_id, status, staff_id);
            }
            
            Database.closeConnection(connection); 
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return account;
    }
    
    public AccountDTO getByUsername(String username) {
        AccountDTO account = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM account WHERE username = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
     
            if (rs.next()) {
                int id = rs.getInt("account_id");
                String password = rs.getString("password");
                int permission_id = rs.getInt("permission_id");
                String status = rs.getString("status");
                int staff_id = rs.getInt("staff_id");
                
                account = new AccountDTO(id, username, password, permission_id, status, staff_id);
            }
            
            Database.closeConnection(connection);
            
        } catch (SQLException e) {
            System.out.println(e); 
        }
        
        return account;
    }
    
    public static boolean isUsernameDuplicate(String username) {
        String query = "SELECT 1 FROM account WHERE username = ? LIMIT 1";

        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có kết quả, username đã tồn tại.
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi nếu có
        }
        return false; // Trả về false nếu không tìm thấy username
    }
    
    public boolean checkStaffExisted(int id) {
        boolean result = false;
        try {
            Connection conn = Database.getConnection();
            String checkQuery = "SELECT COUNT(*) FROM account WHERE staff_id = ?";
            PreparedStatement ps = conn.prepareStatement(checkQuery);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
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
            String deleteQuery = "DELETE FROM account WHERE account_id = ? ";
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

