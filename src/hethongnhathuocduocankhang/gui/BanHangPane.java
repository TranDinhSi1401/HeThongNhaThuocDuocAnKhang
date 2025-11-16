/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.dao.ChiTietHoaDonDAO;
import hethongnhathuocduocankhang.dao.ChiTietXuatLoDAO;
import hethongnhathuocduocankhang.dao.DonViTinhDAO;
import hethongnhathuocduocankhang.dao.HoaDonDAO;
import hethongnhathuocduocankhang.dao.KhachHangDAO;
import hethongnhathuocduocankhang.dao.KhuyenMaiDAO;
import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.dao.LoSanPhamDAO;
import hethongnhathuocduocankhang.entity.ChiTietHoaDon;
import hethongnhathuocduocankhang.entity.ChiTietXuatLo;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.HoaDon;
import hethongnhathuocduocankhang.entity.KhachHang;
import hethongnhathuocduocankhang.entity.KhuyenMai;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.SanPham;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 *
 * @author trand
 */
public class BanHangPane extends javax.swing.JPanel {
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
        
        JTableHeader header = tblCTHD.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 30));
        header.setBorder(null);
        header.setBackground(new Color(245, 245, 245));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        TableColumn colMaSP = tblCTHD.getColumnModel().getColumn(8);
        tblCTHD.removeColumn(colMaSP);
        TableColumn colMaDVT = tblCTHD.getColumnModel().getColumn(7);
        tblCTHD.removeColumn(colMaDVT);
        
        ButtonGroup group = new ButtonGroup();
        group.add(radTienMat);
        group.add(radChuyenKhoan);
        
        double tongTien = getTongTien();
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String tongTienStr = vndFormat.format(tongTien);
        lblTongTien1.setText(tongTienStr);
        
        DefaultTableModel model = (DefaultTableModel)tblCTHD.getModel();
        model.addTableModelListener(e -> {
            if (e.getColumn() == 2 || e.getColumn() == 4) {
                int row = e.getFirstRow();
                
                String tendvt = model.getValueAt(row, 2).toString();
                int soluong = Integer.parseInt(model.getValueAt(row, 4).toString());
                double giamgia = 0;
                
                String masp = model.getValueAt(row, 8).toString();
                ArrayList<KhuyenMai> dskm = KhuyenMaiDAO.getKhuyenMaiTheoMaSP(masp);
                dskm.sort((b, a) -> Double.compare(a.getPhanTram(), b.getPhanTram()));  
                ArrayList<DonViTinh> dsdvt = DonViTinhDAO.getDonViTinhTheoMaSP(masp);
                
                ArrayList<LoSanPham> dsLSP = LoSanPhamDAO.getLoSanPhamTheoMaSP(masp);
                int tongSoLuong = 0;
                for(LoSanPham lsp : dsLSP) {
                    tongSoLuong += lsp.getSoLuong();
                }
                
                for(KhuyenMai km : dskm) {
                    if(soluong >= km.getSoLuongToiThieu() && soluong <= km.getSoLuongToiDa()) {
                        giamgia = km.getPhanTram();
                        break;
                    } 
                }
                for (DonViTinh dvt : dsdvt) {
                    if (dvt.getTenDonVi().equals(tendvt)) {
                        int heSoQuyDoi = dvt.getHeSoQuyDoi();
                        double dongia = dvt.getGiaBanTheoDonVi();
                        double thanhtien = soluong * dongia * (1 - giamgia / 100);
                        String madvt = dvt.getMaDonViTinh();
                        
                        if(soluong * heSoQuyDoi > tongSoLuong) {
                            JOptionPane.showMessageDialog(this, "Không đủ số lượng");
                            return;
                        }
                        
                        model.setValueAt(dongia, row, 3);
                        model.setValueAt(giamgia, row, 5);
                        model.setValueAt(thanhtien, row, 6);
                        model.setValueAt(madvt, row, 7);
                        break;
                    }
                }
                capNhatTongTien(model);
            }
        });
        
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

        pLeftSouth.setBackground(new java.awt.Color(245, 245, 245));
        pLeftSouth.setPreferredSize(new java.awt.Dimension(400, 100));
        pLeftSouth.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 15));

        btnXoa.setBackground(new java.awt.Color(255, 51, 51));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setPreferredSize(new java.awt.Dimension(100, 35));
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaMouseClicked(evt);
            }
        });
        pLeftSouth.add(btnXoa);

        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.setPreferredSize(new java.awt.Dimension(100, 35));
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        pLeftSouth.add(btnXoaTrang);

        pSouth.add(pLeftSouth, java.awt.BorderLayout.LINE_START);

        pRightSouth.setBackground(new java.awt.Color(245, 245, 245));
        pRightSouth.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 15));

        btnThanhToan.setBackground(new java.awt.Color(0, 203, 0));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh toán");
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

        pThongTinKH.setBackground(new java.awt.Color(245, 245, 245));
        pThongTinKH.setLayout(new javax.swing.BoxLayout(pThongTinKH, javax.swing.BoxLayout.Y_AXIS));

        p1.setBackground(new java.awt.Color(245, 245, 245));
        p1.setPreferredSize(new java.awt.Dimension(100, 30));
        p1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        lblSdtKH.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblSdtKH.setText("SĐT khách hàng:");
        p1.add(lblSdtKH);

        pThongTinKH.add(p1);

        txtSdtKH.setForeground(new java.awt.Color(117, 117, 117));
        txtSdtKH.setText("Nhập sđt khách hàng");
        txtSdtKH.setPreferredSize(new java.awt.Dimension(95, 30));
        txtSdtKH.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1, true),
            BorderFactory.createEmptyBorder(0, 10, 0, 0)
        ));
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
        pThongTinKH.add(txtSdtKH);

        p2.setBackground(new java.awt.Color(245, 245, 245));
        p2.setPreferredSize(new java.awt.Dimension(100, 30));
        p2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblThongTinKH.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblThongTinKH.setText("Thông tin khách hàng:");
        p2.add(lblThongTinKH);

        pThongTinKH.add(p2);

        p3.setBackground(new java.awt.Color(245, 245, 245));
        p3.setPreferredSize(new java.awt.Dimension(100, 30));
        p3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMaKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMaKH.setForeground(new java.awt.Color(51, 51, 51));
        lblMaKH.setText("Mã khách hàng:");
        p3.add(lblMaKH);

        lblMaKH1.setForeground(new java.awt.Color(51, 51, 51));
        lblMaKH1.setText("KH-99999");
        p3.add(lblMaKH1);

        pThongTinKH.add(p3);

        p4.setBackground(new java.awt.Color(245, 245, 245));
        p4.setPreferredSize(new java.awt.Dimension(100, 30));
        p4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblHoTen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHoTen.setForeground(new java.awt.Color(51, 51, 51));
        lblHoTen.setText("Họ tên:");
        p4.add(lblHoTen);

        lblHoTen1.setForeground(new java.awt.Color(51, 51, 51));
        lblHoTen1.setText("Khách Vãng Lai");
        p4.add(lblHoTen1);

        pThongTinKH.add(p4);

        p5.setBackground(new java.awt.Color(245, 245, 245));
        p5.setPreferredSize(new java.awt.Dimension(100, 30));
        p5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblDiemTichLuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDiemTichLuy.setForeground(new java.awt.Color(51, 51, 51));
        lblDiemTichLuy.setText("Điểm tích lũy:");
        p5.add(lblDiemTichLuy);

        lblDiemTichLuy1.setForeground(new java.awt.Color(51, 51, 51));
        lblDiemTichLuy1.setText("0");
        p5.add(lblDiemTichLuy1);

        pThongTinKH.add(p5);

        pRightCenter.add(pThongTinKH, java.awt.BorderLayout.PAGE_START);

        pThanhToan.setBackground(new java.awt.Color(245, 245, 245));
        pThanhToan.setLayout(new javax.swing.BoxLayout(pThanhToan, javax.swing.BoxLayout.Y_AXIS));

        p6.setBackground(new java.awt.Color(245, 245, 245));
        p6.setPreferredSize(new java.awt.Dimension(100, 30));
        p6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblHinhThucThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblHinhThucThanhToan.setText("Hình thức thanh toán:");
        p6.add(lblHinhThucThanhToan);

        p6.setPreferredSize(new Dimension(Short.MAX_VALUE, 30));
        p6.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        p6.setMinimumSize(new Dimension(0, 30));

        pThanhToan.add(p6);

        p7.setBackground(new java.awt.Color(245, 245, 245));
        p7.setPreferredSize(new java.awt.Dimension(100, 50));

        radTienMat.setBackground(new java.awt.Color(245, 245, 245));
        radTienMat.setSelected(true);
        radTienMat.setText("Tiền mặt");
        p7.add(radTienMat);

        radChuyenKhoan.setBackground(new java.awt.Color(245, 245, 245));
        radChuyenKhoan.setText("Chuyển khoản");
        p7.add(radChuyenKhoan);

        p7.setPreferredSize(new Dimension(Short.MAX_VALUE, 50));
        p7.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        p7.setMinimumSize(new Dimension(0, 50));

        pThanhToan.add(p7);

        p8.setBackground(new java.awt.Color(245, 245, 245));
        p8.setPreferredSize(new java.awt.Dimension(100, 30));
        p8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTongTien.setText("Tổng tiền:");
        p8.add(lblTongTien);

        lblTongTien1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTongTien1.setForeground(new java.awt.Color(51, 51, 51));
        lblTongTien1.setText("0");
        p8.add(lblTongTien1);

        p8.setPreferredSize(new Dimension(Short.MAX_VALUE, 30));
        p8.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        p8.setMinimumSize(new Dimension(0, 30));

        pThanhToan.add(p8);

        p9.setBackground(new java.awt.Color(245, 245, 245));
        p9.setPreferredSize(new java.awt.Dimension(100, 30));
        p9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTienKhachDua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTienKhachDua.setText("Tiền khách đưa:");
        p9.add(lblTienKhachDua);

        p9.setPreferredSize(new Dimension(Short.MAX_VALUE, 30));
        p9.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        p9.setMinimumSize(new Dimension(0, 30));

        pThanhToan.add(p9);

        txtTienKhachDua.setPreferredSize(new java.awt.Dimension(64, 30));
        txtTienKhachDua.setPreferredSize(new Dimension(Short.MAX_VALUE, 30));
        txtTienKhachDua.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtTienKhachDua.setMinimumSize(new Dimension(0, 30));
        txtTienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaActionPerformed(evt);
            }
        });
        pThanhToan.add(txtTienKhachDua);

        p10.setBackground(new java.awt.Color(245, 245, 245));
        p10.setPreferredSize(new java.awt.Dimension(100, 80));
        p10.setLayout(new java.awt.GridLayout(2, 3, 5, 5));

        btnGoiY1.setText("100");
        p10.add(btnGoiY1);

        btnGoiY2.setText("200");
        p10.add(btnGoiY2);

        btnGoiY3.setText("300");
        p10.add(btnGoiY3);

        btnGoiY4.setText("400");
        p10.add(btnGoiY4);

        btnGoiY5.setText("500");
        p10.add(btnGoiY5);

        btnGoiY6.setText("600");
        p10.add(btnGoiY6);

        p10.setPreferredSize(new Dimension(Short.MAX_VALUE, 60));
        p10.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        p10.setMinimumSize(new Dimension(0, 60));
        pThanhToan.add(Box.createVerticalStrut(5));

        pThanhToan.add(p10);

        p11.setBackground(new java.awt.Color(245, 245, 245));
        p11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTienThua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTienThua.setText("Tiền thừa:");
        p11.add(lblTienThua);

        lblTienThua1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTienThua1.setForeground(new java.awt.Color(51, 51, 51));
        p11.add(lblTienThua1);

        pThanhToan.add(p11);

        pRightCenter.add(pThanhToan, java.awt.BorderLayout.CENTER);

        pRightCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(pRightCenter, java.awt.BorderLayout.LINE_END);

        pLeftCenter.setBackground(new java.awt.Color(255, 255, 255));
        pLeftCenter.setLayout(new java.awt.BorderLayout(0, 10));

        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        tblCTHD.setBackground(new java.awt.Color(245, 245, 245));
        tblCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên sản phẩm", "Đơn vị tính", "Đơn giá", "Số lượng", "Giảm giá", "Thành tiền", "Mã đơn vị", "Mã sản phẩm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
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
        tblCTHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTHDMouseClicked(evt);
            }
        });
        jScrollPane.setViewportView(tblCTHD);
        if (tblCTHD.getColumnModel().getColumnCount() > 0) {
            tblCTHD.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblCTHD.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblCTHD.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblCTHD.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        pLeftCenter.add(jScrollPane, java.awt.BorderLayout.CENTER);

        pTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        pTimKiem.setPreferredSize(new java.awt.Dimension(100, 40));
        pTimKiem.setLayout(new java.awt.BorderLayout(0, 10));
        pTimKiem.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/search.png"))); // NOI18N
        btnTimKiem.setPreferredSize(new java.awt.Dimension(38, 40));
        btnTimKiem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        btnTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiemMouseClicked(evt);
            }
        });
        pTimKiem.add(btnTimKiem, java.awt.BorderLayout.LINE_END);

        txtTimKiem.setForeground(new java.awt.Color(117, 117, 117));
        txtTimKiem.setText("Nhập mã sản phẩm");
        txtTimKiem.setPreferredSize(new java.awt.Dimension(119, 40));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1, true),
            BorderFactory.createEmptyBorder(0, 10, 0, 0)
        ));
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

    public void capNhatTongTien(DefaultTableModel model) {
        int n = model.getRowCount();
        double tongTien = 0;
        for(int i = 0; i < n; i++) {
            tongTien += Double.parseDouble(model.getValueAt(i, 6).toString());
        }
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String tongTienStr = vndFormat.format(tongTien);
        lblTongTien1.setText(String.valueOf(tongTienStr));
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
    
    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        xoaTrang();
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void btnTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseClicked
        themSanPhamVaoTable();
    }//GEN-LAST:event_btnTimKiemMouseClicked

    private void themSanPhamVaoTable() {
        // Lấy các thông tin liên quan đến mã sp
        String maSP = txtTimKiem.getText();
        SanPham sp = SanPhamDAO.timSPTheoMa(maSP);
        ArrayList<DonViTinh> dsDVT = DonViTinhDAO.getDonViTinhTheoMaSP(maSP);
        ArrayList<KhuyenMai> dsKM = KhuyenMaiDAO.getKhuyenMaiTheoMaSP(maSP);
        ArrayList<LoSanPham> dsLSP = LoSanPhamDAO.getLoSanPhamTheoMaSP(maSP);
        int tongSoLuong = dsLSP.stream().mapToInt(LoSanPham :: getSoLuong).sum();
        
        // Bắt lỗi các trường hợp có thể xảy ra
        if (sp == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
            return;
        }
        
        if (dsDVT == null || dsDVT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này chưa có đơn vị tính!");
            return;
        }
        
        if(tongSoLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này không đủ số lượng!");
            return;
        }
        
        // render chi tiết hóa đơn
        DefaultTableModel model = (DefaultTableModel)tblCTHD.getModel();
        
        int stt = model.getRowCount() + 1;
        
        String tenSP = sp.getTen();
        
        dsDVT.sort((a, b) -> Double.compare(a.getHeSoQuyDoi(), b.getHeSoQuyDoi()));
        DonViTinh dvtMacDinh = dsDVT.get(0);
        String tenDVT = dvtMacDinh.getTenDonVi();
        
        double donGia = dvtMacDinh.getGiaBanTheoDonVi();
        
        int soLuong = 1;   
        
        double giamGia = 0;
        dsKM.sort((b, a) -> Double.compare(a.getPhanTram(), b.getPhanTram()));
        for(KhuyenMai km : dsKM) {
            if(soLuong >= km.getSoLuongToiThieu() && soLuong <= km.getSoLuongToiDa()) {
                giamGia = km.getPhanTram();
                break;
            }
        }
        double thanhTien = soLuong * donGia * (1 - giamGia / 100);
        String maDVT = dvtMacDinh.getMaDonViTinh();
        
        Object[] newRow = {stt, tenSP, tenDVT, donGia, soLuong, giamGia, thanhTien, maDVT, maSP};
        model.addRow(newRow);
        
        // tạo combobox cho cột đơn vị tính
//        TableColumn columnDVT = tblCTHD.getColumnModel().getColumn(2);
//        JComboBox<String> cbDonViTinh = new JComboBox<>();
//        for (DonViTinh dvt : dsDVT) {
//            cbDonViTinh.addItem(dvt.getTenDonVi());
//        }
//        columnDVT.setCellEditor(new DefaultCellEditor(cbDonViTinh));




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
                if (value instanceof Number) {
                    lbl.setText(currencyVN.format(((Number) value).doubleValue()));
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
                if (value instanceof Number) {
                    lbl.setText(((Number) value).doubleValue() + " %");
                }
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        };
        colGiamGia.setCellRenderer(percentRenderer);
        
        // Cột STT căn trái
        TableColumn colSTT = tblCTHD.getColumnModel().getColumn(0);
        DefaultTableCellRenderer leftAlignRenderer = new DefaultTableCellRenderer();
        leftAlignRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        colSTT.setCellRenderer(leftAlignRenderer);
        
        capNhatTongTien(model);
    }
    
    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        int selectedRow = tblCTHD.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblCTHD.getModel();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
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
    }//GEN-LAST:event_btnXoaMouseClicked

    private void txtSdtKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSdtKHActionPerformed
        String sdt = txtSdtKH.getText().trim();
        KhachHang kh = null;
        try {
            kh = KhachHangDAO.getKhachHangTheoSdt(sdt);
        } catch (SQLException sQLException) {
        }
        String maKH = kh.getMaKH();
        String hoTen = kh.getHoTenDem() + " " + kh.getTen();
        int diemTichLuy = kh.getDiemTichLuy();
        lblMaKH1.setText(maKH);
        lblHoTen1.setText(hoTen);
        lblDiemTichLuy1.setText(String.valueOf(diemTichLuy));
        System.out.println(kh);
    }//GEN-LAST:event_txtSdtKHActionPerformed

    private void txtTienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaActionPerformed
        try {
            double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText());
            double tongTien = getTongTien();
            if(tienKhachDua < tongTien) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn hoặc bằng tổng tiền");
                return;
            }
            double tienThua = tienKhachDua - tongTien;
            NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String tienThuaStr = vndFormat.format(tienThua);
            lblTienThua1.setText(tienThuaStr);
            System.out.println(tongTien);
        }catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số dương");
        }
    }//GEN-LAST:event_txtTienKhachDuaActionPerformed
 
    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        TaiKhoan tk = GiaoDienChinhGUI.getTk();
        if(tk == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhấn vào ca làm trước khi thanh toán");
            return;
        }
        if(tblCTHD.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm cần thanh toán");
            return;
        }
        
        HoaDon hdMoiNhat = HoaDonDAO.getHoaDonMoiNhat();
        LocalDateTime now = LocalDateTime.now();
        String maHDMoi;
        
        if (hdMoiNhat == null) {
            maHDMoi = taoMaHoaDonMoi(now.toLocalDate(), 1);
        } else if (now.toLocalDate().isAfter(hdMoiNhat.getNgayLapHoaDon().toLocalDate())) {
            maHDMoi = taoMaHoaDonMoi(now.toLocalDate(), 1);
        } else {
            String maHDTruoc = hdMoiNhat.getMaHoaDon();
            int soCuoiHD = laySoThuTu(maHDTruoc);
            maHDMoi = taoMaHoaDonMoi(now.toLocalDate(), soCuoiHD + 1);
        }
        
        // Tạo hóa đơn
        String maNV = GiaoDienChinhGUI.getTk().getNhanVien().getMaNV();
        LocalDateTime ngayLapHD = LocalDateTime.now();
        String maKH = lblMaKH1.getText().trim();
        boolean chuyenKhoan = radChuyenKhoan.isSelected();
        boolean trangThai = true;
        double tongTien = getTongTien();
        HoaDon hd = new HoaDon(maHDMoi, new NhanVien(maNV), ngayLapHD, new KhachHang(maKH), chuyenKhoan, trangThai, tongTien);
        
        System.out.println(hd);
        if(false == HoaDonDAO.insertHoaDon(hd)) {
            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thất bại");
            return;
        }      
        
        // Tạo danh sách chi tiết hóa đơn
        ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel)tblCTHD.getModel();
        for(int i = 0; i < model.getRowCount(); i++) {
           String maDVT = String.valueOf(model.getValueAt(i, 7));
           int soLuong = Integer.parseInt(model.getValueAt(i, 4).toString());
           double donGia = Double.parseDouble(model.getValueAt(i, 3).toString());
           double giamGia = Double.parseDouble(model.getValueAt(i, 5).toString());
           double thanhTien = Double.parseDouble(model.getValueAt(i, 6).toString());

           String maSP = (String) model.getValueAt(i, 8);
           
           ChiTietHoaDon cthdMoiNhat = ChiTietHoaDonDAO.getChiTietHoaDonMoiNhat();
           String maCTHDMoi;
           if (cthdMoiNhat == null) {
               maCTHDMoi = taoMaChiTietHoaDonMoi(now.toLocalDate(), 1);
           } else if (now.toLocalDate().isAfter(HoaDonDAO.getHoaDonTheoMaHD(cthdMoiNhat.getHoaDon().getMaHoaDon()).getNgayLapHoaDon().toLocalDate())) {
               maCTHDMoi = taoMaChiTietHoaDonMoi(now.toLocalDate(), 1);
           } else {
               String maCTHDTruoc = cthdMoiNhat.getMaChiTietHoaDon();
               int soCuoi = laySoThuTu(maCTHDTruoc);
               maCTHDMoi = taoMaChiTietHoaDonMoi(now.toLocalDate(), soCuoi + 1);
           }
           
           ChiTietHoaDon cthd = new ChiTietHoaDon(maCTHDMoi, new HoaDon(maHDMoi), new DonViTinh(maDVT), soLuong, donGia, giamGia, thanhTien);           
           dsCTHD.add(cthd);
           if(false == ChiTietHoaDonDAO.insertChiTietHoaDon(cthd)) {
               JOptionPane.showMessageDialog(this, "Tạo chi tiết hóa đơn thất bại");
               return;
           } 
           
           // Trừ tồn kho và tạo chi tiết xuất lô
           int soLuongXuat = soLuong;
           ArrayList<LoSanPham> dsLSP = LoSanPhamDAO.getLoSanPhamTheoMaSP(maSP);
           for(LoSanPham lsp : dsLSP) {
               int soLuongTon = lsp.getSoLuong();
                if (soLuongXuat <= 0)
                    break;

                if (soLuongTon >= soLuongXuat) {
                    LoSanPhamDAO.truSoLuong(lsp.getMaLoSanPham(), soLuongXuat);
                    
                    ChiTietXuatLo ctxl = new ChiTietXuatLo(new LoSanPham(lsp.getMaLoSanPham()), new ChiTietHoaDon(maCTHDMoi), soLuongXuat);
                    ChiTietXuatLoDAO.insertChiTietXuatLo(ctxl);

                    soLuongXuat = 0;
                } else {
                    LoSanPhamDAO.truSoLuong(lsp.getMaLoSanPham(), soLuongTon);
                    ChiTietXuatLo ctxl = new ChiTietXuatLo(new LoSanPham(lsp.getMaLoSanPham()), new ChiTietHoaDon(maCTHDMoi), soLuongXuat);
                    ChiTietXuatLoDAO.insertChiTietXuatLo(ctxl);
                    soLuongXuat -= soLuongTon;
                }
           }
           
           if (soLuongXuat > 0) {
                JOptionPane.showMessageDialog(this, "Không đủ số lượng");
                return;
            }
        }
        
        // Cập nhật điểm tích lũy cho khách hàng mua lớn hơn bằng 100 ngàn 
        if(tongTien >= 100000 && !"KH-99999".equals(maKH)) {
            int diemTichLuy = (int) Math.floor(tongTien / 100000);
            KhachHangDAO.updateDiemTichLuy(diemTichLuy, maKH);
        }
        
        xoaTrang();
        String noiDung = taoNoiDungHoaDon(hd, dsCTHD);

        // Hiển thị trong một hộp thoại lớn:
        JTextArea textArea = new JTextArea(noiDung);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(600, 500));

        JOptionPane.showMessageDialog(null, scroll, "Hóa đơn " + hd.getMaHoaDon(), JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // Thêm sản phẩm vào table cthd
        themSanPhamVaoTable();
        // Xóa nội dung trong ô text để chuẩn bị cho lần quét tiếp theo
        txtTimKiem.setText("");       
        // Tự động đặt con trỏ chuột trở lại ô này
        txtTimKiem.requestFocusInWindow();
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        if(txtTimKiem.getText().equals("Nhập mã sản phẩm")) {
            txtTimKiem.setText("");
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        if(txtTimKiem.getText().equals("")) {
            txtTimKiem.setText("Nhập mã sản phẩm");
        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void tblCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTHDMouseClicked
//        DefaultTableModel model = (DefaultTableModel)tblCTHD.getModel();
//        int row = tblCTHD.rowAtPoint(evt.getPoint());
//        int col = tblCTHD.columnAtPoint(evt.getPoint());
//        String maSP = model.getValueAt(row, 8).toString();
//        ArrayList<DonViTinh> dsDVT = DonViTinhDAO.getDonViTinhTheoMaSP(maSP);
//
//        if (tblCTHD.isEditing()) {
//            tblCTHD.getCellEditor().stopCellEditing();
//        }
//        JComboBox<String> cbDonViTinh = new JComboBox<>();
//        for (DonViTinh dvt : dsDVT) {
//            cbDonViTinh.addItem(dvt.getTenDonVi());
//        }
//
//        TableColumn columnDVT = tblCTHD.getColumnModel().getColumn(2);
//        columnDVT.setCellEditor(new DefaultCellEditor(cbDonViTinh));
//        tblCTHD.editCellAt(row, col);
//        Component editor = tblCTHD.getEditorComponent();
//        if (editor != null) {
//            editor.requestFocus();
//        }
    }//GEN-LAST:event_tblCTHDMouseClicked

    private void txtSdtKHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSdtKHFocusGained
        if(txtSdtKH.getText().equals("Nhập sđt khách hàng")) {
            txtSdtKH.setText("");
        }
    }//GEN-LAST:event_txtSdtKHFocusGained

    private void txtSdtKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSdtKHFocusLost
        if(txtSdtKH.getText().equals("")) {
            txtSdtKH.setText("Nhập sđt khách hàng");
        }
    }//GEN-LAST:event_txtSdtKHFocusLost
    
    private String taoMaHoaDonMoi(LocalDate ngay, int soThuTu) {
        return String.format("HD-%s-%04d", 
            ngay.format(java.time.format.DateTimeFormatter.ofPattern("yyMMdd")),
            soThuTu);
    }

    private String taoMaChiTietHoaDonMoi(LocalDate ngay, int soThuTu) {
        return String.format("CTHD-%s-%04d", 
            ngay.format(java.time.format.DateTimeFormatter.ofPattern("yyMMdd")),
            soThuTu);
    }
    
    private int laySoThuTu(String ma) {
        try {
            String[] parts = ma.split("-");
            return Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private void xoaTrang() {
        DefaultTableModel model = (DefaultTableModel)tblCTHD.getModel();
        model.setRowCount(0);

        lblTongTien1.setText("");
        txtTienKhachDua.setText("");
        lblTienThua1.setText("");
        radTienMat.setSelected(true);
        txtSdtKH.setText("Nhập sđt khách hàng");
        lblMaKH1.setText("KH-99999");
        lblHoTen1.setText("Khách vãng lai");
        lblDiemTichLuy1.setText("0");
        txtTimKiem.setText("Nhập mã sản phẩm");
    }

    public static String taoNoiDungHoaDon(HoaDon hd, ArrayList<ChiTietHoaDon> dsCTHD) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        sb.append("====================================================================\n");
        sb.append("                         HÓA ĐƠN BÁN HÀNG\n");
        sb.append("====================================================================\n");
        sb.append(String.format("Mã hóa đơn : %s\n", hd.getMaHoaDon()));
        sb.append(String.format("Ngày lập   : %s\n", hd.getNgayLapHoaDon().format(fmt)));
        sb.append(String.format("Nhân viên  : %s\n", hd.getNhanVien().getMaNV()));
        sb.append(String.format("Khách hàng : %s\n", hd.getKhachHang().getMaKH()));
        sb.append(String.format("Hình thức  : %s\n", hd.isChuyenKhoan() ? "Chuyển khoản" : "Tiền mặt"));
        sb.append("--------------------------------------------------------------------\n");
        sb.append(String.format("%-4s %-40s %-6s %-8s %-12s %-10s %-12s\n",
                "STT", "Sản phẩm", "SL", "ĐVT", "Đơn giá", "Giảm giá", "Thành tiền"));
        sb.append("--------------------------------------------------------------------\n");

        int stt = 1;
        double tongTien = 0;
        for (ChiTietHoaDon cthd : dsCTHD) {
            String maSP = DonViTinhDAO.getMaSanPhamTheoMaDVT(cthd.getDonViTinh().getMaDonViTinh());
            String tenSP = SanPhamDAO.timSPTheoMa(maSP).getTen();
            String tenDVT = DonViTinhDAO.getDonViTinhTheoMaDVT(cthd.getDonViTinh().getMaDonViTinh()).getTenDonVi();

            double thanhTien = cthd.getThanhTien();
            tongTien += thanhTien;

            sb.append(String.format("%-4d %-40s %-6d %-8s %-12.0f %-10.0f%% %-12.0f\n",
                    stt++, tenSP, cthd.getSoLuong(), tenDVT,
                    cthd.getDonGia(), cthd.getGiamGia() * 100, thanhTien));
        }

        sb.append("--------------------------------------------------------------------\n");
        sb.append(String.format("%62s: %.0f VND\n", "TỔNG CỘNG", tongTien));
        sb.append("====================================================================\n");
        sb.append("         CẢM ƠN QUÝ KHÁCH, HẸN GẶP LẠI!\n");
        sb.append("====================================================================\n");

        return sb.toString();
    }


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGoiY1;
    private javax.swing.JButton btnGoiY2;
    private javax.swing.JButton btnGoiY3;
    private javax.swing.JButton btnGoiY4;
    private javax.swing.JButton btnGoiY5;
    private javax.swing.JButton btnGoiY6;
    private javax.swing.JButton btnThanhToan;
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
