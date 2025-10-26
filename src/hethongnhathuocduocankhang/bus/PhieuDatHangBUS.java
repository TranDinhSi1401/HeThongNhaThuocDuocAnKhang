/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.bus;

import java.util.ArrayList;
import hethongnhathuocduocankhang.dao.PhieuDatHangDAO;
import hethongnhathuocduocankhang.entity.SanPham;
import hethongnhathuocduocankhang.gui.TaoPhieuDatHangGUI;

/**
 *
 * @author admin
 */
public class PhieuDatHangBUS {
       private PhieuDatHangDAO phieuDatDAO;
    public PhieuDatHangBUS() {
        phieuDatDAO = new PhieuDatHangDAO();
    }
    public ArrayList<SanPham> danhsachSanPham(){
        return phieuDatDAO.dsSanPham();
    }
    public SanPham timSanPham(String ma){
        return phieuDatDAO.timSanPham(ma);
    }
       
       
       
       
}
