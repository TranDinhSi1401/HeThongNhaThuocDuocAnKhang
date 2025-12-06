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
    LoSanPhamDAO loSanPhamDao= new LoSanPhamDAO();
    
//    public ArrayList<Integer> soLuongLoQuaKiem(ArrayList<LoSanPham> dsLo){
//        int conHan =0;
//        int sapHetHan=0;
//        int hetHan=0;
//        int daHuy=0;
//        for (LoSanPham i:dsLo){
//            if(!i.isDaHuy()){
//                long kq = kiemTra(LocalDate.now(), i.getNgayHetHan());
//                if(kq>=0&&kq<=30) sapHetHan++;
//                else if(kq<0) hetHan++;
//                else conHan++; 
//            }else{
//                daHuy++;
//            }
//        }
//        ArrayList<Integer> dsKQ= new ArrayList<>();
//        dsKQ.add(daHuy);
//        dsKQ.add(hetHan);
//        dsKQ.add(sapHetHan);
//        dsKQ.add(conHan);        
//        return dsKQ;
//    }
    
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
    
    //Chưa làm
    public ArrayList timKiemVaTraVeLoVoiNhieuDieuKien(){
        ArrayList<LoSanPham> dsLo = LoSanPhamDAO.dsLoSanPham();

        return dsLo;
    }
    
    public ArrayList<LoSanPham> timKiemLo ( 
            String maLo, 
            String maSP, 
            String tenSP, 
            String maNCC, 
            String trangThai){
        
        Map<String, Object> ds = thongKe(LoSanPhamDAO.dsLoSanPham());
        ArrayList<LoSanPham> dsLoDaHuy = (ArrayList<LoSanPham>) ds.get("dsLoDaHuy");
        ArrayList<LoSanPham> dsLoHetHan = (ArrayList<LoSanPham>) ds.get("dsLoHetHan");
        ArrayList<LoSanPham> dsLoSapHetHan = (ArrayList<LoSanPham>) ds.get("dsSapHetHan");
        ArrayList<LoSanPham> dsLoConHan = (ArrayList<LoSanPham>) ds.get("dsLoConHan");
        ArrayList<LoSanPham> dsLo = LoSanPhamDAO.dsLoSanPham();
        
        ArrayList<LoSanPham> dsTraVe = new ArrayList<>();
        
        switch(trangThai){
            case "còn hạn" -> dsTraVe = dsLoConHan;
            case "sắp hết hạn" -> dsTraVe = dsLoSapHetHan;
            case "hết hạn" -> dsTraVe = dsLoHetHan;
            case "đã hủy" -> dsTraVe = dsLoDaHuy;
            default ->{ dsTraVe = dsLo;
            }
        }
        return (ArrayList<LoSanPham>) dsTraVe.stream()
                .filter(lo->maLo == null || lo.getMaLoSanPham().contains(maLo))
                .filter(lo->maSP==null||lo.getSanPham().getMaSP().contains(maSP))
                .filter(lo->tenSP==null||lo.getSanPham().getTen().contains(tenSP))
                .collect(Collectors.toList());        
    }
}
