/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.AccountDAO;
import DTO.AccountDTO;
import java.util.ArrayList;

/**
 *
 * @author hieun
 */
public class AccountBUS {
    
    PermissionBUS permissionBUS = new PermissionBUS();
    StaffBUS staffBUS = new StaffBUS();
    
    private final AccountDAO accountDAO = new AccountDAO();
    
    public boolean createAccount(AccountDTO account) {
        return accountDAO.add(account) > 0; 
    }
    
    public boolean update(AccountDTO account) {
        return accountDAO.update(account) > 0;
    }
    
    public boolean updateStatusOfAccount(int id, String status) {
        return accountDAO.editStatus(id, status) > 0;
    }
    
    public boolean checkLogIn(String username, String password) {
        return accountDAO.checkLogIn(username, password);
    }
    
    public AccountDTO getById(int id) {
        return accountDAO.getById(id);
    }
    
    public AccountDTO getAccountByUsername(String username) {
        return accountDAO.getByUsername(username); 
    }
    
    public ArrayList<AccountDTO> getAll() {
        return accountDAO.getAll();
    }
    
    public boolean searchAccount(String username) { 
        return accountDAO.searchAccount(username); 
    }
    
    public ArrayList<AccountDTO> search(String text, String type) {
        ArrayList<AccountDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(AccountDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || staffBUS.getNameByID(i.getStaff_id()).toLowerCase().contains(text)
                            || i.getUsername().toLowerCase().contains(text)
                            || permissionBUS.getNameByID(i.getPermission_id()).toLowerCase().contains(text)
                            || i.getStatus().toLowerCase().contains(text)
                            )
                        result.add(i);
                }
            }
            case "Mã tài khoản" -> {
                for(AccountDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Tên nhân viên" -> {
                for(AccountDTO i : getAll()) {
                    if(staffBUS.getNameByID(i.getStaff_id()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Tên tài khoản" -> {
                for(AccountDTO i : getAll()) {
                    if(i.getUsername().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Nhóm quyền" -> {
                for(AccountDTO i : getAll()) {
                    if(permissionBUS.getNameByID(i.getPermission_id()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Trạng thái" -> {
                for(AccountDTO i : getAll()) {
                    if(i.getStatus().toLowerCase().contains(text))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
} 
