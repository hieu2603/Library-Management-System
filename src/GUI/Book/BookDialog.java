/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.Book;

import BUS.BookBUS;
import BUS.BookItemBUS;
import BUS.BookshelfBUS;
import BUS.CategoryBUS;
import BUS.PublisherBUS;
import DTO.BookDTO;
import DTO.BookItemDTO;
import DTO.BookshelfDTO;
import DTO.CategoryDTO;
import DTO.PublisherDTO;
import DTO.SessionManager;
import GUI.Bookshelf.GetBookshelfDialog;
import GUI.Category.CategoryDialog;
import GUI.Publisher.GetPublisherDialog;
import config.Constants;
import helper.Validator;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duc3m
 */
public class BookDialog extends javax.swing.JDialog {
    
    BookDTO book;
    CategoryDTO category;
    PublisherDTO publisher;
    BookshelfDTO bookshelf;
    String mode;
    int functionId = Constants.functions.get("Quản lý sách");
    
    CategoryBUS categoryBUS = new CategoryBUS();
    PublisherBUS publisherBUS = new PublisherBUS();
    BookshelfBUS bookshelfBUS = new BookshelfBUS();
    BookBUS bookBUS = new BookBUS();
    BookItemBUS bookItemBUS = new BookItemBUS();
    
    ArrayList<BookItemDTO> bookItemList;
    
    String imageURL;
    boolean imageChanged = false;

    public BookDialog(java.awt.Frame parent, boolean modal, BookDTO book, String mode) {
        super(parent, modal);
        this.book = book;
        this.mode = mode;
        initComponents();
        customInit();
    }

