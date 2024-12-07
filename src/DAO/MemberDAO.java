package DAO;

import config.Database;
import DTO.MemberDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MemberDAO {
    
    public static MemberDAO getInstance() {
        return new MemberDAO();
    }

    public int add(MemberDTO member) {
        int result = 0;
        try {
            Connection conn = Database.getConnection();

            String query = "INSERT INTO member(full_name, phone, email, address, membership_date, status, violationCount) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, member.getFull_name());
            ps.setString(2, member.getPhone());
            ps.setString(3, member.getEmail());
            ps.setString(4, member.getAddress());
            ps.setTimestamp(5, member.getMembership_date());
            ps.setInt(6, 1);
            ps.setInt(7, 0);

            result = ps.executeUpdate();

            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
        return result;
    }

    public int update(MemberDTO member) {
        int result = 0;
        try {
            Connection conn = Database.getConnection();
            String query = "UPDATE member SET full_name = ?, phone = ?, email = ?, address = ?, membership_date = ?, status = ?, violationCount = ? WHERE member_id = ?  ";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, member.getFull_name());
            ps.setString(2, member.getPhone());
            ps.setString(3, member.getEmail());
            ps.setString(4, member.getAddress());
            ps.setTimestamp(5, member.getMembership_date());
            ps.setString(6, member.getStatus());
            ps.setInt(7, member.getViolationCount());
            ps.setInt(8, member.getId());

            result = ps.executeUpdate();
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;

    }
    
    public int increaseViolationCount(int memberId) {
        int result = 0;
        try {
            Connection conn = Database.getConnection();
            String query = "UPDATE member SET violationCount = violationCount + 1 WHERE member_id = ?  ";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, memberId);

            result = ps.executeUpdate();
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;

    }

    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

    public MemberDTO getMemberById(int id) {
        MemberDTO member = null;

        try {
            Connection conn = Database.getConnection();
            String query = "SELECT * FROM member WHERE member_id = ? ";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int member_id = rs.getInt("member_id");
                String full_name = rs.getString("full_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                Timestamp membership_date = rs.getTimestamp("membership_date");
                String member_status = rs.getString("status");
                int violationCount = rs.getInt("violationCount");

                member = new MemberDTO(member_id, full_name, phone, email, address, membership_date, member_status, violationCount);

            }
            Database.closeConnection(conn);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return member;
    }

    public ArrayList<MemberDTO> getMemberByStatus(int status) {
        ArrayList<MemberDTO> list = new ArrayList<>();
        MemberDTO member = null;
        try {
            Connection conn = Database.getConnection();
            String query = "SELECT * FROM member WHERE status = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int member_id = rs.getInt("member_id");
                String full_name = rs.getString("full_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                Timestamp membership_date = rs.getTimestamp("membership_date");
                String member_status = rs.getString("status");
                int violationCount = rs.getInt("violationCount");

                member = new MemberDTO(member_id, full_name, phone, email, address, membership_date, member_status, violationCount);
                list.add(member);

            }

            Database.closeConnection(conn);

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public ArrayList<MemberDTO> getAllMember() {
        ArrayList<MemberDTO> list = new ArrayList<>();
        try {
            Connection conn = Database.getConnection();
            String query = "SELECT * FROM member";
            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int member_id = rs.getInt("member_id");
                String full_name = rs.getString("full_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                Timestamp membership_date = rs.getTimestamp("membership_date");
                String member_status = rs.getString("status");
                int violationCount = rs.getInt("violationCount");

                MemberDTO member = new MemberDTO(member_id, full_name, phone, email, address, membership_date, member_status, violationCount);
                list.add(member);
            }
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
        return list;

    }

    public ArrayList<MemberDTO> searchMembers(String keyword) {
        ArrayList<MemberDTO> list = new ArrayList<>();

        try {
            Connection conn = Database.getConnection();
            // Truy vấn tìm kiếm với LIKE để tìm kiếm các nhân viên chứa từ khóa
            String query = "SELECT * FROM member WHERE full_name LIKE ? OR phone LIKE ? OR address LIKE ? OR member_id LIKE ?";
            PreparedStatement ps = conn.prepareStatement(query);

            String searchPattern = "%" + keyword + "%"; // Thêm ký tự % để tìm kiếm chứa từ khóa
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            // Chuyển đổi từ khóa thành số nguyên nếu có thể để tìm theo ID
            try {
                int memberId = Integer.parseInt(keyword);
                ps.setInt(4, memberId); // Đặt ID nếu tìm kiếm là số
            } catch (NumberFormatException e) {
                ps.setNull(4, java.sql.Types.INTEGER); // Nếu không phải là số, đặt giá trị null
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                int id = rs.getInt("member_id"); 
                String fullName = rs.getString("full_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                Timestamp membershipDate = rs.getTimestamp("membership_date");
                String status = rs.getString("status");
                int violationCount = rs.getInt("violationCount");

                MemberDTO member = new MemberDTO(id, fullName, phone, email, address, membershipDate, status, violationCount);
                list.add(member);
            }

            Database.closeConnection(conn);
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }

        return list; // Trả về danh sách các nhân viên tìm thấy
    }
    
    public int getAllCount() {
        int result = 0;
        
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT COUNT(*) count FROM member";

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
