/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.ChiTietHoaDonDAO;
import hethongnhathuocduocankhang.dao.PhieuTraHangDAO;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ThongKePhieuTraHangGUI extends JPanel {

    private JComboBox<String> cmbThang;
    private JComboBox<Integer> cmbNam;
    private JButton btnXemThongKe;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblTongSoTraHang;
    private JLabel lblThoiGianThongKe;

    public ThongKePhieuTraHangGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- PANEL NORTH (Bộ lọc thống kê) ---
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // Panel chứa các điều kiện lọc
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pnlFilter.setBorder(new EmptyBorder(0, 0, 10, 0));

        Font fontLabel = new Font("Arial", Font.BOLD, 14);
        Font fontInput = new Font("Arial", Font.PLAIN, 14);

        JLabel lblThang = new JLabel("Tháng:");
        lblThang.setFont(fontLabel);
        String[] arrThang = new String[13];
        arrThang[0] = "Tất cả";
        for (int i = 1; i <= 12; i++) {
            arrThang[i] = "Tháng " + i;
        }
        cmbThang = new JComboBox<>(arrThang);
        cmbThang.setFont(fontInput);
        cmbThang.setPreferredSize(new Dimension(120, 30));

        // Chọn Năm
        JLabel lblNam = new JLabel("Năm:");
        lblNam.setFont(fontLabel);
        Vector<Integer> arrNam = new Vector<>();
        for (int i = 2025; i <= 2045; i++) {
            arrNam.add(i);
        }
        cmbNam = new JComboBox<>(arrNam);
        cmbNam.setFont(fontInput);
        cmbNam.setPreferredSize(new Dimension(100, 30));

        pnlFilter.add(lblThang);
        pnlFilter.add(cmbThang);
        pnlFilter.add(lblNam);
        pnlFilter.add(cmbNam);

        // Panel chứa nút Xem
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        btnXemThongKe = new JButton("Xem thống kê");
        btnXemThongKe.setPreferredSize(new Dimension(140, 30));
        setupTopButton(btnXemThongKe, new Color(50, 150, 250)); // Helper method giống file mẫu

        pnlActions.add(btnXemThongKe);

        pnlNorth.add(pnlFilter, BorderLayout.WEST);
        pnlNorth.add(pnlActions, BorderLayout.CENTER);

        this.add(pnlNorth, BorderLayout.NORTH);

        // --- PANEL CENTER (Bảng dữ liệu) ---
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        String[] columnNames = {
            "STT", "Mã SP", "Tên Sản Phẩm", "Đơn Vị Tính",
            "Tổng Bán", "Tổng Trả", "Tỷ Lệ Trả (%)", "Chi Tiết Lý Do (Số lượng | %)"
        };

        // Model không cho phép sửa
        model = new DefaultTableModel(new Object[][]{}, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 4 || columnIndex == 5) {
                    return Integer.class; // STT, Số lượng
                }
                return String.class;
            }
        };
        
        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setAutoCreateRowSorter(false);

        // --- CẤU HÌNH HEADER & ĐƯỜNG KẺ (GRID) ---
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(220, 220, 220)); // Màu nền header xám nhẹ
        table.getTableHeader().setReorderingAllowed(false);
        
        // 3 Dòng lệnh quan trọng để hiện đường kẻ:
        table.setShowGrid(true);                // Bật hiển thị lưới
        table.setShowHorizontalLines(true);     // Bật đường kẻ ngang
        table.setShowVerticalLines(true);       // Bật đường kẻ dọc
        table.setGridColor(Color.LIGHT_GRAY);   // Màu của đường kẻ (giống code cũ của bạn)

        // Header bảng
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        // Cấu hình độ rộng cột & Căn lề
        TableColumnModel columnModel = table.getColumnModel();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        leftRenderer.setBorder(new EmptyBorder(0, 5, 0, 0)); // Padding trái 5px

        // 0. STT
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // 1. Mã SP
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);

        // 2. Tên SP
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(2).setCellRenderer(leftRenderer);

        // 3. ĐVT
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(3).setCellRenderer(centerRenderer);

        // 4. Tổng Bán
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);

        // 5. Tổng Trả
        columnModel.getColumn(5).setPreferredWidth(80);
        columnModel.getColumn(5).setCellRenderer(centerRenderer);

        // 6. Tỷ Lệ Trả
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(6).setCellRenderer(centerRenderer);

        // 7. Chi Tiết Lý Do (Rộng nhất để hiển thị text dài)
        columnModel.getColumn(7).setPreferredWidth(400);
        columnModel.getColumn(7).setCellRenderer(leftRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        // --- PANEL SOUTH (Footer tổng kết) ---
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.setBorder(new EmptyBorder(5, 0, 0, 0));

        Font fontFooter = new Font("Arial", Font.BOLD, 14);

        lblThoiGianThongKe = new JLabel("Thời gian: Chưa chọn");
        lblThoiGianThongKe.setFont(new Font("Arial", Font.ITALIC, 14));

        lblTongSoTraHang = new JLabel("Tổng số lượng sản phẩm bị trả: 0");
        lblTongSoTraHang.setFont(fontFooter);
        lblTongSoTraHang.setForeground(new Color(204, 0, 0)); // Màu đỏ giống mẫu

        pnlSouth.add(lblThoiGianThongKe, BorderLayout.WEST);
        pnlSouth.add(lblTongSoTraHang, BorderLayout.EAST);

        this.add(pnlSouth, BorderLayout.SOUTH);

        btnXemThongKe.addActionListener(e -> updateTable());
    }

    private void updateTable() {
        model.setRowCount(0);

        int thang = cmbThang.getSelectedIndex();
        int nam = Integer.valueOf(cmbNam.getSelectedItem().toString());

        // Cập nhật text thời gian
        if (thang == 0) {
            lblThoiGianThongKe.setText("Thời gian: Cả năm " + nam);
        } else {
            lblThoiGianThongKe.setText("Thời gian: Tháng " + thang + "/" + nam);
        }

        // 1. Lấy dữ liệu Tổng Bán (Mẫu số) từ DAO
        Map<String, Integer> mapBan = ChiTietHoaDonDAO.getTongSoLuongBanRaTheoThoiGian(thang, nam);

        // 2. Lấy dữ liệu Trả hàng (Tử số & Chi tiết) từ DAO
        ArrayList<Object[]> rawDataTra = PhieuTraHangDAO.getDuLieuThongKeTraHang(thang, nam);

        if (rawDataTra.isEmpty()) {
            lblTongSoTraHang.setText("Tổng số lượng sản phẩm bị trả: 0");
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trả hàng trong thời gian này.");
            return;
        }

        // 3. Gom nhóm dữ liệu trả theo Mã Sản Phẩm
        Map<String, List<Object[]>> mapTraGrouped = new HashMap<>();
        for (Object[] row : rawDataTra) {
            String maSP = (String) row[0];
            mapTraGrouped.putIfAbsent(maSP, new ArrayList<>());
            mapTraGrouped.get(maSP).add(row);
        }

        int tongTatCaHangTra = 0;
        int stt = 1;

        // 4. Duyệt từng sản phẩm và tính toán
        for (Map.Entry<String, List<Object[]>> entry : mapTraGrouped.entrySet()) {
            String maSP = entry.getKey();
            List<Object[]> listChiTiet = entry.getValue();

            String tenSP = (String) listChiTiet.get(0)[1];
            String tenDVT = (String) listChiTiet.get(0)[2];

            // Lấy tổng bán từ Map (nếu không tìm thấy thì gán = 0)
            int tongBan = mapBan.getOrDefault(maSP, 0);

            // Tính toán tổng trả & Đếm lý do
            int tongTraSP = 0;
            Map<String, Integer> mapLyDoCount = new HashMap<>();

            for (Object[] item : listChiTiet) {
                int sl = (Integer) item[3];
                String lyDo = (String) item[4];

                tongTraSP += sl;
                mapLyDoCount.put(lyDo, mapLyDoCount.getOrDefault(lyDo, 0) + sl);
            }

            tongTatCaHangTra += tongTraSP;

            // Xử lý mẫu số để tính % (Tránh chia cho 0)
            // Logic: Nếu bán = 0 mà có trả, coi như bán = trả để % là 100 (hoặc xử lý tùy ý)
            int mauSo = (tongBan == 0) ? tongTraSP : tongBan;

            // Tính tổng tỷ lệ trả
            double tyLeTraTong = (double) tongTraSP * 100 / mauSo;

            // Tạo chuỗi chi tiết
            StringBuilder chiTietStr = new StringBuilder();

            // Tìm lý do nhiều nhất để (optionally) highlight hoặc sắp xếp
            // Ở đây chỉ cần liệt kê
            for (Map.Entry<String, Integer> lyDoEntry : mapLyDoCount.entrySet()) {
                String lyDo = lyDoEntry.getKey();
                int count = lyDoEntry.getValue();

                // Tính % cho lý do cụ thể dựa trên TỔNG BÁN
                double phanTramLyDo = (double) count * 100 / mauSo;

                if (chiTietStr.length() > 0) {
                    chiTietStr.append(", ");
                }
                // Format: Tên lỗi: 5 (5.0%)
                chiTietStr.append(String.format("%s: %d (%.1f%%)", convertLyDo(lyDo), count, phanTramLyDo));
            }

            Object[] row = {
                stt++,
                maSP,
                tenSP,
                tenDVT,
                tongBan,
                tongTraSP,
                String.format("%.1f%%", tyLeTraTong), // Cột tỷ lệ tổng
                chiTietStr.toString() // Cột chi tiết
            };
            model.addRow(row);
        }

        lblTongSoTraHang.setText("Tổng số lượng sản phẩm bị trả: " + tongTatCaHangTra);
    }

    // Helper format giống file mẫu
    private void setupTopButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setMargin(new Insets(5, 10, 5, 10));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    // Chuyển đổi Enum sang tiếng Việt
    private String convertLyDo(String enumString) {
        if (enumString == null) {
            return "";
        }
        switch (enumString) {
            case "HANG_LOI_DO_NHA_SAN_XUAT":
                return "Lỗi NSX";
            case "DI_UNG_MAN_CAM":
                return "Dị ứng/Mẫn cảm";
            case "NHU_CAU_KHACH_HANG":
                return "Nhu cầu KH";
            case "HANG_NGUYEN_VEN":
                return "Nguyên vẹn";
            case "HANG_KHONG_NGUYEN_VEN":
                return "Hư hỏng";
            case "HANG_DA_SU_DUNG":
                return "Đã dùng";
            default:
                return enumString;
        }
    }

    // Getters
    public JButton getBtnXemThongKe() {
        return btnXemThongKe;
    }
}
