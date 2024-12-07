/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.BorrowTicketDetailDTO;
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
public class BorrowTicketDetailDAO {
    
    public static BorrowTicketDetailDAO getInstance() {
        return new BorrowTicketDetailDAO();
    }
    
    public ArrayList<BorrowTicketDetailDTO> getByBorrowTicketId(int id) {
        ArrayList<BorrowTicketDetailDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM borrowticket_details WHERE borrow_ticket_id = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int borrow_ticket_details_id = rs.getInt("borrow_ticket_details_id");
                int borrow_ticket_id = rs.getInt("borrow_ticket_id");
                String isbn = rs.getString("isbn");
                String status = rs.getString("status");

                BorrowTicketDetailDTO borrowticket_detail = new BorrowTicketDetailDTO(borrow_ticket_details_id, borrow_ticket_id, isbn, status);

                list.add(borrowticket_detail);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    
    public BorrowTicketDetailDTO getByTicketIdAndISBN(int id, String isbn) {
        BorrowTicketDetailDTO result = null;

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM borrowticket_details WHERE borrow_ticket_id = ? AND isbn LIKE ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, isbn);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int borrow_ticket_details_id = rs.getInt("borrow_ticket_details_id");
                int borrow_ticket_id = rs.getInt("borrow_ticket_id");
                String isbn1 = rs.getString("isbn");
                String status = rs.getString("status");

                result = new BorrowTicketDetailDTO(borrow_ticket_details_id, borrow_ticket_id, isbn1, status);

            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }
    
    public ArrayList<BorrowTicketDetailDTO> getNotReturnedByMemberId(int id) {
        ArrayList<BorrowTicketDetailDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM `borrowticket` br, `borrowticket_details` bd WHERE br.borrow_ticket_id = bd.borrow_ticket_id AND br.member_id = ? AND bd.status = 1";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int borrow_ticket_details_id = rs.getInt("borrow_ticket_details_id");
                int borrow_ticket_id = rs.getInt("borrow_ticket_id");
                String isbn = rs.getString("isbn");
                String status = rs.getString("bd.status");

                BorrowTicketDetailDTO borrowticket_detail = new BorrowTicketDetailDTO(borrow_ticket_details_id, borrow_ticket_id, isbn, status);

                list.add(borrowticket_detail);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    
    public int borrowBooks(ArrayList<BorrowTicketDetailDTO> list) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            for (BorrowTicketDetailDTO i : list) {
                String query = "INSERT INTO borrowticket_details (borrow_ticket_id, isbn, status) VALUES (?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(query);
                
                ps.setInt(1, i.getBorrow_ticket_id());
                ps.setString(2, i.getIsbn());
                ps.setString(3, i.getStatus());
                result = ps.executeUpdate();
                
                BookItemDAO.getInstance().changeStatus(BookItemDAO.getInstance().getByISBN(i.getIsbn()), "Đang mượn");
            }

            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }
    
    public int changeStatus(BorrowTicketDetailDTO detail, String status) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "UPDATE borrowticket_details SET status = ? WHERE isbn LIKE ? AND borrow_ticket_id = ?";
        
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, status);
            ps.setString(2, detail.getIsbn());
            ps.setInt(3, detail.getBorrow_ticket_id());
            
            result = ps.executeUpdate();
            
            Database.closeConnection(connection);
        
        } catch (SQLException e) {
            System.out.println(e); 
        }
        
        return result;
    }
    
    public int getStatusCount(int id, String status) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT COUNT(*) count FROM borrowticket_details WHERE borrow_ticket_id = ? AND STATUS = ?";
        
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setInt(1, id);
            ps.setString(2, status);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                result = rs.getInt("count");
            }
            
            Database.closeConnection(connection);
        
        } catch (SQLException e) {
            System.out.println(e); 
        }
        
        return result;
    }
    
    public int getAllCount() {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT COUNT(*) count FROM borrowticket_details";

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
