/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.dao.LoSanPhamDAO;
import hethongnhathuocduocankhang.dao.NhaCungCapDAO;
import hethongnhathuocduocankhang.dao.SanPhamCungCapDAO;
import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import hethongnhathuocduocankhang.entity.SanPham;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class QuanLyLoBUS {    
        public ArrayList<LoSanPham> getLoKhongHuy(){
        ArrayList<LoSanPham> all = hethongnhathuocduocankhang.dao.LoSanPhamDAO.dsLoSanPham();
        ArrayList<LoSanPham> kq = new ArrayList<>();
        if(all!=null){
            for(LoSanPham lo: all){
                if(!lo.isDaHuy()){
                    kq.add(lo);
                }
            }
        }
        return kq;
    }

    public Object[] toTableRow(LoSanPham lo){
        DonViTinh donVi = hethongnhathuocduocankhang.dao.DonViTinhDAO.getMotDonViTinhTheoMaSP(lo.getSanPham().getMaSP());
        SanPham sp = SanPhamDAO.timSPTheoMa(lo.getSanPham().getMaSP());
        return new Object[]{
            lo.getSanPham().getMaSP(),
            sp!=null? sp.getTen():"",
            lo.getMaLoSanPham(),
            donVi!=null? donVi.getTenDonVi():"",
            lo.getSoLuong()
        };
    }

        public String tinhTrangThaiLo(LoSanPham lo) {
        if (lo.isDaHuy()) {
            return "Đã hủy";
        }

        long kq = kiemTra(LocalDate.now(), lo.getNgayHetHan());

        if (kq < 0) {
            return "Hết hạn";
        } else if (kq >= 0 && kq <180) {
            return "Sắp hết hạn";
        } else {
            return "Còn hạn";
        }
    }

    
    public Map<String, Object> thongKe(ArrayList<LoSanPham> dsLo){
        int daHuy =0, hetHan=0, sapHetHan =0, conHan=0;
        ArrayList<LoSanPham> dsDaHuy = new ArrayList<>();
        ArrayList<LoSanPham> dsHetHan = new ArrayList<>();
        ArrayList<LoSanPham> dsSapHetHan = new ArrayList<>();
        ArrayList<LoSanPham> dsConHan = new ArrayList<>();
        for (LoSanPham i:dsLo){
            if(!i.isDaHuy()){
                long kq = kiemTra(LocalDate.now(), i.getNgayHetHan());
                if(kq>=0&&kq<=30){
                    sapHetHan++;
                    dsSapHetHan.add(i);
                }
                else if(kq<0){
                    hetHan++;
                    dsHetHan.add(i);
                }
                else{
                    conHan++;
                    dsConHan.add(i);
                } 
            }else{
                daHuy++;
                dsDaHuy.add(i);
            }
        }
        Map<String, Object> dsThongKeLo = new  HashMap<>();
        dsThongKeLo.put("SoLoDaHuy", daHuy);
        dsThongKeLo.put("SoLoHetHan", hetHan);
        dsThongKeLo.put("SoLoSapHetHan", sapHetHan);
        dsThongKeLo.put("SoLoConHan", conHan);
        
        dsThongKeLo.put("dsLoDaHuy", dsDaHuy);
        dsThongKeLo.put("dsLoHetHan", dsHetHan);
        dsThongKeLo.put("dsLoSapHetHan", dsSapHetHan);
        dsThongKeLo.put("dsConHan", dsConHan);
        
        return dsThongKeLo;
    }
    
    public long kiemTra(LocalDate ht, LocalDate hh){
        return ChronoUnit.DAYS.between(ht, hh);
    }

    
    public static String chuyenDinhDang(String date){
        DateTimeFormatter nhap = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate datee = LocalDate.parse(date, nhap);
        DateTimeFormatter xuat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String newDate = datee.format(xuat);
        return newDate;
    }
 
    private ArrayList<LoSanPham> timLoTheotenNhaCungCap(ArrayList<LoSanPham> dsLo, String tenNCC){
        ArrayList<LoSanPham> ds = new ArrayList<>();
        NhaCungCap ncc = NhaCungCapDAO.timMotNCCTheoTen(tenNCC);
        if(ncc!=null){
            for(LoSanPham lo:dsLo){
                String maSanPham = lo.getSanPham().getMaSP();
                NhaCungCap nccTheoLo = NhaCungCapDAO.timNCCTheoMa(SanPhamCungCapDAO.getSanPhamCungCap(maSanPham).getNhaCungCap().getMaNCC());
                if(ncc.getTenNCC() == null ? nccTheoLo.getTenNCC() == null : ncc.getTenNCC().equals(nccTheoLo.getTenNCC())){
                    ds.add(lo);
                }
            }
        }
        return ds;
    }
    
    public ArrayList<LoSanPham> timKiemLoVoiNhieuDieuKien(String tieuChi, String noiDung, String trangThai) {
        String loaiTimKiem = (tieuChi == null) ? "tất cả" : tieuChi.trim().toLowerCase();
        String trangThaiLoc = (trangThai == null) ? "tất cả" : trangThai.trim().toLowerCase();
        String noiDungSafe = (noiDung == null) ? "" : noiDung.trim();
        String noiDungLowerCase = noiDungSafe.toLowerCase();
        
        if (!"tất cả".equals(loaiTimKiem)) { 
            if (noiDungSafe.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Bạn phải nhập thông tin tìm kiếm khi chọn tiêu chí cụ thể.");
                return new ArrayList<>();
            }
        }
        if (!noiDungSafe.isEmpty()) {
            switch(loaiTimKiem){
                case "mã lô sản phẩm":
                    if(!noiDungSafe.matches("LO-[A-Z]{2}-[0-9]{4}-[0-9]{8}-[0-9]{1}")){
                        JOptionPane.showMessageDialog(null, "Mã lô phải tuân theo định dạng LO-SP-XXXX-XXXXXX-X, vui lòng nhập lại");
                        return new ArrayList<>();
                    }
                    break;
                case "mã sản phẩm":
                    if(!noiDungSafe.matches("SP-\\d{4}")){
                        JOptionPane.showMessageDialog(null, "Mã sản phẩm phải bắt đầu bằng SP- và 4 số nguyên.");
                        return new ArrayList<>();
                    }
                    break;
                case "tên sản phẩm":
                    if(noiDungSafe.trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Bạn chưa nhập tên sản phẩm, vui lòng nhập tên sản phẩm và thử lại");
                    }break;
                case "nhà cung cấp":
                    if(noiDungSafe.trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Bạn chưa nhập tên nhà cung cấp, vui lòng nhập và thử lại");
                        return new ArrayList<>();
                    }
                    break;
            }
        }
        Map<String, Object> dsThongKe = thongKe(LoSanPhamDAO.dsLoSanPham());
        ArrayList<LoSanPham> dsLoDaLocTheoTrangThai;
        try {
            dsLoDaLocTheoTrangThai = switch(trangThaiLoc){
                case "còn hạn" -> (ArrayList<LoSanPham>) dsThongKe.get("dsConHan");
                case "sắp hết hạn" -> (ArrayList<LoSanPham>) dsThongKe.get("dsLoSapHetHan");
                case "hết hạn" -> (ArrayList<LoSanPham>) dsThongKe.get("dsLoHetHan");
                case "đã hủy" -> (ArrayList<LoSanPham>) dsThongKe.get("dsLoDaHuy");
                default -> LoSanPhamDAO.dsLoSanPham();
            };
        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(null, "Lỗi dữ liệu nội bộ khi lọc theo trạng thái: " + e.getMessage());
            return new ArrayList<>();
        }
        if(loaiTimKiem.equals("nhà cung cấp")){
            return timLoTheotenNhaCungCap(dsLoDaLocTheoTrangThai, noiDungSafe);
        }
        Map<String, SanPham> spCache = new HashMap<>();
        List<LoSanPham> ketQuaLoc = dsLoDaLocTheoTrangThai.stream().filter(lo -> {
            if (loaiTimKiem.equals("tất cả")) {
                return true;
            }
            switch(loaiTimKiem){
                case "mã lô sản phẩm":
                    return lo.getMaLoSanPham() != null && lo.getMaLoSanPham().toLowerCase().contains(noiDungLowerCase);
                case "mã sản phẩm": 
                    if (lo.getSanPham() == null || lo.getSanPham().getMaSP() == null) return false;
                    return lo.getSanPham().getMaSP().toLowerCase().contains(noiDungLowerCase);
                case "tên sản phẩm":
                    SanPham spThamChieu = lo.getSanPham();
                    if (spThamChieu == null || spThamChieu.getMaSP() == null) return false;
                    String maSP = spThamChieu.getMaSP();
                    SanPham chiTietSP = spCache.get(maSP);
                    if (chiTietSP == null) {
                        chiTietSP = SanPhamDAO.timSPTheoMa(maSP); 
                        if (chiTietSP != null) {
                            spCache.put(maSP, chiTietSP);
                        } else {
                            return false; 
                        }
                    }

                    // Kiểm tra điều kiện lọc
                    return chiTietSP.getTen() != null && chiTietSP.getTen().toLowerCase().contains(noiDungLowerCase);

                default: 
                    return true;
            }
        }).collect(Collectors.toList()); 

        return new ArrayList<>(ketQuaLoc);
    }
    public static boolean tongSoLuongTheoSanPham(String maSP, LoSanPham loSP){
        ArrayList<LoSanPham> dsLo = LoSanPhamDAO.getLoSanPhamTheoMaSP(maSP);
        int tongSL=0;
        for(LoSanPham lo:dsLo)
            tongSL+=lo.getSoLuong();
        SanPham sp = SanPhamDAO.timSPTheoMa(maSP);
        if(sp.getTonToiDa()<tongSL)
            return true;
        return false;
    }
    
    /**
     * Lấy thống kê số lượng lô theo trạng thái
     * @return int[4] - [0]=Còn hạn, [1]=Sắp hết hạn, [2]=Hết hạn, [3]=Đã hủy
     */
    public static int[] layThongKeLoTheoTrangThai() {
        return LoSanPhamDAO.demLoTheoTrangThai();
    }
    
}
