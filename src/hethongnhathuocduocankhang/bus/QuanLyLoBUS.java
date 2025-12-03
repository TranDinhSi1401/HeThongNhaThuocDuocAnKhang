/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.dao.LoSanPhamDAO;
import hethongnhathuocduocankhang.entity.LoSanPham;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class QuanLyLoBUS {
    LoSanPhamDAO loSanPhamDao= new LoSanPhamDAO();
    
    public ArrayList<Integer> soLuongLoQuaKiem(ArrayList<LoSanPham> dsLo){
        int conHan =0;
        int sapHetHan=0;
        int hetHan=0;
        int daHuy=0;
        for (LoSanPham i:dsLo){
            if(!i.isDaHuy()){
                long kq = kiemTra(LocalDate.now(), i.getNgayHetHan());
                if(kq>=0&&kq<=30) sapHetHan++;
                else if(kq<0) hetHan++;
                else conHan++; 
            }else{
                daHuy++;
            }
        }
        ArrayList<Integer> dsKQ= new ArrayList<>();
        dsKQ.add(daHuy);
        dsKQ.add(hetHan);
        dsKQ.add(sapHetHan);
        dsKQ.add(conHan);        
        return dsKQ;
    }
    public long kiemTra(LocalDate ht, LocalDate hh){
        return ChronoUnit.DAYS.between(ht, hh);
    }

    public ArrayList timKiemVaTraVeLoVoiNhieuDieuKien(){
        ArrayList<LoSanPham> dsLo = LoSanPhamDAO.dsLoSanPham();
        
        
        
        
        return dsLo;
    }

}
