// File: BookStatisticsBUS.java
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
}