package com.demo.MiniHotel.modules.kieuphong.service;

import com.demo.MiniHotel.model.KieuPhong;
import com.demo.MiniHotel.modules.kieuphong.dto.KieuPhongRequest;

import java.util.List;

public interface IKieuPhongService {
    public KieuPhong addNewKieuPhong(KieuPhongRequest request);
    public List<KieuPhong> getAllKieuPhong();
    public KieuPhong getKieuPhongById(Integer id) throws Exception;
    public KieuPhong updateKieuPhong(KieuPhongRequest request, Integer id) throws Exception;
    public void deleteKieuPhong(Integer id) throws Exception;
}
