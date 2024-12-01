package BUS;

import DAO.PenaltyDAO;
import DTO.PenaltyDTO;
import java.util.ArrayList;

public class PenaltyBUS {
    private final PenaltyDAO penaltyDAO = new PenaltyDAO();
    
    public static PenaltyBUS getInstance() {
        return new PenaltyBUS();
    }
    
    public int getFineFromStatus(String status) {
        ArrayList<PenaltyDTO> penaltyList = getAll();
        for(PenaltyDTO i : penaltyList) {
            if(status.equals(i.getPenaltyName()))
                return i.getFine();
        }
        return 0;
    }
    
    public int getIdFromStatus(String status) {
        ArrayList<PenaltyDTO> penaltyList = getAll();
        for(PenaltyDTO i : penaltyList) {
            if(status.equals(i.getPenaltyName()))
                return i.getId();
        }
        return 0;
    }
    
    public String getNameFromId(int id) {
        ArrayList<PenaltyDTO> penaltyList = getAll();
        for(PenaltyDTO i : penaltyList) {
            if(id == i.getId())
                return i.getPenaltyName();
        }
        return "";
    }

    public boolean createPenalty(PenaltyDTO penalty) {
        return penaltyDAO.add(penalty) > 0;
    }

    public boolean removePenalty(PenaltyDTO penalty) {
        return penaltyDAO.remove(penalty) > 0;
    }

    public boolean updatePenalty(PenaltyDTO penalty) {
        return penaltyDAO.update(penalty) > 0;
    }

    public ArrayList<PenaltyDTO> sortPenaltyOrderByInc() {
        return penaltyDAO.sortIncrease();
    }

    public ArrayList<PenaltyDTO> sortPenaltyOrderByDesc() {
        return penaltyDAO.sortDecrease();
    }

    public ArrayList<PenaltyDTO> getAll() {
        return penaltyDAO.getAll();
    }

    public ArrayList<PenaltyDTO> getById(int id) {
        return penaltyDAO.getById(id);
    }
}