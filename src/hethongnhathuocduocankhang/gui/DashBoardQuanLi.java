/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.HoaDonDAO;
import hethongnhathuocduocankhang.dao.NhanVienDAO;
import hethongnhathuocduocankhang.entity.NhanVien;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.layout.FormatLayout;

/**
 *
 * @author trand
 */
public class DashBoardQuanLi extends javax.swing.JPanel {

    private final JPanel pnlCenter;
    private final JRadioButton radThang;
    private final JRadioButton radNam;
    private final JComboBox<String> cmbThang;
    private final JComboBox<String> cmbNam;
    private final JPanel pnlNutTaiLai;

    /**
     * Creates new form TongQuanGUI
     */
    public DashBoardQuanLi() {
        initComponents();
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(GiaoDienChinhGUI.getTk().getTenDangNhap().trim());
            renderThongTinNhanVien(nv);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Không có thông tin nhân viên");
        }

        //pnlBieuDo.setBorder(BorderFactory.createTitledBorder("Biểu đồ Doanh thu tháng MM"));
        pnlBieuDo.setLayout(new BorderLayout());

        pnlCenter = new JPanel();
        pnlCenter.setLayout(new BorderLayout());
        pnlBieuDo.add(pnlCenter, BorderLayout.CENTER);

        // Khởi tạo nút bấm
        JButton btnTaiBieuDo = new JButton("Tải Biểu Đồ");
        radThang = new JRadioButton("Tháng", true);
        radNam = new JRadioButton("Năm");
        ButtonGroup tieuChiGroup = new ButtonGroup();
        tieuChiGroup.add(radThang);
        tieuChiGroup.add(radNam);

        String[] cacThang = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 19", "Tháng 10", "Tháng 11", "Tháng 12"};

