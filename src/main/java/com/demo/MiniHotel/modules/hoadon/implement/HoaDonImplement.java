package com.demo.MiniHotel.modules.hoadon.implement;

import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.bophan.service.IBoPhanService;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonRequest;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonResponse;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import com.demo.MiniHotel.modules.nhomquyen.service.INhomQuyenService;
import com.demo.MiniHotel.modules.phieuthuephong.service.IPhieuThueService;
import com.demo.MiniHotel.modules.taikhoan.service.ITaiKhoanService;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongUserResponse;
import com.demo.MiniHotel.repository.HoaDonNgayRepository;
import com.demo.MiniHotel.repository.HoaDonRepository;
import com.demo.MiniHotel.repository.PhieuThuePhongRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HoaDonImplement implements IHoaDonService {
    private final HoaDonRepository repository;
    private final INhanVienService nhanVienService;
    private final PhieuThuePhongRepository phieuThuePhongRepository;
    private final HoaDonNgayRepository hoaDonNgayRepository;

    @Autowired
    private EntityManager entityManager;


    @Override
    public HoaDon addNewHoaDon(HoaDonRequest request) throws Exception {
        NhanVien nhanVien = nhanVienService.getNhanVienById(request.getIdNhanVien());
        HoaDon hoaDon = new HoaDon();
        hoaDon.setSoHoaDon(generateSoHoaDon());
        hoaDon.setTongTien(request.getTongTien());
        hoaDon.setNgayTao(request.getNgayTao());

        hoaDon.setNhanVien(nhanVien);

        if(request.getIdPhieuThue() != null){
            Optional<PhieuThuePhong> phieuThuePhongOptional = phieuThuePhongRepository.findById(request.getIdPhieuThue());
            if(phieuThuePhongOptional.isEmpty()) throw new Exception("PhieuThuePhong not found.");
            hoaDon.setPhieuThuePhong(phieuThuePhongOptional.get());
        }

        return repository.save(hoaDon);
    }

    private String generateSoHoaDon() {
        String lUUID = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        return lUUID.substring(0, 10);
    }

    @Override
    public List<HoaDon> getAllHoaDon() {
        return repository.findAll();
    }

    @Override
    public HoaDon getHoaDonById(String id) throws Exception {
        Optional<HoaDon> HoaDonOptional = repository.findById(id);
        if(HoaDonOptional.isEmpty()){
            throw new Exception("HoaDon not found.");
        }

        return HoaDonOptional.get();
    }

    @Override
    public HoaDon updateHoaDon(HoaDonRequest request, String id) throws Exception {
        NhanVien nhanVien = nhanVienService.getNhanVienById(request.getIdNhanVien());
        HoaDon hoaDon = getHoaDonById(id);
        hoaDon.setTongTien(request.getTongTien());
        hoaDon.setNgayTao(request.getNgayTao());

        hoaDon.setNhanVien(nhanVien);

        if(request.getIdPhieuThue() != null){
            Optional<PhieuThuePhong> phieuThuePhongOptional = phieuThuePhongRepository.findById(request.getIdPhieuThue());
            if(phieuThuePhongOptional.isEmpty()) throw new Exception("PhieuThuePhong not found.");
            hoaDon.setPhieuThuePhong(phieuThuePhongOptional.get());
        }

        return repository.save(hoaDon);
    }

    @Override
    public void deleteHoaDon(String id) throws Exception {
        Optional<HoaDon> HoaDonOptional = repository.findById(id);
        if(HoaDonOptional.isEmpty()){
            throw new Exception("HoaDon not found.");
        }

        repository.deleteById(id);
    }

    @Override
    public List<ChiTietPhieuThue> getChiTietPhieuThueBySoHoaDon(String soHoaDon) throws Exception {
        return getHoaDonById(soHoaDon).getChiTietPhieuThues();
    }

    @Override
    public List<ChiTietSuDungDichVu> getChiTietSuDungDichVuBySoHoaDon(String soHoaDon) throws Exception {
        return getHoaDonById(soHoaDon).getChiTietSuDungDichVus();
    }

    @Override
    public HoaDonResponse themHoaDonMoi(Integer idNhanVien, Long tongTien, LocalDate ngayTao) throws Exception {
        HoaDonRequest request = new HoaDonRequest();
        request.setIdNhanVien(idNhanVien);
        request.setTongTien(tongTien);
        request.setNgayTao(ngayTao);

        HoaDon hoaDon = new HoaDon();
        hoaDon.setSoHoaDon(generateSoHoaDon());
        hoaDon.setTongTien(request.getTongTien());
        hoaDon.setNgayTao(request.getNgayTao());

        NhanVien nhanVien = nhanVienService.getNhanVienById(request.getIdNhanVien());
        hoaDon.setNhanVien(nhanVien);

        return convertHoaDonResponse(repository.save(hoaDon));
    }

    @Override
    public List<HoaDonNgay> getHoaDonNgaysHienTai() {
        return hoaDonNgayRepository.findAll();
    }

    @Override
    public List<HoaDonNgay> getHoaDonNgaysTheoNgay(LocalDate ngay) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("HoaDonTheoNgay", HoaDonNgay.class)
                .registerStoredProcedureParameter("ngay", LocalDate.class, ParameterMode.IN)
                .setParameter("ngay", ngay);
        List<HoaDonNgay> hoaDonNgays = query.getResultList();
        return hoaDonNgays;
    }

    @Override
    public List<DoanhThuTheoNgay> getDoanhThuTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("DoanhThuTheoThoiGian", DoanhThuTheoNgay.class)
                .registerStoredProcedureParameter("ngay_bat_dau", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ngay_ket_thuc", LocalDate.class, ParameterMode.IN)
                .setParameter("ngay_bat_dau", ngayBatDau)
                .setParameter("ngay_ket_thuc", ngayKetThuc);
        List<DoanhThuTheoNgay> doanhThuTheoNgays = query.getResultList();
        return doanhThuTheoNgays;
    }

    public HoaDonResponse convertHoaDonResponse(HoaDon hoaDon){
        return new HoaDonResponse(hoaDon.getSoHoaDon(),
                hoaDon.getNhanVien().getHoTen(),
                hoaDon.getTongTien(), hoaDon.getNgayTao());
    }
}