    public void customInit() {
        setLocationRelativeTo(null);
        
        btn_save.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(mode.equals("view"))
                    updateEvent();
                if(mode.equals("add"))
                    addEvent();
            }
        });
        
        btn_edit.addActionListener((ActionEvent e) -> {
            enableForm();
        });
        
        btn_exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
            }
        });
        
        btn_img.addActionListener((ActionEvent e) -> {
            setImage();
        });
        
        btn_publisher.addActionListener((ActionEvent e) -> {
            publisher = GetPublisherDialog.getPublisher();
            if(publisher == null)
                return;
            txt_publisher.setText(publisher.getName());
        });
        
        btn_category.addActionListener((ActionEvent e) -> {
            category = CategoryDialog.getCategory();
            if(category == null)
                return;
            txt_category.setText(category.getName());
        });
        
        btn_bookshelf.addActionListener((ActionEvent e) -> {
            bookshelf = GetBookshelfDialog.getBookshelf();
            if(bookshelf == null)
                return;
            txt_bookshelf.setText(bookshelf.getName());
        });
        
        if(mode.equals("view"))
            initViewMode();
        if(mode.equals("add"))
            initAddMode();
    }
    
    public void initViewMode() {
        category = categoryBUS.getById(book.getCategoryId());
        publisher = publisherBUS.getById(book.getPublisherId());
        bookshelf = bookshelfBUS.getById(book.getBookshelf_id());
        
        lbl_image.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/book/" + book.getImage())).getImage().getScaledInstance(200, 267, Image.SCALE_SMOOTH)));
        txt_title.setText(book.getTitle());
        txt_author.setText(book.getAuthor());
        txt_year.setText(book.getYearPublish() + "");
        txt_quantity.setText(book.getQuantity() + "");
        txt_publisher.setText(publisher.getName());
        txt_bookshelf.setText(bookshelf.getName());
        txt_category.setText(category.getName());
        
        txt_title.setFocusable(false);
        txt_author.setFocusable(false);
        txt_year.setFocusable(false);
        btn_img.setEnabled(false);
        btn_publisher.setEnabled(false);
        btn_category.setEnabled(false);
        btn_bookshelf.setEnabled(false);
        
        bookItemList = bookItemBUS.getByBookId(book.getId());
        txt_quantity1.setText(calculateAvailableQuantity(bookItemList) + "");
        loadDataToTable(bookItemList);
        
        if(!SessionManager.getInstance().permissionCheck(functionId, "edit"))
            btn_edit.setEnabled(false);
    }
    
    public int calculateAvailableQuantity(ArrayList<BookItemDTO> bookItemList) {
        int result = 0;
        for (BookItemDTO i : bookItemList) {
            if(i.getStatus().equals("Có sẵn"))
                result++;
        }
        return result;
    }
    
    public void loadDataToTable(ArrayList<BookItemDTO> bookItemList) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        for (BookItemDTO i : bookItemList) {
            tableModel.addRow(new Object[] {
                    i.getIsbn(),
                    i.getStatus()
            });
        }
    }
    
    public void setImage() {
        JFileChooser jfc;
        jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and GIF images", "png", "gif", "jpg", "jpeg");
        jfc.addChoosableFileFilter(filter);
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            imageURL = (String) jfc.getSelectedFile().getPath();
            File file = jfc.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(new javax.swing.ImageIcon(String.valueOf(jfc.getSelectedFile())).getImage().getScaledInstance(200, 267, Image.SCALE_SMOOTH));
            BufferedImage b;
            try {
                b = ImageIO.read(file);
                lbl_image.setIcon(imageIcon);
            } catch (IOException ex) {
                Logger.getLogger(BookDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (mode.equals("edit")) {
            imageChanged = true;
        }
    }
    
    public String addImage(String imageURL) {
        Random randomGenerator = new Random();
        int rand = randomGenerator.nextInt(1000);
        File sourceFile = new File(imageURL);
        String destPath = "./src/images/book";
        File destFolder = new File(destPath);
        String newName = rand + sourceFile.getName();
        try {
            Path dest = Paths.get(destFolder.getPath(), newName);
            Files.copy(sourceFile.toPath(), dest);
            if (mode.equals("view")) {
                Path oldDest = Paths.get(destPath, book.getImage());
                Files.delete(oldDest);
            }
        } catch (IOException e) {
        }
        return newName;
    }
    
    public void initAddMode() {
        jLabel1.setText("THÊM SÁCH MỚI");
        btn_save.setText("Thêm");
        btn_edit.setVisible(false);
        
        btn_card1.setVisible(false);
        
    }
    
    public void enableForm() {
        txt_title.setFocusable(true);
        txt_author.setFocusable(true);
        txt_year.setFocusable(true);
        btn_edit.setEnabled(false);
        btn_img.setEnabled(true);
        btn_publisher.setEnabled(true);
        btn_category.setEnabled(true);
        btn_bookshelf.setEnabled(true);
    }
    
    public boolean validateInputs() {
        if(Validator.isEmpty(txt_title.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên sách");
            return false;
        }
        if(Validator.isEmpty(txt_author.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tác giả");
            return false;
        }
        if(Validator.isEmpty(txt_publisher.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhà xuất bản");
            return false;
        }
        if(Validator.isEmpty(txt_bookshelf.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn kệ sách");
            return false;
        }
        if(Validator.isEmpty(txt_category.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn danh mục");
            return false;
        }
        if(Validator.isEmpty(txt_year.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập năm xuất bản");
            return false;
        }
        if(!Validator.isName(txt_title.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sách hợp lệ");
            return false;
        }
        if(!Validator.isName(txt_author.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên tác giả hợp lệ");
            return false;
        }
        if(!Validator.isInteger(txt_year.getText())) {
            JOptionPane.showMessageDialog(this, "Năm xuất bản phải là số nguyên");
            return false;
        }
        if (imageURL == null) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hình ảnh cho sách");
            return false;
        }
        return true;
    }
    
    public void editBook() {
        book.setTitle(txt_title.getText());
        book.setAuthor(txt_author.getText());
        book.setYearPublish(Integer.parseInt(txt_year.getText()));
        book.setPublisherId(publisher.getId());
        book.setCategoryId(category.getId());
        book.setBookshelf_id(bookshelf.getId());
        if(imageChanged)
            book.setImage(addImage(imageURL));
    }
    
    public void updateEvent() {
        if(!validateInputs())
            return;
        editBook();
        if(bookBUS.updateBook(book)) {
            JOptionPane.showMessageDialog(null, "Sửa thông tin sách thành công");
            dispose();
        }
    }
    
    public BookDTO getNewBook() {
        String title = txt_title.getText();
        String author = txt_author.getText();
        int publisher_id = publisher.getId();
        int publish_year = Integer.parseInt(txt_year.getText());
        int category_id = category.getId();
        String image = addImage(imageURL);
        int bookshelf_id = bookshelf.getId();
        
        return new BookDTO(title, author, publisher_id, publish_year, category_id, 0, image, bookshelf_id);
    }
    
    public void addEvent() {
        if(!validateInputs())
            return;
        book = getNewBook();
        if(bookBUS.addBook(book)) {
            JOptionPane.showMessageDialog(null, "Thêm sách mới thành công");
            dispose();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_image = new javax.swing.JLabel();
        lbl_title = new javax.swing.JLabel();
        txt_title = new javax.swing.JTextField();
        lbl_author = new javax.swing.JLabel();
        txt_author = new javax.swing.JTextField();
        lbl_publisher = new javax.swing.JLabel();
        txt_publisher = new javax.swing.JTextField();
        btn_img = new javax.swing.JButton();
        lbl_category = new javax.swing.JLabel();
        txt_category = new javax.swing.JTextField();
        lbl_year = new javax.swing.JLabel();
        txt_year = new javax.swing.JTextField();
        lbl_quantity = new javax.swing.JLabel();
        txt_quantity = new javax.swing.JTextField();
        btn_save = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();
        btn_card1 = new javax.swing.JButton();
        btn_publisher = new javax.swing.JButton();
        btn_category = new javax.swing.JButton();
        lbl_quantity1 = new javax.swing.JLabel();
        txt_quantity1 = new javax.swing.JTextField();
        lbl_publisher1 = new javax.swing.JLabel();
        txt_bookshelf = new javax.swing.JTextField();
        btn_bookshelf = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_card2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CHI TIẾT SÁCH");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

        jPanel3.setPreferredSize(new java.awt.Dimension(636, 358));
        jPanel3.setLayout(new java.awt.CardLayout());

        lbl_image.setBorder(new javax.swing.border.MatteBorder(null));
        lbl_image.setPreferredSize(new java.awt.Dimension(200, 267));

        lbl_title.setText("Tên sách");

        lbl_author.setText("Tác giả");

        lbl_publisher.setText("Nhà xuất bản");

        txt_publisher.setFocusable(false);

        btn_img.setBackground(new java.awt.Color(0, 133, 249));
        btn_img.setForeground(new java.awt.Color(255, 255, 255));
        btn_img.setText("Chọn ảnh");
        btn_img.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_img.setFocusPainted(false);

        lbl_category.setText("Danh mục");

        txt_category.setFocusable(false);

        lbl_year.setText("Năm xuất bản");

        lbl_quantity.setText("Số lượng");

        txt_quantity.setFocusable(false);

        btn_save.setBackground(new java.awt.Color(21, 154, 32));
        btn_save.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Lưu");
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.setFocusPainted(false);

        btn_edit.setBackground(new java.awt.Color(255, 215, 64));
        btn_edit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit.setText("Sửa");
        btn_edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_edit.setFocusPainted(false);

        btn_exit.setBackground(new java.awt.Color(244, 67, 54));
        btn_exit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_exit.setForeground(new java.awt.Color(255, 255, 255));
        btn_exit.setText("Hủy");
        btn_exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_exit.setFocusPainted(false);

        btn_card1.setBackground(new java.awt.Color(0, 153, 153));
        btn_card1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_card1.setForeground(new java.awt.Color(255, 255, 255));
        btn_card1.setText("Hiện có");
        btn_card1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_card1.setFocusPainted(false);
        btn_card1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_card1MousePressed(evt);
            }
        });

        btn_publisher.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_publisher.setText("...");
        btn_publisher.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_publisher.setFocusPainted(false);
        btn_publisher.setMaximumSize(new java.awt.Dimension(40, 40));
        btn_publisher.setPreferredSize(new java.awt.Dimension(40, 40));
        btn_publisher.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_publisher.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        btn_category.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_category.setText("...");
        btn_category.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_category.setFocusPainted(false);
        btn_category.setMaximumSize(new java.awt.Dimension(40, 40));
        btn_category.setPreferredSize(new java.awt.Dimension(40, 40));
        btn_category.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_category.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        lbl_quantity1.setText("SL có thể mượn");

        txt_quantity1.setFocusable(false);

        lbl_publisher1.setText("Kệ sách");

        txt_bookshelf.setFocusable(false);

        btn_bookshelf.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_bookshelf.setText("...");
        btn_bookshelf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_bookshelf.setFocusPainted(false);
        btn_bookshelf.setMaximumSize(new java.awt.Dimension(40, 40));
        btn_bookshelf.setPreferredSize(new java.awt.Dimension(40, 40));
        btn_bookshelf.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_bookshelf.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(btn_img))
                    .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_title)
                    .addComponent(lbl_author)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(29, 29, 29)
                            .addComponent(btn_card1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txt_title)
                        .addComponent(txt_author)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_category)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(txt_category, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_category, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbl_year, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_year))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(txt_quantity)
                                    .addGap(5, 5, 5))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lbl_quantity)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_quantity1)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbl_quantity1)
                                    .addGap(7, 7, 7))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_publisher)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(txt_publisher)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_publisher, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_publisher1)
                                .addComponent(txt_bookshelf, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_bookshelf, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_title)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_title, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_author)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_author, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_publisher)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_publisher, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(txt_publisher, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_publisher1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_bookshelf, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(txt_bookshelf, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(lbl_category)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_category, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(btn_category, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lbl_year)
                                                .addComponent(lbl_quantity))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_year, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addComponent(txt_quantity1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_card1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_quantity1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_img)))
                .addContainerGap())
        );

        jPanel3.add(jPanel2, "card1");

        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ISBN", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
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
        }

        btn_card2.setBackground(new java.awt.Color(0, 153, 153));
        btn_card2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_card2.setForeground(new java.awt.Color(255, 255, 255));
        btn_card2.setText("Quay lại");
        btn_card2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_card2.setFocusPainted(false);
        btn_card2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_card2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_card2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn_card2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel4, "card2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(279, 279, 279)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_card1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_card1MousePressed
        CardLayout cardLayout = (CardLayout) jPanel3.getLayout();
        cardLayout.show(jPanel3, "card2");
    }//GEN-LAST:event_btn_card1MousePressed

    private void btn_card2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_card2MousePressed
        CardLayout cardLayout = (CardLayout) jPanel3.getLayout();
        cardLayout.show(jPanel3, "card1");
    }//GEN-LAST:event_btn_card2MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_bookshelf;
    private javax.swing.JButton btn_card1;
    private javax.swing.JButton btn_card2;
    private javax.swing.JButton btn_category;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_img;
    private javax.swing.JButton btn_publisher;
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_author;
    private javax.swing.JLabel lbl_category;
    private javax.swing.JLabel lbl_image;
    private javax.swing.JLabel lbl_publisher;
    private javax.swing.JLabel lbl_publisher1;
    private javax.swing.JLabel lbl_quantity;
    private javax.swing.JLabel lbl_quantity1;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JLabel lbl_year;
    private javax.swing.JTextField txt_author;
    private javax.swing.JTextField txt_bookshelf;
    private javax.swing.JTextField txt_category;
    private javax.swing.JTextField txt_publisher;
    private javax.swing.JTextField txt_quantity;
    private javax.swing.JTextField txt_quantity1;
    private javax.swing.JTextField txt_title;
    private javax.swing.JTextField txt_year;
    // End of variables declaration//GEN-END:variables
}
