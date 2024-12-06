/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.PurchaseTicket;

import BUS.BookBUS;
import BUS.PurchaseTicketBUS;
import BUS.PurchaseTicketDetailBUS;
import BUS.StaffBUS;
import BUS.SupplierBUS;
import DTO.BookDTO;
import DTO.BookItemDTO;
import DTO.PurchaseTicketDTO;
import DTO.PurchaseTicketDetailDTO;
import DTO.SessionManager;
import DTO.StaffDTO;
import DTO.SupplierDTO;
import GUI.Book.BookDialog;
import GUI.Book.GetBookDialog;
import GUI.Supplier.GetSupplierDialog;
import helper.Formatter;
import helper.InputGetter;
import helper.Validator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duc3m
 */
public class PurchaseTicketDialog extends javax.swing.JDialog {

    StaffDTO staff;
    SupplierDTO supplier;
    PurchaseTicketDTO purchaseTicket;
    String mode;
    
    StaffBUS staffBUS = new StaffBUS();
    SupplierBUS supplierBUS = new SupplierBUS();
    PurchaseTicketBUS purchaseTicketBUS = new PurchaseTicketBUS();
    PurchaseTicketDetailBUS purchaseTicketDetailBUS = new PurchaseTicketDetailBUS();
    
    ArrayList<PurchaseTicketDetailDTO> detailList;
    ArrayList<BookItemDTO> bookItemList;
    
    private HashMap<Integer, String> isbnList = new HashMap<>();
    
    public PurchaseTicketDialog(java.awt.Frame parent, boolean modal, PurchaseTicketDTO purchaseTicket, String mode) {
        super(parent, modal);
        this.purchaseTicket = purchaseTicket;
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
        
        if(mode.equals("view"))
            initViewMode();
        if(mode.equals("add"))
            initAddMode();
    }
    
    public void initData() {
        supplier = supplierBUS.getById(purchaseTicket.getSupplier_id());
        staff = staffBUS.getById(purchaseTicket.getStaff_id());
        detailList = purchaseTicketDetailBUS.getByPurchaseTicketId(purchaseTicket.getId());
    }
    
    public void initViewMode() {
        initData();
        txt_id.setText(purchaseTicket.getId() + "");
        txt_supplier.setText(supplier.getName());
        txt_staff.setText(staff.getFullName());
        txt_purchaseDate.setText(Formatter.getDate(purchaseTicket.getPurchase_date()));
        txt_status.setText(purchaseTicket.getStatus());
        txt_totalPrice.setText(Formatter.FormatVND(purchaseTicket.getTotal_price()));
        
        btn_addBook.setVisible(false);
        btn_save.setVisible(false);
        btn_supplier.setEnabled(false);
        
        loadDataToTable(detailList);
    }
    
    public void initAddMode() {
        lbl_title.setText("TẠO PHIẾU NHẬP MỚI");
        detailList = new ArrayList<>();
        bookItemList = new ArrayList<>();
        staff = SessionManager.getInstance().getLoggedInStaff();
        txt_staff.setText(staff.getFullName());
        
        txt_purchaseDate.setText(Formatter.getDate(new Timestamp(System.currentTimeMillis())));
        
        lbl_id.setEnabled(false);
        txt_id.setEnabled(false);
        lbl_status.setEnabled(false);
        txt_status.setEnabled(false);
    }
    
