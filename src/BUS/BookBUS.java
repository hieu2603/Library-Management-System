package BUS;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import DAO.BookDAO;
import DTO.BookDTO;
import helper.Formatter;
import java.util.ArrayList;

/**
 *
 * @author hieun
 */
public class BookBUS {
    private final BookDAO bookDAO = new BookDAO();
    
    PublisherBUS publisherBUS = new PublisherBUS();
    CategoryBUS categoryBUS = new CategoryBUS();
    
    public static BookBUS getInstance() {
        return new BookBUS();
    }
    
    public boolean addBook(BookDTO book) {
        return bookDAO.add(book) > 0;
    }
    
    public boolean updateBook(BookDTO book) {
        return bookDAO.update(book) > 0;
    }
    
    public ArrayList<BookDTO> getAll() {
        return bookDAO.getAll();
    }
    
    public ArrayList<BookDTO> getAllHavingBookItem() {
        return bookDAO.getAllHavingBookItem();
    }
    
    public ArrayList<BookDTO> getByBookshelfID(int id) {
        return bookDAO.getByBookshelfID(id);
    }
    
    public BookDTO getById(int id) {
        return bookDAO.getById(id);
    }
    
    public BookDTO getByISBN(String isbn) {
        return bookDAO.getByISBN(isbn);
    }
    
    public ArrayList<BookDTO> search(String text, String type) {
        ArrayList<BookDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(BookDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || i.getTitle().toLowerCase().contains(text)
                            || i.getAuthor().toLowerCase().contains(text)
                            || publisherBUS.getNameByID(i.getPublisherId()).toLowerCase().contains(text)
                            || Integer.toString(i.getYearPublish()).contains(text)
                            || categoryBUS.getNameByID(i.getCategoryId()).toLowerCase().contains(text)
                            )
                        result.add(i);
                }
            }
            case "Mã sách" -> {
                for(BookDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Tên sách" -> {
                for(BookDTO i : getAll()) {
                    if(i.getTitle().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Tác giả" -> {
                for(BookDTO i : getAll()) {
                    if(i.getAuthor().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Nhà xuất bản" -> {
                for(BookDTO i : getAll()) {
                    if(publisherBUS.getNameByID(i.getPublisherId()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Năm xuất bản" -> {
                for(BookDTO i : getAll()) {
                    if(Integer.toString(i.getYearPublish()).contains(text))
                        result.add(i);
                }
            }
            case "Thể loại" -> {
                for(BookDTO i : getAll()) {
                    if(categoryBUS.getNameByID(i.getCategoryId()).toLowerCase().contains(text))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
}
