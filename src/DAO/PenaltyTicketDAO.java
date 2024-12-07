package DAO;

import config.Database;
import DTO.PenaltyTicketDTO;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PenaltyTicketDAO {

    public static PenaltyTicketDAO getInstance() {
        return new PenaltyTicketDAO();
    }
    
    // Add PenaltyTicket
    public int add(PenaltyTicketDTO penaltyTicket) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "INSERT INTO penaltyticket (member_id, staff_id, penalty_date, return_ticket_id, total_fine) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, penaltyTicket.getMember_id());
            ps.setInt(2, penaltyTicket.getStaff_id());
            ps.setTimestamp(3, penaltyTicket.getPenalty_date());
            ps.setInt(4, penaltyTicket.getReturnTicket_id());
            ps.setInt(5, penaltyTicket.getTotal_fine());

            result = ps.executeUpdate();
            
            MemberDAO.getInstance().increaseViolationCount(penaltyTicket.getMember_id());

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    // Remove PenaltyTicket by Id
    public int remove(PenaltyTicketDTO penaltyTicket) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "DELETE FROM penaltyticket WHERE penalty_ticket_id = ?";

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, penaltyTicket.getId());

            result = ps.executeUpdate();

            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    // Update PenaltyTicket by Id
    public int update(PenaltyTicketDTO penaltyTicket) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "UPDATE penaltyticket SET member_id = ?, staff_id = ?, penalty_date = ?, return_ticket_id = ?, total_fine = ? WHERE penalty_ticket_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, penaltyTicket.getMember_id());
            ps.setInt(2, penaltyTicket.getStaff_id());
            ps.setTimestamp(3, penaltyTicket.getPenalty_date());
            ps.setInt(4, penaltyTicket.getReturnTicket_id());
            ps.setInt(5, penaltyTicket.getTotal_fine());
            ps.setInt(6, penaltyTicket.getId());

            result = ps.executeUpdate();

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }
    
    public PenaltyTicketDTO getById(int id) {
        PenaltyTicketDTO penaltyTicket = null;
        
        try {
            Connection connection = Database.getConnection();
            
            String query = "SELECT * FROM penaltyticket WHERE penalty_ticket_id = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id); 
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int id1 = rs.getInt("penalty_ticket_id");
                int member_id = rs.getInt("member_id");
                int staff_id = rs.getInt("staff_id");
                Timestamp penalty_date = rs.getTimestamp("penalty_date");
                int returnticket_id = rs.getInt("return_ticket_id");
                int total_fine = rs.getInt("total_fine");
                
                penaltyTicket = new PenaltyTicketDTO(id1, member_id, staff_id, penalty_date, returnticket_id, total_fine);
            }
            
            Database.closeConnection(connection); 
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return penaltyTicket;
    }

    // Reset PenaltyTicket
    public ArrayList<PenaltyTicketDTO> getAll() {
        ArrayList<PenaltyTicketDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM penaltyticket";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("penalty_ticket_id");
                int member_id = rs.getInt("member_id");
                int staff_id = rs.getInt("staff_id");
                Timestamp penalty_date = rs.getTimestamp("penalty_date");
                int returnTicket_id = rs.getInt("return_ticket_id");
                int fine = rs.getInt("total_fine");

                PenaltyTicketDTO penaltyTicket = new PenaltyTicketDTO(id, member_id, staff_id, penalty_date, returnTicket_id, fine);

                list.add(penaltyTicket);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    
    public int getLastID() {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM `penaltyticket` ORDER BY `penalty_ticket_id` DESC LIMIT 1";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                result = rs.getInt("penalty_ticket_id");
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    // Filter by date
    public ArrayList<PenaltyTicketDTO> filterDate(String startDate, String endDate) {
        ArrayList<PenaltyTicketDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM penaltyticket WHERE penalty_date >= ? AND penalty_date <= ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, startDate);
            ps.setString(2, endDate);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("penalty_ticket_id");
                int member_id = rs.getInt("member_id");
                int staff_id = rs.getInt("staff_id");
                Timestamp penalty_date = rs.getTimestamp("penalty_date");
                int returnTicket_id = rs.getInt("return_ticket_id");
                int fine = rs.getInt("total_fine");

                PenaltyTicketDTO penaltyTicket = new PenaltyTicketDTO(id, member_id, staff_id, penalty_date, returnTicket_id, fine);

                list.add(penaltyTicket);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    // Filter by dynamic properties
    public ArrayList<PenaltyTicketDTO> filterDynamic(int type, int id) {
        ArrayList<PenaltyTicketDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM penaltyticket WHERE ? = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, type);
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id1 = rs.getInt("penalty_ticket_id");
                int member_id = rs.getInt("member_id");
                int staff_id = rs.getInt("staff_id");
                Timestamp penalty_date = rs.getTimestamp("penalty_date");
                int returnTicket_id = rs.getInt("return_ticket_id");
                int fine = rs.getInt("total_fine");

                PenaltyTicketDTO penaltyTicket = new PenaltyTicketDTO(id1, member_id, staff_id, penalty_date, returnTicket_id, fine);

                list.add(penaltyTicket); // Correct the variable name to penaltyTicket
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

}
