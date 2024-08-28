package com.demo.MiniHotel.modules.chitiet_phuthu.service;


import com.demo.MiniHotel.embedded.IdChiTietPhuThuEmb;
import com.demo.MiniHotel.model.ChiTietPhuThu;
import com.demo.MiniHotel.model.HoaDon;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuRequest;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;

import java.util.List;

public interface IChiTietPhuThuService {
    public List<ChiTietPhuThuResponse> themChiTietPhuThu(ChiTietPhuThuRequest request) throws Exception;
    public List<ChiTietPhuThuResponse> getChiTietPhuThuByIdChiTietPhieuThue(Integer idChiTietPhieuThue) throws Exception;
    public ChiTietPhuThu updateChiTietDichVu(ChiTietPhuThuRequest request) throws Exception;
    public void deleteChiTietPhuThu(IdChiTietPhuThuEmb idChiTietPhuThuEmb) throws Exception;
    public ChiTietPhuThu addHoaDonToChiTietPhuThu(Integer idChiTietPhieuThue, Integer idPhuThu, String soHoaDon) throws Exception;
    public HoaDon getHoaDonInChiTietPhuThu(Integer idChiTietPhieuThue, Integer idDichVu) throws Exception;

    public List<ChiTietPhuThu> thanhToanChiTietPhuThuCuaChiTietPhieuThue(Integer idChiTietPhieuThue, String soHoaDon) throws Exception;
}
