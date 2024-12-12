/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.PermissionDAO;
import DAO.PermissionDetailDAO;
import DTO.PermissionDTO;
import DTO.PermissionDetailDTO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Duc3m
 */
public class PermissionBUS {
    
    private final PermissionDAO permissionDAO = new PermissionDAO();
    private final PermissionDetailDAO permissionDetailDAO = new PermissionDetailDAO();
    
    public static PermissionBUS getInstance() {
        return new PermissionBUS();
    }
    
    public ArrayList<PermissionDTO> getAll() {
        return permissionDAO.getAll();
    }
    
    public PermissionDTO getById(int id) {
        return permissionDAO.getById(id);
    }
    
    public String getNameByID(int id) {
        return getById(id).getName();
    }
    
    public boolean add(PermissionDTO permission, ArrayList<PermissionDetailDTO> pdList) {
        if(permissionDAO.add(permission) != 0) {
            permissionDetailDAO.add(pdList);
            return true;
        }
        return false;
    }
    
    public boolean delete(int id) {
        if(!permissionDAO.checkDelete(id)) {
            JOptionPane.showMessageDialog(null, "Không thể xóa nhóm quyền này");
            return false;
        }
        return permissionDAO.delete(id) > 0;
    }
    
    public int addReturnId(PermissionDTO permission) {
        return permissionDAO.addReturnId(permission);
    }
    
    public boolean update(PermissionDTO permission, ArrayList<PermissionDetailDTO> pdList) {
        if(permissionDAO.update(permission) != 0) {
            permissionDetailDAO.update(pdList, permission.getId());
            return true;
        }
        return false;
    }
    
    public boolean functionCheck(ArrayList<PermissionDetailDTO> pdList, int functionID) {
        for(PermissionDetailDTO i : pdList)
            if(i.getFunction_id() == functionID)
                return true;
        return false;
    }
    
    public boolean actionCheck(ArrayList<PermissionDetailDTO> pdList, int functionId, String action) {
        for(PermissionDetailDTO i : pdList)
            if(i.getFunction_id()== functionId)
                if(i.getAction().equals(action))
                    return true;
        return false;
    }
    
    public ArrayList<PermissionDTO> search(String text, String type) {
        ArrayList<PermissionDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(PermissionDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || i.getName().toLowerCase().contains(text)
                            )
                        result.add(i);
                }
            }
            case "Mã nhóm quyền" -> {
                for(PermissionDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Tên nhóm quyền" -> {
                for(PermissionDTO i : getAll()) {
                    if(i.getName().toLowerCase().contains(text))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
}
