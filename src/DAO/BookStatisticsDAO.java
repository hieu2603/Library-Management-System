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
        int totalCategories = 0;
        int totalPublishers = 0;

        try {
            Connection connection = Database.getConnection();

            // Tổng số sách
            String totalBooksQuery = "SELECT COUNT(*) AS totalBooks FROM book";
            PreparedStatement psTotalBooks = connection.prepareStatement(totalBooksQuery);
            ResultSet rsTotalBooks = psTotalBooks.executeQuery();
            if (rsTotalBooks.next()) {
                totalBooks = rsTotalBooks.getInt("totalBooks");
            }

            // Số sách có sẵn
            String availableBooksQuery = "SELECT COUNT(*) AS totalAvailable FROM bookitem WHERE status = 'Có sẵn'";
            PreparedStatement psAvailableBooks = connection.prepareStatement(availableBooksQuery);
            ResultSet rsAvailableBooks = psAvailableBooks.executeQuery();
            if (rsAvailableBooks.next()) {
                totalAvailableBooks = rsAvailableBooks.getInt("totalAvailable");
            }

            // Số sách đang được mượn
            String borrowedBooksQuery = "SELECT COUNT(*) AS totalBorrowed FROM bookitem WHERE status = 'Đang mượn'";
            PreparedStatement psBorrowedBooks = connection.prepareStatement(borrowedBooksQuery);
            ResultSet rsBorrowedBooks = psBorrowedBooks.executeQuery();
            if (rsBorrowedBooks.next()) {
                totalBorrowedBooks = rsBorrowedBooks.getInt("totalBorrowed");
            }

            // Tổng số danh mục
            String categoriesQuery = "SELECT COUNT(*) AS totalCategories FROM category";
            PreparedStatement psCategories = connection.prepareStatement(categoriesQuery);
            ResultSet rsCategories = psCategories.executeQuery();
            if (rsCategories.next()) {
                totalCategories = rsCategories.getInt("totalCategories");
            }

            // Tổng số nhà xuất bản
            String publishersQuery = "SELECT COUNT(*) AS totalPublishers FROM publisher";
            PreparedStatement psPublishers = connection.prepareStatement(publishersQuery);
            ResultSet rsPublishers = psPublishers.executeQuery();
            if (rsPublishers.next()) {
                totalPublishers = rsPublishers.getInt("totalPublishers");
            }

            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return new BookStatisticsDTO(
            totalBooks,
            totalAvailableBooks,
            totalBorrowedBooks,
            totalCategories,
            totalPublishers
        );
    }
}