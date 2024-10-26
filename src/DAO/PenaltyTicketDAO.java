
package DAO;

import DTO.PenaltyTicketDTO;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PenaltyTicketDAO {

    // Add PenaltyTicket
    public int add(PenaltyTicketDTO penaltyTicket) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "INSET INTO penaltyticket (member_id, staff_id, penalty_id, penalty_date, note) VALUES (?, ?, ?, ?, ?)"
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, penaltyTicket.getMemberId());
            ps.setInt(2, penaltyTicket.getStaffId());
            ps.setInt(3, penaltyTicket.getPenaltyId());
            ps.setString(4, penaltyTicket.getPenaltyDate());
            ps.setString(5, penaltyTicket.getNote());

            result = ps.executeUpdate();

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

            String query = "UPDATE penaltyticket SET member_id = ?, staff_id = ?, penalty_id = ?, penalty_date = ?, note = ? WHERE penalty_ticket_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, penaltyTicket.getMemberId());
            ps.setInt(2, penaltyTicket.getStaffId());
            ps.setInt(3, penaltyTicket.getPenaltyId());
            ps.setString(4, penaltyTicket.getPenaltyDate());
            ps.setString(5, penaltyTicket.getNote());

            result = ps.executeUpdate();

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    // Reset PenaltyTicket
    public ArrayList<PenaltyTicketDTO> reset() {
        ArrayList<PenaltyTicketDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM penaltyticket";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String id = rs.getString("penalty_ticket_id");
                int member_id = rs.getInt("member_id");
                int staff_id = rs.getInt("staff_id");
                int penalty_id = rs.getInt("penalty_id");
                String penalty_date = rs.getString("penalty_date");
                String note = rs.getString("note");

                PenaltyTicketDTO penaltyTicket = new PenaltyTicketDTO(id, member_id, staff_id, penalty_id, penalty_date, note);

                list.add(penalty);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
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

            while(rs.next()) {
                String id = rs.getString("penalty_ticket_id");
                int member_id = rs.getInt("member_id");
                int staff_id = rs.getInt("staff_id");
                int penalty_id = rs.getInt("penalty_id");
                String penalty_date = rs.getString("penalty_date");
                String note = rs.getString("note");

                PenaltyTicketDTO penaltyTicket = new PenaltyTicketDTO(id, member_id, staff_id, penalty_id, penalty_date, note);
                
                list.add(penalty);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    // Filter by dynamic properties
    public ArrayList<PenaltyTicketDTO> fitlerDynamic(String type, String id) {
        ArrayList<PenaltyTicketDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM penaltyticket WHERE ? = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, type);
            ps.setString(2, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String id = rs.getString("penalty_ticket_id");
                int member_id = rs.getInt("member_id");
                int staff_id = rs.getInt("staff_id");
                int penalty_id = rs.getInt("penalty_id");
                String penalty_date = rs.getString("penalty_date");
                String note = rs.getString("note");

                PenaltyTicketDTO penaltyTicket = new PenaltyTicketDTO(id, member_id, staff_id, penalty_id, penalty_date, note);
                
                list.add(penalty);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
}