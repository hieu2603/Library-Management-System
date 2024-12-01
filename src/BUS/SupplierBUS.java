/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.SupplierDAO;
import DTO.SupplierDTO;
import java.util.ArrayList;

/**
 *
 * @author Dương
 */
public class SupplierBUS {

    private final SupplierDAO supplierDAO = new SupplierDAO();
    
    public static SupplierBUS getInstance() {
        return new SupplierBUS();
    }

    public boolean createsupplier(SupplierDTO supplier) {
        return supplierDAO.add(supplier) > 0;
    }

    public boolean updateSupplier(SupplierDTO supplier) {
        return supplierDAO.update(supplier) > 0;
    }

    public boolean delete(int id) {
            return supplierDAO.delete(id) > 0;
}

    public SupplierDTO getById(int id) {
        return supplierDAO.getSupplierById(id);
    }

//    public ArrayList<SupplierDTO> getByStatus(int status) {
//        return supplierDAO.getSupplierByStatus(status);
//    }

    public ArrayList<SupplierDTO> getAllsupplier() {
        return supplierDAO.getAllSupplier();
    }
    public ArrayList<SupplierDTO> searchSupplier(String keyword){
        return supplierDAO.searchSupplier(keyword);
    }
    
    public boolean isNameDuplicate(String name){
        return supplierDAO.isNameDuplicate(name);
    }
    
    public boolean isPhoneDuplicate(String name){
        return supplierDAO.isPhoneDuplicate(name);
    }

}
