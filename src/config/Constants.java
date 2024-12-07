/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Duc3m
 */
public class Constants {
    public static final double PROFIT = 1.1;
    
    public static final String[] staff_status = new String[] {
        "Đang làm việc",
        "Đã nghỉ việc"
    };
    
    public static final String[] member_status = new String[] {
        "Đang hoạt động",
        "Ngừng hoạt động"
    };
    
    public static final String[] account_status = new String[] {
        "Đang hoạt động",
        "Ngừng hoạt động"
    };
    
    public static final HashMap<String, Integer> functions = new HashMap<>(){{
            put("Quản lý sách", 1);
            put("Quản lý mượn sách", 2);
            put("Quản lý trả sách", 3);
            put("Quản lý nhập sách", 4);
            put("Quản lý nhân viên", 5);
            put("Quản lý thành viên", 6);
            put("Quản lý tài khoản", 7);
            put("Quản lý vi phạm", 8);
            put("Quản lý phân quyền", 9);
            put("Thống kê", 10);
            put("Quản lý kệ sách", 11);
            put("Quản lý nhà xuất bản", 12);
            put("Quản lý nhà cung cấp", 13);
    }};
    
}
