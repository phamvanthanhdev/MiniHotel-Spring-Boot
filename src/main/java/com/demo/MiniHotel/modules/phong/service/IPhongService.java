package com.demo.MiniHotel.modules.phong.service;

import com.demo.MiniHotel.model.Phong;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phong.dto.PhongRequest;

import java.util.List;

public interface IPhongService {
    public Phong addNewPhong(PhongRequest request) throws Exception;
    public List<Phong> getAllPhong();
    public Phong getPhongById(String maPhong) throws Exception;
    public Phong updatePhong(PhongRequest request, String maPhong) throws Exception;
    public void deletePhong(String maPhong) throws Exception;
    public Phong capNhatTrangThai(String maPhong, int idTrangThai) throws Exception;
}
