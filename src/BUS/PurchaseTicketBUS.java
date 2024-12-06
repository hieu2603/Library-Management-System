/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.BookItemDAO;
import DAO.PurchaseTicketDAO;
import DAO.PurchaseTicketDetailDAO;
import DTO.BookItemDTO;
import DTO.PurchaseTicketDTO;
import DTO.PurchaseTicketDetailDTO;
import java.util.ArrayList;

/**
 *
 * @author hieun
 */
public class PurchaseTicketBUS {

    private final PurchaseTicketDAO purchaseTicketDAO = new PurchaseTicketDAO();
    private final PurchaseTicketDetailDAO detailDAO = new PurchaseTicketDetailDAO();
    private final BookItemDAO bookItemDAO = new BookItemDAO();
    
    SupplierBUS supplierBUS = new SupplierBUS();
    StaffBUS staffBUS = new StaffBUS();

    public boolean addPurchaseTicket(PurchaseTicketDTO purchaseTicket) {
        return purchaseTicketDAO.add(purchaseTicket) > 0;
    }
    
    public boolean addWithLists(PurchaseTicketDTO purchaseTicket, ArrayList<PurchaseTicketDetailDTO> detailList, ArrayList<BookItemDTO> bookItemList) {
        if(purchaseTicketDAO.add(purchaseTicket) != 0) {
            detailDAO.addList(detailList);
            bookItemDAO.addList(bookItemList);
            return true;
        }
        return false;
    }
    
    public ArrayList<PurchaseTicketDTO> getAll() {
        return purchaseTicketDAO.getAll();
    }
    
    public PurchaseTicketDTO getById(int id) {
        return purchaseTicketDAO.getById(id);
    }
    
    public int getLastID() {
        return purchaseTicketDAO.getLastID();
    }
    
    public ArrayList<PurchaseTicketDTO> search(String text, String type) {
        ArrayList<PurchaseTicketDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(PurchaseTicketDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || supplierBUS.getNameByID(i.getSupplier_id()).toLowerCase().contains(text)
                            || staffBUS.getNameByID(i.getStaff_id()).toLowerCase().contains(text)
                            || i.getStatus().toLowerCase().contains(type)
                            )
                        result.add(i);
                }
            }
            case "Mã phiếu nhập" -> {
                for(PurchaseTicketDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Nhà cung cấp" -> {
                for(PurchaseTicketDTO i : getAll()) {
                    if(supplierBUS.getNameByID(i.getSupplier_id()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Nhân viên" -> {
                for(PurchaseTicketDTO i : getAll()) {
                    if(staffBUS.getNameByID(i.getStaff_id()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Trạng thái" -> {
                for(PurchaseTicketDTO i : getAll()) {
                    if(i.getStatus().toLowerCase().contains(type))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
}
