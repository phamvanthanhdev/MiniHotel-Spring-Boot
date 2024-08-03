package com.demo.MiniHotel.modules.loaiphong.service;

import com.demo.MiniHotel.model.LoaiPhong;
import com.demo.MiniHotel.modules.loaiphong.dto.LoaiPhongRequest;

import java.util.List;

public interface ILoaiPhongService {
    public LoaiPhong addNewLoaiPhong(LoaiPhongRequest request);
    public List<LoaiPhong> getAllLoaiPhong();
    public LoaiPhong getLoaiPhongById(Integer id) throws Exception;
    public LoaiPhong updateLoaiPhong(LoaiPhongRequest request, Integer id) throws Exception;
    public void deleteLoaiPhong(Integer id) throws Exception;
}
