/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ReturnTicketDAO;
import DAO.ReturnTicketDetailDAO;
import DTO.ReturnTicketDTO;
import DTO.ReturnTicketDetailDTO;
import java.util.ArrayList;

/**
 *
 * @author Duc3m
 */
public class ReturnTicketBUS {
    
    private final ReturnTicketDAO returnTicketDAO = new ReturnTicketDAO();
    private final ReturnTicketDetailDAO detailDAO = new ReturnTicketDetailDAO();
    
    StaffBUS staffBUS = new StaffBUS();
    MemberBUS memberBUS = new MemberBUS();
    
    public static ReturnTicketBUS getInstance() {
        return new ReturnTicketBUS();
    }
    
    public ArrayList<ReturnTicketDTO> getAll() {
        return returnTicketDAO.getAll();
    }
    
    public ReturnTicketDTO getByID(int id) {
        return returnTicketDAO.getById(id);
    }
    
    public ArrayList<ReturnTicketDTO> getByMemberIDToPenalty(int member_id) {
        return returnTicketDAO.getByMemberIDToPenalty(member_id);
    }
    
    public boolean addWithDetail(ReturnTicketDTO returnTicket, ArrayList<ReturnTicketDetailDTO> detailList) {
        if(returnTicketDAO.add(returnTicket) != 0) {
            detailDAO.returnBooks(detailList);
            return true;
        }
        return false;
    }
    
    public ArrayList<ReturnTicketDTO> search(String text, String type) {
        ArrayList<ReturnTicketDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(ReturnTicketDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || staffBUS.getNameByID(i.getStaff_id()).toLowerCase().contains(text)
                            || memberBUS.getNameByID(i.getMember_id()).toLowerCase().contains(text)
                            || i.getStatus().toLowerCase().contains(type)
                            )
                        result.add(i);
                }
            }
            case "Mã phiếu trả" -> {
                for(ReturnTicketDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Nhân viên" -> {
                for(ReturnTicketDTO i : getAll()) {
                    if(staffBUS.getNameByID(i.getStaff_id()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Thành viên" -> {
                for(ReturnTicketDTO i : getAll()) {
                    if(memberBUS.getNameByID(i.getMember_id()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Trạng thái" -> {
                for(ReturnTicketDTO i : getAll()) {
                    if(i.getStatus().toLowerCase().contains(type))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
}
