/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.SupplierDAO;
import DTO.SupplierDTO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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
        if(!supplierDAO.checkDelete(id)) {
            JOptionPane.showMessageDialog(null, "Không thể xóa nhà cung cấp này");
            return false;
        }
        return supplierDAO.delete(id) > 0;
    }

    public SupplierDTO getById(int id) {
        return supplierDAO.getSupplierById(id);
    }

    public String getNameByID(int id) {
        return supplierDAO.getSupplierById(id).getName();
    }

    // public ArrayList<SupplierDTO> getByStatus(int status) {
    // return supplierDAO.getSupplierByStatus(status);
    // }

    public ArrayList<SupplierDTO> getAll() {
        return supplierDAO.getAllSupplier();
    }

    public ArrayList<SupplierDTO> searchSupplier(String keyword) {
        return supplierDAO.searchSupplier(keyword);
    }

    public boolean isNameDuplicate(String name) {
        return supplierDAO.isNameDuplicate(name);
    }

    public boolean isPhoneDuplicate(String name) {
        return supplierDAO.isPhoneDuplicate(name);
    }

    public ArrayList<SupplierDTO> search(String text, String type) {
        ArrayList<SupplierDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (SupplierDTO i : getAll()) {
                    if (Integer.toString(i.getId()).contains(text)
                            || i.getName().toLowerCase().contains(text)
                            || i.getAddress().toLowerCase().contains(text)
                            || i.getPhone().contains(text))
                        result.add(i);
                }
            }
            case "Mã nhà cung cấp" -> {
                for (SupplierDTO i : getAll()) {
                    if (Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Tên" -> {
                for (SupplierDTO i : getAll()) {
                    if (i.getName().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Địa chỉ" -> {
                for (SupplierDTO i : getAll()) {
                    if (i.getAddress().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Số điện thoại" -> {
                for (SupplierDTO i : getAll()) {
                    if (i.getPhone().contains(text))
                        result.add(i);
                }
            }
            default -> throw new AssertionError();
        }

        return result;
    }

}
