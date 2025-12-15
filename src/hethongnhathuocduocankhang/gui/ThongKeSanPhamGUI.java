/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.DonViTinhDAO;
import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.SanPham;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ThongKeSanPhamGUI extends JPanel {

    private JTextField txtTopN;
    private JComboBox<String> cmbThang;
    private JComboBox<Integer> cmbNam;
    private JComboBox<String> cmbTieuChi;
    private JButton btnXemThongKe;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblTongDoanhThuTop;
    private JLabel lblThoiGianThongKe;

    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public ThongKeSanPhamGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // ANEL NORTH (Bộ lọc thống kê)
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // Panel chứa các điều kiện lọc: Top N, Tiêu chí, Tháng, Năm
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pnlFilter.setBorder(new EmptyBorder(0, 0, 10, 0));

        Font fontLabel = new Font("Arial", Font.BOLD, 14);
        Font fontInput = new Font("Arial", Font.PLAIN, 14);

        JLabel lblTop = new JLabel("Top:");
        lblTop.setFont(fontLabel);
        txtTopN = new JTextField("10", 3);
        txtTopN.setFont(fontInput);
        txtTopN.setHorizontalAlignment(JTextField.CENTER);
        txtTopN.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(5, 5, 5, 5)
        ));

        txtTopN.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> txtTopN.selectAll());
            }
        });

        JLabel lblTieuChi = new JLabel("Thống kê theo:");
        lblTieuChi.setFont(fontLabel);
        String[] arrTieuChi = {"Số Lượng Bán"};
        cmbTieuChi = new JComboBox<>(arrTieuChi);
        cmbTieuChi.setFont(fontInput);
        cmbTieuChi.setPreferredSize(new Dimension(150, 30));

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

        // Chọn Năm (2025 -> 2045)
        JLabel lblNam = new JLabel("Năm:");
        lblNam.setFont(fontLabel);
        Vector<Integer> arrNam = new Vector<>();
        for (int i = 2025; i <= 2045; i++) {
            arrNam.add(i);
        }
        cmbNam = new JComboBox<>(arrNam);
        cmbNam.setFont(fontInput);
        cmbNam.setPreferredSize(new Dimension(100, 30));

        pnlFilter.add(lblTop);
        pnlFilter.add(txtTopN);
        pnlFilter.add(lblTieuChi);
        pnlFilter.add(cmbTieuChi);
        pnlFilter.add(lblThang);
        pnlFilter.add(cmbThang);
        pnlFilter.add(lblNam);
        pnlFilter.add(cmbNam);

        // Panel chứa nút Xem
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        btnXemThongKe = new JButton("Xem thống kê");
        btnXemThongKe.setPreferredSize(new Dimension(140, 30));
        setupTopButton(btnXemThongKe, new Color(50, 150, 250));

        pnlActions.add(btnXemThongKe);

        pnlNorth.add(pnlFilter, BorderLayout.WEST);
        pnlNorth.add(pnlActions, BorderLayout.CENTER);

        this.add(pnlNorth, BorderLayout.NORTH);

        // PANEL CENTER
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        String[] columnNames = {
            "STT", "Mã SP", "Tên Sản Phẩm", "Đơn Vị Tính",
            "Số Lượng Bán", "Giá bán trung bình", "Tổng Doanh Thu"
        };

        // Model không cho phép sửa
        model = new DefaultTableModel(new Object[][]{}, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 4) {
                    return Integer.class; // STT, Số lượng
                }
                if (columnIndex == 5 || columnIndex == 6) {
                    return Double.class;  // Tiền
                }
                return String.class;
            }
        };

        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setAutoCreateRowSorter(false);

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

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        rightRenderer.setBorder(new EmptyBorder(0, 0, 0, 5));

        // 0. STT
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // 1. Mã SP
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);

        // 2. Tên SP
        columnModel.getColumn(2).setPreferredWidth(250);

        // 3. ĐVT
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(3).setCellRenderer(centerRenderer);

        // 4. Số lượng bán
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);

        // 5. Giá bán trung bình
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(5).setCellRenderer(rightRenderer);

        // 6. Tổng doanh thu
        columnModel.getColumn(6).setPreferredWidth(150);
        columnModel.getColumn(6).setCellRenderer(rightRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        // PANEL SOUTH (Footer tổng kết)
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.setBorder(new EmptyBorder(5, 0, 0, 0));

        Font fontFooter = new Font("Arial", Font.BOLD, 14);

        lblThoiGianThongKe = new JLabel("Thời gian: Chưa chọn");
        lblThoiGianThongKe.setFont(new Font("Arial", Font.ITALIC, 14));

        lblTongDoanhThuTop = new JLabel("Tổng doanh thu (Top N): 0 đ");
        lblTongDoanhThuTop.setFont(fontFooter);
        lblTongDoanhThuTop.setForeground(new Color(204, 0, 0));

        pnlSouth.add(lblThoiGianThongKe, BorderLayout.WEST);
        pnlSouth.add(lblTongDoanhThuTop, BorderLayout.EAST);

        this.add(pnlSouth, BorderLayout.SOUTH);

        btnXemThongKe.addActionListener(e -> updateTable());
    }

    private void updateTable() {
        model.setRowCount(0);

        int thang = cmbThang.getSelectedIndex();
        int nam = Integer.valueOf(cmbNam.getSelectedItem().toString());
        String tieuChi = (String) cmbTieuChi.getSelectedItem();

        int topN = 0;
        try {
            topN = Integer.parseInt(txtTopN.getText());
            if (topN <= 0) {
                JOptionPane.showMessageDialog(this, "Top N phải là số nguyên dương.");
                txtTopN.setText("10");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên cho Top N");
            return;
        }

        if (thang == 0) {
            lblThoiGianThongKe.setText("Thời gian: Cả năm " + nam);
        } else {
            lblThoiGianThongKe.setText("Thời gian: Tháng " + thang + "/" + nam);
        }

        LinkedHashMap<SanPham, Number[]> dsSP = new LinkedHashMap<>();
        if (thang == 0) {
            dsSP = SanPhamDAO.getSPBanChayTrongNam(LocalDate.of(nam, 1, 1));
        } else {
            LocalDate ngayChon = LocalDate.of(nam, thang, 1);
            dsSP = SanPhamDAO.getSPBanChayTrongThang(ngayChon);
        }

        if (dsSP.isEmpty()) {
            lblTongDoanhThuTop.setText("Tổng doanh thu (Top " + topN + "): 0 đ");
            return;
        }

        List<Map.Entry<SanPham, Number[]>> list = new LinkedList<>(dsSP.entrySet());

//        Comparator<Map.Entry<SanPham, Number[]>> comparator;
//        if (tieuChi.equals("Số Lượng Bán")) {
//            comparator = (o1, o2) -> o2.getValue()[0].intValue() - o1.getValue()[0].intValue();
//        } else { 
//            // Sắp xếp theo Tổng Doanh Thu (index 1) giảm dần (Mặc định)
//            comparator = (o1, o2) -> Double.compare(o2.getValue()[1].doubleValue(), o1.getValue()[1].doubleValue());
//        }
//        
//        Collections.sort(list, comparator);
        double tongDoanhThuTopN = 0;

        for (int i = 0; i < Math.min(topN, list.size()); i++) {
            Map.Entry<SanPham, Number[]> entry = list.get(i);

            // Lấy ĐVT Cơ bản
            DonViTinh dvtCB = null;
            ArrayList<DonViTinh> dsDVTSP = DonViTinhDAO.getDonViTinhTheoMaSP(entry.getKey().getMaSP());
            for (DonViTinh d : dsDVTSP) {
                if (d.isDonViTinhCoBan()) {
                    dvtCB = d;
                    break;
                }
            }

            String tenDVT = (dvtCB != null) ? dvtCB.getTenDonVi() : "N/A";

            // Lấy dữ liệu từ mảng Number[] của DAO
            // [0]: SLBAN (int)
            // [1]: TONGTIEN (double)
            // [2]: DONGIA_TB (double)
            Number[] values = entry.getValue();

            int soLuong = values[0].intValue();
            double tongTien = values[1].doubleValue();
            double donGiaTB = values[2].doubleValue();

            tongDoanhThuTopN += tongTien;

            Object[] row = {
                i + 1,
                entry.getKey().getMaSP(),
                entry.getKey().getTen(),
                tenDVT,
                soLuong, 
                String.format("%,.0f VND", donGiaTB), 
                String.format("%,.0f VND", tongTien)
            };
            model.addRow(row);
        }
        
        lblTongDoanhThuTop.setText("Tổng doanh thu (Top " + topN + "): " + currencyFormat.format(tongDoanhThuTopN));
    }

    private void setupTopButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(button.getBackground().equals(Color.LIGHT_GRAY) ? Color.BLACK : Color.WHITE);
        if (bgColor.equals(Color.LIGHT_GRAY)) {
            button.setForeground(Color.BLACK);
        } else {
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));
        }
        button.setMargin(new Insets(5, 10, 5, 10));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    // Getters 
    public JTextField getTxtTopN() {
        return txtTopN;
    }

    public JComboBox<String> getCmbThang() {
        return cmbThang;
    }

    public JComboBox<Integer> getCmbNam() {
        return cmbNam;
    }

    public JComboBox<String> getCmbTieuChi() {
        return cmbTieuChi;
    }

    public JButton getBtnXemThongKe() {
        return btnXemThongKe;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JLabel getLblTongDoanhThuTop() {
        return lblTongDoanhThuTop;
    }
}
