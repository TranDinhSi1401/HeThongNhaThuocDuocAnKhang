/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import com.formdev.flatlaf.FlatClientProperties;
import com.kitfox.svg.app.beans.SVGIcon;
import hethongnhathuocduocankhang.bus.BanHangBUS;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.dao.DonViTinhDAO;
import hethongnhathuocduocankhang.dao.KhachHangDAO;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.KhachHang;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 *
 * @author trand
 */
public class BanHangPane extends javax.swing.JPanel {
    private BanHangBUS bus = new BanHangBUS();
    private boolean isMerging = false;
    private Object oldSoLuong = null;
    private Object oldDonViTinh = null;
    /**
     * Creates new form BanHangGUI
     */
    
    public BanHangPane() {
        initComponents();
        
        try {
            ConnectDB.getInstance().connect();
        }catch(SQLException e) {
            System.out.println("Không thể kết nối vs CSDL");
        }
        
        hideColumn(tblCTHD, 8);
        hideColumn(tblCTHD, 7);

        ButtonGroup group = new ButtonGroup();
        group.add(radTienMat);
        group.add(radChuyenKhoan);
        
        TableColumn columnDVT = tblCTHD.getColumnModel().getColumn(2);
        
        // render cột đơn vị tính theo comboBox
        columnDVT.setCellEditor(new DefaultCellEditor(new JComboBox<String>()) {

            private JComboBox<String> currentCombo;

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int row, int column) {
                oldDonViTinh = value;
                
                String maSP = table.getValueAt(row, 8).toString();
                ArrayList<DonViTinh> dsDVT = DonViTinhDAO.getDonViTinhTheoMaSP(maSP);

                currentCombo = new JComboBox<>();
                for (DonViTinh dvt : dsDVT) {
                    currentCombo.addItem(dvt.getTenDonVi());
                }

                if (value != null) {
                    currentCombo.setSelectedItem(value.toString());
                }
                
                currentCombo.addActionListener(e -> stopCellEditing());
                
                return currentCombo;
            }

            @Override
            public Object getCellEditorValue() {
                return currentCombo.getSelectedItem();
            }
        });

        
        DefaultTableModel model = (DefaultTableModel)tblCTHD.getModel();
        // Lấy giá trị cũ trước thay đổi để rollback
        tblCTHD.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tblCTHD.getSelectedRow();
                int col = tblCTHD.getSelectedColumn();
                if (col == 4 && row != -1) {
                    oldSoLuong = tblCTHD.getValueAt(row, 4);
                }
            }
        });

        // Sự kiện thay đổi số lượng hoặc đvt
        model.addTableModelListener(e -> {
            if (e.getColumn() == 2 || e.getColumn() == 4) {
                if (isMerging) return;
                
                int row = e.getFirstRow();
                Object dvtObj = model.getValueAt(row, 2);
                Object slObj  = model.getValueAt(row, 4);
                Object maSPObj = model.getValueAt(row, 8);

                if (dvtObj == null || slObj == null || maSPObj == null) {
                    System.out.println("null pointer");
                    return;
                }

                String tenDVT = dvtObj.toString();
                int soLuong = Integer.parseInt(slObj.toString());
                String masp = maSPObj.toString();

                for(int i = 0; i < tblCTHD.getRowCount(); i++) {
                    if(i == row) continue;    
                    if(model.getValueAt(i, 2).equals(dvtObj) && model.getValueAt(i, 8).equals(maSPObj)) {
                        int soLuongGop = Integer.parseInt(model.getValueAt(i, 4).toString()) + soLuong;
                        isMerging = true;
                        
                        model.setValueAt(soLuongGop, i, 4);
                        model.removeRow(row);
                        
                        isMerging = false;
                        return;
                    }
                }
                
                try {
                    Object[] updatedInfo = bus.thayDoiChiTietHoaDon(masp, soLuong, tenDVT);
                    model.setValueAt(updatedInfo[0], row, 3);
                    model.setValueAt(updatedInfo[1], row, 5);
                    model.setValueAt(updatedInfo[2], row, 6);
                    model.setValueAt(updatedInfo[3], row, 7);
                    capNhatTongTien(model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE );
                    if(ex.getMessage().trim().equalsIgnoreCase("Không đủ số lượng") || ex.getMessage().trim().equalsIgnoreCase("Số lượng phải lớn hơn bằng 1")) {
                        isMerging = true; // chặn event vòng lặp
                        if(e.getColumn() == 4) {
                            // roll back số lượng
                             model.setValueAt(oldSoLuong, row, 4);
                        } else {
                            // roll back dvt
                            model.setValueAt(oldDonViTinh, row, 2);
                        }    
                        isMerging = false; // mở lại
                    }
                }
               
            }
        });
        
        // Sự kiện hỗ trợ tự định dạng số khi nhập
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat("#,##0", symbols);
        
        txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
            
            private boolean coDangFormat = false;

            // Lắng nghe khi thêm ký tự
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!coDangFormat) {
                    formatText();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!coDangFormat) {
                    formatText();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void formatText() {
                if (coDangFormat) {
                    return;
                }

                // Lấy văn bản thô (chỉ số) dựa trên nội dung hiện tại của ô text
                String rawText;
                try {
                    // Lấy văn bản hiện tại (có thể bao gồm dấu phân cách cũ)
                    String currentText = txtTienKhachDua.getText();
                    // Bỏ qua place holder
                    if (currentText == null 
                            || currentText.isBlank() 
                            || currentText.equals("Nhập tiền khách đưa [F5]")) {
                        return;
                    }
                    // Lọc để lấy chuỗi chỉ gồm số
                    rawText = currentText.replaceAll("[^\\d]", "");
                } catch (Exception e) {
                    // Xử lý lỗi nếu việc lấy text thất bại
                    return;
                }

                if (rawText.isEmpty()) {
                    // Không cần định dạng nếu rỗng
                    return;
                }

                // Bắt đầu quá trình định dạng
                coDangFormat = true;

                try {
                    long value = Long.parseLong(rawText);
                    String formattedText = formatter.format(value);

                    // CHỈ THỰC HIỆN TÁC VỤ THAY ĐỔI GIAO DIỆN TRÊN EDT
                    SwingUtilities.invokeLater(() -> {
                        try {
                            // Lấy lại vị trí con trỏ và văn bản gốc ngay trong invokeLater()
                            int caretPosition = txtTienKhachDua.getCaretPosition();
                            String originalText = txtTienKhachDua.getText();

                            // Cập nhật Text
                            txtTienKhachDua.setText(formattedText);

                            // Điều chỉnh vị trí con trỏ
                            int offset = formattedText.length() - originalText.length();
                            int newCaretPosition = caretPosition + offset;

                            // Giới hạn
                            if (newCaretPosition < 0) {
                                newCaretPosition = 0;
                            }
                            if (newCaretPosition > formattedText.length()) {
                                newCaretPosition = formattedText.length();
                            }

                            txtTienKhachDua.setCaretPosition(newCaretPosition);

                        } catch (Exception ex) {
                            // Bắt lỗi trong invokeLater
                        } finally {
                            // Kết thúc quá trình định dạng, reset cờ
                            coDangFormat = false;
                        }
                    });

                } catch (NumberFormatException ex) {
                    // Bắt lỗi NumberFormatException (nếu số quá lớn)
                    coDangFormat = false;
                }
            }
        });
        
        mapKeyToFocus("F1", txtTimKiem);
        mapKeyToFocus("F2", txtSdtKH);
        mapKeyToClickButton("F3", radTienMat);
        mapKeyToClickButton("F4", radChuyenKhoan);
        mapKeyToFocus("F5", txtTienKhachDua);
        mapKeyToClickButton("F6", btnThanhToan);
        mapKeyToClickButton("F7", btnXoa);
        mapKeyToClickButton("F8", btnXoaTrang);
        mapKeyToClickButton("F9", btnThemKH);
        
        
        
        SwingUtilities.invokeLater(() -> {
            txtTimKiem.requestFocusInWindow();
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pSouth = new javax.swing.JPanel();
        pLeftSouth = new javax.swing.JPanel();
        btnXoa = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();
        pRightSouth = new javax.swing.JPanel();
        btnThanhToan = new javax.swing.JButton();
        pRightCenter = new javax.swing.JPanel();
        pThongTinKH = new javax.swing.JPanel();
        p1 = new javax.swing.JPanel();
        lblSdtKH = new javax.swing.JLabel();
        btnThemKH = new javax.swing.JButton();
        txtSdtKH = new javax.swing.JTextField();
        p2 = new javax.swing.JPanel();
        lblThongTinKH = new javax.swing.JLabel();
        p3 = new javax.swing.JPanel();
        lblMaKH = new javax.swing.JLabel();
        lblMaKH1 = new javax.swing.JLabel();
        p4 = new javax.swing.JPanel();
        lblHoTen = new javax.swing.JLabel();
        lblHoTen1 = new javax.swing.JLabel();
        p5 = new javax.swing.JPanel();
        lblDiemTichLuy = new javax.swing.JLabel();
        lblDiemTichLuy1 = new javax.swing.JLabel();
        pThanhToan = new javax.swing.JPanel();
        p6 = new javax.swing.JPanel();
        lblHinhThucThanhToan = new javax.swing.JLabel();
        p7 = new javax.swing.JPanel();
        radTienMat = new javax.swing.JRadioButton();
        radChuyenKhoan = new javax.swing.JRadioButton();
        p8 = new javax.swing.JPanel();
        lblTongTien = new javax.swing.JLabel();
        lblTongTien1 = new javax.swing.JLabel();
        p9 = new javax.swing.JPanel();
        lblTienKhachDua = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        p10 = new javax.swing.JPanel();
        btnGoiY1 = new javax.swing.JButton();
        btnGoiY2 = new javax.swing.JButton();
        btnGoiY3 = new javax.swing.JButton();
        btnGoiY4 = new javax.swing.JButton();
        btnGoiY5 = new javax.swing.JButton();
        btnGoiY6 = new javax.swing.JButton();
        p11 = new javax.swing.JPanel();
        lblTienThua = new javax.swing.JLabel();
        lblTienThua1 = new javax.swing.JLabel();
        pLeftCenter = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        tblCTHD = new javax.swing.JTable();
        pTimKiem = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        pSouth.setBackground(new java.awt.Color(255, 255, 255));
        pSouth.setPreferredSize(new java.awt.Dimension(100, 60));
        pSouth.setLayout(new java.awt.BorderLayout());
        pSouth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        pLeftSouth.setPreferredSize(new java.awt.Dimension(400, 100));
        pLeftSouth.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 15));

        btnXoa.setBackground(new java.awt.Color(255, 51, 51));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa [F7]");
        btnXoa.setPreferredSize(new java.awt.Dimension(100, 35));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        pLeftSouth.add(btnXoa);

        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaTrang.setText("Xóa trắng [F8]");
        btnXoaTrang.setPreferredSize(new java.awt.Dimension(130, 35));
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        pLeftSouth.add(btnXoaTrang);

        pSouth.add(pLeftSouth, java.awt.BorderLayout.LINE_START);

        pRightSouth.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 15));

        btnThanhToan.setBackground(new java.awt.Color(0, 203, 0));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh toán [F6]");
        btnThanhToan.setPreferredSize(new java.awt.Dimension(260, 35));
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        pRightSouth.add(btnThanhToan);

        pSouth.add(pRightSouth, java.awt.BorderLayout.CENTER);

        add(pSouth, java.awt.BorderLayout.PAGE_END);

        pRightCenter.setBackground(new java.awt.Color(255, 255, 255));
        pRightCenter.setPreferredSize(new java.awt.Dimension(300, 100));
        pRightCenter.setLayout(new java.awt.BorderLayout(0, 10));

        pThongTinKH.setPreferredSize(new java.awt.Dimension(100, 250));
        pThongTinKH.setLayout(new javax.swing.BoxLayout(pThongTinKH, javax.swing.BoxLayout.Y_AXIS));
        pThongTinKH.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );

        pThongTinKH.putClientProperty(FlatClientProperties.STYLE, "arc:20");

        p1.setPreferredSize(new java.awt.Dimension(100, 30));
        p1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        lblSdtKH.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblSdtKH.setText("SĐT khách hàng:");
        p1.add(lblSdtKH);

        btnThemKH.setText("Thêm mới [F9]");
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });
        p1.add(btnThemKH);

        pThongTinKH.add(Box.createVerticalStrut(5));

        pThongTinKH.add(p1);

        txtSdtKH.setText("Nhập sđt khách hàng [F2]");
        txtSdtKH.setPreferredSize(new java.awt.Dimension(95, 40));
        txtSdtKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSdtKHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSdtKHFocusLost(evt);
            }
        });
        txtSdtKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtKHActionPerformed(evt);
            }
        });
        txtSdtKH.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        pThongTinKH.add(txtSdtKH);

        p2.setPreferredSize(new java.awt.Dimension(100, 30));
        p2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblThongTinKH.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblThongTinKH.setText("Thông tin khách hàng:");
        p2.add(lblThongTinKH);

        pThongTinKH.add(Box.createVerticalStrut(10));

        pThongTinKH.add(p2);

        p3.setPreferredSize(new java.awt.Dimension(100, 30));
        p3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMaKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMaKH.setForeground(new java.awt.Color(51, 51, 51));
        lblMaKH.setText("Mã khách hàng:");
        p3.add(lblMaKH);

        lblMaKH1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaKH1.setForeground(new java.awt.Color(51, 51, 51));
        lblMaKH1.setText("KH-00000");
        p3.add(lblMaKH1);

        pThongTinKH.add(p3);

        p4.setPreferredSize(new java.awt.Dimension(100, 30));
        p4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblHoTen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHoTen.setForeground(new java.awt.Color(51, 51, 51));
        lblHoTen.setText("Họ tên:");
        p4.add(lblHoTen);

        lblHoTen1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHoTen1.setForeground(new java.awt.Color(51, 51, 51));
        lblHoTen1.setText("Khách Vãng Lai");
        p4.add(lblHoTen1);

        pThongTinKH.add(p4);

        p5.setPreferredSize(new java.awt.Dimension(100, 30));
        p5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblDiemTichLuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDiemTichLuy.setForeground(new java.awt.Color(51, 51, 51));
        lblDiemTichLuy.setText("Điểm tích lũy:");
        p5.add(lblDiemTichLuy);

        lblDiemTichLuy1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDiemTichLuy1.setForeground(new java.awt.Color(51, 51, 51));
        lblDiemTichLuy1.setText("0");
        p5.add(lblDiemTichLuy1);

        pThongTinKH.add(p5);
        pThongTinKH.add(Box.createVerticalStrut(10));

        pRightCenter.add(pThongTinKH, java.awt.BorderLayout.PAGE_START);

        pThanhToan.setLayout(new javax.swing.BoxLayout(pThanhToan, javax.swing.BoxLayout.Y_AXIS));

        p6.setPreferredSize(new java.awt.Dimension(100, 30));
        p6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblHinhThucThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblHinhThucThanhToan.setText("Hình thức thanh toán:");
        p6.add(lblHinhThucThanhToan);

        p6.setPreferredSize(new Dimension(Short.MAX_VALUE, 30));
        p6.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        p6.setMinimumSize(new Dimension(0, 30));

        pThanhToan.add(p6);

        p7.setPreferredSize(new java.awt.Dimension(100, 50));

        radTienMat.setSelected(true);
        radTienMat.setText("Tiền mặt [F3]");
        p7.add(radTienMat);

        radChuyenKhoan.setText("Chuyển khoản [F4]");
        radChuyenKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radChuyenKhoanActionPerformed(evt);
            }
        });
        p7.add(radChuyenKhoan);

        p7.setPreferredSize(new Dimension(Short.MAX_VALUE, 50));
        p7.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        p7.setMinimumSize(new Dimension(0, 50));

        pThanhToan.add(p7);

        p8.setPreferredSize(new java.awt.Dimension(100, 30));
        p8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTongTien.setText("Tổng tiền:");
        p8.add(lblTongTien);

        lblTongTien1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTongTien1.setForeground(new java.awt.Color(51, 51, 51));
        lblTongTien1.setText("0 ₫");
        p8.add(lblTongTien1);

        p8.setPreferredSize(new Dimension(Short.MAX_VALUE, 30));
        p8.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        p8.setMinimumSize(new Dimension(0, 30));

        pThanhToan.add(p8);

        p9.setPreferredSize(new java.awt.Dimension(100, 30));
        p9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTienKhachDua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTienKhachDua.setText("Tiền khách đưa:");
        p9.add(lblTienKhachDua);

        p9.setPreferredSize(new Dimension(Short.MAX_VALUE, 30));
        p9.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        p9.setMinimumSize(new Dimension(0, 30));

        pThanhToan.add(p9);

        txtTienKhachDua.setText("Nhập tiền khách đưa [F5]");
        txtTienKhachDua.setMinimumSize(new java.awt.Dimension(64, 40));
        txtTienKhachDua.setPreferredSize(new java.awt.Dimension(64, 40));
        txtTienKhachDua.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTienKhachDuaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTienKhachDuaFocusLost(evt);
            }
        });
        txtTienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaActionPerformed(evt);
            }
        });
        txtTienKhachDua.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        pThanhToan.add(txtTienKhachDua);

        p10.setPreferredSize(new java.awt.Dimension(100, 80));
        p10.setLayout(new java.awt.GridLayout(2, 3, 5, 5));

        btnGoiY1.setText("10,000");
        btnGoiY1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoiY1ActionPerformed(evt);
            }
        });
        p10.add(btnGoiY1);

        btnGoiY2.setText("20,000");
        btnGoiY2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoiY2ActionPerformed(evt);
            }
        });
        p10.add(btnGoiY2);

        btnGoiY3.setText("50,000");
        btnGoiY3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoiY3ActionPerformed(evt);
            }
        });
        p10.add(btnGoiY3);

        btnGoiY4.setText("100,000");
        btnGoiY4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoiY4ActionPerformed(evt);
            }
        });
        p10.add(btnGoiY4);

        btnGoiY5.setText("200,000");
        btnGoiY5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoiY5ActionPerformed(evt);
            }
        });
        p10.add(btnGoiY5);

        btnGoiY6.setText("500,000");
        btnGoiY6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoiY6ActionPerformed(evt);
            }
        });
        p10.add(btnGoiY6);

        p10.setPreferredSize(new Dimension(Short.MAX_VALUE, 60));
        p10.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        p10.setMinimumSize(new Dimension(0, 60));
        pThanhToan.add(Box.createVerticalStrut(5));

        pThanhToan.add(p10);

        p11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTienThua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTienThua.setText("Tiền thừa:");
        p11.add(lblTienThua);

        lblTienThua1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTienThua1.setForeground(new java.awt.Color(51, 51, 51));
        p11.add(lblTienThua1);

        pThanhToan.add(p11);

        pThanhToan.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );

        pThanhToan.putClientProperty(FlatClientProperties.STYLE, "arc:20");

        pRightCenter.add(pThanhToan, java.awt.BorderLayout.CENTER);

        pRightCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(pRightCenter, java.awt.BorderLayout.LINE_END);

        pLeftCenter.setBackground(new java.awt.Color(255, 255, 255));
        pLeftCenter.setLayout(new java.awt.BorderLayout(0, 10));

        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        tblCTHD.setBackground(new java.awt.Color(255, 255, 255));
        tblCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên sản phẩm", "Đơn vị tính", "Đơn giá", "Số lượng", "Giảm giá", "Thành tiền", "Mã đơn vị", "Mã sản phẩm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCTHD.setRowHeight(50);
        tblCTHD.setShowHorizontalLines(true);
        tblCTHD.setShowVerticalLines(false);
        tblCTHD.setGridColor(new Color(220, 220, 220));
        tblCTHD.setIntercellSpacing(new Dimension(0, 0));
        jScrollPane.setViewportView(tblCTHD);
        if (tblCTHD.getColumnModel().getColumnCount() > 0) {
            tblCTHD.getColumnModel().getColumn(0).setResizable(false);
            tblCTHD.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblCTHD.getColumnModel().getColumn(1).setResizable(false);
            tblCTHD.getColumnModel().getColumn(1).setPreferredWidth(220);
            tblCTHD.getColumnModel().getColumn(2).setResizable(false);
            tblCTHD.getColumnModel().getColumn(2).setPreferredWidth(70);
            tblCTHD.getColumnModel().getColumn(3).setResizable(false);
            tblCTHD.getColumnModel().getColumn(4).setResizable(false);
            tblCTHD.getColumnModel().getColumn(4).setPreferredWidth(60);
            tblCTHD.getColumnModel().getColumn(5).setResizable(false);
            tblCTHD.getColumnModel().getColumn(6).setResizable(false);
            tblCTHD.getColumnModel().getColumn(7).setResizable(false);
            tblCTHD.getColumnModel().getColumn(8).setResizable(false);
        }

        pLeftCenter.add(jScrollPane, java.awt.BorderLayout.CENTER);

        pTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        pTimKiem.setPreferredSize(new java.awt.Dimension(100, 40));
        pTimKiem.setLayout(new java.awt.BorderLayout(0, 10));
        pTimKiem.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        btnTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/search.png"))); // NOI18N
        btnTimKiem.setIcon(getIconSVG("search"));
        btnTimKiem.setPreferredSize(new java.awt.Dimension(38, 40));
        //btnTimKiem.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));

        btnTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiemMouseClicked(evt);
            }
        });
        pTimKiem.add(btnTimKiem, java.awt.BorderLayout.LINE_END);

        txtTimKiem.setText("Nhập mã sản phẩm [F1]");
        txtTimKiem.setPreferredSize(new java.awt.Dimension(119, 40));
        //txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
            //    BorderFactory.createMatteBorder(1, 1, 1, 0, Color.BLACK),
            //    BorderFactory.createEmptyBorder(0, 10, 0, 0)
            //));
    txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            txtTimKiemFocusGained(evt);
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            txtTimKiemFocusLost(evt);
        }
    });
    txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtTimKiemActionPerformed(evt);
        }
    });
    pTimKiem.add(txtTimKiem, java.awt.BorderLayout.CENTER);

    pLeftCenter.add(pTimKiem, java.awt.BorderLayout.PAGE_START);

    pLeftCenter.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 0));

    add(pLeftCenter, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public void themCTHDVaoTable(Object[] newRow) {
        DefaultTableModel model = (DefaultTableModel) tblCTHD.getModel();
        // Lấy stt của bảng CTHD
        int stt = model.getRowCount() + 1;
        newRow[0] = stt;
        // Thêm vào table
        model.addRow(newRow);
        // Định dạng tiền tệ và phần trăm
        TableColumn colDonGia = tblCTHD.getColumnModel().getColumn(3);
        TableColumn colGiamGia = tblCTHD.getColumnModel().getColumn(5);
        TableColumn colThanhTien = tblCTHD.getColumnModel().getColumn(6);

        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Đơn giá & Thành tiền → tiền VN
        DefaultTableCellRenderer currencyRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof Number number) {
                    lbl.setText(currencyVN.format(number.doubleValue()));
                }
                lbl.setHorizontalAlignment(SwingConstants.RIGHT);
                return lbl;
            }
        };

        colDonGia.setCellRenderer(currencyRenderer);
        colThanhTien.setCellRenderer(currencyRenderer);

        // Giảm giá → thêm %
        DefaultTableCellRenderer percentRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof Number number) {
                    lbl.setText(number.doubleValue() + " %");
                }
                lbl.setHorizontalAlignment(SwingConstants.RIGHT);
                return lbl;
            }
        };
        colGiamGia.setCellRenderer(percentRenderer);
        
        // Căn giữa các cột stt và đơn vị tính      
        TableColumn colSTT = tblCTHD.getColumnModel().getColumn(0);
        TableColumn colDVT = tblCTHD.getColumnModel().getColumn(2);
        DefaultTableCellRenderer centerAlignRenderer = new DefaultTableCellRenderer();
        centerAlignRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        colSTT.setCellRenderer(centerAlignRenderer);
        colDVT.setCellRenderer(centerAlignRenderer);
        
        capNhatTongTien(model);
    }
      
    private void capNhatGoiYSauKhiTongTienThayDoi(double tongTien) {
        long t = (long)tongTien;

        long[] menhGia = {10000, 20000, 50000, 100000, 200000, 500000};
        JButton[] buttons = {btnGoiY1, btnGoiY2, btnGoiY3, btnGoiY4, btnGoiY5, btnGoiY6};

        for (int i = 0; i < menhGia.length; i++) {
            long goiY = lamTronLen(t, menhGia[i]);

            if (i > 0 && goiY <= Long.parseLong(buttons[i-1].getText().replace(",", ""))) {
                goiY += menhGia[i];
            }
            
            buttons[i].setText(String.format("%,d", goiY));
        }
    }
    
    private long lamTronLen(long soTien, long menhGia) {
        if (soTien == menhGia) {
            return soTien;
        }
        return ((soTien / menhGia) + 1) * menhGia;
    }
    
    public void capNhatTongTien(DefaultTableModel model) {
        int n = model.getRowCount();
        double tongTien = 0;
        for(int i = 0; i < n; i++) {
            tongTien += Double.parseDouble(model.getValueAt(i, 6).toString());
        }
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String tongTienStr = vndFormat.format(tongTien);
        lblTongTien1.setText(String.valueOf(tongTienStr));
        
        capNhatGoiYSauKhiTongTienThayDoi(tongTien);
    }
    
    public double getTongTien() {
        try {
            String text = lblTongTien1.getText()
                .replace("₫", "") 
                .replace("\u00A0", "")     
                .replaceAll("[^\\d]", "")  
                .trim();
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private void capNhatTienThua() {
        String text = txtTienKhachDua.getText().replaceAll("[^\\d.]", "");
        double tienKhachDua = Double.parseDouble(text);
        //double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText());
        double tongTien = getTongTien();
        if(tienKhachDua < tongTien) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn hoặc bằng tổng tiền", "Warning Message", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double tienThua = tienKhachDua - tongTien;
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String tienThuaStr = vndFormat.format(tienThua);
        lblTienThua1.setText(tienThuaStr);
    }
    
    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        xoaTrang();
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void btnTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseClicked
        // Thêm sản phẩm vào table cthd
        try {
            String maSP = txtTimKiem.getText().trim();
            if(bus.kiemTraKeDon(maSP)) {
                JOptionPane.showMessageDialog(this, "Thuốc bạn vừa tìm kiếm là thuốc kê đơn \n Vui lòng kiểm tra đơn kê rõ ràng và lưu thông tin khách hàng", "Cảnh báo kê đơn", JOptionPane.WARNING_MESSAGE);
            }
            Object[] newRow = bus.themChiTietHoaDon(maSP, tblCTHD);
            if(newRow != null) {
                themCTHDVaoTable(newRow);
            }
        }catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning Message", JOptionPane.WARNING_MESSAGE);
        }
        // Xóa nội dung trong ô text để chuẩn bị cho lần quét tiếp theo
        txtTimKiem.setText("");       
        // Tự động đặt con trỏ chuột trở lại ô này
        txtTimKiem.requestFocusInWindow();
    }//GEN-LAST:event_btnTimKiemMouseClicked

    private void txtSdtKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSdtKHActionPerformed
        String sdt = txtSdtKH.getText().trim();
        KhachHang kh = bus.layThongTinKhachHang(sdt);
        if(kh != null) {
            String maKH = kh.getMaKH();
            String hoTen = kh.getHoTenDem() + " " + kh.getTen();
            int diemTichLuy = kh.getDiemTichLuy();
            lblMaKH1.setText(maKH);
            lblHoTen1.setText(hoTen);
            lblDiemTichLuy1.setText(String.valueOf(diemTichLuy));
        } else {
            JOptionPane.showMessageDialog(this, "Số điện thoại không tồn tại", "Warning Message", JOptionPane.WARNING_MESSAGE);
        }       
    }//GEN-LAST:event_txtSdtKHActionPerformed

    private void txtTienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaActionPerformed
        try {
            capNhatTienThua();
        }catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số dương", "Warning Message", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtTienKhachDuaActionPerformed
 
    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        try {
            String maKH = lblMaKH1.getText().trim();
            boolean chuyenKhoan = radChuyenKhoan.isSelected();
            double tongTien = getTongTien();
            double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText().replaceAll("\\s", ""));
            double tienThua = Double.parseDouble(lblTienThua1.getText().replaceAll("[^\\d]", ""));
            if(bus.thanhToan(tblCTHD, maKH, chuyenKhoan, tongTien, tienKhachDua, tienThua)) {
                xoaTrang();
            }
        } catch(Exception e) {
            if(e.getMessage().equalsIgnoreCase("For input string: \"Nhậptiềnkháchđưa[F5]\"")) {
                JOptionPane.showMessageDialog(this, "tiền khách đưa phải là số dương", "Error Message", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // Thêm sản phẩm vào table cthd
        try {
            String maSP = txtTimKiem.getText().trim();
            if(bus.kiemTraKeDon(maSP)) {
                JOptionPane.showMessageDialog(this, "Thuốc bạn vừa tìm kiếm là thuốc kê đơn \n Vui lòng kiểm tra đơn kê rõ ràng và lưu thông tin khách hàng", "Cảnh báo kê đơn", JOptionPane.WARNING_MESSAGE);
            }           
            Object[] newRow = bus.themChiTietHoaDon(maSP, tblCTHD);
            if(newRow != null) {
                themCTHDVaoTable(newRow);
            }          
        }catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning Message", JOptionPane.WARNING_MESSAGE);
        }
        // Xóa nội dung trong ô text để chuẩn bị cho lần quét tiếp theo
        txtTimKiem.setText("");       
        // Tự động đặt con trỏ chuột trở lại ô này
        txtTimKiem.requestFocusInWindow();
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        if(txtTimKiem.getText().equals("Nhập mã sản phẩm [F1]")) {
            txtTimKiem.setText("");
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        if(txtTimKiem.getText().equals("")) {
            txtTimKiem.setText("Nhập mã sản phẩm [F1]");
        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void txtSdtKHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSdtKHFocusGained
        if(txtSdtKH.getText().equals("Nhập sđt khách hàng [F2]")) {
            txtSdtKH.setText("");
        }
    }//GEN-LAST:event_txtSdtKHFocusGained

    private void txtSdtKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSdtKHFocusLost
        if(txtSdtKH.getText().equals("")) {
            txtSdtKH.setText("Nhập sđt khách hàng [F2]");
        }
    }//GEN-LAST:event_txtSdtKHFocusLost

    private void btnGoiY1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoiY1ActionPerformed
        txtTienKhachDua.setText(btnGoiY1.getText().replace(",", ""));
        capNhatTienThua();
    }//GEN-LAST:event_btnGoiY1ActionPerformed

    private void btnGoiY2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoiY2ActionPerformed
        txtTienKhachDua.setText(btnGoiY2.getText().replace(",", ""));
        capNhatTienThua();
    }//GEN-LAST:event_btnGoiY2ActionPerformed

    private void btnGoiY3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoiY3ActionPerformed
        txtTienKhachDua.setText(btnGoiY3.getText().replace(",", ""));
        capNhatTienThua();
    }//GEN-LAST:event_btnGoiY3ActionPerformed

    private void btnGoiY4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoiY4ActionPerformed
        txtTienKhachDua.setText(btnGoiY4.getText().replace(",", ""));
        capNhatTienThua();
    }//GEN-LAST:event_btnGoiY4ActionPerformed

    private void btnGoiY5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoiY5ActionPerformed
        txtTienKhachDua.setText(btnGoiY5.getText().replace(",", ""));
        capNhatTienThua();
    }//GEN-LAST:event_btnGoiY5ActionPerformed

    private void btnGoiY6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoiY6ActionPerformed
        txtTienKhachDua.setText(btnGoiY6.getText().replace(",", ""));
        capNhatTienThua();
    }//GEN-LAST:event_btnGoiY6ActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int selectedRow = tblCTHD.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblCTHD.getModel();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!", "Warning Message", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn xóa dòng này không?", 
                "Xác nhận", 
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(selectedRow);
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(i + 1, i, 0);
            }
        }  
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtTienKhachDuaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTienKhachDuaFocusGained
        if(txtTienKhachDua.getText().trim().equalsIgnoreCase("Nhập tiền khách đưa [F5]")) {
            txtTienKhachDua.setText("");
        }
    }//GEN-LAST:event_txtTienKhachDuaFocusGained

    private void txtTienKhachDuaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTienKhachDuaFocusLost
        if(txtTienKhachDua.getText().trim().equalsIgnoreCase("")) {
            txtTienKhachDua.setText("Nhập tiền khách đưa [F5]");
        }
    }//GEN-LAST:event_txtTienKhachDuaFocusLost

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        ThemKhachHangGUI pnlThemKH = new ThemKhachHangGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm khách hàng mới");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemKH);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        int maKHCUoiCung = KhachHangDAO.getMaKHCUoiCung();
        maKHCUoiCung++;
        String maKHNew = String.format("KH-%05d", maKHCUoiCung);

        pnlThemKH.setTxtMaKhachHang(maKHNew);
        pnlThemKH.setTxtDiemTichLuy(0);
        pnlThemKH.getTxtDiemTichLuy().setEnabled(false);

        dialog.setVisible(true);

        KhachHang khNew = pnlThemKH.getKhachHangMoi();

        if (khNew != null) {
            if (KhachHangDAO.themKhachHang(khNew)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại (có thể trùng SĐT).");
            }
        }
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void radChuyenKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radChuyenKhoanActionPerformed
        double tongTien = getTongTien();
        DecimalFormat df = new DecimalFormat("0");
        txtTienKhachDua.setText(df.format(tongTien));
        capNhatTienThua();
    }//GEN-LAST:event_radChuyenKhoanActionPerformed
        
    public void xoaTrang() {
        DefaultTableModel model = (DefaultTableModel)tblCTHD.getModel();
        model.setRowCount(0);

        lblTongTien1.setText("0 ₫");
        txtTienKhachDua.setText("Nhập tiền khách đưa [F5]");
        lblTienThua1.setText("");
        radTienMat.setSelected(true);
        txtSdtKH.setText("Nhập sđt khách hàng [F2]");
        lblMaKH1.setText("KH-00000");
        lblHoTen1.setText("Khách vãng lai");
        lblDiemTichLuy1.setText("0");
        txtTimKiem.setText("Nhập mã sản phẩm [F1]");
        JButton[] btns = {btnGoiY1, btnGoiY2, btnGoiY3, btnGoiY4, btnGoiY5, btnGoiY6};
        long[] menhGia = {10000, 20000, 50000, 100000, 200000, 500000};
        for(int i = 0; i < btns.length; i++) {
            btns[i].setText(String.format("%,d", menhGia[i]));
        }
    }

    private void mapKeyToFocus(String key, JComponent component) {
        InputMap im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = component.getActionMap();

        im.put(KeyStroke.getKeyStroke(key), "focus_" + key);
        am.put("focus_" + key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                component.requestFocus();
                if (component instanceof JTextField jTextField) {
                    jTextField.selectAll();
                }
            }
        });
    }

    private void mapKeyToClickButton(String key, AbstractButton button) {
        InputMap im = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = button.getActionMap();

        im.put(KeyStroke.getKeyStroke(key), "click_" + key);
        am.put("click_" + key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick(); // kích hoạt sự kiện button
            }
        });
    }
    
    private void hideColumn(JTable table, int colIndex) {
        TableColumn column = table.getColumnModel().getColumn(colIndex);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setResizable(false);
    }
    
    private Icon getIconSVG(String o) {
        try {
            String path = "/resources/images/"+ o +".svg";
            SVGIcon svgIcon = new SVGIcon();
            svgIcon.setSvgURI(getClass().getResource(path).toURI());

            svgIcon.setAutosize(0);
            svgIcon.setScaleToFit(true);

            //chỉnh kích thước
            svgIcon.setPreferredSize(new java.awt.Dimension(30, 30));

            return svgIcon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGoiY1;
    private javax.swing.JButton btnGoiY2;
    private javax.swing.JButton btnGoiY3;
    private javax.swing.JButton btnGoiY4;
    private javax.swing.JButton btnGoiY5;
    private javax.swing.JButton btnGoiY6;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JLabel lblDiemTichLuy;
    private javax.swing.JLabel lblDiemTichLuy1;
    private javax.swing.JLabel lblHinhThucThanhToan;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblHoTen1;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblMaKH1;
    private javax.swing.JLabel lblSdtKH;
    private javax.swing.JLabel lblThongTinKH;
    private javax.swing.JLabel lblTienKhachDua;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTienThua1;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTongTien1;
    private javax.swing.JPanel p1;
    private javax.swing.JPanel p10;
    private javax.swing.JPanel p11;
    private javax.swing.JPanel p2;
    private javax.swing.JPanel p3;
    private javax.swing.JPanel p4;
    private javax.swing.JPanel p5;
    private javax.swing.JPanel p6;
    private javax.swing.JPanel p7;
    private javax.swing.JPanel p8;
    private javax.swing.JPanel p9;
    private javax.swing.JPanel pLeftCenter;
    private javax.swing.JPanel pLeftSouth;
    private javax.swing.JPanel pRightCenter;
    private javax.swing.JPanel pRightSouth;
    private javax.swing.JPanel pSouth;
    private javax.swing.JPanel pThanhToan;
    private javax.swing.JPanel pThongTinKH;
    private javax.swing.JPanel pTimKiem;
    private javax.swing.JRadioButton radChuyenKhoan;
    private javax.swing.JRadioButton radTienMat;
    private javax.swing.JTable tblCTHD;
    private javax.swing.JTextField txtSdtKH;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables


}
