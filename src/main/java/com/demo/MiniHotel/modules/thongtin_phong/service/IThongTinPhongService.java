package com.demo.MiniHotel.modules.thongtin_phong.service;

import com.demo.MiniHotel.model.ThongTinPhong;
import com.demo.MiniHotel.modules.thongtin_phong.dto.PhongTrongResponse;
import com.demo.MiniHotel.modules.thongtin_phong.dto.ThongTinPhongHienTaiResponse;
import com.demo.MiniHotel.modules.thongtin_phong.dto.ThongTinPhongResponse;
import com.demo.MiniHotel.modules.thongtin_phong.dto.ThongTinPhongSapXepResponse;

import java.time.LocalDate;
import java.util.List;

public interface IThongTinPhongService {
    public List<ThongTinPhongResponse> getThongTinPhongTheoThoiGian(LocalDate ngayDenThue, LocalDate ngayDiThue);
    public List<ThongTinPhong> getAllThongTinPhong();
    public Boolean kiemTraPhongThue(LocalDate ngayDenThue, LocalDate ngayDiThue, String maPhongThue);
    public List<ThongTinPhongHienTaiResponse> getThongTinPhongHienTai();
    List<PhongTrongResponse> getPhongTrongByIdHangPhong(int idHangPhong, LocalDate ngayDenThue, LocalDate ngayDiThue);
    List<ThongTinPhongResponse> getThongTinPhongTheoHangPhong(LocalDate ngayDenThue, LocalDate ngayDiThue, int idHangPhong);
    public List<ThongTinPhongSapXepResponse> getThongTinPhongHienTaiSapXep();
}