    public void loadDataToTable(ArrayList<PurchaseTicketDetailDTO> detailList) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        for (PurchaseTicketDetailDTO i : detailList) {
            BookDTO book = BookBUS.getInstance().getById(i.getBook_id());
            tableModel.addRow(new Object[] {
                    i.getBook_id(),
                    book.getTitle(),
                    i.getQuantity(),
                    Formatter.FormatVND(i.getPrice()),
                    Formatter.FormatVND(i.getTotal_price())
            });
        }
    }
    
    public void addBookEvent() {
        String[] option = {"Tạo sách mới", "Chọn sách", "Hủy"};
        int selection = JOptionPane.showOptionDialog(null, "Bạn muốn nhập sản sách mới hay chọn sách có sẵn?", "", 0, 3, null, option, option[2]);
        //Tao sach moi
        if (selection == 0) {
            BookDialog bDialog = new BookDialog(null, true, null, "add");
            bDialog.setVisible(true);
        }
        if (selection == 1) {
            BookDTO book = GetBookDialog.getBook();
            if (book == null) {
                return;
            }

            int ticket_id = purchaseTicketBUS.getLastID() + 1;
            int book_id = book.getId();
            int quantity = InputGetter.getNumberInput("Số lượng sách");
            long price = InputGetter.getNumberInput("Đơn giá");

            String isbn;
            do {
                isbn = InputGetter.getStringInput("ISBN");
                if (isbn == null) {
                    return;
                }
                if(!Validator.isValidISBN(isbn)) {
                    JOptionPane.showMessageDialog(this, "ISBN phải là số có 11 chữ số và không bắt đầu bằng số 0");
                }
            } while (!Validator.isValidISBN(isbn));
            
            isbnList.put(book.getId(), isbn);

            PurchaseTicketDetailDTO detail = new PurchaseTicketDetailDTO(ticket_id, book_id, quantity, price, (long) (price * quantity));
            detailList.add(detail);
            loadDataToTable(detailList);
            txt_totalPrice.setText(Formatter.FormatVND(getTotalPrice()));
        }
    }
    
    public long getTotalPrice() {
        long result = 0;
        for (PurchaseTicketDetailDTO i : detailList) {
            result += i.getTotal_price();
        }
        return result;
    }
    
    public void generateBookItemList() {
        for (PurchaseTicketDetailDTO i : detailList) {
            String isbn = isbnList.get(i.getBook_id());
            int book_id = i.getBook_id();
            int purchaseticket_id = purchaseTicketBUS.getLastID() + 1;
            String status = "1";
            long price = i.getPrice();
            for (int j = 0; j < i.getQuantity(); j++) {
                String newISBN = String.valueOf(Long.parseLong(isbn) + j);
                BookItemDTO bookItem = new BookItemDTO(newISBN, book_id, purchaseticket_id, status, price);
                bookItemList.add(bookItem);
            }
        }
    }
    
    public PurchaseTicketDTO getNewPurchaseTicket() {
        int supplier_id = supplier.getId();
        int staff_id = staff.getId();
        Timestamp purchase_date = new Timestamp(System.currentTimeMillis());
        String status = "1";
        long total_price = getTotalPrice();
        
        return new PurchaseTicketDTO(supplier_id, staff_id, purchase_date, status, total_price);
    }
    
    public boolean validateInputs() {
        if(Validator.isEmpty(txt_supplier.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhà cung cấp");
            return false;
        }
        return true;
    }
    
    public void addEvent() {
        if(!validateInputs()){
            return;
        }
        if(jTable1.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "Phải có ít nhất 1 sách để tạo phiếu nhập!");
            return;
        }
        purchaseTicket = getNewPurchaseTicket();
        generateBookItemList();
        if(purchaseTicketBUS.addWithLists(purchaseTicket, detailList, bookItemList)) {
            JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thành công!");
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
        txt_supplier = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_staff = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_purchaseDate = new javax.swing.JTextField();
        lbl_status = new javax.swing.JLabel();
        txt_status = new javax.swing.JTextField();
        btn_supplier = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txt_totalPrice = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lbl_title = new javax.swing.JLabel();
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

        jLabel2.setText("Nhà cung cấp");

        txt_supplier.setFocusable(false);
        txt_supplier.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel3.setText("Nhân viên");

        txt_staff.setFocusable(false);

        jLabel4.setText("Ngày nhập");

        txt_purchaseDate.setFocusable(false);

        lbl_status.setText("Trạng thái");

        txt_status.setFocusable(false);

        btn_supplier.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btn_supplier.setText("...");
        btn_supplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_supplier.setFocusPainted(false);
        btn_supplier.setMaximumSize(new java.awt.Dimension(40, 40));
        btn_supplier.setPreferredSize(new java.awt.Dimension(70, 40));
        btn_supplier.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_supplier.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        btn_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supplierActionPerformed(evt);
            }
        });

        jLabel5.setText("Tổng giá");

        txt_totalPrice.setFocusable(false);

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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_supplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_staff))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_status)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_status, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_purchaseDate, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txt_totalPrice))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_id)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txt_id, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_supplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btn_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_purchaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_totalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_status))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_staff, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sách", "Tên sách", "Số lượng", "Đơn giá", "Tổng giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        lbl_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setText("CHI TIẾT PHIẾU NHẬP");
        lbl_title.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(223, 223, 223))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lbl_title)
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

    private void btn_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supplierActionPerformed
        supplier = GetSupplierDialog.getSupplier();
        if(supplier == null)
            return;
        txt_supplier.setText(supplier.getName());
    }//GEN-LAST:event_btn_supplierActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addBook;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_supplier;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_purchaseDate;
    private javax.swing.JTextField txt_staff;
    private javax.swing.JTextField txt_status;
    private javax.swing.JTextField txt_supplier;
    private javax.swing.JTextField txt_totalPrice;
    // End of variables declaration//GEN-END:variables
}
