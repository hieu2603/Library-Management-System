
package DAO;

import config.Database;
import DTO.PenaltyDTO;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class PenaltyDAO {
    
    public static PenaltyDAO getInstance() {
        return new PenaltyDAO();
    }

    // Add Penalty
    public int add(PenaltyDTO penalty) {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();

            String query = "INSERT INTO penalty (penalty_name, fine) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, penalty.getPenaltyName());
            ps.setDouble(2, penalty.getFine());

            result = ps.executeUpdate();

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    // Remove Penalty by Id
    public int remove(PenaltyDTO penalty) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "DELETE FROM penalty WHERE penalty_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, penalty.getId());

            result = ps.executeUpdate();

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    // Update Penalty by Id
    public int update(PenaltyDTO penalty) {
        int result = 0;

        try {
            Connection connection = Database.getConnection();

            String query = "UPDATE penalty SET penalty_name = ?, fine = ? WHERE penalty_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, penalty.getPenaltyName());
            ps.setDouble(2, penalty.getFine());

            result = ps.executeUpdate();

            Database.closeConnection(connection);
            
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    // Sort INCREASE
    public ArrayList<PenaltyDTO> sortIncrease() {
        ArrayList<PenaltyDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM penalty ORDER BY amount ASC";

            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("penalty_id");
                String penalty_name = rs.getString("penalty_name");
                int fine = rs.getInt("fine");

                PenaltyDTO penalty = new PenaltyDTO(id, penalty_name, fine);

                list.add(penalty);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    // Sort DECREASE
    public ArrayList<PenaltyDTO> sortDecrease() {
        ArrayList<PenaltyDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM penalty ORDER BY amount DESC";

            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("penalty_id");
                String penalty_name = rs.getString("penalty_name");
                int fine = rs.getInt("fine");

                PenaltyDTO penalty = new PenaltyDTO(id, penalty_name, fine);

                list.add(penalty);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    /// Get All Penalty
    public ArrayList<PenaltyDTO> getAll() {
        ArrayList<PenaltyDTO> list = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM penalty";

            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("penalty_id");
                String penalty_name = rs.getString("penalty_name");
                int fine = rs.getInt("fine");

                PenaltyDTO penalty = new PenaltyDTO(id, penalty_name, fine);

                list.add(penalty);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    /// Get Penalty by ID
    public ArrayList<PenaltyDTO> getById(int id) {
        ArrayList<PenaltyDTO> list = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM penalty WHERE penalty_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            
            while (rs.next()) {
                int id1 = rs.getInt("penalty_id");
                String penalty_name = rs.getString("penalty_name");
                int fine = rs.getInt("fine");

                PenaltyDTO penalty = new PenaltyDTO(id1, penalty_name, fine);
                list.add(penalty);
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list; 
    }
    
}