/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.ReturnTicket;

import BUS.BookBUS;
import BUS.BorrowTicketBUS;
import BUS.MemberBUS;
import BUS.ReturnTicketBUS;
import BUS.ReturnTicketDetailBUS;
import BUS.StaffBUS;
import DAO.BorrowTicketDAO;
import DAO.BorrowTicketDetailDAO;
import DAO.ReturnTicketDAO;
import DTO.BookDTO;
import DTO.BorrowTicketDTO;
import DTO.BorrowTicketDetailDTO;
import DTO.MemberDTO;
import DTO.ReturnTicketDTO;
import DTO.ReturnTicketDetailDTO;
import DTO.SessionManager;
import DTO.StaffDTO;
import GUI.BorrowTicket.GetBorrowedBookDialog;
import GUI.Member.GetMemberDialog;
import helper.Formatter;
import helper.Validator;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Duc3m
 */
public class ReturnTicketDialog extends javax.swing.JDialog {

    String mode;
    ReturnTicketDTO returnTicket;
    ArrayList<ReturnTicketDetailDTO> detailList;
    MemberDTO member;
    StaffDTO staff;
    Timestamp returnDate;
    
    ReturnTicketBUS returnTicketBUS = new ReturnTicketBUS();
    ReturnTicketDetailBUS detailBUS = new ReturnTicketDetailBUS();
    
    ArrayList<BorrowTicketDTO> borrowTicketList;
    ArrayList<BorrowTicketDetailDTO> borrowDetailList;
    
    String[] statuses = {"Nguyên vẹn", "Hư hỏng", "Mất"};
    
    public ReturnTicketDialog(java.awt.Frame parent, boolean modal, ReturnTicketDTO returnTicket, String mode) {
        super(parent, modal);
        this.returnTicket = returnTicket;
        this.mode = mode;
        initComponents();
        customInit();
    }
    
