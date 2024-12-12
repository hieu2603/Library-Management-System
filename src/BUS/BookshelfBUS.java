/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.BookshelfDAO;
import DTO.BookshelfDTO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Duc3m
 */
public class BookshelfBUS {
    
    private final BookshelfDAO bookshelfDAO = new BookshelfDAO();
    
    public static BookshelfBUS getInstance() {
        return new BookshelfBUS();
    }
    
    public ArrayList<BookshelfDTO> getAll() {
        return bookshelfDAO.getAll();
    }
    
    public BookshelfDTO getById(int id) {
        return bookshelfDAO.getById(id);
    }
    
    public boolean add(BookshelfDTO bookshelf) {
        return bookshelfDAO.add(bookshelf) != 0;
    }
    
    public boolean update(BookshelfDTO bookshelf) {
        return bookshelfDAO.update(bookshelf) != 0;
    }
    
    public boolean delete(int id) {
        if(!bookshelfDAO.checkDelete(id)) {
            JOptionPane.showMessageDialog(null, "Không thể xóa kệ sách này");
            return false;
        }
        return bookshelfDAO.delete(id) > 0;
    }
    
    public ArrayList<BookshelfDTO> search(String text, String type) {
        ArrayList<BookshelfDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(BookshelfDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || i.getName().toLowerCase().contains(text)
                            )
                        result.add(i);
                }
            }
            case "Mã kệ sách" -> {
                for(BookshelfDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Tên kệ sách" -> {
                for(BookshelfDTO i : getAll()) {
                    if(i.getName().toLowerCase().contains(text))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
}
