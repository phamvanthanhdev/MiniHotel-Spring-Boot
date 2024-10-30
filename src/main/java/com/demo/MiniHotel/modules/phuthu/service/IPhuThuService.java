package com.demo.MiniHotel.modules.phuthu.service;

import com.demo.MiniHotel.model.ChiTietThayDoiGiaPhuThu;
import com.demo.MiniHotel.model.PhuThu;
import com.demo.MiniHotel.model.ThongTinPhuThu;
import com.demo.MiniHotel.modules.phuthu.dto.ChiTietGiaPhuThuRequest;
import com.demo.MiniHotel.modules.phuthu.dto.ChiTietGiaPhuThuResponse;
import com.demo.MiniHotel.modules.phuthu.dto.PhuThuRequest;
import com.demo.MiniHotel.modules.phuthu.dto.PhuThuResponse;

import java.time.LocalDate;
import java.util.List;

public interface IPhuThuService {
    public PhuThuResponse addNewPhuThu(PhuThuRequest request) throws Exception;
    public List<PhuThu> getAllPhuThu();
    public PhuThu getPhuThuById(Integer id) throws Exception;
    public PhuThuResponse updatePhuThu(PhuThuRequest request, Integer id) throws Exception;
    public void deletePhuThu(Integer id) throws Exception;
    ThongTinPhuThu getThongTinPhuThuResponseById(int idPhuThu);
    public List<PhuThuResponse> getAllPhuThuResponse();
    public List<ThongTinPhuThu> getAllThongTinPhuThu();
    List<ChiTietGiaPhuThuResponse> getChiTietThayDoiGiaPhuThu();
    ChiTietGiaPhuThuResponse getChiTietThayDoiGiaPhuThuById(int idPhuThu, int idNhanVien, LocalDate ngayCapNhat);
    ChiTietThayDoiGiaPhuThu themChiTietThayDoiGiaPhuThu(ChiTietGiaPhuThuRequest request) throws Exception;
    ChiTietThayDoiGiaPhuThu capNhatChiTietThayDoiGiaPhuThu(int idPhuThu, int idNhanVien, LocalDate ngayCapNhat, ChiTietGiaPhuThuRequest request);
    void xoaChiTietThayDoiGiaPhuThu(int idPhuThu, int idNhanVien, LocalDate ngayCapNhat);
    public PhuThuResponse getPhuThuResponseById(Integer id) throws Exception;
}
