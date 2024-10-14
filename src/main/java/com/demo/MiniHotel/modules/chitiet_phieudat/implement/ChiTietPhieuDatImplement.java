package com.demo.MiniHotel.modules.chitiet_phieudat.implement;

import com.demo.MiniHotel.embedded.IdChiTietPhieuDatEmb;
import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.ChiTietPhieuDat;
import com.demo.MiniHotel.model.HangPhong;
import com.demo.MiniHotel.model.PhieuDatPhong;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatRequest;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse2;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietUserResponse;
import com.demo.MiniHotel.modules.chitiet_phieudat.exception.LoginWrongException;
import com.demo.MiniHotel.modules.chitiet_phieudat.service.IChiTietPhieuDatService;
import com.demo.MiniHotel.modules.hangphong.service.IHangPhongService;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
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
    @Override
    public ChiTietPhieuDat addNewChiTietPhieuDat(ChiTietPhieuDatRequest request) throws Exception {
        ChiTietPhieuDat chiTietPhieuDat = new ChiTietPhieuDat();
        chiTietPhieuDat.setIdChiTietPhieuDatEmb(new IdChiTietPhieuDatEmb(request.getIdPhieuDat(), request.getIdHangPhong()));
        HangPhong hangPhong = hangPhongService.getHangPhongById(request.getIdHangPhong());
        PhieuDatPhong phieuDatPhong = phieuDatPhongRepository.findById(request.getIdPhieuDat()).get();
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
    public List<ChiTietPhieuDat> getAllChiTietPhieuDat() {
        return repository.findAll();
    }

    @Override
    public ChiTietPhieuDatResponse getChiTietPhieuDatById(IdChiTietPhieuDatEmb id) throws Exception {
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
        response.setTenHangPhong(chiTietPhieuDat.getHangPhong().getTenHangPhong());
        response.setSoLuong(chiTietPhieuDat.getSoLuong());
        response.setSoLuongTrong(soLuongTrong);
        response.setDonGia(chiTietPhieuDat.getDonGia());
        response.setIdHangPhong(chiTietPhieuDat.getHangPhong().getIdHangPhong());
        return response;
    }

    @Override
    public ChiTietUserResponse convertChiTietUserResponse(ChiTietPhieuDat chiTietPhieuDat) throws Exception {
        ChiTietUserResponse response = new ChiTietUserResponse();
        response.setIdHangPhong(chiTietPhieuDat.getIdChiTietPhieuDatEmb().getIdHangPhong());
        response.setTenHangPhong(chiTietPhieuDat.getHangPhong().getTenHangPhong());
        response.setSoLuong(chiTietPhieuDat.getSoLuong());
        response.setHinhAnh(hangPhongService.getHinhAnhByIdHangPhong(
                chiTietPhieuDat.getIdChiTietPhieuDatEmb().getIdHangPhong()));
        response.setDonGia(chiTietPhieuDat.getDonGia());
        return response;
    }

    @Override
    public List<ChiTietPhieuDat> getChiTietPhieuDatByIdPhieuDat(Integer idPhieuDat) throws Exception {
        return repository.findByPhieuDatPhong_IdPhieuDat(idPhieuDat);
    }

    @Override
    public ChiTietPhieuDat updateSoLuong(ChiTietPhieuDatRequest request, IdChiTietPhieuDatEmb id) throws Exception {
        Optional<ChiTietPhieuDat> chiTietPhieuDatOptional = repository.findById(id);
        if(chiTietPhieuDatOptional.isEmpty()){
            throw new Exception("ChiTietPhieuDat not found!");
        }
        ChiTietPhieuDat chiTietPhieuDat = chiTietPhieuDatOptional.get();
        chiTietPhieuDat.setSoLuong(request.getSoLuong());
        return repository.save(chiTietPhieuDat);
    }

    @Override
    public void deleteChiTietPhieuDat(IdChiTietPhieuDatEmb id) throws Exception {
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
            repository.deleteById(chiTietPhieuDat.getIdChiTietPhieuDatEmb());
        }
    }

    @Override
    public void xoaChiTietPhieuDat(ChiTietPhieuDatRequest chiTietPhieuDatRequest) {
        IdChiTietPhieuDatEmb idChiTietPhieuDatEmb = new IdChiTietPhieuDatEmb();
        idChiTietPhieuDatEmb.setIdPhieuDat(chiTietPhieuDatRequest.getIdPhieuDat());
        idChiTietPhieuDatEmb.setIdHangPhong(chiTietPhieuDatRequest.getIdHangPhong());

        Optional<ChiTietPhieuDat> chiTietPhieuDatOptional = repository.findById(idChiTietPhieuDatEmb);
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
}
