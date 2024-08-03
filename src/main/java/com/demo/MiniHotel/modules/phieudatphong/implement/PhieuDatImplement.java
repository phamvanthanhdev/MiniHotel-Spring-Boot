package com.demo.MiniHotel.modules.phieudatphong.implement;

import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatRequest;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.ChiTietPhieuDatResponse2;
import com.demo.MiniHotel.modules.chitiet_phieudat.service.IChiTietPhieuDatService;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.phieudatphong.dto.ChiTietRequest;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatRequest;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.PhieuDatThoiGianResponse;
import com.demo.MiniHotel.modules.phieudatphong.exception.SoLuongPhongTrongException;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongUserResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.repository.PhieuDatPhongRepository;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhieuDatImplement implements IPhieuDatService {
    private final PhieuDatPhongRepository repository;
    private final IKhachHangService khachHangService;
    private final INhanVienService nhanVienService;
    private final IChiTietPhieuDatService chiTietPhieuDatService;
    private final IThongTinHangPhongService thongTinHangPhongService;
    @Override
    public PhieuDatPhong addNewPhieuDatPhong(PhieuDatRequest request) throws Exception {
        PhieuDatPhong phieuDatPhong = new PhieuDatPhong();
        phieuDatPhong.setNgayBatDau(request.getNgayBatDau());
        phieuDatPhong.setNgayTraPhong(request.getNgayTraPhong());
        phieuDatPhong.setGhiChu(request.getGhiChu());
        phieuDatPhong.setNgayTao(request.getNgayTao());
        phieuDatPhong.setTienTamUng(request.getTienTamUng());
        phieuDatPhong.setTrangThaiHuy(false);

        KhachHang khachHang = khachHangService.getKhachHangById(request.getIdKhachHang());
        phieuDatPhong.setKhachHang(khachHang);

        //Kiểm tra số lượng hạng phòng trống có đủ để đặt
        for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
            if (!thongTinHangPhongService.kiemTraPhongHangPhongTrong(chiTietRequest.getIdHangPhong(), request.getNgayBatDau(),
                    request.getNgayTraPhong(), chiTietRequest.getSoLuong())) {
                throw new SoLuongPhongTrongException("Số lượng phòng trống còn lại không đủ để đặt");
            }
        }

        PhieuDatPhong newPhieuDat = repository.save(phieuDatPhong);
        //Luu chi tiet phieu dat
        for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
            ChiTietPhieuDatRequest chiTietPhieuDatRequest = new ChiTietPhieuDatRequest();
            chiTietPhieuDatRequest.setIdPhieuDat(newPhieuDat.getIdPhieuDat());
            chiTietPhieuDatRequest.setIdHangPhong(chiTietRequest.getIdHangPhong());
            chiTietPhieuDatRequest.setSoLuong(chiTietRequest.getSoLuong());
            Long donGia = thongTinHangPhongService.getDonGiaThongTinHangPhongById(chiTietRequest.getIdHangPhong());
            chiTietPhieuDatRequest.setDonGia(donGia);

            chiTietPhieuDatService.addNewChiTietPhieuDat(chiTietPhieuDatRequest);
        }

        return newPhieuDat;
    }

    @Override
    public List<PhieuDatPhong> getAllPhieuDatPhong() {
        return repository.findAll();
    }

    @Override
    public PhieuDatPhong getPhieuDatPhongById(Integer id) throws Exception {
        Optional<PhieuDatPhong> PhieuDatPhongOptional = repository.findById(id);
        if(PhieuDatPhongOptional.isEmpty()){
            throw new Exception("PhieuDatPhong not found.");
        }

        return PhieuDatPhongOptional.get();
    }

    @Override
    public PhieuDatResponse getPhieuDatResponseById(Integer id) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(id);
        return getPhieuDatResponse(phieuDatPhong);
    }

    @Override
    public List<PhieuDatResponse> getPhieuDatPhongByIdKhachHang(Integer idKhachHang) throws Exception {
        KhachHang khachHang = khachHangService.getKhachHangById(idKhachHang);
        List<PhieuDatPhong> phieuDatPhongs = repository.findByKhachHang_IdKhachHang(khachHang.getIdKhachHang());
        List<PhieuDatResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDat:phieuDatPhongs) {
            responses.add(getPhieuDatResponse(phieuDat));
        }
        return responses;
    }

    @Override
    public PhieuDatPhong updatePhieuDatPhong(PhieuDatRequest request, Integer id) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(id);

        phieuDatPhong.setNgayBatDau(request.getNgayBatDau());
        phieuDatPhong.setNgayTraPhong(request.getNgayTraPhong());
        phieuDatPhong.setGhiChu(request.getGhiChu());
        phieuDatPhong.setNgayTao(request.getNgayTao());
        phieuDatPhong.setTienTamUng(request.getTienTamUng());

        KhachHang khachHang = khachHangService.getKhachHangById(request.getIdKhachHang());
        phieuDatPhong.setKhachHang(khachHang);

        if(request.getIdNhanVien() != null){
            NhanVien nhanVien = nhanVienService.getNhanVienById(request.getIdNhanVien());
            phieuDatPhong.setNhanVien(nhanVien);
        }

        return repository.save(phieuDatPhong);
    }

    @Override
    public void deletePhieuDatPhong(Integer id) throws Exception {
        Optional<PhieuDatPhong> PhieuDatPhongOptional = repository.findById(id);
        if(PhieuDatPhongOptional.isEmpty()){
            throw new Exception("PhieuDatPhong not found.");
        }

        repository.deleteById(id);
    }

    @Override
    public List<ChiTietPhieuDatResponse> getChiTietPhieuDatsByIdPhieuDat(Integer idPhieuDat) throws Exception {
        List<ChiTietPhieuDat> chiTietPhieuDats = chiTietPhieuDatService.getChiTietPhieuDatByIdPhieuDat(idPhieuDat);
        List<ChiTietPhieuDatResponse> responses = new ArrayList<>();
        for (ChiTietPhieuDat chitiet: chiTietPhieuDats) {
            ChiTietPhieuDatResponse response = chiTietPhieuDatService.convertChiTietPhieuDatResponse(chitiet);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<ChiTietPhieuDatResponse2> getChiTietPhieuDatsByIdPhieuDat2(Integer idPhieuDat) throws Exception {
        List<ChiTietPhieuDat> chiTietPhieuDats = chiTietPhieuDatService.getChiTietPhieuDatByIdPhieuDat(idPhieuDat);
        List<ChiTietPhieuDatResponse2> responses2 = new ArrayList<>();
        for (ChiTietPhieuDat chitiet: chiTietPhieuDats) {
            PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(idPhieuDat);
            int soLuongTrong = thongTinHangPhongService.laySoLuongHangPhongTrong(phieuDatPhong.getNgayBatDau(),
                    phieuDatPhong.getNgayTraPhong(), chitiet.getHangPhong().getIdHangPhong());
            soLuongTrong += chitiet.getSoLuong();
            ChiTietPhieuDatResponse2 response2 = chiTietPhieuDatService.convertChiTietPhieuDatResponse2(chitiet, soLuongTrong);
            responses2.add(response2);
        }
        return responses2;
    }

    @Autowired
    private EntityManager entityManager;
    @Override
    public List<PhieuDatThoiGianResponse> getPhieuDatPhongTheoNgay(LocalDate ngay) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PhieuDatTheoNgay", PhieuDatPhong.class)
                .registerStoredProcedureParameter("thoi_gian", LocalDate.class, ParameterMode.IN)
                .setParameter("thoi_gian", ngay);
        List<PhieuDatPhong> phieuDatPhongs = query.getResultList();
        List<PhieuDatThoiGianResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDatPhong: phieuDatPhongs) {
            responses.add(convertPhieuDatThoiGianResponse(phieuDatPhong));
        }
        return responses;
    }


    public PhieuDatResponse getPhieuDatResponse(PhieuDatPhong phieuDatPhong) throws Exception {
        return new PhieuDatResponse(
                phieuDatPhong.getIdPhieuDat(),
                phieuDatPhong.getNgayBatDau(),
                phieuDatPhong.getNgayTraPhong(),
                phieuDatPhong.getGhiChu(),
                phieuDatPhong.getNgayTao(),
                phieuDatPhong.getTienTamUng(),
                phieuDatPhong.getKhachHang().getIdKhachHang(),
                phieuDatPhong.getNhanVien() == null ? null :
                        phieuDatPhong.getNhanVien().getIdNhanVien(),
                phieuDatPhong.getTrangThaiHuy()
        );
    }

    public PhieuDatThoiGianResponse convertPhieuDatThoiGianResponse(PhieuDatPhong phieuDatPhong){
        String tenKhachHang = phieuDatPhong.getKhachHang().getHoTen();
        String cmnd = phieuDatPhong.getKhachHang().getCmnd();
        return new PhieuDatThoiGianResponse(
                phieuDatPhong.getIdPhieuDat(),
                phieuDatPhong.getNgayBatDau(),
                phieuDatPhong.getNgayTraPhong(),
                phieuDatPhong.getGhiChu(),
                phieuDatPhong.getNgayTao(),
                phieuDatPhong.getTienTamUng(),
                phieuDatPhong.getKhachHang().getIdKhachHang(),
                tenKhachHang,
                cmnd,
                phieuDatPhong.getNhanVien() == null ? null :
                        phieuDatPhong.getNhanVien().getIdNhanVien(),
                phieuDatPhong.getTrangThaiHuy()
        );
    }
}
