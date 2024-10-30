package com.demo.MiniHotel.modules.chitiet_ctkm.service;

import com.demo.MiniHotel.model.ChiTietKhuyenMai;
import com.demo.MiniHotel.model.HangPhong;
import com.demo.MiniHotel.modules.chitiet_ctkm.dto.ChiTietKhuyenMaiRequest;
import com.demo.MiniHotel.modules.chitiet_ctkm.dto.ChiTietKhuyenMaiResponse;

import java.time.LocalDate;
import java.util.List;

public interface IChiTietKhuyenMaiService {
    ChiTietKhuyenMai themChiTietKhuyenMai(ChiTietKhuyenMaiRequest request) throws Exception;
    List<ChiTietKhuyenMai> getAllChiTietKhuyenMai();
    ChiTietKhuyenMai getChiTietKhuyenMaiById(int idKhuyenMai, int idHangPhong) throws Exception;
    ChiTietKhuyenMai updateChiTietKhuyenMai(ChiTietKhuyenMaiRequest request, int idKhuyenMai, int idHangPhong) throws Exception;
    void deleteChiTietKhuyenMai(int idKhuyenMai, int idHangPhong) throws Exception;
    ChiTietKhuyenMaiResponse getChiTietKhuyenMaiResponseById(int idKhuyenMai, int idHangPhong) throws Exception;
    List<ChiTietKhuyenMaiResponse> getAllChiTietKhuyenMaiResponse();
    List<HangPhong> layDanhSachHangPhongKhuyenMai(LocalDate ngayBatDauKiemTra, LocalDate ngayKetThucKiemTra);
    List<ChiTietKhuyenMaiResponse> getChiTietKhuyenMaiByIdKhuyenMai(int idKhuyenMai) throws Exception;
}
