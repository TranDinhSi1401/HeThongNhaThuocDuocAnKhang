/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.bus.VaoRaCaBUS;
import hethongnhathuocduocankhang.dao.HoaDonDAO;
import hethongnhathuocduocankhang.dao.LichSuCaLamDAO;
import hethongnhathuocduocankhang.dao.NhanVienDAO;
import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.entity.CaLam;
import hethongnhathuocduocankhang.entity.HoaDon;
import hethongnhathuocduocankhang.entity.LichSuCaLam;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.SanPham;
import java.awt.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DashBoardNhanVien extends javax.swing.JPanel {

    // Components cho phần Ca Làm
    private JLabel lblDongHo;
    private JLabel lblTenCa;
    private JLabel lblGioVaoCa;
    private JLabel lblNhanVienTruc;
    private JLabel lblThuNgay;
    private Timer timer;

    // Components cho phần Bảng (Tables)
    private DefaultTableModel modelHoaDon;
    private JTable tableHoaDon;
    private DefaultTableModel modelLichSu;
    private JTable tableLichSu;
    private DefaultTableModel modelHetHang;
    private JTable tableHetHang;

    // Layout Panels
    private JPanel pnlCenterContent;
    private JPanel pnlStats;
    private JPanel pnlTableHd_LsCalam;

    // --- CONSTRUCTOR ---
    public DashBoardNhanVien() {
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(255, 255, 255));

        try {
            NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(GiaoDienChinhGUI.getTk().getTenDangNhap().trim());
            renderThongTinNhanVien(nv);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Không có thông tin nhân viên");
        }

        setupThongTinCaLam();
        startRealTimeClock();

        this.removeAll();
        this.setLayout(new BorderLayout(0, 10));
        this.add(jPanel1, BorderLayout.NORTH);

        initMainContent();
        this.add(pnlCenterContent, BorderLayout.CENTER);

        configureBtnVaoCa();
    }

    private void startRealTimeClock() {
        DateTimeFormatter dinhDangNgay = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", new java.util.Locale("vi", "VN"));
        DateTimeFormatter dinhDangThoiGian = DateTimeFormatter.ofPattern("HH:mm:ss");

        timer = new javax.swing.Timer(1000, e -> {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            lblDongHo.setText(now.format(dinhDangThoiGian));
            lblThuNgay.setText(now.format(dinhDangNgay));

            int hour = now.getHour();
            if (hour >= 6 && hour < 14) {
                lblTenCa.setText("Ca hiện tại: Ca Sáng");
                lblGioVaoCa.setText("Thời gian: 06:00 - 14:00");
            } else if (hour >= 14 && hour < 22) {
                lblTenCa.setText("Ca hiện tại: Ca Tối");
                lblGioVaoCa.setText("Thời gian: 14:00 - 22:00");
            } else {
                lblTenCa.setText("Ca hiện tại: Ngoài giờ");
                lblGioVaoCa.setText("Thời gian: --:--");
            }
        });
        timer.start();
    }

    private void configureBtnVaoCa() {
        btnVaoCa.setOpaque(true);
        VaoRaCaBUS lsBUS = new VaoRaCaBUS();
        String maNV = GiaoDienChinhGUI.getTk().getTenDangNhap().trim();

        if (lsBUS.kiemTraDangLamViec(maNV)) {
            btnVaoCa.setText("Ra Ca");
            btnVaoCa.setBackground(Color.RED);
        } else {
            btnVaoCa.setText("Vào Ca");
            btnVaoCa.setBackground(Color.GREEN);
            btnVaoCa.setForeground(Color.WHITE);
        }

        for (java.awt.event.ActionListener al : btnVaoCa.getActionListeners()) {
            btnVaoCa.removeActionListener(al);
        }

        btnVaoCa.addActionListener(e -> {
            try {
                CaLam caLam = lsBUS.getCaLamHienTai();
                if (caLam == null) {
                    JOptionPane.showMessageDialog(this, "Không xác định được Ca Làm hiện tại (hoặc lỗi kết nối)!");
                    return;
                }

                if (btnVaoCa.getText().equals("Vào Ca")) {
                    // --- XỬ LÝ VÀO CA ---
                    int confirmVaoCa = JOptionPane.showConfirmDialog(this,
                            "Bạn có chắc chắn muốn BẮT ĐẦU ca làm việc (" + caLam.getTenCa() + ") không?",
                            "Xác nhận vào ca",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (confirmVaoCa != JOptionPane.YES_OPTION) {
                        return;
                    }

                    if (lsBUS.xuLyVaoCa(maNV, caLam)) {
                        JOptionPane.showMessageDialog(this, "Vào ca thành công lúc " + java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                        btnVaoCa.setText("Ra Ca");
                        btnVaoCa.setBackground(Color.RED);
                        initTableHd_LsCalam();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi: Không thể vào ca (Có thể bạn đã chấm công rồi).");
                    }

                } else {
                    // --- XỬ LÝ RA CA ---
                    JPanel pnlGhiChu = new JPanel(new BorderLayout(5, 5));
                    pnlGhiChu.setPreferredSize(new Dimension(400, 150));
                    JLabel lblLoiNhan = new JLabel("Nhập ghi chú ra ca (nếu có):");
                    lblLoiNhan.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    JTextArea txtGhiChu = new JTextArea(5, 20);
                    txtGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    txtGhiChu.setLineWrap(true);
                    txtGhiChu.setWrapStyleWord(true);
                    JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);
                    pnlGhiChu.add(lblLoiNhan, BorderLayout.NORTH);
                    pnlGhiChu.add(scrollGhiChu, BorderLayout.CENTER);

                    int inputResult = JOptionPane.showConfirmDialog(this, pnlGhiChu, "Ghi chú Ra Ca", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (inputResult != JOptionPane.OK_OPTION) {
                        return;
                    }

                    String ghiChu = txtGhiChu.getText().trim();
                    int confirmRaCa = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn kết thúc ca làm việc?", "Xác nhận ra ca", JOptionPane.YES_NO_OPTION);

                    if (confirmRaCa == JOptionPane.YES_OPTION) {
                        if (lsBUS.xuLyRaCa(maNV, caLam, ghiChu)) {
                            JOptionPane.showMessageDialog(this, "Ra ca thành công!");
                            btnVaoCa.setText("Vào Ca");
                            btnVaoCa.setBackground(Color.GREEN);
                            initTableHd_LsCalam();
                        } else {
                            JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy phiên làm việc để ra ca.");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!");
            }
        });
    }

    public void renderThongTinNhanVien(NhanVien nv) {
        if (nv == null) {
            return;
        }

        lblMaNV.setText(nv.getMaNV());
        lblHoTen.setText(nv.getHoTenDem() + " " + nv.getTen());
        lblGioiTinh.setText(nv.isGioiTinh() ? "Nam" : "Nữ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (nv.getNgaySinh() != null) {
            lblNgaySinh.setText(nv.getNgaySinh().format(formatter));
        }

        lblChucVu.setText(GiaoDienChinhGUI.getTk().isQuanLyLo() ? "Quản lý lô" : "Nhân viên");
        lblTrangThai.setText(nv.isNghiViec() ? "Nghỉ việc" : "Đang làm việc");
        lblSdt.setText(nv.getSdt());
    }

    private void initMainContent() {
        pnlCenterContent = new JPanel();
        pnlCenterContent.setLayout(new BoxLayout(pnlCenterContent, BoxLayout.Y_AXIS));
        pnlCenterContent.setBackground(Color.WHITE);

        initDThuHnay_HdDaBan_SpHetHang();
        pnlCenterContent.add(pnlStats);

        pnlCenterContent.add(Box.createVerticalStrut(10));

        initTableHd_LsCalam();
        pnlCenterContent.add(pnlTableHd_LsCalam);
    }

    private void setupThongTinCaLam() {
        pnlThongTinCaLam.removeAll();
        pnlThongTinCaLam.setLayout(new BorderLayout());
        pnlThongTinCaLam.setBackground(Color.WHITE);

        // Panel Đồng hồ
        JPanel pnlDongHo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlDongHo.setBackground(Color.WHITE);
        lblDongHo = new JLabel("00:00:00");
        lblDongHo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblDongHo.setForeground(new Color(0, 153, 51));
        pnlDongHo.add(lblDongHo);

        // Panel Thông tin Ca
        JPanel pnlThongTinCa = new JPanel();
        pnlThongTinCa.setLayout(new BoxLayout(pnlThongTinCa, BoxLayout.Y_AXIS));
        pnlThongTinCa.setBackground(Color.WHITE);
        pnlThongTinCa.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 0));

        lblThuNgay = new JLabel("Ngày: ...");
        styleLabelCaLam(lblThuNgay);
        lblThuNgay.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblThuNgay.setForeground(Color.BLUE);

        lblTenCa = new JLabel("Ca: ...");
        lblGioVaoCa = new JLabel("Bắt đầu: ...");
        lblNhanVienTruc = new JLabel("NV: " + lblHoTen.getText());

        styleLabelCaLam(lblTenCa);
        styleLabelCaLam(lblGioVaoCa);
        styleLabelCaLam(lblNhanVienTruc);
        lblNhanVienTruc.setForeground(new Color(204, 0, 0));
        lblNhanVienTruc.setFont(new Font("Segoe UI", Font.BOLD, 15));

        pnlThongTinCa.add(lblThuNgay);
        pnlThongTinCa.add(Box.createVerticalStrut(5));
        pnlThongTinCa.add(lblTenCa);
        pnlThongTinCa.add(Box.createVerticalStrut(5));
        pnlThongTinCa.add(lblGioVaoCa);
        pnlThongTinCa.add(Box.createVerticalStrut(5));
        pnlThongTinCa.add(lblNhanVienTruc);

        pnlThongTinCaLam.add(pnlDongHo, BorderLayout.NORTH);
        pnlThongTinCaLam.add(pnlThongTinCa, BorderLayout.CENTER);

        pnlThongTinCaLam.revalidate();
        pnlThongTinCaLam.repaint();
    }

    private void initDThuHnay_HdDaBan_SpHetHang() {
        pnlStats = new JPanel(new GridBagLayout());
        pnlStats.setBackground(Color.WHITE);
        pnlStats.setPreferredSize(new Dimension(0, 160));
        pnlStats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 15);
        gbc.weighty = 1.0;

        // Tính toán số liệu Doanh thu & Hóa đơn
        LocalDate homNay = LocalDate.now();
        LocalDate homQua = homNay.minusDays(1);
        double dtHomNay = 0.0, dtHomQua = 0.0;
        int hdHomNay = 0, hdHomQua = 0;

        ArrayList<HoaDon> dsHDHomNaycuaNV = HoaDonDAO.timHDTheoNgayLap(homNay);
        ArrayList<HoaDon> dsHDHomQuacuaNV = HoaDonDAO.timHDTheoNgayLap(homQua);
        String currentNV = GiaoDienChinhGUI.getTk().getTenDangNhap().trim();

        NhanVien nvDangLam = new NhanVien();
        
        try {
            nvDangLam = NhanVienDAO.getNhanVienTheoMaNV(currentNV);
        } catch (Exception e) {
            
        }
        
        for (HoaDon hd : dsHDHomNaycuaNV) {
            try {
                if (hd.getNhanVien().equals(nvDangLam)) {
                    dtHomNay += hd.getTongTien();
                    hdHomNay++;
                }
            } catch (Exception e) {
            }
        }
        for (HoaDon hd : dsHDHomQuacuaNV) {
            try {
                if (hd.getNhanVien().equals(nvDangLam)) {
                    dtHomQua += hd.getTongTien();
                    hdHomQua++;
                }
            } catch (Exception e) {
            }
        }

        // Card 1: Doanh thu
        double phanTramDT = (dtHomQua > 0) ? ((dtHomNay - dtHomQua) / dtHomQua) * 100 : 0;
        String strDoanhThu = NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN")).format(dtHomNay);
        String subTextDT = String.format("%s %.1f%% so với hôm qua", (phanTramDT > 0 ? "↑" : "↓"), Math.abs(phanTramDT));

        gbc.gridx = 0;
        gbc.weightx = 0.25;
        pnlStats.add(createStatCard("Doanh thu hôm nay", strDoanhThu, subTextDT, phanTramDT >= 0), gbc);

        // Card 2: Hóa đơn
        double phanTramHD = (hdHomQua > 0) ? ((double) (hdHomNay - hdHomQua) / hdHomQua) * 100 : 0;
        String subTextHD = String.format("%s %.1f%% so với hôm qua", (phanTramHD > 0 ? "↑" : "↓"), Math.abs(phanTramHD));

        gbc.gridx = 1;
        gbc.weightx = 0.25;
        pnlStats.add(createStatCard("Hóa đơn đã bán", String.valueOf(hdHomNay), subTextHD, phanTramHD >= 0), gbc);

        // Card 3: Hết hàng
        gbc.gridx = 2;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 0, 0, 0);
        pnlStats.add(createTableSPDaHetHang(), gbc);
    }

    private JPanel createStatCard(String title, String value, String subText, boolean isPositive) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(new Color(102, 102, 102));
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblValue.setForeground(Color.BLACK);

        JLabel lblSub = new JLabel(subText);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(isPositive ? new Color(0, 153, 51) : Color.GRAY);

        card.add(pnlHeader, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        card.add(lblSub, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createTableSPDaHetHang() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(5, 5, 5, 5)
        ));

        JLabel lblTitle = new JLabel("Sản phẩm đã hết hàng");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(Color.RED);
        lblTitle.setBorder(new EmptyBorder(0, 5, 5, 5));
        pnl.add(lblTitle, BorderLayout.NORTH);

        String[] cols = {"STT", "Mã SP", "Tên sản phẩm"};
        modelHetHang = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<SanPham> dsSPDaHetHang = SanPhamDAO.getSPDaHetHang();
        int stt = 0;
        for (SanPham sp : dsSPDaHetHang) {
            stt++;
            modelHetHang.addRow(new Object[]{stt + "", sp.getMaSP(), sp.getTen()});
        }

        tableHetHang = new JTable(modelHetHang);
        tableHetHang.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableHetHang.setRowHeight(25);
        tableHetHang.setShowGrid(false);
        tableHetHang.setSelectionBackground(new Color(255, 180, 180));
        tableHetHang.setSelectionForeground(Color.BLACK);

        JTableHeader header = tableHetHang.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(250, 250, 250));

        tableHetHang.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableHetHang.getColumnModel().getColumn(0).setMaxWidth(40);
        tableHetHang.getColumnModel().getColumn(1).setPreferredWidth(80);

        JScrollPane scroll = new JScrollPane(tableHetHang);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        pnl.add(scroll, BorderLayout.CENTER);
        return pnl;
    }

    private void initTableHd_LsCalam() {
        if (pnlTableHd_LsCalam == null) {
            pnlTableHd_LsCalam = new JPanel(new GridLayout(1, 2, 15, 0));
            pnlTableHd_LsCalam.setBackground(Color.WHITE);
        }

        pnlTableHd_LsCalam.removeAll();
        pnlTableHd_LsCalam.add(createTableContainer("Hóa đơn đã bán hôm nay", initTableHoaDon()));
        pnlTableHd_LsCalam.add(createTableContainer("Lịch sử làm việc của tôi", initTableLichSuLamViec()));

        pnlTableHd_LsCalam.revalidate();
        pnlTableHd_LsCalam.repaint();
    }

    private JPanel createTableContainer(String title, JScrollPane scrollPane) {
        JPanel pnlCard = new JPanel(new BorderLayout());
        pnlCard.setBackground(Color.WHITE);
        pnlCard.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JPanel pnlTieuDe = new JPanel(new BorderLayout(10, 10));
        pnlTieuDe.setBackground(new Color(245, 247, 250));
        pnlTieuDe.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTieuDe = new JLabel(title);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);

        pnlCard.add(pnlTieuDe, BorderLayout.NORTH);
        pnlCard.add(scrollPane, BorderLayout.CENTER);
        return pnlCard;
    }

    private JScrollPane initTableHoaDon() {
        String[] columns = {"Mã hóa đơn", "Khách hàng", "PTTT", "Tổng tiền"};
        modelHoaDon = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        LocalDate homNay = LocalDate.now();
        ArrayList<HoaDon> dsHDHomNaycuaNV = HoaDonDAO.timHDTheoNgayLap(homNay);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        try {
            NhanVien nvHienTai = NhanVienDAO.getNhanVienTheoMaNV(GiaoDienChinhGUI.getTk().getTenDangNhap().trim());
            if (nvHienTai != null) {
                for (HoaDon hd : dsHDHomNaycuaNV) {
                    if (hd.getNhanVien().getMaNV().equals(nvHienTai.getMaNV())) {
                        String pttt = hd.isChuyenKhoan() ? "Chuyển khoản" : "Tiền mặt";
                        String tongTien = currencyFormat.format(hd.getTongTien());
                        String tenKhachHang = (hd.getKhachHang() != null) ? hd.getKhachHang().getHoTenDem() + " " + hd.getKhachHang().getTen() : "Khách lẻ";

                        modelHoaDon.addRow(new Object[]{hd.getMaHoaDon(), tenKhachHang, pttt, tongTien});
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tableHoaDon = new JTable(modelHoaDon);
        applyTableStyle(tableHoaDon);

        tableHoaDon.getColumnModel().getColumn(0).setCellRenderer(getLeftRenderer());
        tableHoaDon.getColumnModel().getColumn(1).setCellRenderer(getLeftRenderer());
        tableHoaDon.getColumnModel().getColumn(2).setCellRenderer(getCenterRenderer());
        tableHoaDon.getColumnModel().getColumn(3).setCellRenderer(getRightRenderer());

        tableHoaDon.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableHoaDon.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableHoaDon.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scroll = new JScrollPane(tableHoaDon);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    private JScrollPane initTableLichSuLamViec() {
        String[] columns = {"Ngày làm", "Ca làm", "Giờ vào", "Giờ ra", "Ghi chú"};
        modelLichSu = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String maNV = GiaoDienChinhGUI.getTk().getTenDangNhap().trim();
        ArrayList<LichSuCaLam> listLsLamViecOfNV = LichSuCaLamDAO.timTheoMaNV(maNV);

        DateTimeFormatter dinhDangNgay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dinhDangThGian = DateTimeFormatter.ofPattern("HH:mm:ss");

        if (listLsLamViecOfNV != null) {
            for (int i = listLsLamViecOfNV.size() - 1; i >= 0; i--) {
                LichSuCaLam ls = listLsLamViecOfNV.get(i);
                String ngayLam = ls.getNgayLamViec().format(dinhDangNgay);
                String tenCa = (ls.getCaLam() != null) ? ls.getCaLam().getTenCa() : "Không xác định";
                String gioVao = (ls.getThoiGianVaoCa() != null) ? ls.getThoiGianVaoCa().format(dinhDangThGian) : "";
                String gioRa = (ls.getThoiGianRaCa() == null) ? "Đang trong ca" : ls.getThoiGianRaCa().format(dinhDangThGian);
                String ghiChu = (ls.getGhiChu() != null) ? ls.getGhiChu() : "";
                modelLichSu.addRow(new Object[]{ngayLam, tenCa, gioVao, gioRa, ghiChu});
            }
        }

        tableLichSu = new JTable(modelLichSu);
        applyTableStyle(tableLichSu);

        tableLichSu.getColumnModel().getColumn(0).setCellRenderer(getCenterRenderer());
        tableLichSu.getColumnModel().getColumn(1).setCellRenderer(getCenterRenderer());
        tableLichSu.getColumnModel().getColumn(2).setCellRenderer(getCenterRenderer());
        tableLichSu.getColumnModel().getColumn(3).setCellRenderer(getCenterRenderer());
        tableLichSu.getColumnModel().getColumn(4).setCellRenderer(getLeftRenderer());

        JScrollPane scroll = new JScrollPane(tableLichSu);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    private void styleLabelCaLam(JLabel lbl) {
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lbl.setForeground(new Color(51, 51, 51));
    }

    private void applyTableStyle(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(160, 200, 230));
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setForeground(new Color(102, 102, 102));
        header.setBackground(new Color(245, 247, 250));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        header.setOpaque(false);
        table.setAutoCreateRowSorter(true);
    }

    private void initInfoRow(JPanel pnl, JLabel lblTitle, JLabel lblVal, String title, String val) {
        //pnl.setBackground(new java.awt.Color(245, 245, 245));
        pnl.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        lblTitle.setText(title);
        lblVal.setFont(new java.awt.Font("Segoe UI", 0, 16));
        lblVal.setText(val);
        lblVal.setForeground(new Color(51, 51, 51));
        pnl.add(lblTitle);
        pnl.add(lblVal);
        pnl.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
    }

    private DefaultTableCellRenderer getCenterRenderer() {
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        return center;
    }

    private DefaultTableCellRenderer getRightRenderer() {
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(JLabel.RIGHT);
        right.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        return right;
    }

    private DefaultTableCellRenderer getLeftRenderer() {
        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(JLabel.LEFT);
        left.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        return left;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnVaoCa = new javax.swing.JButton();
        pnlThongTinCaLam = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblHoTen = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblChucVu = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblSdt = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(808, 220));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(360, 220));
        jPanel2.setLayout(new java.awt.BorderLayout(10, 10));

        btnVaoCa.setFont(new java.awt.Font("Segoe UI", 1, 16));
        btnVaoCa.setText("Vào Ca");
        btnVaoCa.setPreferredSize(new java.awt.Dimension(72, 60));
        jPanel2.add(btnVaoCa, java.awt.BorderLayout.PAGE_START);

        pnlThongTinCaLam.setLayout(new java.awt.BorderLayout());
        jPanel22.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel11.setText("Thông tin ca làm");
        jPanel22.add(jLabel11);
        pnlThongTinCaLam.add(jPanel22, java.awt.BorderLayout.PAGE_START);
        jPanel2.add(pnlThongTinCaLam, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setLayout(new java.awt.BorderLayout());

        //jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setPreferredSize(new java.awt.Dimension(220, 220));
        jPanel4.setLayout(new java.awt.GridBagLayout());
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/profile1.png")));
        jPanel4.add(jLabel1, new java.awt.GridBagConstraints());
        jPanel3.add(jPanel4, java.awt.BorderLayout.LINE_START);

        //jPanel7.setBackground(new java.awt.Color(245, 245, 245));
        jPanel7.setPreferredSize(new java.awt.Dimension(200, 220));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        initInfoRow(jPanel8, jLabel2, lblMaNV = new JLabel(), "Mã nhân viên:", "NV-XXXXX");
        jPanel7.add(jPanel8);
        initInfoRow(jPanel9, jLabel3, lblHoTen = new JLabel(), "Họ tên:", "Nguyễn Văn A");
        jPanel7.add(jPanel9);
        initInfoRow(jPanel11, jLabel5, lblNgaySinh = new JLabel(), "Ngày sinh:", "XX/XX/XXXX");
        jPanel7.add(jPanel11);
        initInfoRow(jPanel12, jLabel6, lblChucVu = new JLabel(), "Chức vụ:", "Nhân viên");
        jPanel7.add(jPanel12);
        jPanel3.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel15.setPreferredSize(new java.awt.Dimension(240, 0));
        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.Y_AXIS));
        initInfoRow(jPanel10, jLabel4, lblGioiTinh = new JLabel(), "Giới Tính:", "Nam/Nữ");
        jPanel15.add(jPanel10);
        initInfoRow(jPanel13, jLabel7, lblTrangThai = new JLabel(), "Trạng Thái:", "Đang làm việc");
        jPanel15.add(jPanel13);
        initInfoRow(jPanel14, jLabel8, lblSdt = new JLabel(), "SĐT:", "090xxxxxxx");
        jPanel15.add(jPanel14);

        //jPanel16.setBackground(new java.awt.Color(245, 245, 245));
        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        jLabel9.setText("   ");
        jPanel16.add(jLabel9);
        jPanel15.add(jPanel16);
        jPanel3.add(jPanel15, java.awt.BorderLayout.LINE_END);

        //jPanel18.setBackground(new java.awt.Color(245, 245, 245));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel10.setText("Thông tin nhân viên");
        jPanel18.add(jLabel10);
        jPanel3.add(jPanel18, java.awt.BorderLayout.PAGE_START);
        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);
        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>                        

    // Variables declaration - do not modify                    
    private javax.swing.JButton btnVaoCa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel pnlThongTinCaLam;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblSdt;
    private javax.swing.JLabel lblTrangThai;
    // End of variables declaration                   
}