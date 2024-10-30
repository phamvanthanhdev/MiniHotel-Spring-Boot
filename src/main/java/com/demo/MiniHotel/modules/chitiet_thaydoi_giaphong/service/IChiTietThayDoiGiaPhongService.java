package com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.service;

import com.demo.MiniHotel.model.ChiTietThayDoiGiaPhong;
import com.demo.MiniHotel.model.HangPhong;
import com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.dto.ChiTietGiaPhongRequest;
import com.demo.MiniHotel.modules.chitiet_thaydoi_giaphong.dto.ChiTietGiaPhongResponse;

import java.time.LocalDate;
import java.util.List;

public interface IChiTietThayDoiGiaPhongService {
    ChiTietThayDoiGiaPhong themChiTietThayDoiGiaPhong(ChiTietGiaPhongRequest request) throws Exception;
    List<ChiTietThayDoiGiaPhong> getAllChiTietThayDoiGiaPhong();
    ChiTietThayDoiGiaPhong getChiTietThayDoiGiaPhongById(int idHangPhong, int idNhanVien, LocalDate ngayCapNhat) throws Exception;
    ChiTietGiaPhongResponse updateChiTietThayDoiGiaPhong(ChiTietGiaPhongRequest request, int idHangPhong, int idNhanVien, LocalDate ngayCapNhat) throws Exception;
    void deleteChiTietThayDoiGiaPhong(int idHangPhong, int idNhanVien, LocalDate ngayCapNhat) throws Exception;
    ChiTietGiaPhongResponse getChiTietThayDoiGiaPhongResponseById(int idHangPhong, int idNhanVien, LocalDate ngayCapNhat) throws Exception;
    List<ChiTietGiaPhongResponse> getAllChiTietThayDoiGiaPhongResponse();
}
