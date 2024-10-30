package com.demo.MiniHotel.modules.ctkm.service;

import com.demo.MiniHotel.model.ChuongTrinhKhuyenMai;
import com.demo.MiniHotel.modules.ctkm.dto.ChuongTrinhKhuyenMaiRequest;
import com.demo.MiniHotel.modules.ctkm.dto.ChuongTrinhKhuyenMaiResponse;

import java.util.List;

public interface IChuongTrinhKhuyenMaiService {
    ChuongTrinhKhuyenMai themChuongTrinhKhuyenMai(ChuongTrinhKhuyenMaiRequest request) throws Exception;
    List<ChuongTrinhKhuyenMai> getAllChuongTrinhKhuyenMai();
    ChuongTrinhKhuyenMai getChuongTrinhKhuyenMaiById(Integer id) throws Exception;
    ChuongTrinhKhuyenMai updateChuongTrinhKhuyenMai(ChuongTrinhKhuyenMaiRequest request, Integer id) throws Exception;
    void deleteChuongTrinhKhuyenMai(Integer id) throws Exception;
    ChuongTrinhKhuyenMaiResponse getChuongTrinhKhuyenMaiResponseById(Integer id) throws Exception;
    List<ChuongTrinhKhuyenMaiResponse> getAllChuongTrinhKhuyenMaiResponse();
}
