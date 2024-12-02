package BUS;

import DAO.BookStatisticsDAO;
import DTO.BookStatisticsDTO;

public class BookStatisticsBUS {
    private final BookStatisticsDAO dao;

    public BookStatisticsBUS() {
        this.dao = new BookStatisticsDAO();
    }

    public BookStatisticsDTO getBookStatistics() {
        return dao.getBookStatistics();
    }

    // Optional: Add method to get percentage statistics
    public double getAvailableBooksPercentage() {
        BookStatisticsDTO stats = getBookStatistics();
        return stats.getAvailableBooksPercentage();
    }

    public double getBorrowedBooksPercentage() {
        BookStatisticsDTO stats = getBookStatistics();
        return stats.getBorrowedBooksPercentage(); 
    }
}