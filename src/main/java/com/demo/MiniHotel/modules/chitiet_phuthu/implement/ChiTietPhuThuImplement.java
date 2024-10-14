package com.demo.MiniHotel.modules.chitiet_phuthu.implement;

import com.demo.MiniHotel.embedded.IdChiTietPhuThuEmb;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuRequest;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.service.IChiTietPhuThuService;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import com.demo.MiniHotel.repository.ChiTietPhieuThueRepository;
import com.demo.MiniHotel.repository.ChiTietPhuThuRepository;
import com.demo.MiniHotel.repository.DichVuRepository;
import com.demo.MiniHotel.repository.PhuThuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChiTietPhuThuImplement implements IChiTietPhuThuService {
    private final ChiTietPhuThuRepository repository;
    private final ChiTietPhieuThueRepository chiTietPhieuThueRepository;
    private final PhuThuRepository phuThuRepository;
    private final IHoaDonService hoaDonService;
    @Override
    public List<ChiTietPhuThuResponse> themChiTietPhuThu(ChiTietPhuThuRequest request) throws Exception {
        //Kiem tra chi tiet phiếu thuê có sử dụng dịch vụ này hay chưa
        //Nếu đã sử dụng -> thêm số lượng, nếu chưa -> thêm mới
        IdChiTietPhuThuEmb idChiTietPhuThuEmb =
                new IdChiTietPhuThuEmb(request.getIdChiTietPhieuThue(), request.getIdPhuThu());
        Optional<ChiTietPhuThu> chiTietPhuThuOptional = repository.findById(idChiTietPhuThuEmb);
        if(chiTietPhuThuOptional.isEmpty()) {
            ChiTietPhuThu chiTietPhuThuNew = new ChiTietPhuThu();
            chiTietPhuThuNew.setIdChiTietPhuThuEmb(idChiTietPhuThuEmb);
            chiTietPhuThuNew.setDonGia(request.getDonGia());
            chiTietPhuThuNew.setNgayTao(LocalDate.now());
            chiTietPhuThuNew.setDaThanhToan(request.getDaThanhToan());

            Optional<PhuThu> phuThuOptional = phuThuRepository.findById(request.getIdPhuThu());
            if (phuThuOptional.isEmpty()) {
                throw new Exception("PhuThu not found!");
            }
            Optional<ChiTietPhieuThue> chiTietPhieuThueOptional = chiTietPhieuThueRepository.findById(request.getIdChiTietPhieuThue());
            if (chiTietPhieuThueOptional.isEmpty()) {
                throw new Exception("ChiTietPhieuThue not found!");
            }

            chiTietPhuThuNew.setPhuThu(phuThuOptional.get());
            chiTietPhuThuNew.setChiTietPhieuThue(chiTietPhieuThueOptional.get());
            chiTietPhuThuNew.setSoLuong(request.getSoLuong());
            chiTietPhuThuNew.setHoaDon(null);

            repository.save(chiTietPhuThuNew);
        }else{
            ChiTietPhuThu chiTietPhuThu = chiTietPhuThuOptional.get();
            chiTietPhuThu.setDaThanhToan(request.getDaThanhToan());
            chiTietPhuThu.setDonGia(request.getDonGia());
            if(chiTietPhuThu.getSoLuong() + request.getSoLuong() <= 0){
                deleteChiTietPhuThu(chiTietPhuThu.getIdChiTietPhuThuEmb());
            }else {
                chiTietPhuThu.setSoLuong(chiTietPhuThu.getSoLuong() + request.getSoLuong());
                repository.save(chiTietPhuThu);
            }
        }
        return getChiTietPhuThuByIdChiTietPhieuThue(request.getIdChiTietPhieuThue());
    }



    @Override
    public List<ChiTietPhuThuResponse> getChiTietPhuThuByIdChiTietPhieuThue(Integer idChiTietPhieuThue) throws Exception {
        List<ChiTietPhuThu> chiTietPhuThus = repository.findByChiTietPhieuThue_IdChiTietPhieuThue(idChiTietPhieuThue);
        List<ChiTietPhuThuResponse> responses = new ArrayList<>();
        for (ChiTietPhuThu chiTietPhuThu: chiTietPhuThus) {
            responses.add(convertChiTietPhuThuToResponse(chiTietPhuThu));
        }
        return responses;
    }

    private ChiTietPhuThuResponse convertChiTietPhuThuToResponse(ChiTietPhuThu chiTietPhuThu) {
        return new ChiTietPhuThuResponse(chiTietPhuThu.getIdChiTietPhuThuEmb().getIdChiTietPhieuThue(),
                chiTietPhuThu.getIdChiTietPhuThuEmb().getIdPhuThu(),
                chiTietPhuThu.getPhuThu().getNoiDung(),
                chiTietPhuThu.getSoLuong(),
                chiTietPhuThu.getNgayTao(),
                chiTietPhuThu.getDonGia(), chiTietPhuThu.getDaThanhToan());
    }

    @Override
    public ChiTietPhuThu updateChiTietDichVu(ChiTietPhuThuRequest request) throws Exception {
        IdChiTietPhuThuEmb idChiTietPhuThuEmb =
                new IdChiTietPhuThuEmb(request.getIdChiTietPhieuThue(), request.getIdPhuThu());
        Optional<ChiTietPhuThu> chiTietPhuThuOptional = repository.findById(idChiTietPhuThuEmb);
        if(chiTietPhuThuOptional.isEmpty()){
            throw new Exception("ChiTietPhuThu not found!");
        }
        ChiTietPhuThu chiTietPhuThu = chiTietPhuThuOptional.get();
        if(request.getDonGia() != null)
            chiTietPhuThu.setDonGia(request.getDonGia());
        if(request.getNgayTao() != null)
            chiTietPhuThu.setNgayTao(request.getNgayTao());
        if(request.getDaThanhToan() != null)
            chiTietPhuThu.setDaThanhToan(request.getDaThanhToan());
        if(request.getIdPhuThu() != null) {
            Optional<PhuThu> phuThuOptional = phuThuRepository.findById(request.getIdPhuThu());
            if (phuThuOptional.isEmpty()) {
                throw new Exception("PhuThu not found!");
            }
        }
        if(request.getIdChiTietPhieuThue() != null) {
            Optional<ChiTietPhieuThue> chiTietPhieuThueOptional = chiTietPhieuThueRepository.findById(request.getIdChiTietPhieuThue());
            if (chiTietPhieuThueOptional.isEmpty()) {
                throw new Exception("ChiTietPhieuThue not found!");
            }
        }
        if(request.getSoHoaDon() != null)
            chiTietPhuThu.setHoaDon(hoaDonService.getHoaDonById(request.getSoHoaDon()));

        return repository.save(chiTietPhuThu);
    }

    @Override
    public void deleteChiTietPhuThu(IdChiTietPhuThuEmb idChiTietPhuThuEmb) throws Exception {
        /*IdChiTietPhuThuEmb idChiTietPhuThuEmb =
                new IdChiTietPhuThuEmb(idChiTietPhieuThue, idDichVu);*/
        Optional<ChiTietPhuThu> ChiTietPhuThuOptional = repository.findById(idChiTietPhuThuEmb);
        if(ChiTietPhuThuOptional.isEmpty()){
            throw new Exception("ChiTietPhuThu not found!");
        }
        repository.deleteById(idChiTietPhuThuEmb);
    }

    @Override
    public ChiTietPhuThu addHoaDonToChiTietPhuThu(Integer idChiTietPhieuThue, Integer idPhuThu, String soHoaDon) throws Exception {
        IdChiTietPhuThuEmb idChiTietPhuThuEmb =
                new IdChiTietPhuThuEmb(idChiTietPhieuThue, idPhuThu);
        Optional<ChiTietPhuThu> chiTietPhuThuOptional = repository.findById(idChiTietPhuThuEmb);
        if(chiTietPhuThuOptional.isEmpty()) throw new Exception("ChiTietPhuThu not found.");
        ChiTietPhuThu chiTietPhuThu = chiTietPhuThuOptional.get();
        HoaDon hoaDon = hoaDonService.getHoaDonById(soHoaDon);
        chiTietPhuThu.setHoaDon(hoaDon);
        chiTietPhuThu.setDaThanhToan(true);

        return repository.save(chiTietPhuThu);
    }

    @Override
    public HoaDon getHoaDonInChiTietPhuThu(Integer idChiTietPhieuThue, Integer idDichVu) throws Exception {
        IdChiTietPhuThuEmb idChiTietPhuThuEmb =
                new IdChiTietPhuThuEmb(idChiTietPhieuThue, idDichVu);
        Optional<ChiTietPhuThu> chiTietPhuThuOptional = repository.findById(idChiTietPhuThuEmb);
        if(chiTietPhuThuOptional.isEmpty()) throw new Exception("ChiTietSuDungDV not found.");
        return chiTietPhuThuOptional.get().getHoaDon();
    }

    @Override
    public List<ChiTietPhuThu> thanhToanChiTietPhuThuCuaChiTietPhieuThue(Integer idChiTietPhieuThue, String soHoaDon) throws Exception {
        List<ChiTietPhuThu> chiTietPhuThus = repository.findByChiTietPhieuThue_IdChiTietPhieuThue(idChiTietPhieuThue);
        for (ChiTietPhuThu chiTietPhuThu:chiTietPhuThus) {
            if(!chiTietPhuThu.getDaThanhToan()) {
                addHoaDonToChiTietPhuThu(idChiTietPhieuThue,
                        chiTietPhuThu.getIdChiTietPhuThuEmb().getIdPhuThu(),
                        soHoaDon);
            }
        }
        return chiTietPhuThus;
    }


}
