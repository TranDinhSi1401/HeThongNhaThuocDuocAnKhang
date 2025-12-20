package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.bus.SanPhamBUS;
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

public class ThemSanPhamGUI extends JDialog {

    private SanPhamBUS bus;
    private boolean isEditMode;

    private JTextField txtMaSanPham, txtTenSanPham;
    private JTextArea txtMoTa, txtThanhPhan;
    private JComboBox<String> cmbLoaiSanPham;
    private JTextField txtTonToiThieu, txtTonToiDa;

    private JTextField txtInputBarcode;
    private JButton btnThemBarcode, btnXoaBarcode;
    private JTable tableBarcode;
    private DefaultTableModel modelBarcode;

    private JTextField txtHeSoQuyDoi, txtGiaBanDonVi;
    private JComboBox<String> cboTenDonVi;
    private JCheckBox chkDonViCoBan;
    private JButton btnThemDVT, btnXoaDVT;
    private JTable tableDonViTinh;
    private DefaultTableModel modelDVT;

    private JTextField txtTimNCC;
    private JButton btnTimNCC;
    private JTextField txtGiaNhap;
    private JButton btnThemNCC, btnXoaNCC;
    private JTable tableKQTimKiemNCC, tableNCCChon;
    private DefaultTableModel modelKQTimKiemNCC, modelNCCChon;

    private JTextField txtTimKM;
    private JButton btnTimKM, btnThemKM, btnXoaKM;
    private JTable tableKQTimKiemKM, tableKMChon;
    private DefaultTableModel modelKQTimKiemKM, modelKMChon;

    private JButton btnHuy, btnXacNhan;

    public ThemSanPhamGUI(SanPhamBUS bus, boolean isEditMode, String maSP) {
        this.bus = bus;
        this.isEditMode = isEditMode;

        this.setTitle(isEditMode ? "Cập nhật sản phẩm" : "Thêm sản phẩm mới");
        this.setSize(950, 800);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setLayout(new BorderLayout());

        initComponents(); // Tạo giao diện
        initEvents();     // Gắn sự kiện

        // Gọi BUS chuẩn bị dữ liệu
        if (isEditMode) {
            bus.chuanBiFormSua(this, maSP);
        } else {
            bus.chuanBiFormThem(this);
        }
    }

