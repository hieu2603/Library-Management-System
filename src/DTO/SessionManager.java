/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import BUS.PermissionDetailBUS;
import java.util.ArrayList;

/**
 *
 * @author hieun
 */
public class SessionManager {
    private static SessionManager instance;
    private StaffDTO loggedInStaff;
    private AccountDTO loggedInAccount;
    public PermissionDetailBUS pdBUS = new PermissionDetailBUS();
    public ArrayList<PermissionDetailDTO> pdList;
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        
        return instance;
    }
    
    public boolean permissionCheck(int functionId, String action) {
        pdList = pdBUS.getByPermissionId(loggedInAccount.getPermission_id());
        for(PermissionDetailDTO i : pdList) {
            if(i.getFunction_id()== functionId)
                if(i.getAction().equals(action))
                    return true;
        }
        return false;
    }

    public StaffDTO getLoggedInStaff() {
        return loggedInStaff;
    }

    public void setLoggedInStaff(StaffDTO loggedInStaff) {
        this.loggedInStaff = loggedInStaff;
    }

    public AccountDTO getLoggedInAccount() {
        return loggedInAccount;
    }

    public void setLoggedInAccount(AccountDTO loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }
    
}
