package com.demo.MiniHotel.modules.phieuthuephong.implement;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueRequest;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.ChiTietPhieuThueResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.TraPhongRequest;
import com.demo.MiniHotel.modules.chitiet_phieuthue.dto.TraPhongResponse;
import com.demo.MiniHotel.modules.chitiet_phieuthue.service.IChiTietPhieuThueService;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuResponse;
import com.demo.MiniHotel.modules.chitiet_phuthu.service.IChiTietPhuThuService;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietSuDungDichVuResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.service.IChiTietSuDungDichVuService;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonResponse;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.phieudatphong.exception.SoLuongPhongTrongException;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import com.demo.MiniHotel.modules.phieuthuephong.dto.*;
import com.demo.MiniHotel.modules.phieuthuephong.service.IPhieuThueService;
import com.demo.MiniHotel.modules.phong.service.IPhongService;
import com.demo.MiniHotel.modules.phuthu.service.IPhuThuService;
import com.demo.MiniHotel.modules.thongtin_hangphong.dto.ThongTinHangPhongUserResponse;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.repository.PhieuThuePhongRepository;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public PhieuThueResponse addNewPhieuThuePhong(PhieuThuePhongRequest request) throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());

        PhieuThuePhong phieuThuePhong = new PhieuThuePhong();
        phieuThuePhong.setNgayDen(request.getNgayDen());
        phieuThuePhong.setNgayDi(request.getNgayDi());
//        phieuThuePhong.setNgayTao(request.getNgayTao());
        phieuThuePhong.setNgayTao(LocalDate.now());

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


        return convertPhieuThuePhongResponse(newPhieuThue);
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
        Optional<PhieuThuePhong> PhieuThuePhongOptional = repository.findById(id);
        if(PhieuThuePhongOptional.isEmpty()){
            throw new Exception("PhieuThuePhong not found.");
        }

        repository.deleteById(id);
    }

    @Override
    public ChiTietPhieuThue getChiTietPhieuThueById(Integer idChiTietPhieuThue) throws Exception {
        return chiTietPhieuThueService.getChiTietPhieuThueById(idChiTietPhieuThue);
    }

    @Override
    public PhieuThueResponse getPhieuThuePhongResonseById(Integer id) throws Exception {
        PhieuThuePhong phieuThuePhong = getPhieuThuePhongById(id);
        return convertPhieuThuePhongResponse(phieuThuePhong);
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
            PhieuThueResponse response = convertPhieuThuePhongResponse(phieuThuePhong);
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
        long tongTien = 0;
        for (ChiTietPhieuThueResponse chiTietPhieuThue:chiTietPhieuThueResponses) {
            tongTien += chiTietPhieuThue.getTongTien();
        }

        for (ChiTietSuDungDichVuResponse chiTietDichVu:chiTietSuDungDichVuResponses) {
            if(!chiTietDichVu.getDaThanhToan())
                tongTien += chiTietDichVu.getSoLuong() * chiTietDichVu.getDonGia();
        }

        for (ChiTietPhuThuResponse chiTietPhuThu:chiTietPhuThuResponses) {
            if(!chiTietPhuThu.getDaThanhToan())
                tongTien += chiTietPhuThu.getSoLuong() * chiTietPhuThu.getDonGia();
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
                .chiTietPhieuThues(chiTietPhieuThueResponses)
                .chiTietDichVus(chiTietSuDungDichVuResponses)
                .chiTietPhuThus(chiTietPhuThuResponses)
                .build();
    }

    @Override
    public TraPhongResponse traPhong(TraPhongRequest request) throws Exception {
        //tao mot hoa don moi
        HoaDonResponse hoaDonResponse = hoaDonService.themHoaDonMoi(request.getThucThu());
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

        return TraPhongResponse.builder()
                .idPhieuThue(phieuThue.getIdPhieuThue())
                .ngayNhanPhong(phieuThue.getNgayDen())
                .ngayTraPhong(phieuThue.getNgayDi())
                .hoTenKhach(phieuThue.getHoTenKhach())
                .hoTenNhanVien(hoaDonResponse.getTenNhanVien())
                .tienTamUng(request.getTienTamUng())
                .phanTramGiam(request.getPhanTramGiam())
                .tongThu(request.getTongThu())
                .thucThu(hoaDonResponse.getTongTien())
                .soHoaDon(soHoaDon)
                .ngayTaoHoaDon(hoaDonResponse.getNgayTao())
                .chiTietPhieuThues(chiTietPhieuThueResponses)
                .chiTietDichVus(chiTietSuDungDichVuResponses)
                .chiTietPhuThus(chiTietPhuThuResponses)
                .build();
    }


    private PhieuThueResponse convertPhieuThuePhongResponse(PhieuThuePhong phieuThuePhong){
        Integer idPhieuDat = phieuThuePhong.getPhieuDatPhong() == null ? null : phieuThuePhong.getPhieuDatPhong().getIdPhieuDat();
        Long tienTamUng = phieuThuePhong.getPhieuDatPhong() == null ? 0 : phieuThuePhong.getPhieuDatPhong().getTienTamUng();
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
                tienTamUng);
    }
}
