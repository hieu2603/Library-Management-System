/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.PublisherDAO;
import DTO.PublisherDTO;
import java.util.ArrayList;

/**
 *
 * @author Duc3m
 */
public class PublisherBUS {
    
    PublisherDAO publisherDAO = new PublisherDAO();
    
    public static PublisherBUS getInstance() {
        return new PublisherBUS();
    }
    
    public ArrayList<PublisherDTO> getAll() {
        return publisherDAO.getAll();
    }
    
    public PublisherDTO getById(int id) {
        return publisherDAO.getById(id);
    }
    
    public String getNameByID(int id) {
        return publisherDAO.getById(id).getName();
    }
    
    public boolean add(PublisherDTO publisher) {
        return publisherDAO.add(publisher) > 0;
    }
    
    public boolean update(PublisherDTO publisher) {
        return publisherDAO.update(publisher) > 0;
    }
    
    public ArrayList<PublisherDTO> search(String text, String type) {
        ArrayList<PublisherDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(PublisherDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || i.getName().toLowerCase().contains(text)
                            || i.getAddress().toLowerCase().contains(text)
                            || i.getPhone().contains(text)
                            )
                        result.add(i);
                }
            }
            case "Mã nhà xuất bản" -> {
                for(PublisherDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Tên" -> {
                for(PublisherDTO i : getAll()) {
                    if(i.getName().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Địa chỉ" -> {
                for(PublisherDTO i : getAll()) {
                    if(i.getAddress().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Số điện thoại" -> {
                for(PublisherDTO i : getAll()) {
                    if(i.getPhone().contains(text))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
}
