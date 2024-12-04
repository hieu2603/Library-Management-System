// File: BookStatisticsDTO.java
package DTO;

import java.io.Serializable;

public class BookStatisticsDTO implements Serializable {
    private int totalBooks;
    private int totalAvailableBooks;
    private int totalBorrowedBooks;
    private int totalBorrowTickets;
    private double penaltyRevenue;
    private int totalDamagedLostBooks;

    // Constructor
    public BookStatisticsDTO(
        int totalBooks,
        int totalAvailableBooks,
        int totalBorrowedBooks,
        int totalBorrowTickets,
        double penaltyRevenue,
        int totalDamagedLostBooks
    ) {
        this.totalBooks = totalBooks;
        this.totalAvailableBooks = totalAvailableBooks;
        this.totalBorrowedBooks = totalBorrowedBooks;
        this.totalBorrowTickets = totalBorrowTickets;
        this.penaltyRevenue = penaltyRevenue;
        this.totalDamagedLostBooks = totalDamagedLostBooks;
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

    public int getTotalBorrowTickets() {
        return totalBorrowTickets;
    }

    public void setTotalBorrowTickets(int totalBorrowTickets) {
        this.totalBorrowTickets = totalBorrowTickets;
    }

    public double getPenaltyRevenue() {
        return penaltyRevenue;
    }

    public void setPenaltyRevenue(double penaltyRevenue) {
        this.penaltyRevenue = penaltyRevenue;
    }

    public int getTotalDamagedLostBooks() {
        return totalDamagedLostBooks;
    }

    public void setTotalDamagedLostBooks(int totalDamagedLostBooks) {
        this.totalDamagedLostBooks = totalDamagedLostBooks;
    }
}