        int startYear = 2025;
        int endYear = 2045;
        int arrayLength = endYear - startYear + 1;
        String[] cacNam = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            int currentYear = startYear + i;
            cacNam[i] = String.valueOf(currentYear);
        }

        cmbThang = new JComboBox<>(cacThang);
        cmbThang.setSelectedItem("Tháng " + LocalDate.now().getMonthValue());
        cmbNam = new JComboBox<>(cacNam);
        cmbNam.setSelectedItem(LocalDate.now().getYear());

        pnlNutTaiLai = new JPanel();
        pnlNutTaiLai.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnlNutTaiLai.add(new JLabel("Thống kê theo: "));
        pnlNutTaiLai.add(radThang);
        pnlNutTaiLai.add(radNam);
        pnlNutTaiLai.add(new Label(""));
        pnlNutTaiLai.add(cmbThang);
        pnlNutTaiLai.add(cmbNam);
        cmbThang.setVisible(true);
        cmbNam.setVisible(true);

        pnlNutTaiLai.add(new Label(""));
        pnlNutTaiLai.add(new Label(""));
        pnlNutTaiLai.add(new Label(""));

        pnlNutTaiLai.add(btnTaiBieuDo);

        pnlBieuDo.add(pnlNutTaiLai, BorderLayout.NORTH);

        btnVaoCa.setOpaque(true);
        btnVaoCa.setBackground(Color.GREEN);
        btnVaoCa.setForeground(Color.WHITE);

        // Thêm sự kiện cho nút
        btnTaiBieuDo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radThang.isSelected()) {
                    int thang = Integer.parseInt(cmbThang.getSelectedItem().toString().substring(6)); //với: Tháng 1
                    int nam = Integer.parseInt(cmbNam.getSelectedItem().toString());
                    veBieuDo(thang, nam, "tháng");
                } else {
                    int nam = Integer.parseInt(cmbNam.getSelectedItem().toString());
                    veBieuDo(1, nam, "năm");
                }
            }
        });

        radThang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbThang.setVisible(true);
                cmbNam.setVisible(true);
                // Cập nhật giao diện
                pnlNutTaiLai.revalidate();
                pnlNutTaiLai.repaint();
            }
        });

        radNam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbThang.setVisible(false);
                cmbNam.setVisible(true);
                // Cập nhật giao diện
                pnlNutTaiLai.revalidate();
                pnlNutTaiLai.repaint();
            }
        });

        btnVaoCa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnVaoCa.getText().equals("Vào Ca")) {

                    btnVaoCa.setText("Ra Ca");
                    //Cho phép nút hiển thị màu nền
                    btnVaoCa.setOpaque(true);
                    btnVaoCa.setBackground(Color.RED);
                    btnVaoCa.setForeground(Color.WHITE);
                } else {

                    btnVaoCa.setText("Vào Ca");
                    btnVaoCa.setOpaque(true);
                    btnVaoCa.setBackground(Color.GREEN);
                    btnVaoCa.setForeground(Color.WHITE);
                }
            }
        });

        veBieuDo(LocalDate.now().getMonthValue(), LocalDate.now().getYear(), "tháng");
        //renderThongTinCaLam();
    }

    private void veBieuDo(int thang, int nam, String tieuChi) {
        if (tieuChi.equals("tháng")) {
            LocalDate hienTai = LocalDate.of(nam, thang, 1);
            // B1: Lấy dữ liệu từ DAO
            Map<Integer, Double> data = HoaDonDAO.getDoanhThuTungNgayTrongThang(hienTai);

            // B2: Tạo Dataset (Đầu vào cho JFreeChart)
            DefaultCategoryDataset dataset = taoDataset(data);

            // B3: Tạo đối tượng JFreeChart đã tùy chỉnh
            JFreeChart chart = taoBieuDoCoBan(dataset, tieuChi, thang, nam);

            // B4: TẠO CHARTPANEL (Đây là bước "cầu nối")
            // ChartPanel là một component Swing (giống JPanel)
            // dùng để chứa đối tượng JFreeChart
            ChartPanel chartPanel = new ChartPanel(chart);

            // B5: THÊM CHARTPANEL VÀO PNLCENTER
            pnlCenter.removeAll(); // Xóa biểu đồ cũ (nếu có)
            pnlCenter.add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ mới
            pnlCenter.revalidate(); // Yêu cầu vẽ lại layout
            pnlCenter.repaint();    // Yêu cầu vẽ lại đồ họa
        } else {
            LocalDate thoiGian = LocalDate.of(nam, thang, 1);
            // B1: Lấy dữ liệu từ DAO
            Map<Integer, Double> data = HoaDonDAO.getDoanhThuTungThangTrongNam(thoiGian);

            // B2: Tạo Dataset (Đầu vào cho JFreeChart)
            DefaultCategoryDataset dataset = taoDataset(data);

            // B3: Tạo đối tượng JFreeChart đã tùy chỉnh
            JFreeChart chart = taoBieuDoCoBan(dataset, tieuChi, thang, nam);

            // B4: TẠO CHARTPANEL (Đây là bước "cầu nối")
            // ChartPanel là một component Swing (giống JPanel)
            // dùng để chứa đối tượng JFreeChart
            ChartPanel chartPanel = new ChartPanel(chart);

            // B5: THÊM CHARTPANEL VÀO PNLCENTER
            pnlCenter.removeAll(); // Xóa biểu đồ cũ (nếu có)
            pnlCenter.add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ mới
            pnlCenter.revalidate(); // Yêu cầu vẽ lại layout
            pnlCenter.repaint();    // Yêu cầu vẽ lại đồ họa
        }
    }

    private double giaTriKPI = 3000000;

    private DefaultCategoryDataset taoDataset(Map<Integer, Double> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String series = "Doanh thu";
        for (Map.Entry<Integer, Double> entry : data.entrySet()) {
            String category = "" + entry.getKey();
            dataset.addValue(entry.getValue(), series, category);
        }
        return dataset;
    }

    private JFreeChart taoBieuDoCoBan(CategoryDataset dataset, String tieuChi, int thang, int nam) {
        if (tieuChi.equals("tháng")) {
            // Tạo biểu đồ cơ bản
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Doanh thu " + tieuChi + " " + thang + " " + "năm" + " " + nam,
                    "Ngày", "triệu VND",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false // Tắt Legend, Bật Tooltips
            );

            // Lấy Plot (khu vực vẽ) để tùy chỉnh
            CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

            // Thêm đường KPI (ValueMarker)
            ValueMarker kpiMarker = new ValueMarker(giaTriKPI);
            kpiMarker.setPaint(Color.BLUE);
            kpiMarker.setStroke(new BasicStroke( // Cài đặt nét đứt
                    2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    1.0f, new float[]{6.0f, 6.0f}, 0.0f
            ));
            plot.addRangeMarker(kpiMarker); // Thêm đường KPI vào trục Y (Range)

            // *** GÁN RENDERER TÙY CHỈNH ***
            // Sử dụng lớp CustomBarRenderer chúng ta đã tạo
            plot.setRenderer(new ToMauCot(giaTriKPI));

            return barChart;
        } else if (tieuChi.equals("năm")) {
            // Tạo biểu đồ cơ bản
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Doanh thu " + tieuChi + " " + nam,
                    "Ngày", "triệu VND",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false // Tắt Legend, Bật Tooltips
            );

            // Lấy Plot (khu vực vẽ) để tùy chỉnh
            CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

            // Thêm đường KPI (ValueMarker)
