package com.demo.MiniHotel.modules.phong.service;

import com.demo.MiniHotel.model.Phong;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phong.dto.PhongRequest;
import com.demo.MiniHotel.modules.phong.dto.PhongResponse;
import com.demo.MiniHotel.modules.phong.dto.ThemPhongResponse;

import java.util.List;

public interface IPhongService {
    public Phong addNewPhong(PhongRequest request) throws Exception;
    public List<Phong> getAllPhong();
    public Phong getPhongById(String maPhong) throws Exception;
    public ThemPhongResponse updatePhong(PhongRequest request, String maPhong) throws Exception;
    public void deletePhong(String maPhong) throws Exception;
    public Phong capNhatTrangThai(String maPhong, int idTrangThai) throws Exception;
    List<PhongResponse> getPhongResponses() throws Exception;
    public ThemPhongResponse getPhongResponseById(String maPhong) throws Exception;
}
