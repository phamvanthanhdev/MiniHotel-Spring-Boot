package com.demo.MiniHotel.modules.dichvu.service;

import com.demo.MiniHotel.model.ChiTietThayDoiGiaDichVu;
import com.demo.MiniHotel.model.DichVu;
import com.demo.MiniHotel.model.ThongTinDichVu;
import com.demo.MiniHotel.modules.dichvu.dto.ChiTietGiaDichVuRequest;
import com.demo.MiniHotel.modules.dichvu.dto.ChiTietGiaDichVuResponse;
import com.demo.MiniHotel.modules.dichvu.dto.DichVuRequest;
import com.demo.MiniHotel.modules.dichvu.dto.DichVuResponse;
import com.demo.MiniHotel.modules.phuthu.dto.ChiTietGiaPhuThuResponse;

import java.time.LocalDate;
import java.util.List;

public interface IDichVuService {
    public DichVuResponse addNewDichVu(DichVuRequest request) throws Exception;
    public List<DichVu> getAllDichVu();
    public DichVu getDichVuById(Integer id) throws Exception;
    public DichVuResponse updateDichVu(DichVuRequest request, Integer id) throws Exception;
    public void deleteDichVu(Integer id) throws Exception;
    ThongTinDichVu getThongTinDichVuResponseById(int idDichVu);
    public List<DichVuResponse> getAllDichVuResponse();
    public List<ThongTinDichVu> getAllThongTinDichVu();

    List<ChiTietGiaDichVuResponse> getChiTietThayDoiGiaDichVu();
    ChiTietGiaDichVuResponse getChiTietThayDoiGiaDichVuById(int idDichVu, int idNhanVien, LocalDate ngayCapNhat);
    ChiTietThayDoiGiaDichVu themChiTietThayDoiGiaDichVu(ChiTietGiaDichVuRequest request) throws Exception;
    ChiTietThayDoiGiaDichVu capNhatChiTietThayDoiGiaDichVu(int idDichVu, int idNhanVien, LocalDate ngayCapNhat, ChiTietGiaDichVuRequest request);
    void xoaChiTietThayDoiGiaDichVu(int idDichVu, int idNhanVien, LocalDate ngayCapNhat);
    public DichVuResponse getDichVuResponseById(Integer id) throws Exception;
}
