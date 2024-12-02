/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.PenaltyTicketDetailDAO;
import DTO.PenaltyTicketDetailDTO;
import java.util.ArrayList;

/**
 *
 * @author Duc3m
 */
public class PenaltyTicketDetailBUS {
    private final PenaltyTicketDetailDAO penaltyTicketDetailDAO = new PenaltyTicketDetailDAO();
    
    public ArrayList<PenaltyTicketDetailDTO> getByPenaltyTicketId(int id) {
        return penaltyTicketDetailDAO.getByPenaltyTicketId(id);
    }
    
    public boolean addList(ArrayList<PenaltyTicketDetailDTO> detailList) {
        return penaltyTicketDetailDAO.addList(detailList) != 0;
    }
    
}
