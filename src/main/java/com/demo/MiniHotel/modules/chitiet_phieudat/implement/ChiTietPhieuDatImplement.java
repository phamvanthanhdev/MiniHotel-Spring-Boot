package com.demo.MiniHotel.modules.chitiet_phieudat.implement;

import com.demo.MiniHotel.embedded.IdChiTietPhieuDatEmb;
import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.ChiTietPhieuDat;
import com.demo.MiniHotel.model.HangPhong;
import com.demo.MiniHotel.model.PhieuDatPhong;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.*;
import com.demo.MiniHotel.modules.chitiet_phieudat.exception.LoginWrongException;
import com.demo.MiniHotel.modules.chitiet_phieudat.service.IChiTietPhieuDatService;
import com.demo.MiniHotel.modules.hangphong.service.IHangPhongService;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.repository.ChiTietPhieuDatRepository;
import com.demo.MiniHotel.repository.PhieuDatPhongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChiTietPhieuDatImplement implements IChiTietPhieuDatService {
    private final ChiTietPhieuDatRepository repository;
//    private final IPhieuThueService phieuDatService;
    private final PhieuDatPhongRepository phieuDatPhongRepository;
    private final IHangPhongService hangPhongService;
    private final IThongTinHangPhongService thongTinHangPhongService;
    @Override
    public ChiTietPhieuDat addNewChiTietPhieuDat(ChiTietPhieuDatRequest request) throws Exception {
        ChiTietPhieuDat chiTietPhieuDat = new ChiTietPhieuDat();
//        chiTietPhieuDat.setIdChiTietPhieuDatEmb(new IdChiTietPhieuDatEmb(request.getIdPhieuDat(), request.getIdHangPhong()));
        HangPhong hangPhong = hangPhongService.getHangPhongById(request.getIdHangPhong());
        PhieuDatPhong phieuDatPhong = phieuDatPhongRepository.findById(request.getIdPhieuDat())
                .orElseThrow(() -> new AppException(ErrorCode.PHIEUDAT_NOTFOUND));
        chiTietPhieuDat.setPhieuDatPhong(phieuDatPhong);
        chiTietPhieuDat.setHangPhong(hangPhong);
        chiTietPhieuDat.setDonGia(request.getDonGia());
        chiTietPhieuDat.setSoLuong(request.getSoLuong());

        return repository.save(chiTietPhieuDat);
    }

    @Override
    public void addListChiTietPhieuDat(List<ChiTietPhieuDatRequest> requests) throws Exception {
        for (ChiTietPhieuDatRequest request: requests) {
            addNewChiTietPhieuDat(request);
        }
    }

    @Override
    public ChiTietPhieuDat capNhatSoLuongChiTietPhieuDat(ChiTietPhieuDatRequest request) throws Exception {
        ChiTietPhieuDat chiTietPhieuDat = repository
                .findByPhieuDatPhong_IdPhieuDatAndHangPhong_IdHangPhongAndDonGia(request.getIdPhieuDat(), request.getIdHangPhong(), request.getDonGia())
                .orElseThrow(() -> new AppException(ErrorCode.CHITIETPHIEUDAT_NOTFOUND));

//        int soLuongMoi = chiTietPhieuDat.getSoLuong() + request.getSoLuong();
//
//        PhieuDatPhong phieuDat = phieuDatPhongRepository.findById(request.getIdPhieuDat())
//                .orElseThrow(() -> new AppException(ErrorCode.PHIEUDAT_NOTFOUND));
//        int soLuongTrong = thongTinHangPhongService.laySoLuongHangPhongTrong(
//                phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong(), request.getIdHangPhong());
//        if (soLuongTrong < soLuongMoi) {
//            throw new AppException(ErrorCode.HANGPHONG_NOT_ENOUGH);
//        }


        chiTietPhieuDat.setSoLuong(chiTietPhieuDat.getSoLuong() + request.getSoLuong());



        return repository.save(chiTietPhieuDat);
    }
    @Override
    public void capNhatSoLuongListChiTietPhieuDat(List<ChiTietPhieuDatRequest> requests) throws Exception {
        for (ChiTietPhieuDatRequest request: requests) {
            capNhatSoLuongChiTietPhieuDat(request);
        }
    }

    @Override
    public boolean kiemTraChiTietPhieuDat(Integer idPhieuDat, Integer idHangPhong, long donGia) {
        Optional<ChiTietPhieuDat> chiTietOptional = repository
                .findByPhieuDatPhong_IdPhieuDatAndHangPhong_IdHangPhongAndDonGia(idPhieuDat, idHangPhong, donGia);

        return chiTietOptional.isPresent();
    }

    @Override
    public List<ChiTietPhieuDat> getAllChiTietPhieuDat() {
        return repository.findAll();
    }

    @Override
    public ChiTietPhieuDatResponse getChiTietPhieuDatResponseById(/*IdChiTietPhieuDatEmb id*/ int id) throws Exception {
        Optional<ChiTietPhieuDat> chiTietPhieuDatOptional = repository.findById(id);
        if(chiTietPhieuDatOptional.isEmpty()){
            throw new Exception("ChiTietPhieuDat not found!");
        }
        ChiTietPhieuDat chiTietPhieuDat = chiTietPhieuDatOptional.get();
        ChiTietPhieuDatResponse response = new ChiTietPhieuDatResponse();
//        response.setHinhAnh(hangPhongService.getHinhAnhByIdHangPhong(
//                chiTietPhieuDat.getIdChiTietPhieuDatEmb().getIdHangPhong()));
        response.setDonGia(chiTietPhieuDat.getDonGia());
        return response;
    }


    @Override
    public ChiTietPhieuDatResponse convertChiTietPhieuDatResponse(ChiTietPhieuDat chiTietPhieuDat) throws Exception {
        ChiTietPhieuDatResponse response = new ChiTietPhieuDatResponse();
        response.setIdChiTietPhieuDat(chiTietPhieuDat.getIdChiTietPhieuDat());
        response.setTenHangPhong(chiTietPhieuDat.getHangPhong().getTenHangPhong());
        response.setSoLuong(chiTietPhieuDat.getSoLuong());
//        response.setHinhAnh(hangPhongService.getHinhAnhByIdHangPhong(
//                chiTietPhieuDat.getIdChiTietPhieuDatEmb().getIdHangPhong()));
        response.setDonGia(chiTietPhieuDat.getDonGia());
        return response;
    }

    @Override
    public ChiTietPhieuDatResponse2 convertChiTietPhieuDatResponse2(ChiTietPhieuDat chiTietPhieuDat, int soLuongTrong) throws Exception {
        ChiTietPhieuDatResponse2 response = new ChiTietPhieuDatResponse2();
        response.setIdChiTietPhieuDat(chiTietPhieuDat.getIdChiTietPhieuDat());
        response.setTenHangPhong(chiTietPhieuDat.getHangPhong().getTenHangPhong());
        response.setSoLuong(chiTietPhieuDat.getSoLuong());
        response.setSoLuongTrong(soLuongTrong);
        response.setDonGia(chiTietPhieuDat.getDonGia());
        response.setIdHangPhong(chiTietPhieuDat.getHangPhong().getIdHangPhong());
        return response;
    }

    @Override
    public ChiTietUserResponse convertChiTietUserResponse(ChiTietPhieuDat chiTietPhieuDat, long soNgayDat) throws Exception {
        ChiTietUserResponse response = new ChiTietUserResponse();
        response.setIdChiTietPhieuDat(chiTietPhieuDat.getIdChiTietPhieuDat());
        response.setIdHangPhong(chiTietPhieuDat.getHangPhong().getIdHangPhong());
        response.setTenHangPhong(chiTietPhieuDat.getHangPhong().getTenHangPhong());
        response.setSoLuong(chiTietPhieuDat.getSoLuong());
        response.setHinhAnh(hangPhongService.getHinhAnhByIdHangPhong(
                chiTietPhieuDat.getHangPhong().getIdHangPhong()));
        response.setDonGia(chiTietPhieuDat.getDonGia());
        response.setTongTien(chiTietPhieuDat.getDonGia() * chiTietPhieuDat.getSoLuong() * soNgayDat);
        return response;
    }

    @Override
    public List<ChiTietPhieuDat> getChiTietPhieuDatByIdPhieuDat(Integer idPhieuDat) throws Exception {
        return repository.findByPhieuDatPhong_IdPhieuDat(idPhieuDat);
    }

    @Override
    public ChiTietPhieuDat updateSoLuong(ChiTietPhieuDatRequest request, /*IdChiTietPhieuDatEmb id*/ int id) throws Exception {
        Optional<ChiTietPhieuDat> chiTietPhieuDatOptional = repository.findById(id);
        if(chiTietPhieuDatOptional.isEmpty()){
            throw new Exception("ChiTietPhieuDat not found!");
        }
        ChiTietPhieuDat chiTietPhieuDat = chiTietPhieuDatOptional.get();
        chiTietPhieuDat.setSoLuong(request.getSoLuong());
        return repository.save(chiTietPhieuDat);
    }

    @Override
    public void deleteChiTietPhieuDat(/*IdChiTietPhieuDatEmb id*/ int id) throws Exception {
        Optional<ChiTietPhieuDat> ChiTietPhieuDatOptional = repository.findById(id);
        if(ChiTietPhieuDatOptional.isEmpty()){
            throw new Exception("ChiTietPhieuDat not found!");
        }
        repository.deleteById(id);
    }

    @Override
    public void deleteChiTietPhieuDatByPhieuDatId(Integer idPhieuDat) throws Exception {
        List<ChiTietPhieuDat> chiTietPhieuDats = repository.findByPhieuDatPhong_IdPhieuDat(idPhieuDat);
        if(chiTietPhieuDats.isEmpty()){
            throw new Exception("ChiTietPhieuDat not found!");
        }
        for (ChiTietPhieuDat chiTietPhieuDat:chiTietPhieuDats) {
            repository.deleteById(/*chiTietPhieuDat.getIdChiTietPhieuDatEmb()*/ chiTietPhieuDat.getIdChiTietPhieuDat());
        }
    }

    @Override
    public void xoaChiTietPhieuDat(ChiTietPhieuDatRequest chiTietPhieuDatRequest) {
//        IdChiTietPhieuDatEmb idChiTietPhieuDatEmb = new IdChiTietPhieuDatEmb();
//        idChiTietPhieuDatEmb.setIdPhieuDat(chiTietPhieuDatRequest.getIdPhieuDat());
//        idChiTietPhieuDatEmb.setIdHangPhong(chiTietPhieuDatRequest.getIdHangPhong());

        Optional<ChiTietPhieuDat> chiTietPhieuDatOptional = repository.findById(chiTietPhieuDatRequest.getIdChiTietPhieuDat());
        if(chiTietPhieuDatOptional.isEmpty())
            throw new AppException(ErrorCode.CTPHIEUDAT_NOT_FOUND);

        ChiTietPhieuDat chiTietPhieuDat = chiTietPhieuDatOptional.get();
        int soLuongConLai = chiTietPhieuDat.getSoLuong() - chiTietPhieuDatRequest.getSoLuong();
        if(soLuongConLai <= 0) {
            repository.delete(chiTietPhieuDatOptional.get());
        } else {
            chiTietPhieuDat.setSoLuong(soLuongConLai);
            repository.save(chiTietPhieuDat);
        }
    }

    @Override
    public CapNhatChiTietPhieuDatResponse convertCapNhatChiTietPhieuDatResponse(ChiTietPhieuDat chiTietPhieuDat, long soNgayDat, int soLuongTrong) {
        return CapNhatChiTietPhieuDatResponse.builder()
                .idChiTietPhieuDat(chiTietPhieuDat.getIdChiTietPhieuDat())
                .idPhieuDat(chiTietPhieuDat.getPhieuDatPhong().getIdPhieuDat())
                .idHangPhong(chiTietPhieuDat.getHangPhong().getIdHangPhong())
                .tenHangPhong(chiTietPhieuDat.getHangPhong().getTenHangPhong())
                .soLuong(chiTietPhieuDat.getSoLuong())
                .donGia(chiTietPhieuDat.getDonGia())
                .tongTien(chiTietPhieuDat.getDonGia() * chiTietPhieuDat.getSoLuong() * soNgayDat)
                .soLuongTrong(soLuongTrong)
                .soNgayThue(soNgayDat)
                .build();
    }

    @Override
    public ChiTietPhieuDat getChiTietPhieuDatById(/*IdChiTietPhieuDatEmb id*/int id) throws Exception {
        Optional<ChiTietPhieuDat> chiTietPhieuDatOptional = repository.findById(id);
        if(chiTietPhieuDatOptional.isEmpty()){
            throw new Exception("ChiTietPhieuDat not found!");
        }
        return chiTietPhieuDatOptional.get();
    }

    @Override
    public ChiTietPhieuDat capNhatChiTietPhieuDat(CapNhatChiTietPhieuDatRequest request) throws Exception {
        ChiTietPhieuDat chiTietPhieuDat = getChiTietPhieuDatById(
                /*new IdChiTietPhieuDatEmb(request.getIdPhieuDat(), request.getIdHangPhong())*/ request.getIdChiTietPhieuDat());
        int soLuongBanDau = chiTietPhieuDat.getSoLuong(); // Số lượng khi chưa thay đổi

        if(soLuongBanDau < request.getSoLuong()){ // đã bổ sung => cần kiểm tra
            if (!thongTinHangPhongService.kiemTraPhongHangPhongTrong(
                    request.getIdHangPhong(),
                    chiTietPhieuDat.getPhieuDatPhong().getNgayBatDau(),
                    chiTietPhieuDat.getPhieuDatPhong().getNgayTraPhong(),
                    request.getSoLuong() - soLuongBanDau)) { // Kiểm tra số lượng tăng thêm
                throw new AppException(ErrorCode.HANGPHONG_NOT_ENOUGH);
            }
        }

        chiTietPhieuDat.setSoLuong(request.getSoLuong());
        chiTietPhieuDat.setDonGia(request.getDonGia());

        return repository.save(chiTietPhieuDat);
    }

}
