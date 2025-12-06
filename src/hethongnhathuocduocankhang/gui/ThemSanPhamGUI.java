/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.entity.LoaiSanPhamEnum;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class ThemSanPhamGUI extends JPanel {

    // --- 1. Components Thông tin chung ---
    private JTextField txtMaSanPham, txtTenSanPham;
    private JTextArea txtMoTa, txtThanhPhan;
    private JComboBox<String> cmbLoaiSanPham;
    private JTextField txtTonToiThieu, txtTonToiDa;

    // --- 2. Components QUẢN LÝ BARCODE ---
    private JTextField txtInputBarcode;
    private JButton btnThemBarcode, btnXoaBarcode;
    private JTable tblBarcode;
    private DefaultTableModel modelBarcode;

    // --- 3. Components QUẢN LÝ ĐƠN VỊ TÍNH (Tab 1) ---
    private JTextField txtTenDonVi, txtHeSoQuyDoi, txtGiaBanDonVi;
    private JCheckBox chkDonViCoBan;
    private JButton btnThemDVT, btnXoaDVT;
    private JTable tblDonViTinh;
    private DefaultTableModel modelDVT;

    // --- 4. Components QUẢN LÝ NHÀ CUNG CẤP (Tab 2) ---
    private JTextField txtTimNCC;
    private JButton btnTimNCC;
    private JTextField txtGiaNhap;
    private JButton btnThemNCC;
    private JButton btnXoaNCC;

    private JTable tableKQTimKiemNCC;       // Bảng kết quả tìm kiếm
    private DefaultTableModel modelKQTimKiemNCC;

    private JTable tblNCCChon;          // Bảng danh sách đã chọn
    private DefaultTableModel modelNCCChon;

    // --- 5. Components QUẢN LÝ KHUYẾN MÃI (Tab 3) ---
    private JTextField txtTimKM;
    private JButton btnTimKM;
    private JButton btnThemKM;
    private JButton btnXoaKM;

    private JTable tableKQTimKiemKM;        // Bảng kết quả tìm kiếm
    private DefaultTableModel modelKQTimKiemKM;

    private JTable tblKMChon;           // Bảng danh sách đã chọn
    private DefaultTableModel modelKMChon;

    // --- 6. Components điều hướng ---
    private JButton btnHuy, btnXacNhan;

    public ThemSanPhamGUI() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());

        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 10));
        mainContentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // =====================================================================
        // 1. PANEL TRÊN: THÔNG TIN CHI TIẾT SẢN PHẨM (GridBagLayout)
        // =====================================================================
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlInfo.setBorder(new TitledBorder("Thông tin chi tiết sản phẩm"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // --- Row 0: Mã SP + Barcode ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Mã sản phẩm:"), gbc);

        txtMaSanPham = new JTextField(15);
        // txtMaSanPham.setEditable(false); // Thường mã tự sinh sẽ không cho sửa
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        pnlInfo.add(txtMaSanPham, gbc);

        // Phần Barcode (Góc phải)
        gbc.gridx = 2;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Mã Vạch:"), gbc);

        JPanel pnlBarcodeContainer = new JPanel(new BorderLayout(5, 5));
        JPanel pnlInputBC = new JPanel(new BorderLayout(5, 0));

        txtInputBarcode = new JTextField();
        btnThemBarcode = new JButton("+");
        btnThemBarcode.setMargin(new Insets(2, 8, 2, 8));

        pnlInputBC.add(txtInputBarcode, BorderLayout.CENTER);
        pnlInputBC.add(btnThemBarcode, BorderLayout.EAST);

        modelBarcode = new DefaultTableModel(new String[]{"Mã Barcode"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblBarcode = new JTable(modelBarcode);
        tblBarcode.setTableHeader(null); // Ẩn header cho gọn

        JScrollPane scrBarcode = new JScrollPane(tblBarcode);
        scrBarcode.setBorder(new TitledBorder("DS Mã vạch"));
        scrBarcode.setPreferredSize(new Dimension(150, 80));

        btnXoaBarcode = new JButton("Xóa mã chọn");
        btnXoaBarcode.setFont(new Font("Arial", Font.PLAIN, 10));

        pnlBarcodeContainer.add(pnlInputBC, BorderLayout.NORTH);
        pnlBarcodeContainer.add(scrBarcode, BorderLayout.CENTER);
        pnlBarcodeContainer.add(btnXoaBarcode, BorderLayout.SOUTH);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        gbc.gridheight = 3; // Chiếm 3 dòng dọc
        gbc.fill = GridBagConstraints.BOTH;
        pnlInfo.add(pnlBarcodeContainer, gbc);

        // Reset lại ràng buộc cho các dòng sau
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Row 1: Tên Sản Phẩm ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Tên sản phẩm:"), gbc);

        txtTenSanPham = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        pnlInfo.add(txtTenSanPham, gbc);

        // --- Row 2: Loại Sản Phẩm ---
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Loại sản phẩm:"), gbc);

        cmbLoaiSanPham = new JComboBox<>(new String[]{"Thuốc kê đơn", "Thuốc không kê đơn", "Thực phẩm chức năng"});
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        pnlInfo.add(cmbLoaiSanPham, gbc);

        // --- Row 3: Thành phần ---
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Thành phần:"), gbc);

        txtThanhPhan = new JTextArea(3, 20);
        txtThanhPhan.setLineWrap(true);
        txtThanhPhan.setWrapStyleWord(true);
        JScrollPane scrThanhPhan = new JScrollPane(txtThanhPhan);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        pnlInfo.add(scrThanhPhan, gbc);
        gbc.gridwidth = 1;

        // --- Row 4: Mô tả ---
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Mô tả công dụng:"), gbc);

        txtMoTa = new JTextArea(3, 20);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        JScrollPane scrMoTa = new JScrollPane(txtMoTa);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        pnlInfo.add(scrMoTa, gbc);
        gbc.gridwidth = 1;

        // --- Row 5: Tồn kho ---
        JPanel pnlTonKho = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        txtTonToiThieu = new JTextField(5);
        txtTonToiDa = new JTextField(5);

        pnlTonKho.add(txtTonToiThieu);
        pnlTonKho.add(new JLabel("   Tồn tối đa:  "));
        pnlTonKho.add(txtTonToiDa);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Tồn tối thiểu:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        pnlInfo.add(pnlTonKho, gbc);
        gbc.gridwidth = 1;

        // =====================================================================
        // 2. PANEL DƯỚI: TABBED PANE (Chi tiết con)
        // =====================================================================
        JTabbedPane tabbedPane = new JTabbedPane();

        // ---------------- TAB 1: ĐƠN VỊ TÍNH ----------------
        JPanel pnlDVT = new JPanel(new BorderLayout(5, 5));
        pnlDVT.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel pnlInputDVT = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTenDonVi = new JTextField(6);
        txtHeSoQuyDoi = new JTextField(4);
        txtGiaBanDonVi = new JTextField(7);
        chkDonViCoBan = new JCheckBox("Cơ bản");
        btnThemDVT = new JButton("Thêm");
        btnXoaDVT = new JButton("Xóa");

        pnlInputDVT.add(new JLabel("Tên ĐV:"));
        pnlInputDVT.add(txtTenDonVi);
        pnlInputDVT.add(new JLabel("Quy đổi:"));
        pnlInputDVT.add(txtHeSoQuyDoi);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat("#,##0", symbols);

        txtGiaBanDonVi.setHorizontalAlignment(JTextField.RIGHT);
        txtGiaBanDonVi.getDocument().addDocumentListener(new DocumentListener() {

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
                    String currentText = txtGiaBanDonVi.getText();
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
                            int caretPosition = txtGiaBanDonVi.getCaretPosition();
                            String originalText = txtGiaBanDonVi.getText();

                            // Cập nhật Text
                            txtGiaBanDonVi.setText(formattedText);

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

                            txtGiaBanDonVi.setCaretPosition(newCaretPosition);

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

        pnlInputDVT.add(new JLabel("Giá bán:"));
        pnlInputDVT.add(txtGiaBanDonVi);

        pnlInputDVT.add(chkDonViCoBan);

        pnlInputDVT.add(btnThemDVT);

        pnlInputDVT.add(btnXoaDVT);

        String[] colsDVT = {"Mã ĐV", "Tên Đơn Vị", "Hệ Số Quy Đổi", "Giá Bán", "Cơ Bản"};
        modelDVT = new DefaultTableModel(colsDVT, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDonViTinh = new JTable(modelDVT);

        tblDonViTinh.setRowHeight(
                22);
        tblDonViTinh.getColumnModel()
                .getColumn(0).setPreferredWidth(80);

        pnlDVT.add(pnlInputDVT, BorderLayout.NORTH);
        pnlDVT.add(new JScrollPane(tblDonViTinh), BorderLayout.CENTER);
        tabbedPane.addTab("1. Đơn vị tính", pnlDVT);

        // ---------------- TAB 2: NHÀ CUNG CẤP ----------------
        JPanel pnlNCC = new JPanel(new BorderLayout(5, 5));
        pnlNCC.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel pnlTopControlNCC = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlTopControlNCC.setBorder(BorderFactory.createEtchedBorder());

        txtTimNCC = new JTextField(12);
        btnTimNCC = new JButton("Tìm");
        txtGiaNhap = new JTextField(8);
        btnThemNCC = new JButton("↓ Thêm xuống DS");
        btnThemNCC.setBackground(new Color(220, 235, 250));

        pnlTopControlNCC.add(new JLabel("Tìm NCC (Mã/Tên):"));
        pnlTopControlNCC.add(txtTimNCC);
        pnlTopControlNCC.add(btnTimNCC);
        pnlTopControlNCC.add(new JSeparator(JSeparator.VERTICAL));
        pnlTopControlNCC.add(new JLabel("Giá nhập:"));
        pnlTopControlNCC.add(txtGiaNhap);
        pnlTopControlNCC.add(btnThemNCC);

        pnlNCC.add(pnlTopControlNCC, BorderLayout.NORTH);

        JPanel pnlTablesAreaNCC = new JPanel(new GridLayout(2, 1, 0, 10));

        // Bảng 1: Kết quả tìm kiếm
        JPanel pnlTable1NCC = new JPanel(new BorderLayout());
        pnlTable1NCC.setBorder(BorderFactory.createTitledBorder("Kết quả tìm kiếm"));
        modelKQTimKiemNCC = new DefaultTableModel(new String[]{"Mã NCC", "Tên NCC", "SĐT", "Địa chỉ"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableKQTimKiemNCC = new JTable(modelKQTimKiemNCC);
        tableKQTimKiemNCC.setRowHeight(22);
        pnlTable1NCC.add(new JScrollPane(tableKQTimKiemNCC), BorderLayout.CENTER);

        // Bảng 2: DS Đã chọn
        JPanel pnlTable2NCC = new JPanel(new BorderLayout());
        pnlTable2NCC.setBorder(BorderFactory.createTitledBorder("DS Nhà cung cấp đã chọn"));
        modelNCCChon = new DefaultTableModel(new String[]{"Mã NCC", "Tên NCC", "Giá Nhập"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblNCCChon = new JTable(modelNCCChon);
        tblNCCChon.setRowHeight(22);

        JPanel pnlFooterNCC = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXoaNCC = new JButton("Xóa khỏi DS");
        pnlFooterNCC.add(btnXoaNCC);

        pnlTable2NCC.add(new JScrollPane(tblNCCChon), BorderLayout.CENTER);
        pnlTable2NCC.add(pnlFooterNCC, BorderLayout.SOUTH);

        pnlTablesAreaNCC.add(pnlTable1NCC);
        pnlTablesAreaNCC.add(pnlTable2NCC);
        pnlNCC.add(pnlTablesAreaNCC, BorderLayout.CENTER);
        tabbedPane.addTab("2. Nhà cung cấp", pnlNCC);

        // ---------------- TAB 3: KHUYẾN MÃI ----------------
        JPanel pnlKM = new JPanel(new BorderLayout(5, 5));
        pnlKM.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel pnlTopControlKM = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlTopControlKM.setBorder(BorderFactory.createEtchedBorder());

        txtTimKM = new JTextField(15);
        btnTimKM = new JButton("Tìm");
        btnThemKM = new JButton("↓ Thêm xuống DS");
        btnThemKM.setBackground(new Color(220, 235, 250));

        pnlTopControlKM.add(new JLabel("Tìm KM (Mã KM/Mô tả):"));
        pnlTopControlKM.add(txtTimKM);
        pnlTopControlKM.add(btnTimKM);
        pnlTopControlKM.add(Box.createHorizontalStrut(20));
        pnlTopControlKM.add(btnThemKM);

        pnlKM.add(pnlTopControlKM, BorderLayout.NORTH);

        JPanel pnlTablesAreaKM = new JPanel(new GridLayout(2, 1, 0, 10));

        // Bảng 1: Kết quả tìm kiếm
        JPanel pnlTable1KM = new JPanel(new BorderLayout());
        pnlTable1KM.setBorder(BorderFactory.createTitledBorder("Kết quả tìm kiếm Khuyến mãi"));
        modelKQTimKiemKM = new DefaultTableModel(new String[]{"Mã KM", "Mô tả", "Giảm (%)", "Bắt đầu", "Kết thúc"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableKQTimKiemKM = new JTable(modelKQTimKiemKM);
        tableKQTimKiemKM.setRowHeight(22);
        pnlTable1KM.add(new JScrollPane(tableKQTimKiemKM), BorderLayout.CENTER);

        // Bảng 2: DS KM đã chọn
        JPanel pnlTable2KM = new JPanel(new BorderLayout());
        pnlTable2KM.setBorder(BorderFactory.createTitledBorder("DS Khuyến mãi áp dụng"));
        modelKMChon = new DefaultTableModel(new String[]{"Mã KM", "Mô tả", "Giảm (%)", "SL Min", "SL Max", "Ngày sửa"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblKMChon = new JTable(modelKMChon);
        tblKMChon.setRowHeight(22);

        JPanel pnlFooterKM = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXoaKM = new JButton("Xóa khỏi DS");
        pnlFooterKM.add(btnXoaKM);

        pnlTable2KM.add(new JScrollPane(tblKMChon), BorderLayout.CENTER);
        pnlTable2KM.add(pnlFooterKM, BorderLayout.SOUTH);

        pnlTablesAreaKM.add(pnlTable1KM);
        pnlTablesAreaKM.add(pnlTable2KM);
        pnlKM.add(pnlTablesAreaKM, BorderLayout.CENTER);
        tabbedPane.addTab("3. Khuyến mãi", pnlKM);

        tabbedPane.setPreferredSize(new Dimension(100, 380));

        // =====================================================================
        // 3. NÚT XÁC NHẬN / HỦY (Footer Form)
        // =====================================================================
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnHuy = new JButton("Hủy bỏ");
        btnXacNhan = new JButton("Lưu sản phẩm");

        btnXacNhan.setBackground(new Color(0, 153, 51));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setPreferredSize(new Dimension(120, 35));

        pnlButton.add(btnHuy);
        pnlButton.add(btnXacNhan);

        // --- Add main components to this JPanel ---
        mainContentPanel.add(pnlInfo, BorderLayout.NORTH);
        mainContentPanel.add(tabbedPane, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(pnlButton, BorderLayout.SOUTH);

        // Cấu hình selection mode cho bảng
        tblNCCChon.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblKMChon.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tableKQTimKiemNCC.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tableKQTimKiemKM.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    // ==========================================================================
    // GETTERS (Để lớp BUS truy cập lấy dữ liệu và gán sự kiện)
    // ==========================================================================
    public JTextField getTxtMaSanPham() {
        return txtMaSanPham;
    }

    public JTextField getTxtTenSanPham() {
        return txtTenSanPham;
    }

    public JTextArea getTxtMoTa() {
        return txtMoTa;
    }

    public JTextArea getTxtThanhPhan() {
        return txtThanhPhan;
    }

    public JComboBox<String> getCmbLoaiSanPham() {
        return cmbLoaiSanPham;
    }

    public JTextField getTxtTonToiThieu() {
        return txtTonToiThieu;
    }

    public JTextField getTxtTonToiDa() {
        return txtTonToiDa;
    }

    // Barcode
    public JTextField getTxtInputBarcode() {
        return txtInputBarcode;
    }

    public JButton getBtnThemBarcode() {
        return btnThemBarcode;
    }

    public JButton getBtnXoaBarcode() {
        return btnXoaBarcode;
    }

    public JTable getTblBarcode() {
        return tblBarcode;
    }

    public DefaultTableModel getModelBarcode() {
        return modelBarcode;
    }

    // Đơn vị tính
    public JTextField getTxtTenDonVi() {
        return txtTenDonVi;
    }

    public JTextField getTxtHeSoQuyDoi() {
        return txtHeSoQuyDoi;
    }

    public JTextField getTxtGiaBanDonVi() {
        return txtGiaBanDonVi;
    }

    public JCheckBox getChkDonViCoBan() {
        return chkDonViCoBan;
    }

    public JButton getBtnThemDVT() {
        return btnThemDVT;
    }

    public JButton getBtnXoaDVT() {
        return btnXoaDVT;
    }

    public JTable getTblDonViTinh() {
        return tblDonViTinh;
    }

    public DefaultTableModel getModelDVT() {
        return modelDVT;
    }

    // Nhà cung cấp
    public JTextField getTxtTimNCC() {
        return txtTimNCC;
    }

    public JTextField getTxtGiaNhap() {
        return txtGiaNhap;
    }

    public JButton getBtnTimNCC() {
        return btnTimNCC;
    }

    public JButton getBtnThemNCC() {
        return btnThemNCC;
    }

    public void setCmbLoaiSanPham(LoaiSanPhamEnum loai) {
        if (loai == LoaiSanPhamEnum.THUOC_KE_DON) {
            jComboBox1.setSelectedItem("Thuốc kê đơn");
        } else if (loai == LoaiSanPhamEnum.THUC_PHAM_CHUC_NANG) {
            jComboBox1.setSelectedItem("Thực phẩm chức năng");
        } else {
            jComboBox1.setSelectedItem("Thuốc không kê đơn");
        }
    }

    public JButton getBtnXoaNCC() {
        return btnXoaNCC;
    }

    public JTable getTableKQTimKiemNCC() {
        return tableKQTimKiemNCC;
    }

    public DefaultTableModel getModelTimKiemNCC() {
        return modelKQTimKiemNCC;
    }

    public JTable getTblNCCChon() {
        return tblNCCChon;
    }

    public DefaultTableModel getModelNCCChon() {
        return modelNCCChon;
    }

    // Khuyến mãi
    public JTextField getTxtTimKM() {
        return txtTimKM;
    }

    public JButton getBtnTimKM() {
        return btnTimKM;
    }

    public JButton getBtnThemKM() {
        return btnThemKM;
    }

    public JButton getBtnXoaKM() {
        return btnXoaKM;
    }

    public JTable getTblTimKiemKM() {
        return tableKQTimKiemKM;
    }

    public DefaultTableModel getModelKQTimKiemKM() {
        return modelKQTimKiemKM;
    }

    public JTable getTblKMChon() {
        return tblKMChon;
    }

    public DefaultTableModel getModelKMChon() {
        return modelKMChon;
    }

    // Action buttons
    public JButton getBtnHuy() {
        return btnHuy;
    }

    public JButton getBtnXacNhan() {
        return btnXacNhan;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
