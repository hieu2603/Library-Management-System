/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.MemberDAO;
import DTO.MemberDTO;
import java.util.ArrayList;

/**
 *
 * @author Dương
 */
public class MemberBUS {
    private final MemberDAO memberDAO = new MemberDAO();
      
    public static MemberBUS getInstance() {
        return new MemberBUS();
    }

    public boolean createMember(MemberDTO member) {
        return memberDAO.add(member) > 0;
    }

    public boolean updateMember(MemberDTO member) {
        return memberDAO.update(member) > 0;
    }

    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public MemberDTO getById(int id) {
        return memberDAO.getMemberById(id);
    }
    
    public String getNameByID(int id) {
        return memberDAO.getMemberById(id).getFull_name();
    }
    
    public ArrayList<MemberDTO> getByStatus(int status) {
        return memberDAO.getMemberByStatus(status);
    }

    public ArrayList<MemberDTO> getAll() {
        return memberDAO.getAllMember();
    }
    public ArrayList<MemberDTO> searchMember(String text){
        return memberDAO.searchMembers(text);
    }
    
    public ArrayList<MemberDTO> search(String text, String type) {
        ArrayList<MemberDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch(type){
            case "Tất cả" -> {
                for(MemberDTO i : getAll()) {
                    if(
                            Integer.toString(i.getId()).contains(text)
                            || i.getFull_name().toLowerCase().contains(text)
                            || i.getEmail().toLowerCase().contains(text)
                            || i.getPhone().contains(text)
                            || i.getAddress().toLowerCase().contains(text)
                            || i.getStatus().toLowerCase().contains(text)
                            )
                        result.add(i);
                }
            }
            case "Mã thành viên" -> {
                for(MemberDTO i : getAll()) {
                    if(Integer.toString(i.getId()).contains(text))
                        result.add(i);
                }
            }
            case "Họ tên" -> {
                for(MemberDTO i : getAll()) {
                    if(i.getFull_name().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Số điện thoại" -> {
                for(MemberDTO i : getAll()) {
                    if(i.getPhone().contains(text))
                        result.add(i);
                }
            }
            case "Địa chỉ" -> {
                for(MemberDTO i : getAll()) {
                    if(i.getAddress().toLowerCase().contains(text))
                        result.add(i);
                }
            }
            case "Trạng thái" -> {
                for(MemberDTO i : getAll()) {
                    if(i.getStatus().toLowerCase().contains(text))
                        result.add(i);
                }
            }
             default -> throw new AssertionError();
        }
        
        return result;
    }
    
}
