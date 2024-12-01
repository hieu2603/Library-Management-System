package DTO;

public class PenaltyDTO {
    private int id;
    private String name;
    private int fine;

    public PenaltyDTO(int id, String penalty_name, int fine) {
        this.id = id;
        this.name = penalty_name;
        this.fine = fine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPenaltyName() {
        return name;
    }

    public void setPenaltyName(String penalty_name) {
        this.name =  penalty_name;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
    
}