package BUS;

import DAO.PenaltyTicketDAO;
import DAO.PenaltyTicketDetailDAO;
import DTO.PenaltyTicketDTO;
import DTO.PenaltyTicketDetailDTO;
import java.util.ArrayList;

public class PenaltyTicketBUS {
    private final PenaltyTicketDAO penaltyTicketDAO = new PenaltyTicketDAO();
    private final PenaltyTicketDetailDAO detailDAO =  new PenaltyTicketDetailDAO();
    
    StaffBUS staffBUS = new StaffBUS();
    MemberBUS memberBUS = new MemberBUS();

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
    
    public ArrayList<PenaltyTicketDTO> search(String text, String type) {
        ArrayList<PenaltyTicketDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(PenaltyTicketDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || memberBUS.getNameByID(i.getMember_id()).toLowerCase().contains(text)
                            || staffBUS.getNameByID(i.getStaff_id()).toLowerCase().contains(text)
                            )
                        result.add(i);
                }
            }
            case "Mã phiếu phạt" -> {
                for(PenaltyTicketDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Thành viên" -> {
                for(PenaltyTicketDTO i : getAll()) {
                    if(memberBUS.getNameByID(i.getMember_id()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Nhân viên" -> {
                for(PenaltyTicketDTO i : getAll()) {
                    if(staffBUS.getNameByID(i.getStaff_id()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
}