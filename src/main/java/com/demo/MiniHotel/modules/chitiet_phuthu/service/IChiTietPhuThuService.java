package com.demo.MiniHotel.modules.chitiet_phuthu.service;


import com.demo.MiniHotel.embedded.IdChiTietPhuThuEmb;
import com.demo.MiniHotel.model.ChiTietPhuThu;
import com.demo.MiniHotel.model.HoaDon;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.CapNhatChiTietPhuThuRequest;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuPhongResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuRequest;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietDichVuPhongResponse;

import java.util.List;

public interface IChiTietPhuThuService {
    public List<ChiTietPhuThuResponse> themChiTietPhuThu(ChiTietPhuThuRequest request) throws Exception;
    public List<ChiTietPhuThuResponse> getChiTietPhuThuByIdChiTietPhieuThue(Integer idChiTietPhieuThue) throws Exception;
    public ChiTietPhuThu updateChiTietDichVu(ChiTietPhuThuRequest request) throws Exception;
    public void deleteChiTietPhuThu(Integer idChiTietPhuThu) throws Exception;
    public ChiTietPhuThu addHoaDonToChiTietPhuThu(Integer idChiTietPhuThu, String soHoaDon) throws Exception;
    public HoaDon getHoaDonInChiTietPhuThu(Integer idChiTietPhuThu) throws Exception;
    public List<ChiTietPhuThu> thanhToanChiTietPhuThuCuaChiTietPhieuThue(Integer idChiTietPhieuThue, String soHoaDon) throws Exception;
    long getTongTienChiTietPhuThu(Integer idChiTietPhieuThue) throws Exception;
    List<ChiTietPhuThuPhongResponse> getChiTietPhuThuCuaPhieuThue(int idPhieuThue);
    ChiTietPhuThu getChiTietPhuThuById(int idChiTietPhuThu);
    ChiTietPhuThu capNhatChiTietPhuThu(CapNhatChiTietPhuThuRequest request);
    void xoaChiTietPhuThu(int idChiTietPhuThu) throws Exception;
}
