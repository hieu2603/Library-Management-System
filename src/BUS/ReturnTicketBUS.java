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
    
    public boolean addWithDetail(ReturnTicketDTO borrowTicket, ArrayList<ReturnTicketDetailDTO> detailList) {
        if(returnTicketDAO.add(borrowTicket) != 0) {
            detailDAO.returnBooks(detailList);
            return true;
        }
        return false;
    }
    
}