//            ValueMarker kpiMarker = new ValueMarker(giaTriKPI);
//            kpiMarker.setPaint(Color.BLUE);
//            kpiMarker.setStroke(new BasicStroke( // Cài đặt nét đứt
//                    2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
//                    1.0f, new float[]{6.0f, 6.0f}, 0.0f
//            ));
//            plot.addRangeMarker(kpiMarker); // Thêm đường KPI vào trục Y (Range)
            // *** GÁN RENDERER TÙY CHỈNH ***
            // Sử dụng lớp CustomBarRenderer chúng ta đã tạo
            plot.setRenderer(new ToMauCot(0.0));

            return barChart;
        }
        return null;
    }

    public void renderThongTinNhanVien(NhanVien nv) {
        lblMaNV.setText(nv.getMaNV());
        lblHoTen.setText(nv.getHoTenDem() + " " + nv.getTen());
        if (nv.isGioiTinh()) {
            lblGioiTinh.setText("Nam");
        } else {
            lblGioiTinh.setText("Nữ");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = nv.getNgaySinh().format(formatter);
        lblNgaySinh.setText(formattedDate);
        lblChucVu.setText(GiaoDienChinhGUI.getTk().isQuanLy() ? "Quản lý" : "Nhân viên");
        lblTrangThai.setText(nv.isNghiViec() ? "Nghỉ việc" : "Đang làm việc");
        lblSdt.setText(nv.getSdt());
    }

//    public void renderThongTinCaLam() {
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String formattedDate = today.format(formatter);
//
//        lblThongTinCaLam.setText("Ca Làm" + " Ngày " + formattedDate);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnVaoCa = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
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
        jPanel5 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        pnlBieuDo = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(808, 220));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(360, 220));
        jPanel2.setLayout(new java.awt.BorderLayout(10, 10));

        btnVaoCa.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnVaoCa.setText("Vào Ca");
        btnVaoCa.setPreferredSize(new java.awt.Dimension(72, 60));
        jPanel2.add(btnVaoCa, java.awt.BorderLayout.PAGE_START);

        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel22.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Thông tin ca làm");
        jPanel22.add(jLabel11);

        jPanel17.add(jPanel22, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel17, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setPreferredSize(new java.awt.Dimension(220, 220));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/profile1.png"))); // NOI18N
        jPanel4.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel4, java.awt.BorderLayout.LINE_START);

        jPanel7.setBackground(new java.awt.Color(245, 245, 245));
        jPanel7.setPreferredSize(new java.awt.Dimension(200, 220));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        jPanel8.setBackground(new java.awt.Color(245, 245, 245));
        jPanel8.setForeground(new java.awt.Color(245, 245, 245));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Mã nhân viên:");
        jPanel8.add(jLabel2);

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblMaNV.setForeground(new java.awt.Color(51, 51, 51));
        lblMaNV.setText("NV-XXXXX");
        jPanel8.add(lblMaNV);

        jPanel8.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        jPanel7.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(245, 245, 245));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Họ tên:");
        jPanel9.add(jLabel3);

        lblHoTen.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblHoTen.setForeground(new java.awt.Color(51, 51, 51));
        lblHoTen.setText("Nguyễn Văn A");
        jPanel9.add(lblHoTen);

        jPanel7.add(jPanel9);

        jPanel11.setBackground(new java.awt.Color(245, 245, 245));
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setText("Ngày sinh:");
        jPanel11.add(jLabel5);

        lblNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblNgaySinh.setForeground(new java.awt.Color(51, 51, 51));
        lblNgaySinh.setText("XX/XX/XXXX");
        jPanel11.add(lblNgaySinh);

        jPanel7.add(jPanel11);

        jPanel12.setBackground(new java.awt.Color(245, 245, 245));
        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setText("Chức vụ:");
        jPanel12.add(jLabel6);

        lblChucVu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblChucVu.setForeground(new java.awt.Color(51, 51, 51));
        lblChucVu.setText("Nhân Viên hoặc Quản Lý");
        jPanel12.add(lblChucVu);

        jPanel7.add(jPanel12);

        jPanel3.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel15.setPreferredSize(new java.awt.Dimension(240, 0));
        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.Y_AXIS));

        jPanel10.setBackground(new java.awt.Color(245, 245, 245));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Giới Tính:");
        jPanel10.add(jLabel4);

        lblGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblGioiTinh.setForeground(new java.awt.Color(51, 51, 51));
        lblGioiTinh.setText("Nam hoặc Nữ");
        jPanel10.add(lblGioiTinh);

        jPanel10.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        jPanel15.add(jPanel10);

        jPanel13.setBackground(new java.awt.Color(245, 245, 245));
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Trạng Thái:");
        jPanel13.add(jLabel7);

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTrangThai.setForeground(new java.awt.Color(51, 51, 51));
        lblTrangThai.setText("Đang làm việc");
        jPanel13.add(lblTrangThai);

        jPanel15.add(jPanel13);

        jPanel14.setBackground(new java.awt.Color(245, 245, 245));
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("SĐT:");
        jPanel14.add(jLabel8);

        lblSdt.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSdt.setForeground(new java.awt.Color(51, 51, 51));
        lblSdt.setText("0XXXXXXXXX");
        jPanel14.add(lblSdt);

        jPanel15.add(jPanel14);

        jPanel16.setBackground(new java.awt.Color(245, 245, 245));
        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel9.setText("   ");
        jPanel16.add(jLabel9);

        jPanel15.add(jPanel16);

        jPanel3.add(jPanel15, java.awt.BorderLayout.LINE_END);

        jPanel18.setBackground(new java.awt.Color(245, 245, 245));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Thông tin nhân viên");
        jPanel18.add(jLabel10);

        jPanel3.add(jPanel18, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(808, 220));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.X_AXIS));
        jPanel5.add(jPanel19);

        jPanel5.add(Box.createHorizontalStrut(10));

        jPanel5.add(jPanel20);

        jPanel5.add(Box.createHorizontalStrut(10));

        jPanel5.add(jPanel21);

        add(jPanel5, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout pnlBieuDoLayout = new javax.swing.GroupLayout(pnlBieuDo);
        pnlBieuDo.setLayout(pnlBieuDoLayout);
        pnlBieuDoLayout.setHorizontalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1100, Short.MAX_VALUE)
        );
        pnlBieuDoLayout.setVerticalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 189, Short.MAX_VALUE)
        );

        add(pnlBieuDo, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
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
    private javax.swing.JPanel pnlBieuDo;
    // End of variables declaration//GEN-END:variables

}
