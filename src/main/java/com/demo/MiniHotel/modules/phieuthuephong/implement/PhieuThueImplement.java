package com.demo.MiniHotel.modules.phieuthuephong.implement;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueRequest;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.TraPhongRequest;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.TraPhongResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.service.IChiTietPhieuThueService;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuPhongResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.service.IChiTietPhuThuService;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietDichVuPhongResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.service.IChiTietSuDungDichVuService;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonResponse;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.phieudatphong.dto.QuanLyPhieuDatResponse;
import com.demo.MiniHotel.modules.phieudatphong.exception.SoLuongPhongTrongException;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import com.demo.MiniHotel.modules.phieuthuephong.dto.*;
import com.demo.MiniHotel.modules.phieuthuephong.service.IPhieuThueService;
import com.demo.MiniHotel.modules.phong.service.IPhongService;
import com.demo.MiniHotel.modules.phuthu.service.IPhuThuService;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongUserResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.repository.PhieuThuePhongPagingRepository;
import com.demo.MiniHotel.repository.PhieuThuePhongRepository;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhieuThueImplement implements IPhieuThueService {
    private final PhieuThuePhongRepository repository;
    private final IKhachHangService khachHangService;
    private final INhanVienService nhanVienService;
    private final IChiTietPhieuThueService chiTietPhieuThueService;
    private final IPhieuDatService phieuDatService;
    private final IThongTinHangPhongService thongTinHangPhongService;
    private final IPhongService phongService;
    private final IChiTietSuDungDichVuService chiTietSuDungDichVuService;
    private final IChiTietPhuThuService chiTietPhuThuService;
    private final IHoaDonService hoaDonService;

    private final PhieuThuePhongPagingRepository phieuThuePhongPagingRepository;

    @Override
    public PhieuThueResponse addNewPhieuThuePhong(PhieuThuePhongRequest request) throws Exception {
        LocalDate ngayHienTai = LocalDate.now();
        //Nếu ngày hiện tại trước ngày nhận phòng => không cho phép
        if(ngayHienTai.isBefore(request.getNgayDen()))
            throw new AppException(ErrorCode.NGAYNHANPHONG_NOT_VALID);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());

        PhieuThuePhong phieuThuePhong = new PhieuThuePhong();
        phieuThuePhong.setNgayDen(request.getNgayDen());
        phieuThuePhong.setNgayDi(request.getNgayDi());
//        phieuThuePhong.setNgayTao(request.getNgayTao());
        phieuThuePhong.setNgayTao(LocalDate.now());
        phieuThuePhong.setPhanTramGiam(request.getPhanTramGiam());

        KhachHang khachHang = khachHangService.getKhachHangById(request.getIdKhachHang());
        phieuThuePhong.setKhachHang(khachHang);

        NhanVien nhanVien = nhanVienService.getNhanVienByToken();
        phieuThuePhong.setNhanVien(nhanVien);

        if(request.getIdPhieuDat() != null){
            PhieuDatPhong phieuDatPhong = phieuDatService.getPhieuDatPhongById(request.getIdPhieuDat());

            if(phieuDatPhong.getTrangThaiHuy() != 0){
                throw new AppException(ErrorCode.PHIEUTHUE_NOT_VALID);
            }

            phieuThuePhong.setPhieuDatPhong(phieuDatPhong);

            //Cap nhat phieu dat phong => 1: da hoan tat
            phieuDatService.capNhatTrangThaiPhieuDat(request.getIdPhieuDat(), 1);
        }

        PhieuThuePhong newPhieuThue = repository.save(phieuThuePhong);

        //Luu chi tiet phieu thue
        for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
            ChiTietPhieuThueRequest chiTietPhieuThueRequest = new ChiTietPhieuThueRequest();
            chiTietPhieuThueRequest.setIdPhieuThue(newPhieuThue.getIdPhieuThue());
            chiTietPhieuThueRequest.setMaPhong(chiTietRequest.getMaPhong());
            chiTietPhieuThueRequest.setNgayDen(chiTietRequest.getNgayDen());
            chiTietPhieuThueRequest.setNgayDi(chiTietRequest.getNgayDi());
            chiTietPhieuThueRequest.setDonGia(chiTietRequest.getDonGia());

            chiTietPhieuThueService.addNewChiTietPhieuThue(chiTietPhieuThueRequest);
        }


        return getPhieuThuePhongResonseById(newPhieuThue.getIdPhieuThue());
    }

    @Override
    public List<PhieuThuePhong> getAllPhieuThuePhong() {
        return repository.findAll();
    }

    @Override
    public PhieuThuePhong getPhieuThuePhongById(Integer id) throws Exception {
        Optional<PhieuThuePhong> PhieuThuePhongOptional = repository.findById(id);
        if(PhieuThuePhongOptional.isEmpty()){
            throw new Exception("PhieuThuePhong not found.");
        }

        return PhieuThuePhongOptional.get();
    }

    @Override
    public PhieuThuePhong updatePhieuThuePhong(PhieuThuePhongRequest request, Integer id) throws Exception {
        PhieuThuePhong phieuThuePhong = getPhieuThuePhongById(id);

        phieuThuePhong.setNgayDen(request.getNgayDen());
        phieuThuePhong.setNgayDi(request.getNgayDi());
        phieuThuePhong.setPhanTramGiam(request.getPhanTramGiam());
//        phieuThuePhong.setNgayTao(request.getNgayTao());

        KhachHang khachHang = khachHangService.getKhachHangById(request.getIdKhachHang());
        phieuThuePhong.setKhachHang(khachHang);

//        NhanVien nhanVien = nhanVienService.getNhanVienById(request.getIdNhanVien());
//        phieuThuePhong.setNhanVien(nhanVien);

        if(request.getIdPhieuDat() != null && request.getIdPhieuDat() > 0){
            PhieuDatPhong phieuDatPhong = phieuDatService.getPhieuDatPhongById(request.getIdPhieuDat());
            phieuThuePhong.setPhieuDatPhong(phieuDatPhong);
        }

        return repository.save(phieuThuePhong);
    }

    @Override
    public void deletePhieuThuePhong(Integer id) throws Exception {
        PhieuThuePhong phieuThuePhong = getPhieuThuePhongById(id);
        if(phieuThuePhong.getChiTietPhieuThues().size() > 0)
            throw new AppException(ErrorCode.PHIEUTHUE_DA_CO_CHITIET);

        if(phieuThuePhong.getHoaDons().size() > 0)
            throw new AppException(ErrorCode.PHIEUTHUE_DATHANHTOAN);

        repository.deleteById(id);
    }

    @Override
    public ChiTietPhieuThue getChiTietPhieuThueById(Integer idChiTietPhieuThue) throws Exception {
        return chiTietPhieuThueService.getChiTietPhieuThueById(idChiTietPhieuThue);
    }

    //Lấy chi tiết phiếu thuê tại thời điểm đang thuê (Chỉ tính giá dịch vụ và phụ thu chưa thanh toán)
    @Override
    public PhieuThueResponse getPhieuThuePhongResonseById(Integer id) throws Exception {
        PhieuThuePhong phieuThuePhong = getPhieuThuePhongById(id);

        // Tính tổng tiền
        long tongTien = 0;
        long tongTienPhong = 0;
        long tongTienDichVu = 0;
        long tongTienPhuThu = 0;
        if (phieuThuePhong.getChiTietPhieuThues() != null){
            for (ChiTietPhieuThue chiTietPhieuThue : phieuThuePhong.getChiTietPhieuThues()) {
                long soNgayThue;
                if (chiTietPhieuThue.getNgayDen().equals(chiTietPhieuThue.getNgayDi()))
                    soNgayThue = 1;
                else
                    soNgayThue = ChronoUnit.DAYS.between(chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi());
                // Nếu tiền phải trả là âm => bằng 0
                long tienPhong = chiTietPhieuThue.getDonGia() * soNgayThue - chiTietPhieuThue.getTienGiamGia();
                if (tienPhong < 0) tienPhong = 0;

                long tienDichVu = chiTietSuDungDichVuService.getTongTienChiTietSuDungDichVu(chiTietPhieuThue.getIdChiTietPhieuThue());
                long tienPhuThu = chiTietPhuThuService.getTongTienChiTietPhuThu(chiTietPhieuThue.getIdChiTietPhieuThue());

                tongTien += tienPhong + tienDichVu + tienPhuThu;
                tongTienPhong += tienPhong;
                tongTienDichVu += tienDichVu;
                tongTienPhuThu += tienPhuThu;
            }
        }

        return convertPhieuThuePhongResponse(phieuThuePhong, tongTien, tongTienPhong, tongTienDichVu, tongTienPhuThu);
    }

    @Autowired
    private EntityManager entityManager;
    @Override
    public List<PhieuThueResponse> timKiemPhieuThueTheoCmnd(String cmnd) throws Exception {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PhieuThueKhachHang", PhieuThuePhong.class)
                .registerStoredProcedureParameter("cmnd_tim_kiem", String.class, ParameterMode.IN)
                .setParameter("cmnd_tim_kiem", cmnd);
        List<PhieuThuePhong> phieuThuePhongs = query.getResultList();
        List<PhieuThueResponse> responses = new ArrayList<>();
        for (PhieuThuePhong phieuThuePhong: phieuThuePhongs) {
            PhieuThueResponse response = getPhieuThuePhongResonseById(phieuThuePhong.getIdPhieuThue());
            responses.add(response);
        }
        return responses;
    }

    // Kiểm tra số lượng phòng trả có phải là phòng cuối cùng không => tính tạm ứng cho lần trả phòng cuối
    @Override
    public Boolean kiemTraChiTietPhieuThueCuoiCung(int soLuong, int idPhieuThue) throws Exception {
        List<ChiTietPhieuThue> chiTietPhieuThues = chiTietPhieuThueService.getChiTietPhieuThueByIdPhieuThue(idPhieuThue);
        int count = 0;
        for (ChiTietPhieuThue chiTietPhieuThue: chiTietPhieuThues) {
            if(!chiTietPhieuThue.getDaThanhToan()) {
                count+=1;
            }
        }
        if(soLuong == count){
            return true;
        }
        return false;
    }

    @Override
    public ThongTinTraPhongResponse getThongTinTraPhong(ThongTinTraPhongRequest request) throws Exception {
        PhieuThueResponse phieuThueResponse = getPhieuThuePhongResonseById(request.getIdPhieuThue());

        List<ChiTietPhieuThueResponse> chiTietPhieuThueResponses = new ArrayList<>();
        for (int idChiTietPhieuThue: request.getIdChiTietPhieuThues()) {
            chiTietPhieuThueResponses.add(
                    chiTietPhieuThueService.getChiTietPhieuThueResponseById(idChiTietPhieuThue));
        }

        List<ChiTietSuDungDichVuResponse> chiTietSuDungDichVuResponses = new ArrayList<>();
        List<ChiTietPhuThuResponse> chiTietPhuThuResponses = new ArrayList<>();
        for (ChiTietPhieuThueResponse chiTietPhieuThue:chiTietPhieuThueResponses) {
            chiTietSuDungDichVuResponses.addAll(
                    chiTietSuDungDichVuService.
                            getChiTietSuDungDichVuByIdChiTietPhieuThue(chiTietPhieuThue.getIdChiTietPhieuThue()));
            chiTietPhuThuResponses.addAll(
                    chiTietPhuThuService.
                            getChiTietPhuThuByIdChiTietPhieuThue(chiTietPhieuThue.getIdChiTietPhieuThue()));
        }

        // Tính tổng tiền
        LocalDate ngayTra = LocalDate.now(); // Ngày trả là ngày hiện tại
        long tongTien = 0;
        long tongTienPhong = 0;
        for (ChiTietPhieuThueResponse chiTietPhieuThue:chiTietPhieuThueResponses) {
            // Nếu trả phòng sớm hơn dự kiến => tính ngày trả
            long soNgayThue;
            if(!ngayTra.isAfter(chiTietPhieuThue.getNgayDi())) {
                // Nếu đến và đi trong cùng một ngày thì vẫn tính một ngày thuê
                if (ngayTra.equals(chiTietPhieuThue.getNgayDen())) {
                    soNgayThue = 1;
                } else {
                    soNgayThue = ChronoUnit.DAYS.between(chiTietPhieuThue.getNgayDen(), ngayTra);
                }
                chiTietPhieuThue.setNgayDi(ngayTra);
            }else{
                soNgayThue = ChronoUnit.DAYS.between(chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi());
            }

            // Nếu tiền phải trả là âm => bằng 0
            long tienPhong = chiTietPhieuThue.getDonGia() * soNgayThue - chiTietPhieuThue.getTienGiamGia();
            if(tienPhong < 0) {
                tienPhong = 0;
                chiTietPhieuThue.setTongTienPhong(tienPhong);
            }else {
                chiTietPhieuThue.setTongTienPhong(tienPhong);
            }

            chiTietPhieuThue.setTongTienTatCa(tienPhong
                    + chiTietPhieuThue.getTongTienDichVu()
                    + chiTietPhieuThue.getTongTienPhuThu());

            tongTien += chiTietPhieuThue.getTongTienTatCa();
            tongTienPhong += chiTietPhieuThue.getTongTienPhong();
        }


        // Tiền tạm ứng chỉ được tính cho lần trả phòng cuối cùng
        long tienTamUng = 0;
        if(kiemTraChiTietPhieuThueCuoiCung(request.getIdChiTietPhieuThues().size(), request.getIdPhieuThue()))
            tienTamUng = phieuThueResponse.getTienTamUng();

        return ThongTinTraPhongResponse.builder()
                .idPhieuThue(phieuThueResponse.getIdPhieuThue())
                .ngayTraPhong(phieuThueResponse.getNgayDen())
                .ngayTraPhong(phieuThueResponse.getNgayDi())
                .ngayTao(phieuThueResponse.getNgayTao())
                .hoTenKhach(phieuThueResponse.getHoTenKhach())
                .tongTien(tongTien)
                .tienTamUng(tienTamUng)
                .tongTienPhong(tongTienPhong)
                .phanTramGiam(phieuThueResponse.getPhanTramGiam())
                .chiTietPhieuThues(chiTietPhieuThueResponses)
                .chiTietDichVus(chiTietSuDungDichVuResponses)
                .chiTietPhuThus(chiTietPhuThuResponses)
                .build();
    }

    @Override
    public TraPhongResponse traPhong(TraPhongRequest request) throws Exception {
        //tao mot hoa don moi
        HoaDonResponse hoaDonResponse = hoaDonService.themHoaDonMoi(request.getThucThu(), request.getIdPhieuThue());
        String soHoaDon = hoaDonResponse.getSoHoaDon();
        List<ChiTietPhieuThueResponse> chiTietPhieuThueResponses = new ArrayList<>();
        for (int idChiTietPhieuThue: request.getIdChiTietPhieuThues()) {
            //them hoa don vao chi tiet phieu thue
            chiTietPhieuThueService.themHoaDonToChiTietPhieuThue(idChiTietPhieuThue, hoaDonResponse.getSoHoaDon());
            //them hoa don vao chi tiet phu thu và chuyển da thanh toan = true
            chiTietPhuThuService.thanhToanChiTietPhuThuCuaChiTietPhieuThue(idChiTietPhieuThue, soHoaDon);
            // them hoa don vao chi tiet su dung dich vu
            chiTietSuDungDichVuService.thanhToanChiTietSuDungDVCuaChiTietPhieuThue(idChiTietPhieuThue, soHoaDon);

            // Lấy danh sách chi tiết phiếu thuê để trả về
            chiTietPhieuThueResponses.add(
                    chiTietPhieuThueService.getChiTietPhieuThueResponseById(idChiTietPhieuThue));
        }

        // Thông tin trả về sau khi đã thanh toán
        PhieuThueResponse phieuThue = getPhieuThuePhongResonseById(request.getIdPhieuThue());

        List<ChiTietSuDungDichVuResponse> chiTietSuDungDichVuResponses = new ArrayList<>();
        List<ChiTietPhuThuResponse> chiTietPhuThuResponses = new ArrayList<>();
        for (ChiTietPhieuThueResponse chiTietPhieuThue:chiTietPhieuThueResponses) {
            chiTietSuDungDichVuResponses.addAll(
                    chiTietSuDungDichVuService.
                            getChiTietSuDungDichVuByIdChiTietPhieuThue(chiTietPhieuThue.getIdChiTietPhieuThue()));
            chiTietPhuThuResponses.addAll(
                    chiTietPhuThuService.
                            getChiTietPhuThuByIdChiTietPhieuThue(chiTietPhieuThue.getIdChiTietPhieuThue()));
        }

        LocalDate ngayTra = LocalDate.now(); // Ngày trả là ngày hiện tại
        for (ChiTietPhieuThueResponse chiTietPhieuThue:chiTietPhieuThueResponses) {
            // Nếu trả phòng sớm hơn dự kiến => tính ngày trả => cập nhật thời gian
            long soNgayThue;
            if(!ngayTra.isAfter(chiTietPhieuThue.getNgayDi())) {
                // Nếu đến và đi trong cùng một ngày thì vẫn tính một ngày thuê
                if (ngayTra.equals(chiTietPhieuThue.getNgayDen())) {
                    soNgayThue = 1;
                } else {
                    soNgayThue = ChronoUnit.DAYS.between(chiTietPhieuThue.getNgayDen(), ngayTra);
                }
                // Cập nhật lại thời gian trả
                chiTietPhieuThueService.capNhatNgayTraPhong(chiTietPhieuThue.getIdChiTietPhieuThue(), ngayTra);
                chiTietPhieuThue.setNgayDi(ngayTra);
            }else{
                soNgayThue = ChronoUnit.DAYS.between(chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi());
            }

            chiTietPhieuThue.setTongTienPhong(chiTietPhieuThue.getDonGia() * soNgayThue - chiTietPhieuThue.getTienGiamGia());
            chiTietPhieuThue.setTongTienTatCa(chiTietPhieuThue.getDonGia() * soNgayThue
                    + chiTietPhieuThue.getTongTienDichVu() + chiTietPhieuThue.getTongTienPhuThu()
                    - chiTietPhieuThue.getTienGiamGia());
        }

        return TraPhongResponse.builder()
                .idPhieuThue(phieuThue.getIdPhieuThue())
                .ngayNhanPhong(phieuThue.getNgayDen())
                .ngayTraPhong(phieuThue.getNgayDi())
                .hoTenKhach(phieuThue.getHoTenKhach())
                .hoTenNhanVien(hoaDonResponse.getTenNhanVien())
                .tienTamUng(request.getTienTamUng())
                .phanTramGiam(phieuThue.getPhanTramGiam())
                .tongThu(request.getTongThu())
                .thucThu(hoaDonResponse.getTongTien())
                .soHoaDon(soHoaDon)
                .ngayTaoHoaDon(hoaDonResponse.getNgayTao())
                .chiTietPhieuThues(chiTietPhieuThueResponses)
                .chiTietDichVus(chiTietSuDungDichVuResponses)
                .chiTietPhuThus(chiTietPhuThuResponses)
                .build();
    }

    @Override
    public PhieuThueResponse capNhatPhanTramGiam(Integer id, int phanTramGiam) throws Exception {
        PhieuThuePhong phieuThuePhong = getPhieuThuePhongById(id);
        phieuThuePhong.setPhanTramGiam(phanTramGiam);
        repository.save(phieuThuePhong);
        return getPhieuThuePhongResonseById(id);
    }

    @Override
    public List<PhieuThueResponse> getPhieuThuePhongTheoTrang(int pageNumber, int pageSize) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("ngayDen").descending());
        Page<PhieuThuePhong> phieuThuePhongPages = phieuThuePhongPagingRepository.findAll(pageable);
        List<PhieuThuePhong> phieuThuePhongs = phieuThuePhongPages.stream().toList();

        List<PhieuThueResponse> responses = new ArrayList<>();
        for (PhieuThuePhong phieuThue:phieuThuePhongs) {
            responses.add(getCapNhatPhieuThuePhongResonseById(phieuThue.getIdPhieuThue()));
        }

        return responses;
    }

    @Override
    public Integer getTongTrangPhieuThuePhong(int pageSize){
        int tongPhieuThue = repository.findAll().size();
        int tongTrang = tongPhieuThue / pageSize;
        if(tongPhieuThue % pageSize != 0)
            tongTrang += 1;

        return tongTrang;
    }

    // Xem lại phiếu thuê (Tính tổng giá dịch vụ và phụ thu dù có thanh toán hay chưa)
    @Override
    public PhieuThueResponse getCapNhatPhieuThuePhongResonseById(Integer id) throws Exception {
        PhieuThuePhong phieuThuePhong = getPhieuThuePhongById(id);

        // Tính tổng tiền
        long tongTien = 0;
        long tongTienPhong = 0;
        long tongTienDichVu = 0;
        long tongTienPhuThu = 0;
        for (ChiTietPhieuThue chiTietPhieuThue:phieuThuePhong.getChiTietPhieuThues()) {
            long soNgayThue;
            if(chiTietPhieuThue.getNgayDen().equals(chiTietPhieuThue.getNgayDi()))
                soNgayThue = 1;
            else
                soNgayThue = ChronoUnit.DAYS.between(chiTietPhieuThue.getNgayDen(), chiTietPhieuThue.getNgayDi());
            // Nếu tiền phải trả là âm => bằng 0
            long tienPhong = chiTietPhieuThue.getDonGia() * soNgayThue - chiTietPhieuThue.getTienGiamGia();
            if(tienPhong < 0) tienPhong = 0;

            tongTienPhong += tienPhong;
        }

        for(ChiTietDichVuPhongResponse chiTietDichVu: chiTietSuDungDichVuService.getChiTietDichVuCuaPhieuThue(id)){
            tongTienDichVu += chiTietDichVu.getDonGia() * chiTietDichVu.getSoLuong();
        }
        for(ChiTietPhuThuPhongResponse chiTietPhuThu: chiTietPhuThuService.getChiTietPhuThuCuaPhieuThue(id)){
            tongTienPhuThu += chiTietPhuThu.getDonGia() * chiTietPhuThu.getSoLuong();
        }

        tongTien = tongTienPhong + tongTienDichVu + tongTienPhuThu;

        return convertPhieuThuePhongResponse(phieuThuePhong, tongTien, tongTienPhong, tongTienDichVu, tongTienPhuThu);
    }

    private PhieuThueResponse convertPhieuThuePhongResponse(PhieuThuePhong phieuThuePhong, long tongTien, long tongTienPhong, long tongTienDichVu, long tongTienPhuThu){
        Integer idPhieuDat = phieuThuePhong.getPhieuDatPhong() == null ? null : phieuThuePhong.getPhieuDatPhong().getIdPhieuDat();
        Long tienTamUng = phieuThuePhong.getPhieuDatPhong() == null ? 0 : phieuThuePhong.getPhieuDatPhong().getTienTamUng();

        long tienDuocGiam = tongTienPhong * phieuThuePhong.getPhanTramGiam()/100;
        long tienPhaiTra = tongTien - tienDuocGiam - tienTamUng;

        return new PhieuThueResponse(
                phieuThuePhong.getIdPhieuThue(),
                phieuThuePhong.getNgayDen(),
                phieuThuePhong.getNgayDi(),
                phieuThuePhong.getNgayTao(),
                phieuThuePhong.getKhachHang().getHoTen(),
                phieuThuePhong.getKhachHang().getCmnd(),
                phieuThuePhong.getNhanVien().getIdNhanVien(),
                phieuThuePhong.getNhanVien().getHoTen(),
                idPhieuDat,
                tienTamUng,
                tongTien,
                tongTienPhong,
                tongTienDichVu,
                tongTienPhuThu,
                tienDuocGiam,
                tienPhaiTra,
                phieuThuePhong.getPhanTramGiam());
    }
}
