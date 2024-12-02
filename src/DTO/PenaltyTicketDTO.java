package DTO;

import java.sql.Timestamp;

public class PenaltyTicketDTO {
    private int id;
    private int member_id;
    private int staff_id;
    private Timestamp penalty_date;
    private int returnTicket_id;
    private int total_fine;

    public PenaltyTicketDTO(int member_id, int staff_id, Timestamp penalty_date, int returnTicket_id, int total_fine) {
        this.member_id = member_id;
        this.staff_id = staff_id;
        this.penalty_date = penalty_date;
        this.returnTicket_id = returnTicket_id;
        this.total_fine = total_fine;
    }

    public PenaltyTicketDTO(int id, int member_id, int staff_id, Timestamp penalty_date, int returnTicket_id, int total_fine) {
        this.id = id;
        this.member_id = member_id;
        this.staff_id = staff_id;
        this.penalty_date = penalty_date;
        this.returnTicket_id = returnTicket_id;
        this.total_fine = total_fine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public Timestamp getPenalty_date() {
        return penalty_date;
    }

    public void setPenalty_date(Timestamp penalty_date) {
        this.penalty_date = penalty_date;
    }

    public int getReturnTicket_id() {
        return returnTicket_id;
    }

    public void setReturnTicket_id(int returnTicket_id) {
        this.returnTicket_id = returnTicket_id;
    }

    public int getTotal_fine() {
        return total_fine;
    }

    public void setTotal_fine(int total_fine) {
        this.total_fine = total_fine;
    }

    @Override
    public String toString() {
        return "PenaltyTicketDTO{" + "id=" + id + ", member_id=" + member_id + ", staff_id=" + staff_id + ", penalty_date=" + penalty_date + ", returnTicket_id=" + returnTicket_id + ", total_fine=" + total_fine + '}';
    }

}