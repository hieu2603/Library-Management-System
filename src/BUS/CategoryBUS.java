/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.CategoryDAO;
import DTO.CategoryDTO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Duc3m
 */
public class CategoryBUS {
    
    CategoryDAO categoryDAO = new CategoryDAO();
    
    public static CategoryBUS getInstance() {
        return new CategoryBUS();
    }
    
    public ArrayList<CategoryDTO> getAll() {
        return categoryDAO.getAll();
    }
    
    public CategoryDTO getById(int id) {
        return categoryDAO.getById(id);
    }
    
    public String getNameByID(int id) {
        return categoryDAO.getById(id).getName();
    }
    
    public boolean add(CategoryDTO category) {
        return categoryDAO.add(category) > 0;
    }
    
    public boolean update(CategoryDTO category) {
        return categoryDAO.update(category) > 0;
    }
    
    public boolean delete(int id) {
        if(!categoryDAO.checkDelete(id)) {
            JOptionPane.showMessageDialog(null, "Không thể xóa danh mục này");
            return false;
        }
        return categoryDAO.delete(id) > 0;
    }
    
}
