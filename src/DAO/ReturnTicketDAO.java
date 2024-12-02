/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ReturnTicketDTO;
import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Duc3m
 */
public class ReturnTicketDAO {

    public static ReturnTicketDAO getInstance() {
        return new ReturnTicketDAO();
    }

    public ArrayList<ReturnTicketDTO> getAll() {
        ArrayList<ReturnTicketDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM returnticket";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("return_ticket_id");
                int staff_id = rs.getInt("staff_id");
                int member_id = rs.getInt("member_id");
                Timestamp return_date = rs.getTimestamp("return_date");
                String status = rs.getString("status");

                ReturnTicketDTO returnTicket = new ReturnTicketDTO(id, staff_id, member_id, return_date, status);

                list.add(returnTicket);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public ReturnTicketDTO getById(int id) {
        ReturnTicketDTO returnTicket = null;

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM returnticket WHERE return_ticket_id = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id1 = rs.getInt("return_ticket_id");
                int staff_id = rs.getInt("staff_id");
                int member_id = rs.getInt("member_id");
                Timestamp return_date = rs.getTimestamp("return_date");
                String status = rs.getString("status");

                returnTicket = new ReturnTicketDTO(id1, staff_id, member_id, return_date, status);
            }

            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return returnTicket;
    }

    public int getLastID() {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM `returnticket` ORDER BY `return_ticket_id` DESC LIMIT 1";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result = rs.getInt("return_ticket_id");
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    public int add(ReturnTicketDTO returnTicket) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "INSERT INTO returnticket (staff_id, member_id, return_date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, returnTicket.getStaff_id());
            ps.setInt(2, returnTicket.getMember_id());
            ps.setTimestamp(3, returnTicket.getReturn_date());
            ps.setString(4, returnTicket.getStatus());

            result = ps.executeUpdate();

            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    public ArrayList<ReturnTicketDTO> getByMemberIDToPenalty(int member_id) {
        ArrayList<ReturnTicketDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT r.* FROM (\n"
                    + "	SELECT DISTINCT r.* FROM returnticket r, returnticket_details rd\n"
                    + "	WHERE r.return_ticket_id = rd.return_ticket_id\n"
                    + "	AND rd.`status` != 1 OR rd.days_passed != 0\n"
                    + ") r\n"
                    + "LEFT JOIN penaltyticket p\n"
                    + "ON r.return_ticket_id = p.return_ticket_id\n"
                    + "WHERE p.return_ticket_id IS NULL\n"
                    + "AND r.member_id = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, member_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("return_ticket_id");
                int staff_id = rs.getInt("staff_id");
                int member_id1 = rs.getInt("member_id");
                Timestamp return_date = rs.getTimestamp("return_date");
                String status = rs.getString("status");

                ReturnTicketDTO returnTicket = new ReturnTicketDTO(id, staff_id, member_id1, return_date, status);

                list.add(returnTicket);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

}
