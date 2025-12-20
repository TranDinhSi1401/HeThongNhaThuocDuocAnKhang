package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.dao.*;
import hethongnhathuocduocankhang.entity.*;
import hethongnhathuocduocankhang.gui.QuanLiSanPhamGUI;
import hethongnhathuocduocankhang.gui.ThemSanPhamGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SanPhamBUS {

    // Danh sách tạm
    public List<DonViTinh> listTempDVT = new ArrayList<>();
    public List<SanPhamCungCap> listTempSPCC = new ArrayList<>();
    public List<KhuyenMai> listTempKM = new ArrayList<>();

    public SanPhamBUS() {

    }

    // LOGIC PHỤC VỤ QuanLiSanPhamGUI (Màn hình chính)
    public void loadDataToTable(QuanLiSanPhamGUI view, ArrayList<SanPham> dsSP) {
        DefaultTableModel model = view.getModel();
        model.setRowCount(0);

        if (dsSP == null) {
            dsSP = SanPhamDAO.getAllTableSanPham();
        }

        if (dsSP.isEmpty()) {
            view.getLblTongSoDong().setText("Tổng số sản phẩm: 0");
            return;
        }

        int stt = 1;
        for (SanPham sp : dsSP) {
            model.addRow(new Object[]{
                stt++,
                sp.getMaSP(),
                sp.getTen(),
                sp.getMoTa(),
                sp.getThanhPhan(),
                sp.getLoaiSanPham().toString(),
                sp.getTonToiThieu(),
                sp.getTonToiDa(),
                sp.isDaXoa() ? "Ngừng bán" : "Đang bán"
            });
        }
        view.getLblTongSoDong().setText("Tổng số sản phẩm: " + dsSP.size());
    }

    public void xuLyXoaSanPham(QuanLiSanPhamGUI view) {
        int[] selectedRows = view.getTable().getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm cần xóa.");
            return;
        }

        if (JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa các sản phẩm đã chọn?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int count = 0;
            for (int row : selectedRows) {
                String maSP = view.getModel().getValueAt(row, 1).toString();
                if (SanPhamDAO.xoaSanPham(maSP)) {
                    count++;
                }
            }
            if (count > 0) {
                JOptionPane.showMessageDialog(view, "Đã xóa thành công " + count + " sản phẩm.");
                loadDataToTable(view, null); // Reload lại bảng
                view.getLblSoDongChon().setText("Đang chọn: 0");
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void xuLyTimKiem(QuanLiSanPhamGUI view) {
        String tuKhoa = view.getTxtTimKiem().getText().trim();
        String tieuChi = view.getCmbTieuChiTimKiem().getSelectedItem().toString();
        ArrayList<SanPham> dsKetQua = new ArrayList<>();

        if (tuKhoa.isEmpty()) {
            dsKetQua = SanPhamDAO.getAllTableSanPham();
        } else {
            switch (tieuChi) {
                case "Mã sản phẩm":
                    SanPham sp = SanPhamDAO.timSPTheoMa(tuKhoa);
                    if (sp != null) {
                        dsKetQua.add(sp);
                    }
                    break;
                case "Tên sản phẩm":
                    dsKetQua = SanPhamDAO.timSPTheoTen(tuKhoa);
                    break;
                case "Mã nhà cung cấp":
                    dsKetQua = SanPhamDAO.timSPTheoMaNCC(tuKhoa);
                    break;
            }
        }
        loadDataToTable(view, dsKetQua);
    }

    public void xuLyLoc(QuanLiSanPhamGUI view) {
        String boLoc = view.getCmbBoLoc().getSelectedItem().toString();
        String locTheo = "";

        if (boLoc.equals("Thuốc kê đơn")) {
            locTheo = "THUOC_KE_DON";
        } else if (boLoc.equals("Thuốc không kê đơn")) {
            locTheo = "THUOC_KHONG_KE_DON";
        } else if (boLoc.equals("Thực phẩm chức năng")) {
            locTheo = "THUC_PHAM_CHUC_NANG";
        }

        if (boLoc.equals("Tất cả")) {
            loadDataToTable(view, SanPhamDAO.getAllTableSanPham());
        } else {
            loadDataToTable(view, SanPhamDAO.timSPTheoLoai(locTheo));
        }
    }

    // LOGIC PHỤC VỤ ThemSanPhamGUI (Màn hình con - Dialog)
    public void chuanBiFormThem(ThemSanPhamGUI form) {
        listTempDVT.clear();
        listTempSPCC.clear();
        listTempKM.clear();

        int maSPCuoi = SanPhamDAO.getMaSPCuoiCung();
        String maSPNew = String.format("SP-%04d", maSPCuoi + 1);
        form.getTxtMaSanPham().setText(maSPNew);
        form.getTxtMaSanPham().setEditable(false);
    }

    public void chuanBiFormSua(ThemSanPhamGUI form, String maSP) {
        SanPham sp = SanPhamDAO.timSPTheoMa(maSP);
        if (sp == null) {
            return;
        }

        // Load thông tin cơ bản
        form.getTxtMaSanPham().setText(sp.getMaSP());
        form.getTxtMaSanPham().setEditable(false);
        form.getTxtTenSanPham().setText(sp.getTen());
        form.getTxtMoTa().setText(sp.getMoTa());
        form.getTxtThanhPhan().setText(sp.getThanhPhan());
        form.getTxtTonToiThieu().setText(String.valueOf(sp.getTonToiThieu()));
        form.getTxtTonToiDa().setText(String.valueOf(sp.getTonToiDa()));

        LoaiSanPhamEnum loai = sp.getLoaiSanPham();
        if (loai == LoaiSanPhamEnum.THUOC_KE_DON) {
            form.getCmbLoaiSanPham().setSelectedIndex(0);
        } else if (loai == LoaiSanPhamEnum.THUOC_KHONG_KE_DON) {
            form.getCmbLoaiSanPham().setSelectedIndex(1);
        } else {
            form.getCmbLoaiSanPham().setSelectedIndex(2);
        }

        // Load Barcode
        ArrayList<String> barcodes = MaVachSanPhamDAO.getDsMaVachTheoMaSP(sp.getMaSP());
        for (String code : barcodes) {
            form.getModelBarcode().addRow(new Object[]{code});
        }

        // Load DVT
        listTempDVT = DonViTinhDAO.getDonViTinhTheoMaSP(sp.getMaSP());
        for (DonViTinh dvt : listTempDVT) {
            form.getModelDVT().addRow(new Object[]{
                dvt.getMaDonViTinh(), dvt.getTenDonVi(), dvt.getHeSoQuyDoi(),
                String.format("%,.0f", dvt.getGiaBanTheoDonVi()),
                dvt.isDonViTinhCoBan() ? "Có" : "Không"
            });
        }

        // Load NCC
        listTempSPCC = SanPhamCungCapDAO.getSanPhamCungCapTheoMaSP(sp.getMaSP());
        for (SanPhamCungCap spcc : listTempSPCC) {
            form.getModelNCCChon().addRow(new Object[]{
                spcc.getNhaCungCap().getMaNCC(),
                spcc.getNhaCungCap().getTenNCC(),
                String.format("%,.0f", spcc.getGiaNhap())
            });
        }

        // Load KM
        listTempKM = KhuyenMaiDAO.getKhuyenMaiTheoMaSP(sp.getMaSP());
        for (KhuyenMai km : listTempKM) {
            form.getModelKMChon().addRow(new Object[]{
                km.getMaKhuyenMai(), km.getMoTa(), km.getPhanTram() + "%",
                km.getSoLuongToiThieu(), km.getSoLuongToiDa(), ""
            });
        }
    }

    public boolean luuSanPham(ThemSanPhamGUI form, boolean isEditMode) {
        if (form.getTxtTenSanPham().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(form, "Tên sản phẩm không được để trống.");
            return false;
        }
        if (listTempDVT.isEmpty()) {
            JOptionPane.showMessageDialog(form, "Sản phẩm phải có ít nhất 1 đơn vị tính.");
            return false;
        }

        try {
            SanPham sp = new SanPham();
            sp.setMaSP(form.getTxtMaSanPham().getText());
            sp.setTen(form.getTxtTenSanPham().getText());
            sp.setMoTa(form.getTxtMoTa().getText());
            sp.setThanhPhan(form.getTxtThanhPhan().getText());

            int indexLoai = form.getCmbLoaiSanPham().getSelectedIndex();
            if (indexLoai == 0) {
                sp.setLoaiSanPham(LoaiSanPhamEnum.THUOC_KE_DON);
            } else if (indexLoai == 1) {
                sp.setLoaiSanPham(LoaiSanPhamEnum.THUOC_KHONG_KE_DON);
            } else {
                sp.setLoaiSanPham(LoaiSanPhamEnum.THUC_PHAM_CHUC_NANG);
            }

            try {
                sp.setTonToiThieu(Integer.parseInt(form.getTxtTonToiThieu().getText().trim()));
                sp.setTonToiDa(Integer.parseInt(form.getTxtTonToiDa().getText().trim()));
            } catch (Exception e) {
                sp.setTonToiThieu(0);
                sp.setTonToiDa(9999);
            }

            boolean success = false;

            if (!isEditMode) { // Thêm mới
                if (SanPhamDAO.themSanPham(sp)) {
                    luuCacThongTinLienQuan(sp, form);
                    success = true;
                    JOptionPane.showMessageDialog(form, "Thêm sản phẩm thành công!");
                }
            } else { // Sửa
                if (SanPhamDAO.suaSanPham(sp.getMaSP(), sp)) {
                    String maSP = sp.getMaSP();
                    MaVachSanPhamDAO.xoaMaVachTheoMaSP(maSP);
                    SanPhamCungCapDAO.xoaHetNCCuaSP(maSP);
                    KhuyenMaiSanPhamDAO.xoaHetKMCuaSP(maSP);
                    luuCacThongTinLienQuan(sp, form);
                    success = true;
                    JOptionPane.showMessageDialog(form, "Cập nhật sản phẩm thành công!");
                }
            }
            return success;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(form, "Lỗi khi lưu: " + e.getMessage());
            return false;
        }
    }

    private void luuCacThongTinLienQuan(SanPham sp, ThemSanPhamGUI form) {
        List<DonViTinh> listOldDVT = DonViTinhDAO.getDonViTinhTheoMaSP(sp.getMaSP());
        for (DonViTinh oldDVT : listOldDVT) {
            boolean conTonTai = false;
            for (DonViTinh newDVT : listTempDVT) {
                if (newDVT.getMaDonViTinh().equals(oldDVT.getMaDonViTinh())) {
                    conTonTai = true;
                    break;
                }
            }
            if (!conTonTai) {
                try {
                    DonViTinhDAO.xoaDonViTinh(oldDVT.getMaDonViTinh());
                } catch (Exception e) {
                    System.err.println("Không thể xóa DVT (có thể do ràng buộc FK): " + e.getMessage());
                }
            }
        }

        for (DonViTinh dvt : listTempDVT) {
            dvt.setSanPham(sp);
            if (DonViTinhDAO.getDonViTinhTheoMaDVT(dvt.getMaDonViTinh()) != null) {
                DonViTinhDAO.suaDonViTinh(dvt.getMaDonViTinh(), dvt);
            } else {
                DonViTinhDAO.themDonViTinh(dvt);
            }
        }

        // Lưu Barcode
        for (int i = 0; i < form.getModelBarcode().getRowCount(); i++) {
            String code = form.getModelBarcode().getValueAt(i, 0).toString();
            MaVachSanPham mv = new MaVachSanPham(sp, code);
            MaVachSanPhamDAO.themMaVach(mv);
        }

        // Lưu NCC
        for (SanPhamCungCap spcc : listTempSPCC) {
            try {
                spcc.setSanPham(sp);
                SanPhamCungCapDAO.themSanPhamCungCap(spcc);
            } catch (Exception e) {
            }
        }

        // Lưu KM
        for (KhuyenMai km : listTempKM) {
            KhuyenMaiSanPham kmsp = new KhuyenMaiSanPham();
            kmsp.setSanPham(sp);
            kmsp.setKhuyenMai(km);
            kmsp.setNgayChinhSua(LocalDate.now());
            try {
                KhuyenMaiSanPhamDAO.themKhuyenMaiSanPham(kmsp);
            } catch (Exception e) {
            }
        }
    }

    // CÁC HÀM LOGIC NHỎ TRÊN FORM
    public void xuLyThemDVT(ThemSanPhamGUI form) {
        try {
            Object itemObj = form.getCboTenDonVi().getEditor().getItem();
            String rawTenDV = (itemObj != null) ? itemObj.toString() : "";
            String tenDV = rawTenDV.trim().toUpperCase();

            if (tenDV.isEmpty()) {
                throw new Exception("Vui lòng nhập tên ĐVT!");
            }

            String strHeSo = form.getTxtHeSoQuyDoi().getText().trim();
            if (strHeSo.isEmpty()) {
                throw new Exception("Chưa nhập hệ số!");
            }
            int heSo = Integer.parseInt(strHeSo);

            String giaBanRaw = form.getTxtGiaBanDonVi().getText().replaceAll("\\D", "");
            double gia = giaBanRaw.isEmpty() ? 0.0 : Double.parseDouble(giaBanRaw);

            boolean isCoBan = form.getChkDonViCoBan().isSelected();
            if (isCoBan) {
                for (DonViTinh dv : listTempDVT) {
                    if (dv.isDonViTinhCoBan()) {
                        JOptionPane.showMessageDialog(form, "Đã tồn tại đơn vị tính cơ bản (" + dv.getTenDonVi() + ").");
                        return;
                    }
                }
            }

            String maSP = form.getTxtMaSanPham().getText();
            // Tạo mã giả định
            if (maSP.isEmpty() || !maSP.contains("-")) {
                maSP = "SP-0000";
            }

            String maDVT = "DVT-" + maSP.split("-")[1] + "-" + tenDV;

            DonViTinh dvt = new DonViTinh(maDVT, new SanPham(maSP), heSo, gia, tenDV, isCoBan);
            listTempDVT.add(dvt);

            form.getModelDVT().addRow(new Object[]{
                maDVT, tenDV, heSo, String.format("%,.0f", gia), isCoBan ? "Có" : "Không"
            });

            form.getCboTenDonVi().setSelectedItem("");
            form.getTxtGiaBanDonVi().setText("");
            form.getChkDonViCoBan().setSelected(false);
            form.getTxtHeSoQuyDoi().setEnabled(true);
            form.getTxtHeSoQuyDoi().setText("");
            form.getCboTenDonVi().requestFocus();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Lỗi thêm ĐVT: " + ex.getMessage());
        }
    }

    public void xuLyXoaDVT(ThemSanPhamGUI form) {
        int[] cacDongDaChon = form.getTblDonViTinh().getSelectedRows();
        if (cacDongDaChon.length > 0) {
            for (int i = cacDongDaChon.length - 1; i >= 0; i--) {
                int dongDangChon = cacDongDaChon[i];
                if (dongDangChon < listTempDVT.size()) {
                    listTempDVT.remove(dongDangChon);
                }
                form.getModelDVT().removeRow(dongDangChon);
            }
        }
    }

    // Logic NCC: Tìm kiếm, Thêm vào list chọn, Xóa khỏi list chọn
    public void xuLyTimNCC(ThemSanPhamGUI form) {
        String timNCC = form.getTxtTimNCC().getText().trim();
        form.getModelTimKiemNCC().setRowCount(0);
        ArrayList<NhaCungCap> dsNCC = timNCC.isEmpty() ? NhaCungCapDAO.getAllNhaCungCap() : NhaCungCapDAO.timNCCTheoTen(timNCC);
        for (NhaCungCap ncc : dsNCC) {
            form.getModelTimKiemNCC().addRow(new Object[]{ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSdt(), ncc.getDiaChi()});
        }
    }

    public void xuLyThemNCC(ThemSanPhamGUI form) {
        int[] rows = form.getTableKQTimKiemNCC().getSelectedRows();
        if (rows.length == 0) {
            return;
        }
        try {
            String giaNhapRaw = form.getTxtGiaNhap().getText().replaceAll("\\D", "");
            double gia = giaNhapRaw.isEmpty() ? 0.0 : Double.parseDouble(giaNhapRaw);

            for (int r : rows) {
                String maNCC = form.getModelTimKiemNCC().getValueAt(r, 0).toString();
                String tenNCC = form.getModelTimKiemNCC().getValueAt(r, 1).toString();

                boolean tonTai = listTempSPCC.stream().anyMatch(spcc -> spcc.getNhaCungCap().getMaNCC().equals(maNCC));
                if (!tonTai) {
                    NhaCungCap ncc = NhaCungCapDAO.timNCCTheoMa(maNCC);
                    SanPhamCungCap spcc = new SanPhamCungCap(new SanPham(form.getTxtMaSanPham().getText()), ncc, tonTai, gia);
                    listTempSPCC.add(spcc);
                    form.getModelNCCChon().addRow(new Object[]{maNCC, tenNCC, String.format("%,.0f", gia)});
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Giá nhập không hợp lệ.");
        }
    }

    public void xuLyXoaNCC(ThemSanPhamGUI form) {
        int[] rows = form.getTblNCCChon().getSelectedRows();
        if (rows.length == 0) {
            return;
        }
        for (int i = rows.length - 1; i >= 0; i--) {
            int row = rows[i];
            if (row < listTempSPCC.size()) {
                listTempSPCC.remove(row);
            }
            form.getModelNCCChon().removeRow(row);
        }
    }

    // Logic KM: Tìm, Thêm, Xóa
    public void xuLyTimKM(ThemSanPhamGUI form) {
        String timKM = form.getTxtTimKM().getText().trim();
        form.getModelKQTimKiemKM().setRowCount(0);
        ArrayList<KhuyenMai> dsKM = timKM.isEmpty() ? KhuyenMaiDAO.getAllKhuyenMai() : KhuyenMaiDAO.timKMTheoMoTa(timKM);
        for (KhuyenMai km : dsKM) {
            form.getModelKQTimKiemKM().addRow(new Object[]{
                km.getMaKhuyenMai(), km.getMoTa(), km.getPhanTram(), km.getNgayBatDau(), km.getNgayKetThuc()
            });
        }
    }

    public void xuLyThemKM(ThemSanPhamGUI form) {
        int[] rows = form.getTblTimKiemKM().getSelectedRows();
        for (int r : rows) {
            String maKM = form.getModelKQTimKiemKM().getValueAt(r, 0).toString();
            boolean tonTai = listTempKM.stream().anyMatch(km -> km.getMaKhuyenMai().equals(maKM));
            if (!tonTai) {
                KhuyenMai km = KhuyenMaiDAO.timKMTheoMa(maKM);
                if (km != null) {
                    listTempKM.add(km);
                    form.getModelKMChon().addRow(new Object[]{
                        km.getMaKhuyenMai(), km.getMoTa(), km.getPhanTram() + "%",
                        km.getSoLuongToiThieu(), km.getSoLuongToiDa(), LocalDate.now()
                    });
                }
            }
        }
    }

    public void xuLyXoaKM(ThemSanPhamGUI form) {
        int[] rows = form.getTblKMChon().getSelectedRows();
        if (rows.length == 0) {
            return;
        }
        for (int i = rows.length - 1; i >= 0; i--) {
            int row = rows[i];
            if (row < listTempKM.size()) {
                listTempKM.remove(row);
            }
            form.getModelKMChon().removeRow(row);
        }
    }
}
