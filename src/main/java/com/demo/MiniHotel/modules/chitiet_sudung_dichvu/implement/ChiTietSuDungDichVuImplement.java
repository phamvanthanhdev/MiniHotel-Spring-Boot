package com.demo.MiniHotel.modules.chitiet_sudung_dichvu.implement;

import com.demo.MiniHotel.embedded.IdChiTietSuDungDichVuEmb;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phieuthue.service.IChiTietPhieuThueService;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuRequest;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.service.IChiTietSuDungDichVuService;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import com.demo.MiniHotel.repository.ChiTietPhieuThueRepository;
import com.demo.MiniHotel.repository.ChiTietSuDungDichVuRepository;
import com.demo.MiniHotel.repository.DichVuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChiTietSuDungDichVuImplement implements IChiTietSuDungDichVuService {
    private final ChiTietSuDungDichVuRepository repository;
    private final ChiTietPhieuThueRepository chiTietPhieuThueRepository;
    private final DichVuRepository dichVuRepository;
    private final IHoaDonService hoaDonService;
    @Override
    public List<ChiTietSuDungDichVuResponse> themChiTietSuDungDichVu(ChiTietSuDungDichVuRequest request) throws Exception {
        //Kiem tra chi tiet phiếu thuê có sử dụng dịch vụ này hay chưa
        //Nếu đã sử dụng -> thêm số lượng, nếu chưa -> thêm mới
        IdChiTietSuDungDichVuEmb idChiTietSuDungDichVuEmb =
                new IdChiTietSuDungDichVuEmb(request.getIdChiTietPhieuThue(), request.getIdDichVu());
        Optional<ChiTietSuDungDichVu> chiTietSuDungDichVuOptional = repository.findById(idChiTietSuDungDichVuEmb);
        if(chiTietSuDungDichVuOptional.isEmpty()) {
            ChiTietSuDungDichVu chiTietSuDungDichVuNew = new ChiTietSuDungDichVu();
            chiTietSuDungDichVuNew.setIdChiTietSuDungDichVuEmb(idChiTietSuDungDichVuEmb);
            chiTietSuDungDichVuNew.setDonGia(request.getDonGia());
            chiTietSuDungDichVuNew.setNgayTao(request.getNgayTao());
            chiTietSuDungDichVuNew.setDaThanhToan(false);

            Optional<DichVu> dichVuOptional = dichVuRepository.findById(request.getIdDichVu());
            if (dichVuOptional.isEmpty()) {
                throw new Exception("DichVu not found!");
            }
            Optional<ChiTietPhieuThue> chiTietPhieuThueOptional = chiTietPhieuThueRepository.findById(request.getIdChiTietPhieuThue());
            if (chiTietPhieuThueOptional.isEmpty()) {
                throw new Exception("ChiTietPhieuThue not found!");
            }

            chiTietSuDungDichVuNew.setDichVu(dichVuOptional.get());
            chiTietSuDungDichVuNew.setChiTietPhieuThue(chiTietPhieuThueOptional.get());
            chiTietSuDungDichVuNew.setSoLuong(request.getSoLuong());
            chiTietSuDungDichVuNew.setHoaDon(null);

            repository.save(chiTietSuDungDichVuNew);
        }else{
            ChiTietSuDungDichVu chiTietSuDungDichVu = chiTietSuDungDichVuOptional.get();
            if(chiTietSuDungDichVu.getSoLuong() + request.getSoLuong() <= 0){
                deleteChiTietSuDungDichVu(chiTietSuDungDichVu.getIdChiTietSuDungDichVuEmb());
            }else {
                chiTietSuDungDichVu.setSoLuong(chiTietSuDungDichVu.getSoLuong() + request.getSoLuong());
                repository.save(chiTietSuDungDichVu);
            }
        }
        return getChiTietSuDungDichVuByIdChiTietPhieuThue(request.getIdChiTietPhieuThue());
    }



    @Override
    public List<ChiTietSuDungDichVuResponse> getChiTietSuDungDichVuByIdChiTietPhieuThue(Integer idChiTietPhieuThue) throws Exception {
        List<ChiTietSuDungDichVu> chiTietSuDungDichVus = repository.findByChiTietPhieuThue_IdChiTietPhieuThue(idChiTietPhieuThue);
        List<ChiTietSuDungDichVuResponse> responses = new ArrayList<>();
        for (ChiTietSuDungDichVu chiTietSuDungDichVu: chiTietSuDungDichVus) {
            responses.add(convertChiTietSuDungDichVuToResponse(chiTietSuDungDichVu));
        }
        return responses;
    }

    private ChiTietSuDungDichVuResponse convertChiTietSuDungDichVuToResponse(ChiTietSuDungDichVu chiTietSuDungDichVu) {
        return new ChiTietSuDungDichVuResponse(chiTietSuDungDichVu.getIdChiTietSuDungDichVuEmb().getIdChiTietPhieuThue(),
                chiTietSuDungDichVu.getIdChiTietSuDungDichVuEmb().getIdDichVu(),
                chiTietSuDungDichVu.getDichVu().getTenDichVu(),
                chiTietSuDungDichVu.getSoLuong(),
                chiTietSuDungDichVu.getNgayTao(),
                chiTietSuDungDichVu.getDonGia(), chiTietSuDungDichVu.getDaThanhToan());
    }

    @Override
    public ChiTietSuDungDichVu updateChiTietDichVu(ChiTietSuDungDichVuRequest request) throws Exception {
        IdChiTietSuDungDichVuEmb idChiTietSuDungDichVuEmb =
                new IdChiTietSuDungDichVuEmb(request.getIdChiTietPhieuThue(), request.getIdDichVu());
        Optional<ChiTietSuDungDichVu> chiTietSuDungDichVuOptional = repository.findById(idChiTietSuDungDichVuEmb);
        if(chiTietSuDungDichVuOptional.isEmpty()){
            throw new Exception("ChiTietSuDungDichVu not found!");
        }
        ChiTietSuDungDichVu chiTietSuDungDichVu = chiTietSuDungDichVuOptional.get();
        if(request.getDonGia() != null)
            chiTietSuDungDichVu.setDonGia(request.getDonGia());
        if(request.getNgayTao() != null)
            chiTietSuDungDichVu.setNgayTao(request.getNgayTao());
        if(request.getDaThanhToan() != null)
            chiTietSuDungDichVu.setDaThanhToan(request.getDaThanhToan());
        if(request.getIdDichVu() != null) {
            Optional<DichVu> dichVuOptional = dichVuRepository.findById(request.getIdDichVu());
            if (dichVuOptional.isEmpty()) {
                throw new Exception("DichVu not found!");
            }
        }
        if(request.getIdChiTietPhieuThue() != null) {
            Optional<ChiTietPhieuThue> chiTietPhieuThueOptional = chiTietPhieuThueRepository.findById(request.getIdChiTietPhieuThue());
            if (chiTietPhieuThueOptional.isEmpty()) {
                throw new Exception("ChiTietPhieuThue not found!");
            }
        }
        if(request.getSoHoaDon() != null)
            chiTietSuDungDichVu.setHoaDon(hoaDonService.getHoaDonById(request.getSoHoaDon()));

        return repository.save(chiTietSuDungDichVu);
    }

    @Override
    public void deleteChiTietSuDungDichVu(IdChiTietSuDungDichVuEmb idChiTietSuDungDichVuEmb) throws Exception {
        /*IdChiTietSuDungDichVuEmb idChiTietSuDungDichVuEmb =
                new IdChiTietSuDungDichVuEmb(idChiTietPhieuThue, idDichVu);*/
        Optional<ChiTietSuDungDichVu> ChiTietSuDungDichVuOptional = repository.findById(idChiTietSuDungDichVuEmb);
        if(ChiTietSuDungDichVuOptional.isEmpty()){
            throw new Exception("ChiTietSuDungDichVu not found!");
        }
        repository.deleteById(idChiTietSuDungDichVuEmb);
    }

    @Override
    public ChiTietSuDungDichVu addHoaDonToChiTietSuDungDichVu(Integer idChiTietPhieuThue, Integer idDichVu, String soHoaDon) throws Exception {
        IdChiTietSuDungDichVuEmb idChiTietSuDungDichVuEmb =
                new IdChiTietSuDungDichVuEmb(idChiTietPhieuThue, idDichVu);
        Optional<ChiTietSuDungDichVu> chiTietSuDungDichVuOptional = repository.findById(idChiTietSuDungDichVuEmb);
        if(chiTietSuDungDichVuOptional.isEmpty()) throw new Exception("ChiTietSuDungDV not found.");
        ChiTietSuDungDichVu chiTietSuDungDichVu = chiTietSuDungDichVuOptional.get();
        HoaDon hoaDon = hoaDonService.getHoaDonById(soHoaDon);
        chiTietSuDungDichVu.setHoaDon(hoaDon);
        chiTietSuDungDichVu.setDaThanhToan(true);

        return repository.save(chiTietSuDungDichVu);
    }

    @Override
    public HoaDon getHoaDonInChiTietSuDungDichVu(Integer idChiTietPhieuThue, Integer idDichVu) throws Exception {
        IdChiTietSuDungDichVuEmb idChiTietSuDungDichVuEmb =
                new IdChiTietSuDungDichVuEmb(idChiTietPhieuThue, idDichVu);
        Optional<ChiTietSuDungDichVu> chiTietSuDungDichVuOptional = repository.findById(idChiTietSuDungDichVuEmb);
        if(chiTietSuDungDichVuOptional.isEmpty()) throw new Exception("ChiTietSuDungDV not found.");
        return chiTietSuDungDichVuOptional.get().getHoaDon();
    }

    @Override
    public List<ChiTietSuDungDichVu> thanhToanChiTietSuDungDVCuaChiTietPhieuThue(Integer idChiTietPhieuThue, String soHoaDon) throws Exception {
        List<ChiTietSuDungDichVu> chiTietSuDungDichVus = repository.findByChiTietPhieuThue_IdChiTietPhieuThue(idChiTietPhieuThue);
        for (ChiTietSuDungDichVu chiTietSuDungDichVu:chiTietSuDungDichVus) {
            addHoaDonToChiTietSuDungDichVu(idChiTietPhieuThue,
                    chiTietSuDungDichVu.getIdChiTietSuDungDichVuEmb().getIdDichVu(),
                    soHoaDon);
        }
        return chiTietSuDungDichVus;
    }
}
