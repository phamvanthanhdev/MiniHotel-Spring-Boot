package com.demo.MiniHotel.modules.thongtin_hangphong.service;

import com.demo.MiniHotel.model.ThongTinHangPhong;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongAdminResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongUserResponse;

import java.time.LocalDate;
import java.util.List;

public interface IThongTinHangPhongService {
    public List<ThongTinHangPhong> getAllThongTinHangPhong();
    public ThongTinHangPhong getThongTinHangPhongById(Integer id) throws Exception;
    public ThongTinHangPhongUserResponse getThongTinHangPhongSoLuongById(Integer id, LocalDate ngayDenDat, LocalDate ngayDiDat) throws Exception;
    public Long getDonGiaThongTinHangPhongById(Integer id) throws Exception;
    public ThongTinHangPhongResponse convertThongTinHangPhongResponse(ThongTinHangPhong thongTinHangPhong) throws Exception;
    public Boolean kiemTraPhongHangPhongTrong(int idHangPhong, LocalDate ngayDenDat, LocalDate ngayDiDat, int soLuongDat);
    public Integer laySoLuongHangPhongTrong(LocalDate ngayDenDat, LocalDate ngayDiDat, int idHangPhong);
    public List<ThongTinHangPhongUserResponse> getThongTinHangPhongTheoThoiGian(LocalDate ngayDenDat, LocalDate ngayDiDat) throws Exception;
    public List<ThongTinHangPhongUserResponse> timKiemThongTinHangPhong(LocalDate ngayDenDat, LocalDate ngayDiDat,
                                                                        Integer soNguoi, Long giaMin, Long giaMax) throws Exception;
    public Boolean kiemTraPhongDaThue(String maPhong, LocalDate ngayDenDat, LocalDate ngayDiDat);

    List<ThongTinHangPhongUserResponse> sapXepHangPhongTheoSoLuongDatThue(LocalDate ngayDenDat, LocalDate ngayDiDat) throws Exception;
    List<ThongTinHangPhongUserResponse> sapXepHangPhongTheoGiamGia(LocalDate ngayDenDat, LocalDate ngayDiDat) throws Exception;

    List<ThongTinHangPhongAdminResponse> layThongTinHangPhongAdminTheoThoiGian(LocalDate ngayDenDat, LocalDate ngayDiDat) throws Exception;

    List<ThongTinHangPhongUserResponse> timKiemThongTinHangPhongTheoGia(LocalDate ngayDenDat, LocalDate ngayDiDat,
                                                                        Long giaMin, Long giaMax) throws Exception;
    public List<ThongTinHangPhongUserResponse> timKiemThongTinHangPhongUser(LocalDate ngayDenDat, LocalDate ngayDiDat,
                                                                            Long giaMin, Long giaMax, String noiDung) throws Exception;
}
