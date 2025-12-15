/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.dao.LoSanPhamDAO;
import hethongnhathuocduocankhang.dao.NhaCungCapDAO;
import hethongnhathuocduocankhang.dao.SanPhamCungCapDAO;
import hethongnhathuocduocankhang.dao.SanPhamDAO;
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

/**
 *
 * @author admin
 */
public class QuanLyLoBUS {    
        public String tinhTrangThaiLo(LoSanPham lo) {
        if (lo.isDaHuy()) {
            return "Đã hủy";
        }

        long kq = kiemTra(LocalDate.now(), lo.getNgayHetHan());

        if (kq < 0) {
            return "Hết hạn";
        } else if (kq >= 0 && kq <= 30) {
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
    
    public ArrayList<LoSanPham> timKiemLoVoiNhieuDieuKien(String tieuChi, String noiDung, String trangThai){    
        Map<String, Object> ds = thongKe(LoSanPhamDAO.dsLoSanPham());
        ArrayList<LoSanPham> dsLoDaLocTheoTrangThai = new ArrayList<>();
        switch(trangThai.toLowerCase()){
            case "còn hạn" -> dsLoDaLocTheoTrangThai = (ArrayList<LoSanPham>) ds.get("dsConHan");
            case "sắp hết hạn" -> dsLoDaLocTheoTrangThai = (ArrayList<LoSanPham>) ds.get("dsLoSapHetHan");
            case "hết hạn" -> dsLoDaLocTheoTrangThai = (ArrayList<LoSanPham>) ds.get("dsLoHetHan");
            case "đã hủy" -> dsLoDaLocTheoTrangThai = (ArrayList<LoSanPham>) ds.get("dsLoDaHuy");
            default -> dsLoDaLocTheoTrangThai = LoSanPhamDAO.dsLoSanPham();
        }
        String loaiTimKiem = tieuChi.toLowerCase();

        if(loaiTimKiem.equals("nhà cung cấp") && noiDung != null){
            dsLoDaLocTheoTrangThai = timLoTheotenNhaCungCap(dsLoDaLocTheoTrangThai, noiDung);
            return dsLoDaLocTheoTrangThai;
        }
                
                

        String noiDungLowerCase = (noiDung == null) ? null : noiDung.toLowerCase();
        List<LoSanPham> ketQuaLoc = dsLoDaLocTheoTrangThai.stream().filter(lo -> {
            if (noiDung == null) return true; // Nếu không nhập nội dung, không cần lọc thêm
            switch(loaiTimKiem){
                case "mã lô sản phẩm" -> {
                    return lo.getMaLoSanPham().toLowerCase().contains(noiDungLowerCase);
                }
                case "mã sản phẩm" -> {
                    return lo.getSanPham().getMaSP().toLowerCase().contains(noiDungLowerCase);
                }
                case "tên sản phẩm" -> {
                    SanPham sp = SanPhamDAO.timSPTheoMa(lo.getSanPham().getMaSP());
                    return sp != null && sp.getTen().toLowerCase().contains(noiDungLowerCase);
                }
                default -> { 
                    return true;
                }
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
    
}