    private void initComponents() {
        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 10));
        mainContentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. INFO PANEL
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlInfo.setBorder(new TitledBorder("Thông tin chi tiết sản phẩm"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Mã sản phẩm:"), gbc);

        txtMaSanPham = new JTextField(15);
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        pnlInfo.add(txtMaSanPham, gbc);

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
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tableBarcode = new JTable(modelBarcode);
        tableBarcode.setTableHeader(null);
        JScrollPane scrBarcode = new JScrollPane(tableBarcode);
        scrBarcode.setBorder(new TitledBorder("DS Mã vạch"));
        scrBarcode.setPreferredSize(new Dimension(150, 80));
        btnXoaBarcode = new JButton("Xóa mã chọn");
        btnXoaBarcode.setFont(new Font("Arial", Font.PLAIN, 10));

        pnlBarcodeContainer.add(pnlInputBC, BorderLayout.NORTH);
        pnlBarcodeContainer.add(scrBarcode, BorderLayout.CENTER);
        pnlBarcodeContainer.add(btnXoaBarcode, BorderLayout.SOUTH);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        pnlInfo.add(pnlBarcodeContainer, gbc);
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Tên sản phẩm:"), gbc);
        txtTenSanPham = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        pnlInfo.add(txtTenSanPham, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Loại sản phẩm:"), gbc);
        cmbLoaiSanPham = new JComboBox<>(new String[]{"Thuốc kê đơn", "Thuốc không kê đơn", "Thực phẩm chức năng"});
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        pnlInfo.add(cmbLoaiSanPham, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Thành phần:"), gbc);
        txtThanhPhan = new JTextArea(3, 20);
        txtThanhPhan.setLineWrap(true);
        txtThanhPhan.setWrapStyleWord(true);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        pnlInfo.add(new JScrollPane(txtThanhPhan), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        pnlInfo.add(new JLabel("Mô tả công dụng:"), gbc);
        txtMoTa = new JTextArea(3, 20);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        pnlInfo.add(new JScrollPane(txtMoTa), gbc);
        gbc.gridwidth = 1;

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

        // 2. TABBED PANE
        JTabbedPane tabbedPane = new JTabbedPane();

        // TAB 1: DVT
        JPanel pnlDVT = new JPanel(new BorderLayout(5, 5));
        pnlDVT.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel pnlInputDVT = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtHeSoQuyDoi = new JTextField(4);
        txtGiaBanDonVi = new JTextField(7);
        chkDonViCoBan = new JCheckBox("Cơ bản");
        btnThemDVT = new JButton("Thêm");
        btnXoaDVT = new JButton("Xóa");

        String[] donViMau = {"", "VIEN", "VI", "HOP", "CHAI", "LO", "TUYP", "GOI", "CAI", "THUNG"};
        cboTenDonVi = new JComboBox<>(donViMau);
        cboTenDonVi.setEditable(true);
        cboTenDonVi.setPreferredSize(new Dimension(80, 22));

        pnlInputDVT.add(new JLabel("Tên ĐV:"));
        pnlInputDVT.add(cboTenDonVi);
        pnlInputDVT.add(new JLabel("Quy đổi:"));
        pnlInputDVT.add(txtHeSoQuyDoi);
        pnlInputDVT.add(new JLabel("Giá bán:"));
        pnlInputDVT.add(txtGiaBanDonVi);
        pnlInputDVT.add(chkDonViCoBan);
        pnlInputDVT.add(btnThemDVT);
        pnlInputDVT.add(btnXoaDVT);

        setupMoneyFormatting(txtGiaBanDonVi);

        modelDVT = new DefaultTableModel(new String[]{"Mã ĐV", "Tên Đơn Vị", "Hệ Số Quy Đổi", "Giá Bán", "Cơ Bản"}, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tableDonViTinh = new JTable(modelDVT);
        tableDonViTinh.setRowHeight(22);
        pnlDVT.add(pnlInputDVT, BorderLayout.NORTH);
        pnlDVT.add(new JScrollPane(tableDonViTinh), BorderLayout.CENTER);
        tabbedPane.addTab("1. Đơn vị tính", pnlDVT);

        // TAB 2: NCC
        JPanel pnlNCC = new JPanel(new BorderLayout(5, 5));
        JPanel pnlTopControlNCC = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        txtTimNCC = new JTextField(12);
        btnTimNCC = new JButton("Tìm");
        txtGiaNhap = new JTextField(8);
        btnThemNCC = new JButton("Thêm xuống DS");

        setupMoneyFormatting(txtGiaNhap);

        pnlTopControlNCC.add(new JLabel("Tìm NCC:"));
        pnlTopControlNCC.add(txtTimNCC);
        pnlTopControlNCC.add(btnTimNCC);
        pnlTopControlNCC.add(new JSeparator(JSeparator.VERTICAL));
        pnlTopControlNCC.add(new JLabel("Giá nhập:"));
        pnlTopControlNCC.add(txtGiaNhap);
        pnlTopControlNCC.add(btnThemNCC);
        pnlNCC.add(pnlTopControlNCC, BorderLayout.NORTH);

        JPanel pnlTablesAreaNCC = new JPanel(new GridLayout(2, 1, 0, 10));
        modelKQTimKiemNCC = new DefaultTableModel(new String[]{"Mã NCC", "Tên NCC", "SĐT", "Địa chỉ"}, 0);
        tableKQTimKiemNCC = new JTable(modelKQTimKiemNCC);
        modelNCCChon = new DefaultTableModel(new String[]{"Mã NCC", "Tên NCC", "Giá Nhập"}, 0);
        tableNCCChon = new JTable(modelNCCChon);

        pnlTablesAreaNCC.add(new JScrollPane(tableKQTimKiemNCC));
        JPanel pnlFooterNCC = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXoaNCC = new JButton("Xóa khỏi DS");
        pnlFooterNCC.add(btnXoaNCC);
        JPanel pnlTable2NCC = new JPanel(new BorderLayout());
        pnlTable2NCC.add(new JScrollPane(tableNCCChon), BorderLayout.CENTER);
        pnlTable2NCC.add(pnlFooterNCC, BorderLayout.SOUTH);
        pnlTablesAreaNCC.add(pnlTable2NCC);
        pnlNCC.add(pnlTablesAreaNCC, BorderLayout.CENTER);
        tabbedPane.addTab("2. Nhà cung cấp", pnlNCC);

        // TAB 3: KM
        JPanel pnlKM = new JPanel(new BorderLayout(5, 5));
        JPanel pnlTopControlKM = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        txtTimKM = new JTextField(15);
        btnTimKM = new JButton("Tìm");
        btnThemKM = new JButton("Thêm xuống DS");
        pnlTopControlKM.add(new JLabel("Tìm KM:"));
        pnlTopControlKM.add(txtTimKM);
        pnlTopControlKM.add(btnTimKM);
        pnlTopControlKM.add(Box.createHorizontalStrut(20));
        pnlTopControlKM.add(btnThemKM);
        pnlKM.add(pnlTopControlKM, BorderLayout.NORTH);

        JPanel pnlTablesAreaKM = new JPanel(new GridLayout(2, 1, 0, 10));
        modelKQTimKiemKM = new DefaultTableModel(new String[]{"Mã KM", "Mô tả", "Giảm (%)", "Bắt đầu", "Kết thúc"}, 0);
        tableKQTimKiemKM = new JTable(modelKQTimKiemKM);
        modelKMChon = new DefaultTableModel(new String[]{"Mã KM", "Mô tả", "Giảm (%)", "SL Min", "SL Max", "Ngày sửa"}, 0);
        tableKMChon = new JTable(modelKMChon);

        pnlTablesAreaKM.add(new JScrollPane(tableKQTimKiemKM));
        JPanel pnlFooterKM = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXoaKM = new JButton("Xóa khỏi DS");
        pnlFooterKM.add(btnXoaKM);
        JPanel pnlTable2KM = new JPanel(new BorderLayout());
        pnlTable2KM.add(new JScrollPane(tableKMChon), BorderLayout.CENTER);
        pnlTable2KM.add(pnlFooterKM, BorderLayout.SOUTH);
        pnlTablesAreaKM.add(pnlTable2KM);
        pnlKM.add(pnlTablesAreaKM, BorderLayout.CENTER);
        tabbedPane.addTab("3. Khuyến mãi", pnlKM);

        tabbedPane.setPreferredSize(new Dimension(100, 380));

        // Footer Buttons
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnHuy = new JButton("Hủy bỏ");
        btnXacNhan = new JButton("Lưu sản phẩm");
        btnXacNhan.setBackground(new Color(0, 153, 51));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setPreferredSize(new Dimension(120, 35));
        pnlButton.add(btnHuy);
        pnlButton.add(btnXacNhan);

        mainContentPanel.add(pnlInfo, BorderLayout.NORTH);
        mainContentPanel.add(tabbedPane, BorderLayout.CENTER);

        this.add(new JScrollPane(mainContentPanel), BorderLayout.CENTER);
        this.add(pnlButton, BorderLayout.SOUTH);
    }

    private void initEvents() {
        // Nút Lưu
        btnXacNhan.addActionListener(e -> {
            boolean success = bus.luuSanPham(this, isEditMode);
            if (success) {
                this.dispose();
            }
        });

        // Nút Hủy
        btnHuy.addActionListener(e -> this.dispose());

        // --- Barcode ---
        btnThemBarcode.addActionListener(e -> {
            String code = txtInputBarcode.getText().trim();
            if (code.isEmpty()) {
                return;
            }
            for (int i = 0; i < modelBarcode.getRowCount(); i++) {
                if (modelBarcode.getValueAt(i, 0).equals(code)) {
                    JOptionPane.showMessageDialog(this, "Mã vạch đã tồn tại!");
                    return;
                }
            }
            modelBarcode.addRow(new Object[]{code});
            txtInputBarcode.setText("");
            txtInputBarcode.requestFocus();
        });

        btnXoaBarcode.addActionListener(e -> {
            int row = tableBarcode.getSelectedRow();
            if (row >= 0) {
                modelBarcode.removeRow(row);
            }
        });

        // --- DVT ---
        chkDonViCoBan.addActionListener(e -> {
            if (chkDonViCoBan.isSelected()) {
                txtHeSoQuyDoi.setText("1");
                txtHeSoQuyDoi.setEnabled(false);
            } else {
                txtHeSoQuyDoi.setEnabled(true);
                txtHeSoQuyDoi.setText("");
            }
        });
        btnThemDVT.addActionListener(e -> bus.xuLyThemDVT(this));
        btnXoaDVT.addActionListener(e -> bus.xuLyXoaDVT(this));

        // --- NCC ---
        btnTimNCC.addActionListener(e -> bus.xuLyTimNCC(this));
        btnThemNCC.addActionListener(e -> bus.xuLyThemNCC(this));
        btnXoaNCC.addActionListener(e -> bus.xuLyXoaNCC(this));

        // --- KM ---
        btnTimKM.addActionListener(e -> bus.xuLyTimKM(this));
        btnThemKM.addActionListener(e -> bus.xuLyThemKM(this));
        btnXoaKM.addActionListener(e -> bus.xuLyXoaKM(this));
    }

    private void setupMoneyFormatting(JTextField txtField) {
        txtField.setHorizontalAlignment(JTextField.RIGHT);
        txtField.getDocument().addDocumentListener(new DocumentListener() {
            private boolean formatting = false;

            @Override
            public void insertUpdate(DocumentEvent e) {
                format();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                format();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void format() {
                if (formatting) {
                    return;
                }
                try {
                    String raw = txtField.getText().replaceAll("[^\\d]", "");
                    if (raw.isEmpty()) {
                        return;
                    }
                    formatting = true;
                    long val = Long.parseLong(raw);
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                    symbols.setGroupingSeparator(' ');
                    DecimalFormat fmt = new DecimalFormat("#,##0", symbols);
                    String formatted = fmt.format(val);

                    SwingUtilities.invokeLater(() -> {
                        txtField.setText(formatted);
                        formatting = false;
                    });
                } catch (Exception ex) {
                    formatting = false;
                }
            }
        });
    }

    // GETTERS
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

    public DefaultTableModel getModelBarcode() {
        return modelBarcode;
    }

    public JComboBox<String> getCboTenDonVi() {
        return cboTenDonVi;
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

    public DefaultTableModel getModelDVT() {
        return modelDVT;
    }

    public JTable getTblDonViTinh() {
        return tableDonViTinh;
    }

    public JTextField getTxtTimNCC() {
        return txtTimNCC;
    }

    public DefaultTableModel getModelTimKiemNCC() {
        return modelKQTimKiemNCC;
    }

    public JTable getTableKQTimKiemNCC() {
        return tableKQTimKiemNCC;
    }

    public JTextField getTxtGiaNhap() {
        return txtGiaNhap;
    }

    public DefaultTableModel getModelNCCChon() {
        return modelNCCChon;
    }

    public JTable getTblNCCChon() {
        return tableNCCChon;
    }

    public JTextField getTxtTimKM() {
        return txtTimKM;
    }

    public DefaultTableModel getModelKQTimKiemKM() {
        return modelKQTimKiemKM;
    }

    public JTable getTblTimKiemKM() {
        return tableKQTimKiemKM;
    }

    public DefaultTableModel getModelKMChon() {
        return modelKMChon;
    }

    public JTable getTblKMChon() {
        return tableKMChon;
    }
}
