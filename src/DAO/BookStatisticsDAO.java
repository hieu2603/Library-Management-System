// File: BookStatisticsDAO.java
package DAO;

import DTO.BookStatisticsDTO;
import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookStatisticsDAO {

    public BookStatisticsDTO getBookStatistics() {
        int totalBooks = 0;
        int totalAvailableBooks = 0;
        int totalBorrowedBooks = 0;
        int totalBorrowTickets = 0;
        double penaltyRevenue = 0.0;
        int totalDamagedLostBooks = 0;

        try {
            Connection connection = Database.getConnection();

            // Thống kê tổng số sách
            String totalBooksQuery = "SELECT COUNT(*) AS totalBooks FROM book";
            PreparedStatement psTotalBooks = connection.prepareStatement(totalBooksQuery);
            ResultSet rsTotalBooks = psTotalBooks.executeQuery();
            if (rsTotalBooks.next()) {
                totalBooks = rsTotalBooks.getInt("totalBooks");
            }

            // Thống kê sách có sẵn
            String totalAvailableBooksQuery = "SELECT COUNT(*) AS totalAvailableBooks FROM bookitem WHERE status = 'Có sẵn'";
            PreparedStatement psTotalAvailableBooks = connection.prepareStatement(totalAvailableBooksQuery);
            ResultSet rsTotalAvailableBooks = psTotalAvailableBooks.executeQuery();
            if (rsTotalAvailableBooks.next()) {
                totalAvailableBooks = rsTotalAvailableBooks.getInt("totalAvailableBooks");
            }

            // Thống kê tổng số sách đang mượn
            String totalBorrowedBooksQuery = "SELECT COUNT(*) AS totalBorrowedBooks FROM bookitem WHERE status = 'Đang mượn'";
            PreparedStatement psTotalBorrowedBooks = connection.prepareStatement(totalBorrowedBooksQuery);
            ResultSet rsTotalBorrowedBooks = psTotalBorrowedBooks.executeQuery();
            if (rsTotalBorrowedBooks.next()) {
                totalBorrowedBooks = rsTotalBorrowedBooks.getInt("totalBorrowedBooks");
            }

            // Thống kê tổng số phiếu mượn
            String totalBorrowTicketsQuery = "SELECT COUNT(*) AS totalBorrowTickets FROM borrowticket";
            PreparedStatement psTotalBorrowTickets = connection.prepareStatement(totalBorrowTicketsQuery);
            ResultSet rsTotalBorrowTickets = psTotalBorrowTickets.executeQuery();
            if (rsTotalBorrowTickets.next()) {
                totalBorrowTickets = rsTotalBorrowTickets.getInt("totalBorrowTickets");
            }

            // Thống kê doanh thu từ phiếu phạt
            String penaltyRevenueQuery = "SELECT SUM(total_fine) AS totalPenalty FROM penaltyticket";
            PreparedStatement psPenaltyRevenue = connection.prepareStatement(penaltyRevenueQuery);
            ResultSet rsPenaltyRevenue = psPenaltyRevenue.executeQuery();
            if (rsPenaltyRevenue.next()) {
                penaltyRevenue = rsPenaltyRevenue.getDouble("totalPenalty");
            }

            // Thống kê số sách hư hỏng hoặc mất
            String damagedLostBooksQuery = "SELECT COUNT(*) AS totalDamagedLostBooks FROM bookitem WHERE status IN ('Hư hỏng', 'Mất')";
            PreparedStatement psDamagedLostBooks = connection.prepareStatement(damagedLostBooksQuery);
            ResultSet rsDamagedLostBooks = psDamagedLostBooks.executeQuery();
            if (rsDamagedLostBooks.next()) {
                totalDamagedLostBooks = rsDamagedLostBooks.getInt("totalDamagedLostBooks");
            }

            Database.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println("Error fetching book statistics: " + e.getMessage());
            return null;
        }

        return new BookStatisticsDTO(
            totalBooks,
            totalAvailableBooks,
            totalBorrowedBooks,
            totalBorrowTickets,
            penaltyRevenue,
            totalDamagedLostBooks
        );
    }
}