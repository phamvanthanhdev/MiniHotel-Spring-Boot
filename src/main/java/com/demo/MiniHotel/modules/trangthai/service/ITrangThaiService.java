package com.demo.MiniHotel.modules.trangthai.service;

import com.demo.MiniHotel.model.TrangThai;
import com.demo.MiniHotel.modules.trangthai.dto.TrangThaiRequest;

import java.util.List;

public interface ITrangThaiService {
    public TrangThai addNewTrangThai(TrangThaiRequest request);
    public List<TrangThai> getAllTrangThai();
    public TrangThai getTrangThaiById(Integer id) throws Exception;
    public TrangThai updateTrangThai(TrangThaiRequest request, Integer id) throws Exception;
    public void deleteTrangThai(Integer id) throws Exception;
}
