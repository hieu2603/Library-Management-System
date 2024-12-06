/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.BorrowTicketDAO;
import DAO.BorrowTicketDetailDAO;
import DTO.BorrowTicketDTO;
import DTO.BorrowTicketDetailDTO;
import java.util.ArrayList;

/**
 *
 * @author Duc3m
 */
public class BorrowTicketBUS {
    private final BorrowTicketDAO borrowTicketDAO = new BorrowTicketDAO();
    private final BorrowTicketDetailDAO detailDAO = new BorrowTicketDetailDAO();
    
    StaffBUS staffBUS = new StaffBUS();
    MemberBUS memberBUS = new MemberBUS();
    
    public static BorrowTicketBUS getInstance() {
        return new BorrowTicketBUS();
    }   
    
    public int getLastID() {
        return borrowTicketDAO.getLastID();
    }
    
    public ArrayList<BorrowTicketDTO> getAll() {
        return borrowTicketDAO.getAll();
    }
    
    public BorrowTicketDTO getById(int id) {
        return borrowTicketDAO.getById(id);
    }
    
    public boolean addWithDetail(BorrowTicketDTO borrowTicket, ArrayList<BorrowTicketDetailDTO> detailList) {
        if(borrowTicketDAO.add(borrowTicket) != 0) {
            detailDAO.borrowBooks(detailList);
            return true;
        }
        return false;
    }
    
    public ArrayList<BorrowTicketDTO> search(String text, String type) {
        ArrayList<BorrowTicketDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(BorrowTicketDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || staffBUS.getNameByID(i.getStaff_id()).toLowerCase().contains(text)
                            || memberBUS.getNameByID(i.getMember_id()).toLowerCase().contains(text)
                            || i.getStatus().toLowerCase().contains(type)
                            )
                        result.add(i);
                }
            }
            case "Mã phiếu mượn" -> {
                for(BorrowTicketDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Nhân viên" -> {
                for(BorrowTicketDTO i : getAll()) {
                    if(staffBUS.getNameByID(i.getStaff_id()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Thành viên" -> {
                for(BorrowTicketDTO i : getAll()) {
                    if(memberBUS.getNameByID(i.getMember_id()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Trạng thái" -> {
                for(BorrowTicketDTO i : getAll()) {
                    if(i.getStatus().toLowerCase().contains(type))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
}
