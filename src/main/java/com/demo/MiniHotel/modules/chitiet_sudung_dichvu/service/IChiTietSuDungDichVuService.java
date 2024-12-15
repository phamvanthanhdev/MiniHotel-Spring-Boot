package com.demo.MiniHotel.modules.chitiet_sudung_dichvu.service;


import com.demo.MiniHotel.embedded.IdChiTietSuDungDichVuEmb;
import com.demo.MiniHotel.model.ChiTietSuDungDichVu;
import com.demo.MiniHotel.model.HoaDon;
import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.CapNhatChiTietSuDungDichVuRequest;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietDichVuPhongResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuRequest;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuResponse;

import java.util.List;

public interface IChiTietSuDungDichVuService {
    public List<ChiTietSuDungDichVuResponse> themChiTietSuDungDichVu(ChiTietSuDungDichVuRequest request) throws Exception;
//    public void addListChiTietSuDungDichVu(List<ChiTietPhuThuRequest> requests) throws Exception;
//    public List<ChiTietSuDungDichVu> getAllChiTietSuDungDichVu();
//    public ChiTietSuDungDichVu getChiTietSuDungDichVuById(Integer id) throws Exception;
    public List<ChiTietSuDungDichVuResponse> getChiTietSuDungDichVuByIdChiTietPhieuThue(Integer idChiTietPhieuThue) throws Exception;
    public ChiTietSuDungDichVu updateChiTietDichVu(ChiTietSuDungDichVuRequest request) throws Exception;
    public void deleteChiTietSuDungDichVu(int idChiTietSuDungDichVu) throws Exception;
//    public void deleteChiTietSuDungDichVuByIdPhieuThue(Integer idPhieuThue) throws Exception;

    public ChiTietSuDungDichVu addHoaDonToChiTietSuDungDichVu(Integer idChiTietSuDungDichVu, String soHoaDon) throws Exception;
    public HoaDon getHoaDonInChiTietSuDungDichVu(Integer idChiTietSuDungDichVu) throws Exception;
    public List<ChiTietSuDungDichVu> thanhToanChiTietSuDungDVCuaChiTietPhieuThue(Integer idChiTietPhieuThue, String soHoaDon) throws Exception;
    long getTongTienChiTietSuDungDichVu(Integer idChiTietPhieuThue) throws Exception;
    List<ChiTietDichVuPhongResponse> getChiTietDichVuCuaPhieuThue(int idPhieuThue);
    ChiTietSuDungDichVu getChiTietSuDungDichVuById(int idChiTietSuDungDichVu);
    ChiTietSuDungDichVu capNhatChiTietSuDungDichVu(CapNhatChiTietSuDungDichVuRequest request);

    void xoaChiTietSuDungDichVu(int idChiTietSuDungDichVu) throws Exception;

}
