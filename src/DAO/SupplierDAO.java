package DAO;

import config.Database;
import DTO.SupplierDTO;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SupplierDAO {

    public int add(SupplierDTO supplier) {
        int result = 0;
        try {
            Connection conn = Database.getConnection();
            String query = "INSERT INTO supplier (name, address, phone) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getAddress());
            ps.setString(3, supplier.getPhone());

            result = ps.executeUpdate();
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public int update(SupplierDTO supplier) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Database.getConnection();
            String query = "UPDATE supplier SET name = ?, address = ?, phone = ? WHERE supplier_id = ?";
            ps = conn.prepareStatement(query);

            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getAddress());
            ps.setString(3, supplier.getPhone());
            ps.setInt(4, supplier.getId());

            
            result = ps.executeUpdate(); 

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            // Đảm bảo đóng tài nguyên
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return result; // Trả về số hàng đã cập nhật
    }

    public int delete(int supplier_id) {
        int result = 0;
        try {
            Connection conn = Database.getConnection();
            String checkQuery = "SELECT COUNT (*) FROM borrowticket WHERE supplier_id = ?";
            String deleteQuery = "DELETE FROM supplier WHERE supplier_id = ? ";
            PreparedStatement ps1 = conn.prepareStatement(checkQuery);
            PreparedStatement ps2 = conn.prepareStatement(deleteQuery);

            ps1.setInt(1, supplier_id);
            ResultSet rs = ps1.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                ps2.setInt(1, supplier_id);
                result = ps2.executeUpdate();
            }
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    public SupplierDTO getSupplierById(int id) {
        SupplierDTO supplier = null;
        try {
            Connection conn = Database.getConnection();
            String query = "SELECT * FROM supplier WHERE supplier_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int supplier_id = rs.getInt("supplier_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                supplier = new SupplierDTO(supplier_id, name, address, phone);

            }
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return supplier;
    }

    public ArrayList<SupplierDTO> getAllSupplier() {
        ArrayList<SupplierDTO> list = new ArrayList<>();
        SupplierDTO supplier = null;

        try {
            Connection conn = Database.getConnection();
            String query = "SELECT * FROM supplier";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int supplier_id = rs.getInt("supplier_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                supplier = new SupplierDTO(supplier_id, name, address, phone);
                list.add(supplier);

            }
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    public ArrayList<SupplierDTO> searchSupplier(String keyword) {
       ArrayList<SupplierDTO> list = new ArrayList<>();
    
    try {
        Connection conn = Database.getConnection();
        // Truy vấn tìm kiếm với LIKE để tìm kiếm các nhà cung cấp chứa từ khóa
        String query = "SELECT * FROM supplier WHERE name LIKE ? OR address LIKE ? OR phone LIKE ? OR supplier_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        
        String searchPattern = "%" + keyword + "%"; // Thêm ký tự % để tìm kiếm chứa từ khóa
        ps.setString(1, searchPattern);
        ps.setString(2, searchPattern);
        ps.setString(3, searchPattern);
        
        // Chuyển đổi từ khóa thành số nguyên nếu có thể
        try {
            int supplierId = Integer.parseInt(keyword);
            ps.setInt(4, supplierId); // Đặt ID nếu tìm kiếm là số
        } catch (NumberFormatException e) {
            ps.setNull(4, java.sql.Types.INTEGER); // Nếu không phải là số, đặt giá trị null
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int supplier_id = rs.getInt("supplier_id");
            String name = rs.getString("name");
            String address = rs.getString("address");
            String phone = rs.getString("phone");

            SupplierDTO supplier = new SupplierDTO(supplier_id, name, address, phone);
            list.add(supplier);
        }

        Database.closeConnection(conn);
    } catch (SQLException e) {
        System.out.println(e);
    }
    
    return list; // Trả về danh sách các nhà cung cấp tìm thấy
}
    
    public static boolean isNameDuplicate(String name) {
    String query = "SELECT 1 FROM supplier WHERE name = ? LIMIT 1";
    
    try (Connection conn = Database.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        
        ps.setString(1, name);
        
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next(); // Nếu có kết quả, username đã tồn tại.
        }
        
    } catch (SQLException e) {
        e.printStackTrace(); // Log lỗi nếu có
    }
    return false; // Trả về false nếu không tìm thấy username
    }
    
    public static boolean isPhoneDuplicate(String phone) {
    String query = "SELECT 1 FROM supplier WHERE phone = ? LIMIT 1";  

    try (Connection conn = Database.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setString(1, phone);  

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next(); 
        }

    } catch (SQLException e) {
        e.printStackTrace();  
    }
    return false;  
}


}
