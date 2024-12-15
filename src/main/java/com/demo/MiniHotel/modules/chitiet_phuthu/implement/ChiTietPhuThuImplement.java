package com.demo.MiniHotel.modules.chitiet_phuthu.implement;

import com.demo.MiniHotel.embedded.IdChiTietPhuThuEmb;
import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.CapNhatChiTietPhuThuRequest;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuPhongResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuRequest;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.service.IChiTietPhuThuService;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietDichVuPhongResponse;
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
import java.util.stream.Collectors;

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
        Optional<ChiTietPhuThu> chiTietPhuThuOptional = repository.findByChiTietPhieuThue_IdChiTietPhieuThueAndPhuThu_IdPhuThuAndDonGiaAndDaThanhToan(
                request.getIdChiTietPhieuThue(), request.getIdPhuThu(), request.getDonGia(), request.getDaThanhToan()
        );
        if(chiTietPhuThuOptional.isEmpty()) {
            ChiTietPhuThu chiTietPhuThuNew = new ChiTietPhuThu();
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
            chiTietPhuThu.setSoLuong(chiTietPhuThu.getSoLuong() + request.getSoLuong());
            repository.save(chiTietPhuThu);
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



    @Override
    public ChiTietPhuThu updateChiTietDichVu(ChiTietPhuThuRequest request) throws Exception {
        IdChiTietPhuThuEmb idChiTietPhuThuEmb =
                new IdChiTietPhuThuEmb(request.getIdChiTietPhieuThue(), request.getIdPhuThu());
        ChiTietPhuThu chiTietPhuThu = getChiTietPhuThuById(request.getIdChiTietPhuThu());
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
    public void deleteChiTietPhuThu(Integer idChiTietPhuThu) throws Exception {
        ChiTietPhuThu chiTietPhuThu = getChiTietPhuThuById(idChiTietPhuThu);
        repository.deleteById(chiTietPhuThu.getIdChiTietPhuThu());
    }

    @Override
    public ChiTietPhuThu addHoaDonToChiTietPhuThu(Integer idChiTietPhuThu, String soHoaDon) throws Exception {
        ChiTietPhuThu chiTietPhuThu = getChiTietPhuThuById(idChiTietPhuThu);
        HoaDon hoaDon = hoaDonService.getHoaDonById(soHoaDon);
        chiTietPhuThu.setHoaDon(hoaDon);
        chiTietPhuThu.setDaThanhToan(true);

        return repository.save(chiTietPhuThu);
    }

    @Override
    public HoaDon getHoaDonInChiTietPhuThu(Integer idChiTietPhuThu) throws Exception {
        return getChiTietPhuThuById(idChiTietPhuThu).getHoaDon();
    }

    @Override
    public List<ChiTietPhuThu> thanhToanChiTietPhuThuCuaChiTietPhieuThue(Integer idChiTietPhieuThue, String soHoaDon) throws Exception {
        List<ChiTietPhuThu> chiTietPhuThus = repository.findByChiTietPhieuThue_IdChiTietPhieuThue(idChiTietPhieuThue);
        for (ChiTietPhuThu chiTietPhuThu:chiTietPhuThus) {
            if(!chiTietPhuThu.getDaThanhToan()) {
                addHoaDonToChiTietPhuThu(chiTietPhuThu.getIdChiTietPhuThu(),
                        soHoaDon);
            }
        }
        return chiTietPhuThus;
    }

    @Override
    public long getTongTienChiTietPhuThu(Integer idChiTietPhieuThue) throws Exception {
        long tongTien = 0;
        List<ChiTietPhuThu> chiTietPhuThus = repository.findByChiTietPhieuThue_IdChiTietPhieuThue(idChiTietPhieuThue);
        for (ChiTietPhuThu phuThu: chiTietPhuThus) {
            if(!phuThu.getDaThanhToan())
                tongTien += phuThu.getDonGia() * phuThu.getSoLuong();
        }
        return tongTien;
    }

    @Override
    public List<ChiTietPhuThuPhongResponse> getChiTietPhuThuCuaPhieuThue(int idPhieuThue) {
        List<ChiTietPhuThu> chiTietPhuThus = repository.findByChiTietPhieuThue_PhieuThuePhong_IdPhieuThue(idPhieuThue);
        return chiTietPhuThus.stream().map(this::convertChiTietPhuThuPhongToResponse).collect(Collectors.toList());
    }

    @Override
    public ChiTietPhuThu getChiTietPhuThuById(int idChiTietPhuThu) {
        return repository.findById(idChiTietPhuThu).orElseThrow(
                () -> new AppException(ErrorCode.CHITIETPHUTHU_NOTFOUND)
        );
    }


    @Override
    public ChiTietPhuThu capNhatChiTietPhuThu(CapNhatChiTietPhuThuRequest request) {
        ChiTietPhuThu chiTietPhuThu =
                getChiTietPhuThuById(request.getIdChiTietPhuThu());

        chiTietPhuThu.setSoLuong(request.getSoLuong());
        return repository.save(chiTietPhuThu);
    }

    @Override
    public void xoaChiTietPhuThu(int idChiTietPhuThu) throws Exception {
        ChiTietPhuThu chiTietPhuThu = getChiTietPhuThuById(idChiTietPhuThu);
        repository.deleteById(chiTietPhuThu.getIdChiTietPhuThu());
    }

    private ChiTietPhuThuPhongResponse convertChiTietPhuThuPhongToResponse(ChiTietPhuThu chiTietPhuThu) {
        ChiTietPhieuThue chiTietPhieuThue = chiTietPhuThu.getChiTietPhieuThue();
        return ChiTietPhuThuPhongResponse.builder()
                .idChiTietPhuThu(chiTietPhuThu.getIdChiTietPhuThu())
                .idChiTietPhieuThue(chiTietPhieuThue.getIdChiTietPhieuThue())
                .idPhuThu(chiTietPhuThu.getPhuThu().getIdPhuThu())
                .noiDung(chiTietPhuThu.getPhuThu().getNoiDung())
                .maPhong(chiTietPhieuThue.getPhong().getMaPhong())
                .soLuong(chiTietPhuThu.getSoLuong())
                .ngayTao(chiTietPhuThu.getNgayTao())
                .donGia(chiTietPhuThu.getDonGia())
                .daThanhToan(chiTietPhuThu.getDaThanhToan())
                .build();
    }

    private ChiTietPhuThuResponse convertChiTietPhuThuToResponse(ChiTietPhuThu chiTietPhuThu) {
        return new ChiTietPhuThuResponse(
                chiTietPhuThu.getIdChiTietPhuThu(),
                chiTietPhuThu.getChiTietPhieuThue().getIdChiTietPhieuThue(),
                chiTietPhuThu.getPhuThu().getIdPhuThu(),
                chiTietPhuThu.getPhuThu().getNoiDung(),
                chiTietPhuThu.getSoLuong(),
                chiTietPhuThu.getNgayTao(),
                chiTietPhuThu.getDonGia(), chiTietPhuThu.getDaThanhToan(),
                chiTietPhuThu.getChiTietPhieuThue().getPhong().getMaPhong()
        );
    }
}
