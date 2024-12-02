package DTO;

import java.io.Serializable;
import java.util.Objects;

public class BookStatisticsDTO implements Serializable {
    private int totalBooks;
    private int totalAvailableBooks;
    private int totalBorrowedBooks;
    private int totalCategories;
    private int totalPublishers;

    public BookStatisticsDTO(int totalBooks, int totalAvailableBooks, int totalBorrowedBooks, int totalCategories, int totalPublishers) {
        this.totalBooks = totalBooks;
        this.totalAvailableBooks = totalAvailableBooks;
        this.totalBorrowedBooks = totalBorrowedBooks;
        this.totalCategories = totalCategories;
        this.totalPublishers = totalPublishers;
    }

    // Getters and Setters
    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    public int getTotalAvailableBooks() {
        return totalAvailableBooks;
    }

    public void setTotalAvailableBooks(int totalAvailableBooks) {
        this.totalAvailableBooks = totalAvailableBooks;
    }

    public int getTotalBorrowedBooks() {
        return totalBorrowedBooks;
    }

    public void setTotalBorrowedBooks(int totalBorrowedBooks) {
        this.totalBorrowedBooks = totalBorrowedBooks;
    }

    public int getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(int totalCategories) {
        this.totalCategories = totalCategories;
    }

    public int getTotalPublishers() {
        return totalPublishers;
    }

    public void setTotalPublishers(int totalPublishers) {
        this.totalPublishers = totalPublishers;
    }

    // Methods to calculate percentages
    public double getAvailableBooksPercentage() {
        if (totalBooks == 0) return 0;
        return ((double) totalAvailableBooks / totalBooks) * 100;
    }

    public double getBorrowedBooksPercentage() {
        if (totalBooks == 0) return 0;
        return ((double) totalBorrowedBooks / totalBooks) * 100;
    }

    @Override
    public String toString() {
        return "BookStatisticsDTO{" +
                "totalBooks=" + totalBooks +
                ", totalAvailableBooks=" + totalAvailableBooks +
                ", totalBorrowedBooks=" + totalBorrowedBooks +
                ", totalCategories=" + totalCategories +
                ", totalPublishers=" + totalPublishers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookStatisticsDTO that = (BookStatisticsDTO) o;

        return totalBooks == that.totalBooks &&
                totalAvailableBooks == that.totalAvailableBooks &&
                totalBorrowedBooks == that.totalBorrowedBooks &&
                totalCategories == that.totalCategories &&
                totalPublishers == that.totalPublishers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalBooks, totalAvailableBooks, totalBorrowedBooks, totalCategories, totalPublishers);
    }
}