package com.demo.MiniHotel.modules.hoadon.implement;

import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.bophan.service.IBoPhanService;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.service.IChiTietPhieuThueService;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.service.IChiTietPhuThuService;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.service.IChiTietSuDungDichVuService;
import com.demo.MiniHotel.modules.hoadon.dto.DoanhThuQuyResponse;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonDetailsResponse;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonRequest;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonResponse;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import com.demo.MiniHotel.modules.nhomquyen.service.INhomQuyenService;
import com.demo.MiniHotel.modules.phieuthuephong.service.IPhieuThueService;
import com.demo.MiniHotel.modules.taikhoan.service.ITaiKhoanService;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongUserResponse;
import com.demo.MiniHotel.repository.ChiTietPhieuThueRepository;
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
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HoaDonImplement implements IHoaDonService {
    private final HoaDonRepository repository;
    private final INhanVienService nhanVienService;
    private final PhieuThuePhongRepository phieuThuePhongRepository;
    private final HoaDonNgayRepository hoaDonNgayRepository;
//    private final IChiTietPhieuThueService chiTietPhieuThueService;
    private final ChiTietPhieuThueRepository chiTietPhieuThueRepository;
//    private final IChiTietSuDungDichVuService chiTietSuDungDichVuService;
//    private final IChiTietPhuThuService chiTietPhuThuService;

    @Autowired
    private EntityManager entityManager;


    @Override
    public HoaDon addNewHoaDon(HoaDonRequest request) throws Exception {
        NhanVien nhanVien = nhanVienService.getNhanVienById(request.getIdNhanVien());
        HoaDon hoaDon = new HoaDon();
        hoaDon.setSoHoaDon(generateSoHoaDon());
        hoaDon.setTongTien(request.getTongTien());
        hoaDon.setNgayTao(LocalDate.now());
//        hoaDon.setPhanTramGiam(request.getPhanTramGiam());

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
        return lUUID.substring(0, 12);
    }

    @Override
    public List<HoaDon> getAllHoaDon() {
        return repository.findAll();
    }

    @Override
    public HoaDon getHoaDonById(String id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hoa Don Not found"));
    }

    @Override
    public HoaDonDetailsResponse getHoaDonDetailsById(String id) throws Exception {
        HoaDon hoaDon = repository.findById(id).orElseThrow(() -> new RuntimeException("HoaDon not found"));
        PhieuThuePhong phieuThue = hoaDon.getPhieuThuePhong();

        List<ChiTietPhieuThue> chiTietPhieuThues = hoaDon.getChiTietPhieuThues();

        List<ChiTietPhieuThueResponse> chiTietPhieuThueResponses = new ArrayList<>();
        for (ChiTietPhieuThue chiTietPhieuThue: chiTietPhieuThues) {
            chiTietPhieuThueResponses.add(
                    convertChiTietPhieuThueToResponse(chiTietPhieuThue));
        }

        List<ChiTietSuDungDichVu> chiTietSuDungDichVus = hoaDon.getChiTietSuDungDichVus();
        List<ChiTietPhuThu> chiTietPhuThus = hoaDon.getChiTietPhuThus();

        List<ChiTietSuDungDichVuResponse> chiTietSuDungDichVuResponses = new ArrayList<>();
        List<ChiTietPhuThuResponse> chiTietPhuThuResponses = new ArrayList<>();

        // Tính tổng tiền
        long tongTien = 0;
        for (ChiTietPhieuThueResponse chiTietPhieuThue:chiTietPhieuThueResponses) {
            tongTien += chiTietPhieuThue.getTongTienTatCa();
        }

        for (ChiTietSuDungDichVu chiTietDichVu:chiTietSuDungDichVus) {
            chiTietSuDungDichVuResponses.add(convertChiTietSuDungDichVuToResponse(chiTietDichVu));
            tongTien += chiTietDichVu.getSoLuong() * chiTietDichVu.getDonGia();
        }

        for (ChiTietPhuThu chiTietPhuThu:chiTietPhuThus) {
            chiTietPhuThuResponses.add(convertChiTietPhuThuToResponse(chiTietPhuThu));
            tongTien += chiTietPhuThu.getSoLuong() * chiTietPhuThu.getDonGia();
        }


        return HoaDonDetailsResponse.builder()
                .soHoaDon(hoaDon.getSoHoaDon())
                .ngayTao(hoaDon.getNgayTao())
                .idPhieuThue(phieuThue.getIdPhieuThue())
                .tenNhanVien(hoaDon.getNhanVien().getHoTen())
                .ngayNhanPhong(phieuThue.getNgayDen())
                .ngayTraPhong(phieuThue.getNgayDi())
                .hoTenKhach(phieuThue.getKhachHang().getHoTen())
                .thucThu(hoaDon.getTongTien())
                .tongThu(tongTien)
                .chiTietPhieuThues(chiTietPhieuThueResponses)
                .chiTietDichVus(chiTietSuDungDichVuResponses)
                .chiTietPhuThus(chiTietPhuThuResponses)
                .build();
    }


    @Override
    public HoaDon updateHoaDon(HoaDonRequest request, String id) throws Exception {
        NhanVien nhanVien = nhanVienService.getNhanVienById(request.getIdNhanVien());
        HoaDon hoaDon = getHoaDonById(id);
        hoaDon.setTongTien(request.getTongTien());
        hoaDon.setNgayTao(LocalDate.now());

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
    public HoaDonResponse themHoaDonMoi(Long tongTien, int idPhieuThue) throws Exception {
//        HoaDonRequest request = new HoaDonRequest();
//        request.setIdNhanVien(idNhanVien);
//        request.setTongTien(tongTien);
//        request.setNgayTao(ngayTao);

        HoaDon hoaDon = new HoaDon();
        hoaDon.setSoHoaDon(generateSoHoaDon());
        hoaDon.setTongTien(tongTien);
        hoaDon.setNgayTao(LocalDate.now());
//        hoaDon.setPhanTramGiam(phanTramGiam);

//        NhanVien nhanVien = nhanVienService.getNhanVienById(request.getIdNhanVien());
        NhanVien nhanVien = nhanVienService.getNhanVienByToken();
        hoaDon.setNhanVien(nhanVien);

        PhieuThuePhong phieuThuePhong = phieuThuePhongRepository.findById(idPhieuThue)
                .orElseThrow(() -> new RuntimeException("PhieuThue not found"));
        hoaDon.setPhieuThuePhong(phieuThuePhong);

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

        // Những ngày không có dữ liệu thì set nó là 0
        LocalDate currentDate = ngayBatDau;

        while (!currentDate.isAfter(ngayKetThuc)) {
            boolean isExist = false;
            for (DoanhThuTheoNgay doanhThuTheoNgay: doanhThuTheoNgays) {
                if(currentDate.equals(doanhThuTheoNgay.getNgayTao())) isExist = true;
            }
            if(!isExist) doanhThuTheoNgays.add(new DoanhThuTheoNgay(currentDate, 0L));
            currentDate = currentDate.plusDays(1);
        }

        Collections.sort(doanhThuTheoNgays, Comparator.comparing(DoanhThuTheoNgay::getNgayTao));

        return doanhThuTheoNgays;
    }

    @Override
    public List<DoanhThuTheoThang> getDoanhThuTheoThang(int thangBatDau, int thangKetThuc, int nam) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("DoanhThuTheoThang", DoanhThuTheoThang.class)
                .registerStoredProcedureParameter("thang_bat_dau", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("thang_ket_thuc", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("nam", Integer.class, ParameterMode.IN)
                .setParameter("thang_bat_dau", thangBatDau)
                .setParameter("thang_ket_thuc", thangKetThuc)
                .setParameter("nam", nam);
        List<DoanhThuTheoThang> doanhThuTheoThangs = query.getResultList();

        // Những tháng không có dữ liệu thì set nó là 0
        int currentMonth = thangBatDau;

        while (currentMonth <= thangKetThuc) {
            boolean isExist = false;
            for (DoanhThuTheoThang doanhThuTheoThang: doanhThuTheoThangs) {
                if(currentMonth == doanhThuTheoThang.getThang()) {
                    isExist = true;
                };
            }
            if(!isExist) doanhThuTheoThangs.add(new DoanhThuTheoThang(currentMonth, 0L));
            currentMonth += 1;
        }

        Collections.sort(doanhThuTheoThangs, Comparator.comparing(DoanhThuTheoThang::getThang));

        return doanhThuTheoThangs;
    }

    @Override
    public List<DoanhThuQuyResponse> getDoanhThuTheoQuy(int quyBatDau, int quyKetThuc, int nam) {
        List<DoanhThuQuyResponse> doanhThuQuyResponses = new ArrayList<>();
        int currentQuarter = quyBatDau;

        while (currentQuarter <= quyKetThuc) {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("DoanhThuTheoQuy", DoanhThuTheoThang.class)
                    .registerStoredProcedureParameter("quy", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("nam", Integer.class, ParameterMode.IN)
                    .setParameter("quy", currentQuarter)
                    .setParameter("nam", nam);
            List<DoanhThuTheoThang> doanhThuTheoThangs = query.getResultList();

            DoanhThuQuyResponse response = DoanhThuQuyResponse.builder()
                    .quy(currentQuarter)
                    .doanhThu(getTongDoanhThuQuy(doanhThuTheoThangs))
                    .build();
            doanhThuQuyResponses.add(response);

            currentQuarter += 1;
        }

        return doanhThuQuyResponses;
    }

    public long getTongDoanhThuQuy(List<DoanhThuTheoThang> doanhThuTheoThangs){
        long tongDoanhThu = 0;
        for (DoanhThuTheoThang doanhThuTheoThang: doanhThuTheoThangs) {
            tongDoanhThu+=doanhThuTheoThang.getDoanhThu();
        }
        return tongDoanhThu;
    }

    public HoaDonResponse convertHoaDonResponse(HoaDon hoaDon){
        return new HoaDonResponse(hoaDon.getSoHoaDon(),
                hoaDon.getNhanVien().getHoTen(),
                hoaDon.getTongTien(), hoaDon.getNgayTao());
    }

    private ChiTietSuDungDichVuResponse convertChiTietSuDungDichVuToResponse(ChiTietSuDungDichVu chiTietSuDungDichVu) {
        return new ChiTietSuDungDichVuResponse(
                chiTietSuDungDichVu.getIdChiTietSuDungDichVu(),
                chiTietSuDungDichVu.getChiTietPhieuThue().getIdChiTietPhieuThue(),
                chiTietSuDungDichVu.getDichVu().getIdDichVu(),
                chiTietSuDungDichVu.getDichVu().getTenDichVu(),
                chiTietSuDungDichVu.getSoLuong(),
                chiTietSuDungDichVu.getNgayTao(),
                chiTietSuDungDichVu.getDonGia(),
                chiTietSuDungDichVu.getDaThanhToan(),
                chiTietSuDungDichVu.getChiTietPhieuThue().getPhong().getMaPhong()
        );
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

    private ChiTietPhieuThueResponse convertChiTietPhieuThueToResponse(ChiTietPhieuThue chiTietPhieuThue) throws Exception {
        String tenHangPhong = chiTietPhieuThue.getPhong().getHangPhong().getTenHangPhong();

        long ngay;
        if(chiTietPhieuThue.getNgayDen().equals(chiTietPhieuThue.getNgayDi()))
            ngay = 1;
        else
            ngay = ChronoUnit.DAYS.between(chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi());

        long tongTienPhong = chiTietPhieuThue.getDonGia() * ngay - chiTietPhieuThue.getTienGiamGia();
        long tongTienDichVu = getTongTienChiTietSuDungDichVu(chiTietPhieuThue);
        long tongTienPhuThu = getTongTienChiTietPhuThu(chiTietPhieuThue);
        long tongTienTatCa = chiTietPhieuThue.getDonGia() * ngay + tongTienDichVu + tongTienPhuThu - chiTietPhieuThue.getTienGiamGia();

        int idHangPhong = chiTietPhieuThue.getPhong().getHangPhong().getIdHangPhong();
        return new ChiTietPhieuThueResponse(chiTietPhieuThue.getIdChiTietPhieuThue(),
                chiTietPhieuThue.getPhong().getMaPhong(), idHangPhong, tenHangPhong,
                chiTietPhieuThue.getPhieuThuePhong().getIdPhieuThue(),
                chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi(),
                chiTietPhieuThue.getDonGia(),
                chiTietPhieuThue.getDaThanhToan(),
                chiTietPhieuThue.getTienGiamGia(),
                tongTienPhong,
                tongTienDichVu,
                tongTienPhuThu,
                tongTienTatCa);
    }

    public long getTongTienChiTietPhuThu(ChiTietPhieuThue chiTietPhieuThue) throws Exception {
        long tongTien = 0;
        List<ChiTietPhuThu> chiTietPhuThus = chiTietPhieuThue.getChiTietPhuThus();
        for (ChiTietPhuThu phuThu: chiTietPhuThus) {
            if(!phuThu.getDaThanhToan())
                tongTien += phuThu.getDonGia() * phuThu.getSoLuong();
        }
        return tongTien;
    }
    public long getTongTienChiTietSuDungDichVu(ChiTietPhieuThue chiTietPhieuThue) throws Exception {
        long tongTien = 0;
        List<ChiTietSuDungDichVu> chiTietSuDungDichVus = chiTietPhieuThue.getChiTietSuDungDichVus();
        for (ChiTietSuDungDichVu dichVu:chiTietSuDungDichVus) {
            if(!dichVu.getDaThanhToan())
                tongTien += dichVu.getDonGia() * dichVu.getSoLuong();
        }
        return tongTien;
    }

}