    public void customInit() {
        setLocationRelativeTo(null);
        
        btn_addBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addBookEvent();
            }
        });
        
        btn_save.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(mode.equals("add"))
                    addEvent();
            }
        });
        
        btn_exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
            }
        });
        
        btn_member.addActionListener((ActionEvent e) -> {
            if (jTable1.getRowCount() > 0) {
                JOptionPane.showMessageDialog(this, "Không thể đổi thành viên bây giờ!");
                return;
            }
            member = GetMemberDialog.getMember();
            if(member == null)
                return;
            txt_member.setText(member.getFull_name());
            
            borrowTicketList = BorrowTicketDAO.getInstance().getNotReturnedByMemberID(member.getMember_id());
            borrowDetailList = BorrowTicketDetailDAO.getInstance().getNotReturnedByMemberId(member.getMember_id());
        });

        if(mode.equals("view"))
            initViewMode();
        if(mode.equals("add"))
            initAddMode();
    }
    
    public void initData() {
        member = MemberBUS.getInstance().getById(returnTicket.getMember_id());
        staff = StaffBUS.getInstance().getById(returnTicket.getStaff_id());
        returnDate = returnTicket.getReturn_date();
        detailList = detailBUS.getByReturnTicketId(returnTicket.getId());
    }

    public void initViewMode() {
        initData();
        btn_addBook.setVisible(false);
        btn_save.setVisible(false);
        btn_member.setEnabled(false);
        
        txt_id.setText(returnTicket.getId() + "");
        txt_staff.setText(staff.getFullName());
        txt_member.setText(member.getFull_name());
        txt_returnDate.setText(Formatter.getDate(returnDate));
        txt_status.setText(returnTicket.getStatus());
        
        //Khong cho sua cot tinh trang
        DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ISBN", "Tên sách", "Mã phiếu mượn", "Tình trạng", "Hạn trả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        jTable1.setModel(tableModel);
        
        loadDataToTable(detailList);
    }

    public void initAddMode() {
        detailList = new ArrayList<>();
        staff = SessionManager.getInstance().getLoggedInStaff();
        returnDate = new Timestamp(System.currentTimeMillis());
        
        jLabel7.setText("TẠO PHIẾU TRẢ MỚI");
        lbl_id.setEnabled(false);
        txt_id.setEnabled(false);
        lbl_status.setEnabled(false);
        txt_status.setEnabled(false);
        
        txt_staff.setText(staff.getFullName());
        txt_returnDate.setText(Formatter.getDate(returnDate));
        
        JComboBox comboBox = new JComboBox(statuses);
        TableColumn statusColumn = jTable1.getColumnModel().getColumn(3);
        statusColumn.setCellEditor(new DefaultCellEditor(comboBox));
        
    }
    
    public void loadDataToTable(ArrayList<ReturnTicketDetailDTO> detailList) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        for (ReturnTicketDetailDTO i : detailList) {
            BookDTO book = BookBUS.getInstance().getByISBN(i.getIsbn());
            tableModel.addRow(new Object[] {
                    i.getIsbn(),
                    book.getTitle(),
                    i.getBorrow_ticket_id(),
                    i.getStatus(),
                    (i.getDays_passed()==0?"Đúng hạn":"Trễ hạn " + i.getDays_passed() + " ngày")
            });
        }
    }
    
    public void addRowToTable(ReturnTicketDetailDTO detail) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        BookDTO book = BookBUS.getInstance().getByISBN(detail.getIsbn());
        tableModel.addRow(new Object[]{
            detail.getIsbn(),
            book.getTitle(),
            detail.getBorrow_ticket_id(),
            detail.getStatus(),
            (detail.getDays_passed() == 0 ? "Đúng hạn" : "Trễ hạn " + detail.getDays_passed() + " ngày")
        });
    }
    
    public void addBookEvent() {
        if(Validator.isEmpty(txt_member.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn thành viên");
            return;
        }
        int borrowticket_id = 0;
        String isbn = "";
        BorrowTicketDetailDTO borrowDetail = null;
        GetBorrowedBookDialog gbbDialog = new GetBorrowedBookDialog(null, true, member, borrowTicketList, borrowDetailList);
        gbbDialog.setVisible(true);
        try {
            if (gbbDialog.choosen == false) {
                return;
            }
            borrowticket_id = gbbDialog.getSelectedId();
            isbn = gbbDialog.getSelectedISBN();
            borrowDetail = BorrowTicketDetailDAO.getInstance().getByTicketIdAndISBN(borrowticket_id, isbn);
            borrowDetailList.remove(borrowDetail);

        } catch (Exception ex) {
            
        }
        int returnticket_id = ReturnTicketDAO.getInstance().getLastID() + 1;
        
        long days_passed;
        BorrowTicketDTO borrowTicket = BorrowTicketBUS.getInstance().getById(borrowDetail.getBorrow_ticket_id());
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        if(currentDate.before(borrowTicket.getDue_date())) {
            days_passed = 0;
        } else {
            long differenceInMillis = currentDate.getTime() - borrowTicket.getDue_date().getTime();
            days_passed = differenceInMillis / (24 * 60 * 60 * 1000);
        }
        
        ReturnTicketDetailDTO returnDetail = new ReturnTicketDetailDTO(returnticket_id, borrowticket_id, isbn, "Nguyên vẹn", (int) days_passed);
        detailList.add(returnDetail);
        addRowToTable(returnDetail);
//        loadDataToTable(detailList);
    }
    
    public ReturnTicketDTO getNewReturnTicket() {
        return new ReturnTicketDTO(staff.getId(), member.getMember_id(), returnDate, "1");
    }
    
    public void getStatuses() {
        for(int i=0; i<detailList.size(); i++) {
            String status = (String) jTable1.getValueAt(i, 3);
            detailList.get(i).setStatus(status);
        }
    }
    
    public void addEvent() {
        if(jTable1.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "Phải có ít nhất 1 sách để tạo phiếu trả!");
            return;
        }
        getStatuses();
        returnTicket = getNewReturnTicket();
        if(returnTicketBUS.addWithDetail(returnTicket, detailList)) {
            JOptionPane.showMessageDialog(this, "Tạo phiếu trả thành công!");
            dispose();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_id = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_member = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_staff = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_returnDate = new javax.swing.JTextField();
        lbl_status = new javax.swing.JLabel();
        txt_status = new javax.swing.JTextField();
        btn_member = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btn_save = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();
        btn_addBook = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));
        jPanel2.setPreferredSize(new java.awt.Dimension(686, 80));

        lbl_id.setText("Mã");

        txt_id.setFocusable(false);

        jLabel2.setText("Thành viên");

        txt_member.setFocusable(false);
        txt_member.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel3.setText("Nhân viên");

        txt_staff.setFocusable(false);

        jLabel4.setText("Ngày trả");

        txt_returnDate.setFocusable(false);

        lbl_status.setText("Trạng thái");

        txt_status.setFocusable(false);

        btn_member.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btn_member.setText("...");
        btn_member.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_member.setFocusPainted(false);
        btn_member.setMaximumSize(new java.awt.Dimension(40, 40));
        btn_member.setPreferredSize(new java.awt.Dimension(70, 40));
        btn_member.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_member.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_id))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txt_staff)
                            .addComponent(txt_member, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_member, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(lbl_status)
                    .addComponent(txt_returnDate)
                    .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_id)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_id, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_member, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_returnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_member, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_staff, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ISBN", "Tên sách", "Mã phiếu mượn", "Tình trạng", "Hạn trả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("CHI TIẾT PHIẾU TRẢ");
        jLabel7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btn_save.setBackground(new java.awt.Color(21, 154, 32));
        btn_save.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Đồng ý");
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.setFocusPainted(false);

        btn_exit.setBackground(new java.awt.Color(244, 67, 54));
        btn_exit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_exit.setForeground(new java.awt.Color(255, 255, 255));
        btn_exit.setText("Hủy");
        btn_exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_exit.setFocusPainted(false);

        btn_addBook.setBackground(new java.awt.Color(88, 175, 232));
        btn_addBook.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_addBook.setForeground(new java.awt.Color(255, 255, 255));
        btn_addBook.setText("Thêm sách");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btn_addBook)
                .addGap(18, 18, 18)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_addBook, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addBook;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_member;
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_member;
    private javax.swing.JTextField txt_returnDate;
    private javax.swing.JTextField txt_staff;
    private javax.swing.JTextField txt_status;
    // End of variables declaration//GEN-END:variables
}
