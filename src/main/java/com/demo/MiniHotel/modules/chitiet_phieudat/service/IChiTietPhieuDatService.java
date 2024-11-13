package com.demo.MiniHotel.modules.chitiet_phieudat.service;


import com.demo.MiniHotel.embedded.IdChiTietPhieuDatEmb;
import com.demo.MiniHotel.model.ChiTietPhieuDat;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.*;

import java.util.List;

public interface IChiTietPhieuDatService {
    public ChiTietPhieuDat addNewChiTietPhieuDat(ChiTietPhieuDatRequest request) throws Exception;
    public void addListChiTietPhieuDat(List<ChiTietPhieuDatRequest> requests) throws Exception;
    public List<ChiTietPhieuDat> getAllChiTietPhieuDat();
    public ChiTietPhieuDatResponse getChiTietPhieuDatResponseById(int id) throws Exception;
    public ChiTietUserResponse convertChiTietUserResponse(ChiTietPhieuDat chiTietPhieuDat, long soNgayDat) throws Exception;
    public List<ChiTietPhieuDat> getChiTietPhieuDatByIdPhieuDat(Integer idPhieuDat) throws Exception;
    public ChiTietPhieuDat updateSoLuong(ChiTietPhieuDatRequest request, int id) throws Exception;
    public void deleteChiTietPhieuDat(int id) throws Exception;
    public void deleteChiTietPhieuDatByPhieuDatId(Integer idPhieuDat) throws Exception;
    public ChiTietPhieuDatResponse convertChiTietPhieuDatResponse(ChiTietPhieuDat chiTietPhieuDat) throws Exception;
    public ChiTietPhieuDatResponse2 convertChiTietPhieuDatResponse2(ChiTietPhieuDat chiTietPhieuDat, int soLuongTrong) throws Exception;
    void xoaChiTietPhieuDat(ChiTietPhieuDatRequest chiTietPhieuDatRequest);
    CapNhatChiTietPhieuDatResponse convertCapNhatChiTietPhieuDatResponse(ChiTietPhieuDat chiTietPhieuDat, long soNgayDat, int soLuongTrong);
    public ChiTietPhieuDat getChiTietPhieuDatById(int id) throws Exception;

    ChiTietPhieuDat capNhatChiTietPhieuDat(CapNhatChiTietPhieuDatRequest request) throws Exception;

    ChiTietPhieuDat capNhatSoLuongChiTietPhieuDat(ChiTietPhieuDatRequest request) throws Exception;
    void capNhatSoLuongListChiTietPhieuDat(List<ChiTietPhieuDatRequest> requests) throws Exception;

    boolean kiemTraChiTietPhieuDat(Integer idPhieuDat, Integer idHangPhong, long donGia);
}
