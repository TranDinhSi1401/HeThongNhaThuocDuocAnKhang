/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import com.toedter.calendar.JDateChooser;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import hethongnhathuocduocankhang.util.PasswordUtil;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ThemNhanVienGUI extends javax.swing.JPanel {

    private NhanVien nhanVienMoi = null;
    private TaiKhoan taiKhoanMoi = null;

    public ThemNhanVienGUI() {
        initComponents();
        setupButton(btnHuy, new java.awt.Color(255, 51, 51));      // Nút Hủy (Đỏ)
        setupButton(btnXacNhan, new java.awt.Color(0, 203, 0));    // Nút Xác nhận (Xanh lá)

        // CÁC SỰ KIỆN NÚT BẤM
        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXacNhan();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyHuy();
            }
        });

        btnHienMatKhau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnHienMatKhau.isSelected()) {
                    txtMatKhau.setEchoChar((char) 0); // Hiển thị
                    btnHienMatKhau.setText("Ẩn");
                } else {
                    txtMatKhau.setEchoChar('*'); // Ẩn
                    btnHienMatKhau.setText("👁");
                }
            }
        });

        // KIỂM TRA DỮ LIỆU
        txtHoTenDem.addActionListener(e -> kiemTraHoTenDem());
        txtTen.addActionListener(e -> kiemTraTen());
        txtSdt.addActionListener(e -> kiemTraSDT());
        txtEmail.addActionListener(e -> kiemTraEmail());
        cmbGioiTinh.addActionListener(e -> kiemTraGioiTinh());
        txtCCCD.addActionListener(e -> kiemTraCCCD());

        txtDiaChi.addActionListener(e -> kiemTraDiaChi());
        txtTenDangNhap.addActionListener(e -> kiemTraTenDangNhap());
        txtMatKhau.addActionListener(e -> kiemTraMatKhau());

        chkQuanLy.addActionListener(e -> {
            if (chkQuanLy.isSelected()) {
                chkQuanLyLo.setSelected(false);
                chkQuanLyLo.setEnabled(false);
            } else {
                chkQuanLyLo.setEnabled(true);
            }
        });
    }

    //Hàm khởi tạo giao diện
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTieuDe = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblMaNV = new javax.swing.JLabel();
        lblHoTenDem = new javax.swing.JLabel();
        lblTen = new javax.swing.JLabel();
        lblSdt = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        lblCCCD = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtHoTenDem = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        txtCCCD = new javax.swing.JTextField();

        chonLichNgaySinh = new JDateChooser();
        chonLichNgaySinh.setDateFormatString("yyyy-MM-dd");

        btnHuy = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        cmbGioiTinh = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        lblMatKhau = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        btnHienMatKhau = new javax.swing.JToggleButton();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblTenDangNhap = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        chkQuanLy = new javax.swing.JCheckBox();

        // --- THAY ĐỔI: Khởi tạo check box Quản Lý Lô ---
        chkQuanLyLo = new javax.swing.JCheckBox();
        // -----------------------------------------------

        lblNgayTao = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTieuDe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTieuDe.setText("Thông tin nhân viên");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTieuDe)
                                .addContainerGap(233, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTieuDe)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblMaNV.setText("Mã nhân viên:");
        lblHoTenDem.setText("Họ tên đệm:");
        lblTen.setText("Tên:");
        lblSdt.setText("SĐT:");
        lblGioiTinh.setText("Giới tính:");
        lblCCCD.setText("CCCD:");
        lblNgaySinh.setText("Ngày sinh:");

        txtMaNV.setEnabled(false);

        btnHuy.setText("Hủy");
        btnXacNhan.setText("Xác nhận");

        cmbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Nam", "Nữ"}));

        jLabel9.setText("Địa chỉ:");
        lblMatKhau.setText("Mật khẩu:");
        btnHienMatKhau.setText("👁");
        btnHienMatKhau.setToolTipText("Hiện/Ẩn mật khẩu");
        btnHienMatKhau.setFocusable(false);
        btnHienMatKhau.setMargin(new java.awt.Insets(2, 2, 2, 2));

        lblEmail.setText("Email:");
        lblTenDangNhap.setText("Tên đăng nhập:");
        txtTenDangNhap.setEnabled(false);

        chkQuanLy.setText("Là Quản lý (Admin)");

        // --- THAY ĐỔI: Set text cho checkbox mới ---
        chkQuanLyLo.setText("Quản lý lô (Kho)");
        // ------------------------------------------

        lblNgayTao.setText("Ngày tạo:");
        txtNgayTao.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnHuy)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnXacNhan))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblCCCD)
                                                        .addComponent(lblNgaySinh)
                                                        .addComponent(jLabel9)
                                                        .addComponent(lblTen)
                                                        .addComponent(lblSdt)
                                                        .addComponent(lblGioiTinh)
                                                        .addComponent(lblHoTenDem)
                                                        .addComponent(lblMaNV)
                                                        .addComponent(lblMatKhau)
                                                        .addComponent(lblEmail)
                                                        .addComponent(lblTenDangNhap)
                                                        .addComponent(lblNgayTao))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtMaNV)
                                                        .addComponent(txtHoTenDem)
                                                        .addComponent(txtTen)
                                                        .addComponent(txtSdt)
                                                        .addComponent(cmbGioiTinh, 0, 268, Short.MAX_VALUE)
                                                        .addComponent(txtCCCD)
                                                        .addComponent(chonLichNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txtDiaChi)
                                                        .addComponent(txtEmail)
                                                        .addComponent(txtTenDangNhap)
                                                        .addComponent(txtNgayTao)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(txtMatKhau)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnHienMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(chkQuanLy)
                                                                .addGap(18, 18, 18)
                                                                // --- THAY ĐỔI: Thêm checkbox vào layout ---
                                                                .addComponent(chkQuanLyLo)
                                                                // ------------------------------------------
                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMaNV)
                                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblHoTenDem)
                                        .addComponent(txtHoTenDem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTen)
                                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblSdt)
                                        .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblEmail)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblGioiTinh)
                                        .addComponent(cmbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCCCD)
                                        .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(lblNgaySinh)
                                        .addComponent(chonLichNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTenDangNhap)
                                        .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMatKhau)
                                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnHienMatKhau))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNgayTao)
                                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(chkQuanLy)
                                        // --- THAY ĐỔI: Thêm checkbox vào layout vertical ---
                                        .addComponent(chkQuanLyLo))
                                // --------------------------------------------------
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnHuy)
                                        .addComponent(btnXacNhan))
                                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>                        

    //Setter and Getter
    public javax.swing.JButton getBtnHuy() {
        return btnHuy;
    }

    public javax.swing.JButton getBtnXacNhan() {
        return btnXacNhan;
    }

    public javax.swing.JLabel getLblTieuDe() {
        return lblTieuDe;
    }

    public javax.swing.JTextField getTxtMaNhanVien() {
        return txtMaNV;
    }

    public javax.swing.JTextField getTxtHoTenDem() {
        return txtHoTenDem;
    }

    public javax.swing.JTextField getTxtTen() {
        return txtTen;
    }

    public javax.swing.JTextField getTxtSDT() {
        return txtSdt;
    }

    public javax.swing.JComboBox<String> getCmbGioiTinh() {
        return cmbGioiTinh;
    }

    public javax.swing.JTextField getTxtCCCD() {
        return txtCCCD;
    }

    public JDateChooser getChonLichNgaySinh() {
        return chonLichNgaySinh;
    }

    public javax.swing.JTextField getTxtDiaChi() {
        return txtDiaChi;
    }

    public javax.swing.JCheckBox getChkNghiViec() {
        return chkNghiViec;
    }

    public NhanVien getNhanVienMoi() {
        return this.nhanVienMoi;
    }

    public TaiKhoan getTaiKhoanMoi() {
        return this.taiKhoanMoi;
    }

    public JTextField getTxtTenDangNhap() {
        return txtTenDangNhap;
    }

    public JPasswordField getTxtMatKhau() {
        return txtMatKhau;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtSdt() {
        return txtSdt;
    }

    public JTextField getTxtMaNV() {
        return txtMaNV;
    }

    public JCheckBox getChkQuanLy() {
        return chkQuanLy;
    }

    public JCheckBox getChkQuanLyLo() {
        return chkQuanLyLo;
    }
    // -------------------------------------

    public JCheckBox getChkBiKhoa() {
        return chkBiKhoa;
    }

    public JTextField getTxtNgayTao() {
        return txtNgayTao;
    }

    public void setTxtMaNhanVien(String maNV) {
        txtMaNV.setText(maNV);
    }

    public void setTxtHoTenDem(String hoTenDem) {
        txtHoTenDem.setText(hoTenDem);
    }

    public void setTxtTen(String ten) {
        txtTen.setText(ten);
    }

    public void setTxtSDT(String sdt) {
        txtSdt.setText(sdt);
    }

    public void setTxtCCCD(String cccd) {
        txtCCCD.setText(cccd);
    }

    public void setCmbGioiTinh(boolean gioiTinh) {
        // true: Nam, false: Nữ
        cmbGioiTinh.setSelectedItem(gioiTinh ? "Nam" : "Nữ");
    }

    public void setTxtNgaySinh(LocalDate ngaySinh) {
        if (ngaySinh != null) {
            Date date = Date.from(ngaySinh.atStartOfDay(ZoneId.systemDefault()).toInstant());
            chonLichNgaySinh.setDate(date);
        } else {
            chonLichNgaySinh.setDate(null);
        }
    }

    public void setTxtDiaChi(String diaChi) {
        txtDiaChi.setText(diaChi);
    }

    public void setChkNghiViec(boolean nghiViec) {
        chkNghiViec.setSelected(nghiViec);
    }

    public void setTaiKhoanMoi(TaiKhoan taiKhoanMoi) {
        this.taiKhoanMoi = taiKhoanMoi;
    }

    public void setChkBiKhoa(boolean biKhoa) {
        this.chkBiKhoa.setSelected(biKhoa);
    }

    public void setChkQuanLy(boolean quanLy) {
        this.chkQuanLy.setSelected(quanLy);

        if (quanLy) {
            chkQuanLyLo.setSelected(false);
            chkQuanLyLo.setEnabled(false);
        } else {
            chkQuanLyLo.setEnabled(true);
        }
    }

    public void setChkQuanLyLo(boolean quanLyLo) {
        this.chkQuanLyLo.setSelected(quanLyLo);
    }
    // -------------------------------------

    public void setTxtMaNV(String txtMaNV) {
        this.txtMaNV.setText(txtMaNV);
    }

    public void setTxtSdt(String txtSdt) {
        this.txtSdt.setText(txtSdt);
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail.setText(txtEmail);
    }

    public void setTxtMatKhau(String txtMatKhau) {
        this.txtMatKhau.setText(txtMatKhau);
    }

    public void setTxtTenDangNhap(String txtTenDangNhap) {
        this.txtTenDangNhap.setText(txtTenDangNhap);
    }

    public void setTxtNgayTao(LocalDateTime ngayTao) {
        if (ngayTao != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            txtNgayTao.setText(ngayTao.format(formatter));
        } else {
            txtNgayTao.setText("");
        }
    }

    public void setTxtNgayTao(String ngayTao) {
        this.txtNgayTao.setText(ngayTao);
    }

    private void showError(String message, JComponent component) {
        JOptionPane.showMessageDialog(this, message, "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        if (component != null) {
            component.requestFocusInWindow();
        }
    }

    private void xuLyHuy() {
        this.nhanVienMoi = null;
        closeDialog();
    }

    private void xuLyXacNhan() {
        if (!kiemTraHoTenDem() || !kiemTraTen() || !kiemTraSDT() || !kiemTraEmail() || !kiemTraGioiTinh()
                || !kiemTraCCCD() || !kiemTraNgaySinh() || !kiemTraDiaChi()
                || !kiemTraTenDangNhap() || !kiemTraMatKhau()) {
            return;
        }

        try {
            String maNV = txtMaNV.getText().trim();
            String hoTenDem = txtHoTenDem.getText().trim();
            String ten = txtTen.getText().trim();
            String sdt = txtSdt.getText().trim();
            String email = txtEmail.getText().trim();
            boolean gioiTinh = cmbGioiTinh.getSelectedItem().toString().equals("Nam");
            String cccd = txtCCCD.getText().trim();

            Date date = chonLichNgaySinh.getDate();
            LocalDate ngaySinh = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String diaChi = txtDiaChi.getText().trim();
            String tenDangNhap = txtTenDangNhap.getText().trim();
            String plainPassword = new String(txtMatKhau.getPassword());
            String matKhau = PasswordUtil.hashPassword(plainPassword);

            boolean quanLy = chkQuanLy.isSelected();
            // --- THAY ĐỔI: Lấy giá trị checkbox Quản lý lô ---
            boolean quanLyLo = chkQuanLyLo.isSelected();
            // -------------------------------------------------

            LocalDateTime ngayTao = LocalDateTime.now();

            this.nhanVienMoi = new NhanVien();
            this.taiKhoanMoi = new TaiKhoan();

            this.nhanVienMoi.setMaNV(maNV);
            this.nhanVienMoi.setHoTenDem(hoTenDem);
            this.nhanVienMoi.setTen(ten);
            this.nhanVienMoi.setSdt(sdt);
            this.nhanVienMoi.setCccd(cccd);
            this.nhanVienMoi.setGioiTinh(gioiTinh);
            this.nhanVienMoi.setNgaySinh(ngaySinh);
            this.nhanVienMoi.setDiaChi(diaChi);

            this.taiKhoanMoi.setNhanVien(nhanVienMoi);
            this.taiKhoanMoi.setMatKhau(matKhau);
            this.taiKhoanMoi.setQuanLy(quanLy);
            this.taiKhoanMoi.setQuanLyLo(quanLyLo);
            // --------------------------------------------------------------
            this.taiKhoanMoi.setEmail(email);
            this.taiKhoanMoi.setNgayTao(ngayTao);

            closeDialog();

        } catch (Exception ex) {
            this.nhanVienMoi = null;
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeDialog() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    // --- CÁC HÀM KIỂM TRA ---
    private boolean kiemTraHoTenDem() {
        String s = txtHoTenDem.getText().trim();
        if (s.isEmpty()) {
            showError("Họ tên đệm không được rỗng.", txtHoTenDem);
            return false;
        }
        if (s.length() > 50) {
            showError("Họ tên đệm không quá 50 ký tự.", txtHoTenDem);
            return false;
        }
        return true;
    }

    private boolean kiemTraTen() {
        String s = txtTen.getText().trim();
        if (s.isEmpty()) {
            showError("Tên không được rỗng.", txtTen);
            return false;
        }
        if (s.length() > 20) {
            showError("Tên không quá 20 ký tự.", txtTen);
            return false;
        }
        return true;
    }

    private boolean kiemTraSDT() {
        String s = txtSdt.getText().trim();
        if (!s.matches("^0\\d{9}$")) {
            showError("SĐT không hợp lệ (10 số, bắt đầu bằng 0).", txtSdt);
            return false;
        }
        return true;
    }

    private boolean kiemTraEmail() {
        String s = txtEmail.getText().trim();
        if (s.isEmpty()) {
            showError("Email không được rỗng.", txtEmail);
            return false;
        }
        // Regex email cơ bản
        if (!s.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("Email không đúng định dạng.", txtEmail);
            return false;
        }
        return true;
    }

    private boolean kiemTraGioiTinh() {
        if (cmbGioiTinh.getSelectedItem() == null) {
            showError("Phải chọn giới tính.", cmbGioiTinh);
            return false;
        }
        return true;
    }

    private boolean kiemTraCCCD() {
        String s = txtCCCD.getText().trim();
        if (!s.matches("^\\d{12}$")) {
            showError("CCCD phải gồm 12 chữ số.", txtCCCD);
            return false;
        }
        return true;
    }

    private boolean kiemTraNgaySinh() {
        if (chonLichNgaySinh.getDate() == null) {
            showError("Ngày sinh không được rỗng.", chonLichNgaySinh);
            return false;
        }

        Date date = chonLichNgaySinh.getDate();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Kiểm tra logic
        if (localDate.isAfter(LocalDate.now())) {
            showError("Ngày sinh phải trước hiện tại.", chonLichNgaySinh);
            return false;
        }

        int tuoi = LocalDate.now().getYear() - localDate.getYear();
        if (tuoi < 18) {
            showError("Nhân viên phải đủ 18 tuổi.", chonLichNgaySinh);
            return false;
        }
        return true;
    }

    private boolean kiemTraDiaChi() {
        if (txtDiaChi.getText().trim().length() > 255) {
            showError("Địa chỉ quá dài.", txtDiaChi);
            return false;
        }
        return true;
    }

    private boolean kiemTraTenDangNhap() {
        String s = txtTenDangNhap.getText().trim();
        if (s.isEmpty()) {
            showError("Tên đăng nhập không được rỗng.", txtTenDangNhap);
            return false;
        }
        if (s.contains(" ")) {
            showError("Tên đăng nhập không được chứa khoảng trắng.", txtTenDangNhap);
            return false;
        }
        return true;
    }

    private boolean kiemTraMatKhau() {
        String s = new String(txtMatKhau.getPassword());
        if (s.isEmpty()) {
            showError("Mật khẩu không được rỗng.", txtMatKhau);
            return false;
        }
        if (s.length() < 6) {
            showError("Mật khẩu phải từ 6 ký tự trở lên.", txtMatKhau);
            return false;
        }
        return true;
    }

    private void setupButton(javax.swing.JButton button, java.awt.Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(java.awt.Color.WHITE);
        button.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false); // nút phẳng hoàn toàn
        button.setOpaque(true);
    }

    private javax.swing.JToggleButton btnHienMatKhau;
    private javax.swing.JCheckBox chkBiKhoa;
    private javax.swing.JCheckBox chkQuanLy;
    private javax.swing.JCheckBox chkQuanLyLo;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JCheckBox chkNghiViec;
    private javax.swing.JComboBox<String> cmbGioiTinh;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblHoTenDem;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblSdt;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblCCCD;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtHoTenDem;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtCCCD;
    private JDateChooser chonLichNgaySinh;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblTenDangNhap;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTenDangNhap;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JTextField txtNgayTao;
}
