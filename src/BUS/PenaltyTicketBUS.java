package BUS;

import DAO.PenaltyTicketDAO;
import DAO.PenaltyTicketDetailDAO;
import DTO.PenaltyTicketDTO;
import DTO.PenaltyTicketDetailDTO;
import java.util.ArrayList;

public class PenaltyTicketBUS {
    private final PenaltyTicketDAO penaltyTicketDAO = new PenaltyTicketDAO();
    private final PenaltyTicketDetailDAO detailDAO =  new PenaltyTicketDetailDAO();

    public boolean createPenaltyTicket(PenaltyTicketDTO penaltyTicket) {
        return penaltyTicketDAO.add(penaltyTicket) > 0;
    }

    public boolean removePenaltyTicket(PenaltyTicketDTO penaltyTicket) {
        return penaltyTicketDAO.remove(penaltyTicket) > 0;
    }

    public boolean updatePenaltyTicket(PenaltyTicketDTO penaltyTicket) {
        return penaltyTicketDAO.update(penaltyTicket) > 0;
    }

    public ArrayList<PenaltyTicketDTO> getAll() {
        return penaltyTicketDAO.getAll();
    }
    
    public PenaltyTicketDTO getByID(int id) {
        return penaltyTicketDAO.getById(id);
    }

    public ArrayList<PenaltyTicketDTO> filterPenaltyTicketByDate(String startDate, String endDate) {
        return penaltyTicketDAO.filterDate(startDate, endDate);
    }

    public ArrayList<PenaltyTicketDTO> filterPenaltyTicketDynamicType(int type, int id) {
        return penaltyTicketDAO.filterDynamic(type, id);
    }
    
    public boolean addWithDetail(PenaltyTicketDTO penaltyTicket, ArrayList<PenaltyTicketDetailDTO> detailList) {
        if(penaltyTicketDAO.add(penaltyTicket) != 0) {
            detailDAO.addList(detailList);
            return true;
        }
        return false;
    }
    
}