/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import javax.swing.JOptionPane;

/**
 *
 * @author Duc3m
 */
public class InputGetter {

    public static Integer getIntegerInput(String text) {
        int result;
        try {
            String input = JOptionPane.showInputDialog("Nhập " + text);
            if (input == null) {
                return null;
            }
            result = Integer.parseInt(input);
            if (result <= 0) {
                JOptionPane.showMessageDialog(null, text + " phải là số lớn hơn 0");
                return 0;
            } else {
                return result;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, text + " phải là số lớn hơn 0");
            return 0;
        }
    }
    
    public static Long getLongInput(String text) {
        Long result;
        try {
            String input = JOptionPane.showInputDialog("Nhập " + text);
            if (input == null) {
                return null;
            }
            result = Long.valueOf(input);
            if (result <= 0) {
                JOptionPane.showMessageDialog(null, text + " phải là số lớn hơn 0");
                return Long.valueOf(0);
            } else {
                return result;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, text + " phải là số lớn hơn 0");
            return Long.valueOf(0);
        }
    }

    public static String getStringInput(String text) {
        String result = "";
        while (result.equals("")) {
            result = JOptionPane.showInputDialog("Nhập " + text);
            if (result == null) {
                return null;
            }
            if (result.equals("")) {
                JOptionPane.showMessageDialog(null, text + " không được để trống");
                return "";
            }
        }
        return result;
    }

}
