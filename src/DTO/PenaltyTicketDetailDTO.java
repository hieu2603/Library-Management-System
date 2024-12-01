/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Duc3m
 */
public class PenaltyTicketDetailDTO {
    private int id;
    private int penaltyticket_id;
    private int penalty_id;
    private String isbn;
    private int fine;
    private int days_passed;

    public PenaltyTicketDetailDTO(int penaltyticket_id, int penalty_id, String isbn, int fine, int days_passed) {
        this.penaltyticket_id = penaltyticket_id;
        this.penalty_id = penalty_id;
        this.isbn = isbn;
        this.fine = fine;
        this.days_passed = days_passed;
    }

    public PenaltyTicketDetailDTO(int id, int penaltyticket_id, int penalty_id, String isbn, int fine, int days_passed) {
        this.id = id;
        this.penaltyticket_id = penaltyticket_id;
        this.penalty_id = penalty_id;
        this.isbn = isbn;
        this.fine = fine;
        this.days_passed = days_passed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPenaltyticket_id() {
        return penaltyticket_id;
    }

    public void setPenaltyticket_id(int penaltyticket_id) {
        this.penaltyticket_id = penaltyticket_id;
    }

    public int getPenalty_id() {
        return penalty_id;
    }

    public void setPenalty_id(int penalty_id) {
        this.penalty_id = penalty_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public int getDays_passed() {
        return days_passed;
    }

    public void setDays_passed(int days_passed) {
        this.days_passed = days_passed;
    }

    @Override
    public String toString() {
        return "PenaltyTicketDetailDTO{" + "id=" + id + ", penaltyticket_id=" + penaltyticket_id + ", penalty_id=" + penalty_id + ", isbn=" + isbn + ", fine=" + fine + ", days_passed=" + days_passed + '}';
    }

}
