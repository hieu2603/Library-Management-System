/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.StaffDAO;
import DTO.StaffDTO;
import java.util.ArrayList;

/**
 *
 * @author hieun
 */
public class StaffBUS {
    
    private final StaffDAO staffDAO = new StaffDAO();
    
    public static StaffBUS getInstance() {
        return new StaffBUS();
    }

    public boolean createStaff(StaffDTO staff) {
        return staffDAO.add(staff) > 0;
    }

    public boolean updateStaff(StaffDTO staff) {
        return staffDAO.update(staff) > 0;
    }

    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public StaffDTO getById(int id) {
        return staffDAO.getById(id);
    }
    
    public String getNameByID(int id) {
        return staffDAO.getById(id).getFullName();
    }
    
    public ArrayList<StaffDTO> getByStatus(int status) {
        return staffDAO.getByStatus(status);
    }

    public ArrayList<StaffDTO> getAll() {
        return staffDAO.getAll();
    }
    
    public ArrayList<StaffDTO> search(String text, String type) {
        ArrayList<StaffDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(StaffDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || i.getFullName().toLowerCase().contains(text)
                            || i.getEmail().toLowerCase().contains(text)
                            || i.getPhone().contains(text)
                            || i.getAddress().toLowerCase().contains(text)
                            || i.getStatus().toLowerCase().contains(text)
                            )
                        result.add(i);
                }
            }
            case "Mã nhân viên" -> {
                for(StaffDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Họ tên" -> {
                for(StaffDTO i : getAll()) {
                    if(i.getFullName().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Email" -> {
                for(StaffDTO i : getAll()) {
                    if(i.getEmail().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Số điện thoại" -> {
                for(StaffDTO i : getAll()) {
                    if(i.getPhone().contains(text))
                        result.add(i);
                }
            }
            case "Địa chỉ" -> {
                for(StaffDTO i : getAll()) {
                    if(i.getAddress().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Trạng thái" -> {
                for(StaffDTO i : getAll()) {
                    if(i.getStatus().toLowerCase().contains(text))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
}
