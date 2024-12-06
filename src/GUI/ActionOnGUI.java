/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import DTO.MemberDTO;
import DTO.AccountDTO;
import DTO.StaffDTO;
import DTO.SupplierDTO;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hieun
 */
public class ActionOnGUI {

    // Thoát frame hiện tại và hiện frame mới
    public static void disposeAndOpenNewFrame(JFrame disposeFrame, JFrame newFrame) {
        disposeFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                JFrame openFrame = newFrame;
                openFrame.setVisible(true);
            }
        });
    }

    public static <T> void showDataOnTable(DefaultTableModel model, ArrayList<T> list) {
        model.setRowCount(0);

        if (list.isEmpty()) {
            return;
        }

        T firstObject = list.get(0);

        if (firstObject instanceof StaffDTO) {
            for (T item : list) {

                StaffDTO staff = (StaffDTO) item;

                Object[] rowData = new Object[] {
                        staff.getId(),
                        staff.getFullName(),
                        staff.getEmail(),
                        staff.getPhone(),
                        staff.getAddress(),
                        staff.getHireDate(),
                        staff.getStatus(), };

                model.addRow(rowData);
            }
        } else if (firstObject instanceof MemberDTO) {
            for (T item : list) {
                MemberDTO member = (MemberDTO) item;
                Object[] rowData = new Object[] {
                        member.getId(),
                        member.getFull_name(),
                        member.getPhone(),
                        member.getAddress(),
                        member.getMembership_date(),
                        member.getStatus(),
                        member.getViolationCount(), };
                model.addRow(rowData);

            }

        } else if (firstObject instanceof SupplierDTO) {
            for (T item : list) {
                SupplierDTO supplier = (SupplierDTO) item;
                Object[] rowData = new Object[] {
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getAddress(),
                        supplier.getPhone(), };
                model.addRow(rowData);
            }
        }

        else if (firstObject instanceof AccountDTO) {
            for (T item : list) {

                AccountDTO account = (AccountDTO) item;

                Object[] rowData = new Object[] {
                        account.getId(),
                        account.getUsername(),
                        account.getPassword(),
                        account.getPermission_id(),
                        account.getStatus()
                };

                model.addRow(rowData);
            }
        }

    }

}
