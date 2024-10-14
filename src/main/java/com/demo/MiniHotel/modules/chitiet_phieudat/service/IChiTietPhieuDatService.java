package com.demo.MiniHotel.modules.chitiet_phieudat.service;


import com.demo.MiniHotel.embedded.IdChiTietPhieuDatEmb;
import com.demo.MiniHotel.model.ChiTietPhieuDat;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatRequest;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse2;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietUserResponse;

import java.util.List;

public interface IChiTietPhieuDatService {
    public ChiTietPhieuDat addNewChiTietPhieuDat(ChiTietPhieuDatRequest request) throws Exception;
    public void addListChiTietPhieuDat(List<ChiTietPhieuDatRequest> requests) throws Exception;
    public List<ChiTietPhieuDat> getAllChiTietPhieuDat();
    public ChiTietPhieuDatResponse getChiTietPhieuDatById(IdChiTietPhieuDatEmb id) throws Exception;
    public ChiTietUserResponse convertChiTietUserResponse(ChiTietPhieuDat chiTietPhieuDat) throws Exception;
    public List<ChiTietPhieuDat> getChiTietPhieuDatByIdPhieuDat(Integer idPhieuDat) throws Exception;
    public ChiTietPhieuDat updateSoLuong(ChiTietPhieuDatRequest request, IdChiTietPhieuDatEmb id) throws Exception;
    public void deleteChiTietPhieuDat(IdChiTietPhieuDatEmb id) throws Exception;
    public void deleteChiTietPhieuDatByPhieuDatId(Integer idPhieuDat) throws Exception;
    public ChiTietPhieuDatResponse convertChiTietPhieuDatResponse(ChiTietPhieuDat chiTietPhieuDat) throws Exception;
    public ChiTietPhieuDatResponse2 convertChiTietPhieuDatResponse2(ChiTietPhieuDat chiTietPhieuDat, int soLuongTrong) throws Exception;
    void xoaChiTietPhieuDat(ChiTietPhieuDatRequest chiTietPhieuDatRequest);
}
