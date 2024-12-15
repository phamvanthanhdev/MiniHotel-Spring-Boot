package com.demo.MiniHotel.modules.phieudatphong.implement;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.helper.EmailSenderService;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phieudat.dto.*;
import com.demo.MiniHotel.modules.chitiet_phieudat.service.IChiTietPhieuDatService;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangRequest;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangResponse;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.phieudatphong.dto.*;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import com.demo.MiniHotel.modules.thongtin_hangphong.service.IThongTinHangPhongService;
import com.demo.MiniHotel.repository.PhieuDatPhongPagingRepository;
import com.demo.MiniHotel.repository.PhieuDatPhongRepository;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import com.demo.MiniHotel.repository.TaiKhoanRepository;
import com.demo.MiniHotel.vnpay.PhieuDatThanhToanRequest;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhieuDatImplement implements IPhieuDatService {
    private final PhieuDatPhongRepository repository;
    private final IKhachHangService khachHangService;
    private final INhanVienService nhanVienService;
    private final IChiTietPhieuDatService chiTietPhieuDatService;
    private final IThongTinHangPhongService thongTinHangPhongService;
    private final TaiKhoanRepository taiKhoanRepository;
    private final PhieuDatPhongPagingRepository phieuDatPhongPagingRepository;

    @NonFinal
    @Value("${soNgayPhaiThanhToan}")
    long soNgayPhaiThanhToan;

//    @Override
//    public PhieuDatPhong datPhongKhachSan(PhieuDatThanhToanRequest request) throws Exception {
//        KhachHangResponse khachHangResponse = khachHangService.addKhachHangDatPhong(request.getKhachHang());
//        request.getPhieuDat().setIdKhachHang(khachHangResponse.getIdKhachHang());
//
//        return addNewPhieuDatPhong(request.getPhieuDat());
//    }

    @Override
    public ApiResponse datPhongKhachSanBySP(PhieuDatThanhToanRequest request) throws Exception {
        KhachHangResponse khachHangResponse = khachHangService.addKhachHangDatPhong(request.getKhachHang());
        request.getPhieuDat().setIdKhachHang(khachHangResponse.getIdKhachHang());

        return bookingBySP(request.getPhieuDat());
    }

    @Override
    public PhieuDatPhong addNewPhieuDatPhong(PhieuDatRequest request) throws Exception {
        PhieuDatPhong phieuDatPhong = new PhieuDatPhong();
        phieuDatPhong.setNgayBatDau(request.getNgayBatDau());
        phieuDatPhong.setNgayTraPhong(request.getNgayTraPhong());
        phieuDatPhong.setGhiChu(request.getGhiChu());
        phieuDatPhong.setNgayTao(request.getNgayTao());
        phieuDatPhong.setTienTamUng(0L);
        phieuDatPhong.setTrangThaiHuy(0);

        KhachHang khachHang = khachHangService.getKhachHangById(request.getIdKhachHang());
        phieuDatPhong.setKhachHang(khachHang);

        //Kiểm tra số lượng hạng phòng trống có đủ để đặt
        for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
            if (!thongTinHangPhongService.kiemTraPhongHangPhongTrong(chiTietRequest.getIdHangPhong(), request.getNgayBatDau(),
                    request.getNgayTraPhong(), chiTietRequest.getSoLuong())) {
                throw new AppException(ErrorCode.HANGPHONG_NOT_ENOUGH);
            }
        }

        PhieuDatPhong newPhieuDat = repository.save(phieuDatPhong);
        //Luu chi tiet phieu dat
        List<ChiTietPhieuDat> chiTietPhieuDats = new ArrayList<>();
        for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
            if(chiTietRequest.getSoLuong() > 0) {
                ChiTietPhieuDatRequest chiTietPhieuDatRequest = new ChiTietPhieuDatRequest();
                chiTietPhieuDatRequest.setIdPhieuDat(newPhieuDat.getIdPhieuDat());
                chiTietPhieuDatRequest.setIdHangPhong(chiTietRequest.getIdHangPhong());
                chiTietPhieuDatRequest.setSoLuong(chiTietRequest.getSoLuong());
                Long donGia = thongTinHangPhongService.getDonGiaThongTinHangPhongById(chiTietRequest.getIdHangPhong());
                chiTietPhieuDatRequest.setDonGia(donGia);

                chiTietPhieuDats.add(chiTietPhieuDatService.addNewChiTietPhieuDat(chiTietPhieuDatRequest));
            }
        }

        // Gửi mail thông báo
        sendMailThongBaoDatPhong(newPhieuDat.getIdPhieuDat(), khachHang.getEmail(),
                chiTietPhieuDats);

        return newPhieuDat;
    }

    @Override
    public PhieuDatPhong thanhToanPhieuDat(Integer id, Long tienTamUng) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(id);
        phieuDatPhong.setTienTamUng(tienTamUng);

        return repository.save(phieuDatPhong);
    }

    @Override
    public List<PhieuDatPhong> getAllPhieuDatPhong() {
        return repository.findAll();
    }

    @Override
    public PhieuDatPhong getPhieuDatPhongById(Integer id) throws Exception {
        Optional<PhieuDatPhong> PhieuDatPhongOptional = repository.findById(id);
        if(PhieuDatPhongOptional.isEmpty()){
            throw new Exception("PhieuDatPhong not found. - " + id);
        }
        return PhieuDatPhongOptional.get();
    }

    @Override
    public PhieuDatDetailsResponse getPhieuDatDetailsById(Integer id) throws Exception {
        Optional<PhieuDatPhong> phieuDatPhongOptional = repository.findById(id);
        if(phieuDatPhongOptional.isEmpty()){
            throw new Exception("PhieuDatPhong not found.");
        }
        PhieuDatPhong phieuDat = phieuDatPhongOptional.get();
        long tongTien = 0;
        for (ChiTietPhieuDat chiTiet:phieuDat.getChiTietPhieuDats()) {
            tongTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
        }
        long soNgayThue = ChronoUnit.DAYS.between(phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong());
        tongTien = tongTien * soNgayThue;

        return getPhieuDatDetailsResponse(phieuDatPhongOptional.get(), tongTien);
    }

    @Override
    public PhieuDatResponse getPhieuDatResponseById(Integer id) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(id);
        return getPhieuDatResponse(phieuDatPhong);
    }

//    @Override
//    public List<PhieuDatResponse> getPhieuDatPhongByIdKhachHang(Integer idKhachHang) throws Exception {
//        KhachHang khachHang = khachHangService.getKhachHangById(idKhachHang);
//        List<PhieuDatPhong> phieuDatPhongs = repository.findByKhachHang_IdKhachHang(khachHang.getIdKhachHang());
//        List<PhieuDatResponse> responses = new ArrayList<>();
//        for (PhieuDatPhong phieuDat:phieuDatPhongs) {
//            responses.add(getPhieuDatResponse(phieuDat));
//        }
//        Collections.reverse(responses);
//        return responses;
//    }

    @Override
    public List<PhieuDatUserResponse> getPhieuDatUserByIdKhachHang(/*Integer idKhachHang*/) throws Exception {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        KhachHang khachHang = taiKhoan.getKhachHang();
//        KhachHang khachHang = khachHangService.getKhachHangById(idKhachHang);
        List<PhieuDatPhong> phieuDatPhongs = repository.findByKhachHang_IdKhachHang(khachHang.getIdKhachHang());
        List<PhieuDatUserResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDat:phieuDatPhongs) {
            long tongTien = 0;
            for (ChiTietPhieuDat chiTiet:phieuDat.getChiTietPhieuDats()) {
                tongTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
            }
            long soNgayThue = ChronoUnit.DAYS.between(phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong());
            responses.add(getPhieuDatUserResponse(phieuDat, tongTien * soNgayThue));
        }
        Collections.reverse(responses);
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
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(id);

        if(phieuDatPhong.getTrangThaiHuy() != 0)
            throw new AppException(ErrorCode.PHIEUDAT_NOT_CANCEL);

        if(phieuDatPhong.getChiTietPhieuDats().size() > 0)
            throw new AppException(ErrorCode.PHIEUDAT_DANGCOPHONG);

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

    @Override
    public List<ChiTietUserResponse> getChiTietUserResponseByIdPhieuDat(Integer idPhieuDat) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(idPhieuDat);
        long soNgayDat = ChronoUnit.DAYS.between(phieuDatPhong.getNgayBatDau(), phieuDatPhong.getNgayTraPhong());
        List<ChiTietPhieuDat> chiTietPhieuDats = chiTietPhieuDatService.getChiTietPhieuDatByIdPhieuDat(idPhieuDat);
        List<ChiTietUserResponse> responses = new ArrayList<>();
        for (ChiTietPhieuDat chitiet: chiTietPhieuDats) {
            ChiTietUserResponse response = chiTietPhieuDatService.convertChiTietUserResponse(chitiet, soNgayDat);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<ChiTietUserResponse> getChiTietUserByIdPhieuDat(Integer idPhieuDat) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(idPhieuDat);
        long soNgayDat = ChronoUnit.DAYS.between(phieuDatPhong.getNgayBatDau(), phieuDatPhong.getNgayTraPhong());
        List<ChiTietPhieuDat> chiTietPhieuDats = chiTietPhieuDatService.getChiTietPhieuDatByIdPhieuDat(idPhieuDat);
        List<ChiTietUserResponse> responses = new ArrayList<>();
        for (ChiTietPhieuDat chitiet: chiTietPhieuDats) {
            ChiTietUserResponse response = chiTietPhieuDatService.convertChiTietUserResponse(chitiet, soNgayDat);
            responses.add(response);
        }
        return responses;
    }

    @Autowired
    private EntityManager entityManager;
    @Override
    public List<PhieuDatThoiGianResponse> getPhieuDatPhongTheoNgay(PhieuDatTheoNgayRequest request) {
        List<PhieuDatPhong> phieuDatPhongs;
        if(request.getTrangThai() < 0){
            phieuDatPhongs = repository.findByNgayBatDau(request.getNgay());
        }else{
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PhieuDatTheoNgay", PhieuDatPhong.class)
                    .registerStoredProcedureParameter("ngay", LocalDate.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("trangThai", Integer.class, ParameterMode.IN)
                    .setParameter("ngay", request.getNgay())
                    .setParameter("trangThai", request.getTrangThai());
            phieuDatPhongs = query.getResultList();
        }

        return phieuDatPhongs.stream().map(this::convertPhieuDatThoiGianResponse).toList();
    }

    @Override
    public List<PhieuDatThoiGianResponse> getPhieuDatPhongTheoGian(LocalDate ngayBatDauTim, LocalDate ngayKetThucTim) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PhieuDatTheoThoiGian", PhieuDatPhong.class)
                .registerStoredProcedureParameter("ngay_bat_dau_tim", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ngay_ket_thuc_tim", LocalDate.class, ParameterMode.IN)
                .setParameter("ngay_bat_dau_tim", ngayBatDauTim)
                .setParameter("ngay_ket_thuc_tim", ngayKetThucTim);
        List<PhieuDatPhong> phieuDatPhongs = query.getResultList();
        List<PhieuDatThoiGianResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDatPhong: phieuDatPhongs) {
            responses.add(convertPhieuDatThoiGianResponse(phieuDatPhong));
        }
        return responses;
    }

    @Override
    public List<PhieuDatResponse> getPhieuDatTheoCMND(String cmnd) throws Exception {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PhieuDatKhachHang", PhieuDatPhong.class)
                .registerStoredProcedureParameter("cmnd_tim_kiem", String.class, ParameterMode.IN)
                .setParameter("cmnd_tim_kiem", cmnd);
        List<PhieuDatPhong> phieuDatPhongs = query.getResultList();
        List<PhieuDatResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDatPhong: phieuDatPhongs) {
            responses.add(getPhieuDatResponse(phieuDatPhong));
        }
        return responses;
    }

    @Override
    public PhieuDatPhong huyDatPhong(HuyDatRequest request) throws Exception {
        PhieuDatPhong phieuDat = getPhieuDatPhongById(request.getIdPhieuDat());
        if(phieuDat.getTrangThaiHuy() != 0)
            throw new AppException(ErrorCode.PHIEUDAT_NOT_CANCEL);
        phieuDat.setTrangThaiHuy(2);
        phieuDat.setTienTraLai(request.getTienTra());
        return repository.save(phieuDat);
    }

    @Override
    public void capNhatDanhSachKhachHang(CapNhatKhachHangResquest req) throws Exception {
        for (KhachHangRequest khachHang:req.getKhachHangs()) {
            log.info(khachHang.toString());
            if(khachHang.getHoTen() == null || khachHang.getSdt()== null || khachHang.getCmnd()== null
                    || khachHang.getEmail()== null || khachHang.getHoTen().equals("") || khachHang.getSdt().equals("")
                    || khachHang.getCmnd().equals("") || khachHang.getEmail().equals("")){
                throw new Exception("Thông tin khách hàng chưa hợp lệ");
            }

        }
    }

    @PreAuthorize("hasRole('STAFF')")
    @Override
    public List<PhieuDatUserResponse> getPhieuDatByCccd(String cccd) throws Exception {
        KhachHang khachHang = khachHangService.getKhachHangByCccd(cccd);
        List<PhieuDatPhong> phieuDatPhongs = repository.findByKhachHang_IdKhachHang(khachHang.getIdKhachHang());
        List<PhieuDatUserResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDat:phieuDatPhongs) {
            long tongTien = 0;
            for (ChiTietPhieuDat chiTiet:phieuDat.getChiTietPhieuDats()) {
                tongTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
            }
            long soNgayThue = ChronoUnit.DAYS.between(phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong());
            responses.add(getPhieuDatUserResponse(phieuDat, tongTien * soNgayThue));
        }
        Collections.reverse(responses);
        return responses;
    }

    @Override
    public void boSungChiTietPhieuDat(List<ChiTietPhieuDatRequest> chiTietPhieuDatRequests) throws Exception {
        if(chiTietPhieuDatRequests.size() > 0) {
            PhieuDatPhong phieuDat = getPhieuDatPhongById(chiTietPhieuDatRequests.get(0).getIdPhieuDat());

            //Kiểm tra số lượng hạng phòng trống có đủ để đặt
            for (ChiTietPhieuDatRequest chiTiet : chiTietPhieuDatRequests) {
                int soLuongTrong = thongTinHangPhongService.laySoLuongHangPhongTrong(
                        phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong(), chiTiet.getIdHangPhong());
                if (soLuongTrong < chiTiet.getSoLuong()) {
                    throw new AppException(ErrorCode.HANGPHONG_NOT_ENOUGH);
                }
            }

            List<ChiTietPhieuDatRequest> chiTietPhieuDatNews = new ArrayList<>();
            List<ChiTietPhieuDatRequest> chiTietPhieuDatExists = new ArrayList<>();
            for (ChiTietPhieuDatRequest chiTietRequest : chiTietPhieuDatRequests) {
                boolean isExist = chiTietPhieuDatService.kiemTraChiTietPhieuDat(
                        chiTietRequest.getIdPhieuDat(), chiTietRequest.getIdHangPhong(), chiTietRequest.getDonGia());

                log.info("idPhieuDat " + chiTietRequest.getIdPhieuDat() + " idHangPhong " + chiTietRequest.getIdHangPhong()+ " Ton tai " + isExist);

                if(isExist)
                    chiTietPhieuDatExists.add(chiTietRequest);
                else
                    chiTietPhieuDatNews.add(chiTietRequest);
            }

            if(!chiTietPhieuDatNews.isEmpty())
                chiTietPhieuDatService.addListChiTietPhieuDat(chiTietPhieuDatNews);

            if(!chiTietPhieuDatExists.isEmpty())
                chiTietPhieuDatService.capNhatSoLuongListChiTietPhieuDat(chiTietPhieuDatExists);


            /*//Kiểm tra hạng phòng đã được đặt trước đó(nếu có + số lượng)
            for (ChiTietPhieuDatRequest chiTietRequest : chiTietPhieuDatRequests) {
                for (ChiTietPhieuDat chiTiet : phieuDat.getChiTietPhieuDats()) {
                    if(Objects.equals(chiTietRequest.getIdHangPhong(), chiTiet.getHangPhong().getIdHangPhong())){
                        chiTietRequest.setSoLuong(chiTiet.getSoLuong() + chiTietRequest.getSoLuong());
                    }
                }
            }

            //Thêm chi tiết phiếu đặt
            chiTietPhieuDatService.addListChiTietPhieuDat(chiTietPhieuDatRequests);*/
        }
    }

    @Override
    public PhieuDatPhong capNhatTrangThaiPhieuDat(int idPhieuDat, int trangThai) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(idPhieuDat);
        phieuDatPhong.setTrangThaiHuy(trangThai);
        return repository.save(phieuDatPhong);
    }


    @Override
    public List<QuanLyPhieuDatResponse> getPhieuDatPhongTheoTrang(int pageNumber, int pageSize) throws Exception {
        Pageable firstPageWithTwoElements = PageRequest.of(pageNumber, pageSize, Sort.by("ngayBatDau").descending());
        Page<PhieuDatPhong> allProducts = phieuDatPhongPagingRepository.findAll(firstPageWithTwoElements);
        List<PhieuDatPhong> phieuDatPhongs = allProducts.stream().toList();

        List<QuanLyPhieuDatResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDat:phieuDatPhongs) {
            long tongTien = 0;
            for (ChiTietPhieuDat chiTiet:phieuDat.getChiTietPhieuDats()) {
                tongTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
            }
            long soNgayThue = ChronoUnit.DAYS.between(phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong());
            responses.add(getQuanLyPhieuDatResponse(phieuDat, tongTien * soNgayThue));
        }

        return responses;
    }

    @Override
    public Integer getTongTrangPhieuDatPhong(int pageSize){
        int tongPhieuDat = repository.findAll().size();
        int tongTrang = tongPhieuDat / pageSize;
        if(tongPhieuDat % pageSize != 0)
            tongTrang += 1;

        return tongTrang;
    }

    @Override
    public CapNhatPhieuDatResponse getCapNhatPhieuDatById(Integer id) throws Exception {
        PhieuDatPhong phieuDat = getPhieuDatPhongById(id);
        long tongTien = 0;
        long soNgayThue = ChronoUnit.DAYS.between(phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong());
        List<CapNhatChiTietPhieuDatResponse> chiTietPhieuDatResponses = new ArrayList<>();
        for (ChiTietPhieuDat chiTiet:phieuDat.getChiTietPhieuDats()) {
            tongTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
            int soLuongTrong = thongTinHangPhongService.laySoLuongHangPhongTrong(phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong(), chiTiet.getHangPhong().getIdHangPhong());
            chiTietPhieuDatResponses.add(
                    chiTietPhieuDatService.convertCapNhatChiTietPhieuDatResponse(chiTiet, soNgayThue, soLuongTrong)
            );
        }
        tongTien = tongTien * soNgayThue;
        return getCapNhatPhieuDatDetailsResponse(phieuDat, tongTien, chiTietPhieuDatResponses);
    }

    @Override
    public PhieuDatPhong capNhatPhieuDat(CapNhatPhieuDatRequest request) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(request.getIdPhieuDat());

        if(!request.getNgayNhanPhong().equals(phieuDatPhong.getNgayBatDau())
        || !request.getNgayTraPhong().equals(phieuDatPhong.getNgayTraPhong())){
            if(phieuDatPhong.getTrangThaiHuy() != 0)
                throw new AppException(ErrorCode.PHIEUDAT_NOT_CANCEL);

            if(request.getNgayNhanPhong().isBefore(LocalDate.now()))
                throw new AppException(ErrorCode.THOIGIAN_NOT_VALID_2);

            if(request.getNgayNhanPhong().isAfter(request.getNgayTraPhong()))
                throw new AppException(ErrorCode.THOIGIAN_NOT_VALID);

            //Nếu thời gian mới nằm giữa thời gian cũ => luôn thay đổi được
            if(!request.getNgayNhanPhong().isBefore(phieuDatPhong.getNgayBatDau())
                    && !request.getNgayTraPhong().isAfter(phieuDatPhong.getNgayTraPhong())){
                phieuDatPhong.setNgayBatDau(request.getNgayNhanPhong());
                phieuDatPhong.setNgayTraPhong(request.getNgayTraPhong());
            }else{
                // Kiểm tra hạng phòng đã đặt có đủ trong thời gian mới
                int soLuongTrong = 0;
                for (ChiTietPhieuDat chiTiet : phieuDatPhong.getChiTietPhieuDats()) {
                    if(request.getNgayNhanPhong().isBefore(phieuDatPhong.getNgayBatDau())
                            && request.getNgayTraPhong().isAfter(phieuDatPhong.getNgayBatDau())
                            && !request.getNgayTraPhong().isAfter(phieuDatPhong.getNgayTraPhong())
                    ){
                        LocalDate ngayCuoiKiemTra = phieuDatPhong.getNgayBatDau().minusDays(1);
                        soLuongTrong = thongTinHangPhongService.laySoLuongHangPhongTrong(request.getNgayNhanPhong(),
                                ngayCuoiKiemTra, chiTiet.getHangPhong().getIdHangPhong());
                        log.info("TH2. " + soLuongTrong+ "-" + chiTiet.getSoLuong());
                    }

                    if(request.getNgayTraPhong().isAfter(phieuDatPhong.getNgayTraPhong())
                            && !request.getNgayNhanPhong().isBefore(phieuDatPhong.getNgayBatDau())
                            && request.getNgayNhanPhong().isBefore(phieuDatPhong.getNgayTraPhong())
                    ){
                        LocalDate ngayDauKiemTra = phieuDatPhong.getNgayTraPhong().plusDays(1);
                        soLuongTrong = thongTinHangPhongService.laySoLuongHangPhongTrong(ngayDauKiemTra,
                                request.getNgayTraPhong(), chiTiet.getHangPhong().getIdHangPhong());
                        log.info("TH3. " + soLuongTrong+ "-" + chiTiet.getSoLuong());
                    }

                    if((request.getNgayNhanPhong().isBefore(phieuDatPhong.getNgayBatDau())
                            && request.getNgayTraPhong().isBefore(phieuDatPhong.getNgayBatDau()))
                            || (request.getNgayNhanPhong().isAfter(phieuDatPhong.getNgayTraPhong())
                            && request.getNgayTraPhong().isAfter(phieuDatPhong.getNgayTraPhong()))
                    ){

                        soLuongTrong = thongTinHangPhongService.laySoLuongHangPhongTrong(request.getNgayNhanPhong(),
                                request.getNgayTraPhong(), chiTiet.getHangPhong().getIdHangPhong());
                        log.info("TH1. " + soLuongTrong + "-" + chiTiet.getSoLuong());
                    }

                    if (soLuongTrong < chiTiet.getSoLuong()) {
                        throw new AppException(ErrorCode.HANGPHONG_NOT_CHANGE);
                    }
                }
            }

            phieuDatPhong.setNgayBatDau(request.getNgayNhanPhong());
            phieuDatPhong.setNgayTraPhong(request.getNgayTraPhong());
        }

        if(request.getTienTra() != null)
            phieuDatPhong.setTienTraLai(request.getTienTra());

        return repository.save(phieuDatPhong);
    }

    @Override
    public List<PhieuDatUserResponse> getPhieuDatPhongKhachHangTheoTrang(int pageNumber, int pageSize) throws Exception {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        KhachHang khachHang = taiKhoan.getKhachHang();


        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("ngayBatDau").descending());
        List<PhieuDatPhong> phieuDatPhongs = phieuDatPhongPagingRepository.findByKhachHang_IdKhachHang(khachHang.getIdKhachHang(), page);

        List<PhieuDatUserResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDat:phieuDatPhongs) {
            long tongTien = 0;
            for (ChiTietPhieuDat chiTiet:phieuDat.getChiTietPhieuDats()) {
                tongTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
            }
            long soNgayThue = ChronoUnit.DAYS.between(phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong());
            responses.add(getPhieuDatUserResponse(phieuDat, tongTien * soNgayThue));
        }

        return responses;
    }

    @Override
    public Integer getTongTrangPhieuDatPhongKhachHang(int pageSize){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        KhachHang khachHang = taiKhoan.getKhachHang();

        int tongPhieuDat = (int) repository.countByKhachHang_IdKhachHang(khachHang.getIdKhachHang());

        return (int) Math.ceil((tongPhieuDat / pageSize));
    }

    @Override
    public List<PhieuDatUserResponse> getPhieuDatPhongCccdTheoTrang(int pageNumber, int pageSize, String cccd) throws Exception {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("ngayBatDau").descending());
//        List<PhieuDatPhong> phieuDatPhongs = phieuDatPhongPagingRepository.findByKhachHang_Cmnd(cccd, page);
        List<PhieuDatPhong> phieuDatPhongs = phieuDatPhongPagingRepository.findByKhachHangForCheckin(cccd, page);

        List<PhieuDatUserResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDat:phieuDatPhongs) {
            long tongTien = 0;
            for (ChiTietPhieuDat chiTiet:phieuDat.getChiTietPhieuDats()) {
                tongTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
            }
            long soNgayThue = ChronoUnit.DAYS.between(phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong());
            responses.add(getPhieuDatUserResponse(phieuDat, tongTien * soNgayThue));
        }

        return responses;
    }

    @Override
    public Integer getTongTrangPhieuDatPhongCccd(int pageSize, String cccd){
        int tongPhieuDat = (int) repository.countByKhachHangForCheckin(cccd);
        int tongTrang = tongPhieuDat / pageSize;
        if(tongPhieuDat % pageSize != 0)
            tongTrang += 1;

        return tongTrang;
    }

    @Override
    public List<QuanLyPhieuDatResponse> getPhieuDatFilter(int luaChon, LocalDate ngayBatDauLoc, LocalDate ngayKetThucLoc, int trangThai, String noiDung) throws Exception {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PhieuDatFilter", PhieuDatPhong.class)
                .registerStoredProcedureParameter("lua_chon", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ngay_bat_dau_loc", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ngay_ket_thuc_loc", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("trang_thai", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("noi_dung", String.class, ParameterMode.IN)
                .setParameter("lua_chon", luaChon)
                .setParameter("ngay_bat_dau_loc", ngayBatDauLoc)
                .setParameter("ngay_ket_thuc_loc", ngayKetThucLoc)
                .setParameter("trang_thai", trangThai)
                .setParameter("noi_dung", noiDung);
        List<PhieuDatPhong> phieuDatPhongs = query.getResultList();
        List<QuanLyPhieuDatResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDat:phieuDatPhongs) {
            long tongTien = 0;
            for (ChiTietPhieuDat chiTiet:phieuDat.getChiTietPhieuDats()) {
                tongTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
            }
            long soNgayThue = ChronoUnit.DAYS.between(phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong());
            responses.add(getQuanLyPhieuDatResponse(phieuDat, tongTien * soNgayThue));
        }

        return responses;
    }

    // Tự động hủy phiếu đặt quá hạn thanh toán
    @Override
    public void tuDongHuyPhieuDats() throws Exception {
        List<PhieuDatPhong> phieuDatPhongs = getAllPhieuDatPhong();
        for (PhieuDatPhong phieuDatPhong: phieuDatPhongs) {
            LocalDate ngayHienTai = LocalDate.now();
            long khoangCachNgay = ChronoUnit.DAYS.between(phieuDatPhong.getNgayTao(), ngayHienTai);
            if(phieuDatPhong.getTrangThaiHuy() == 0
                    &&phieuDatPhong.getTienTamUng() <= 0
                    && khoangCachNgay > soNgayPhaiThanhToan){
                phieuDatPhong.setTrangThaiHuy(2);
                phieuDatPhong.setTienTraLai(0L);
                repository.save(phieuDatPhong);

                log.info("Đã hủy phiếu đặt id " + phieuDatPhong.getIdPhieuDat());
                String emailKhachHang = phieuDatPhong.getKhachHang().getEmail();
                sendMailThongBaoHuyPhieuDat(phieuDatPhong.getIdPhieuDat(), emailKhachHang);
            }
        }
    }

    @Autowired
    private EmailSenderService senderService;
    public void sendMailThongBaoHuyPhieuDat(int idPhieuDat, String emailKhachHang) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(idPhieuDat);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        senderService.sendSimpleEmail(emailKhachHang,
                "Thông tin hủy phiếu đặt phòng khách sạn",
                "Mã phiếu đặt " + idPhieuDat
                + "\nHệ thống đã tự động hủy phiếu đặt phòng của bạn vì không thanh toán tạm ứng trong khoảng " +
                        "thời gian quy định vào trước ngày " + phieuDatPhong.getNgayTao().plusDays(3).format(formatter)
        );

    }

    @Override
    public void sendMailThongBaoDatPhong(int idPhieuDat, String emailKhachHang, List<ChiTietPhieuDat> chiTietPhieuDats) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(idPhieuDat);
        int soNgay = (int) ChronoUnit.DAYS.between(phieuDatPhong.getNgayBatDau(), phieuDatPhong.getNgayTraPhong());
        if(phieuDatPhong.getNgayBatDau().equals(phieuDatPhong.getNgayTraPhong()))
            soNgay = 1;
        long tongTien = 0;
        String noiDungChiTietPhieuDat = "";
        int i = 0;
        for (ChiTietPhieuDat chiTiet:chiTietPhieuDats) {
            tongTien += chiTiet.getSoLuong() * chiTiet.getDonGia() * soNgay;
            noiDungChiTietPhieuDat += (i + 1) + ". "
                    + chiTiet.getHangPhong().getTenHangPhong()
                    + ", Số lượng: " + chiTiet.getSoLuong()
                    + ", Đơn giá: " + chiTiet.getDonGia() + " VND"
                    + ", Thành tiền: "+ chiTiet.getSoLuong() * chiTiet.getDonGia() * soNgay + " VND";
            if(i < chiTietPhieuDats.size() - 1)
                noiDungChiTietPhieuDat += "\n";
            i++;
        }
        senderService.sendSimpleEmail(emailKhachHang,
                "Thông tin đặt phòng khách sạn",
                "Bạn đã đặt phòng thành công."
                        + "\nMã phiếu đặt " + idPhieuDat
                        + "\n" + noiDungChiTietPhieuDat
//                        + "\nTiền tạm ứng: " + tienTamUng + " VND"
                        + "\nTổng tiền phiếu đặt: " + tongTien + " VND"
        );

        log.info("Send mail to " + emailKhachHang + " with id " + idPhieuDat);
    }

    @Override
    public List<QuanLyPhieuDatResponse> getPhieuDatKhachHangFilter(int luaChon, LocalDate ngayBatDauLoc, LocalDate ngayKetThucLoc, int trangThai, Integer idPhieuDat) throws Exception {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        KhachHang khachHang = taiKhoan.getKhachHang();

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PhieuDatKhachHangFilter", PhieuDatPhong.class)
                .registerStoredProcedureParameter("lua_chon", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ngay_bat_dau_loc", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ngay_ket_thuc_loc", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("trang_thai", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idphieu_dat", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("idkhach_hang", Integer.class, ParameterMode.IN)
                .setParameter("lua_chon", luaChon)
                .setParameter("ngay_bat_dau_loc", ngayBatDauLoc)
                .setParameter("ngay_ket_thuc_loc", ngayKetThucLoc)
                .setParameter("trang_thai", trangThai)
                .setParameter("idphieu_dat", idPhieuDat)
                .setParameter("idkhach_hang", khachHang.getIdKhachHang());
        List<PhieuDatPhong> phieuDatPhongs = query.getResultList();
        List<QuanLyPhieuDatResponse> responses = new ArrayList<>();
        for (PhieuDatPhong phieuDat:phieuDatPhongs) {
            long tongTien = 0;
            for (ChiTietPhieuDat chiTiet:phieuDat.getChiTietPhieuDats()) {
                tongTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
            }
            long soNgayThue = ChronoUnit.DAYS.between(phieuDat.getNgayBatDau(), phieuDat.getNgayTraPhong());
            responses.add(getQuanLyPhieuDatResponse(phieuDat, tongTien * soNgayThue));
        }

        return responses;
    }

    // Khoi phuc phieu dat sau khi da huy
    @Override
    public ApiResponse khoiPhucPhieuDat(int idPhieuDat) throws Exception {
        PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(idPhieuDat);
        LocalDate ngayHienTai = LocalDate.now();
        if(phieuDatPhong.getNgayBatDau().isBefore(ngayHienTai)){
            throw new AppException(ErrorCode.THOIGIAN_NOT_VALID);
        }

        List<ChiTietPhieuDat> chiTietPhieuDats = phieuDatPhong.getChiTietPhieuDats();
        String message = "";
        for (ChiTietPhieuDat chiTietPhieuDat:chiTietPhieuDats) {
            int soLuongTrong = thongTinHangPhongService.laySoLuongHangPhongTrong(
                    phieuDatPhong.getNgayBatDau(), phieuDatPhong.getNgayTraPhong(), chiTietPhieuDat.getHangPhong().getIdHangPhong());
            if(soLuongTrong < chiTietPhieuDat.getSoLuong()){
//                throw new AppException(ErrorCode.HANGPHONG_NOT_ENOUGH);
                message += ", " + chiTietPhieuDat.getHangPhong().getTenHangPhong() + " chỉ còn lại " + soLuongTrong + " phòng";
            }
        }

        if(!message.equals("")){
            message = "Không đủ hạng phòng trống" + message;
            return ApiResponse.builder()
                    .code(1000)
                    .message(message)
                    .build();
        }else {
            phieuDatPhong.setTrangThaiHuy(0);
            phieuDatPhong.setTienTraLai(0L);

            repository.save(phieuDatPhong);

            return ApiResponse.builder()
                    .code(200)
                    .result(getCapNhatPhieuDatById(phieuDatPhong.getIdPhieuDat()))
                    .build();
        }
    }

    @Override
    public PhieuDatPhong datPhongKhachSan2(PhieuDatRequest request) throws Exception {
        if(request.getNgayBatDau().isAfter(request.getNgayTraPhong()) || request.getNgayBatDau().isBefore(LocalDate.now()))
            throw new AppException(ErrorCode.THOIGIAN_NOT_VALID);
        PhieuDatPhong phieuDatPhong = new PhieuDatPhong();
        phieuDatPhong.setNgayBatDau(request.getNgayBatDau());
        phieuDatPhong.setNgayTraPhong(request.getNgayTraPhong());
        phieuDatPhong.setGhiChu(request.getGhiChu());
        phieuDatPhong.setNgayTao(LocalDate.now());
        phieuDatPhong.setTienTamUng(request.getTienTamUng());
        phieuDatPhong.setTrangThaiHuy(0);

        KhachHang khachHang = khachHangService.getKhachHangById(request.getIdKhachHang());
        phieuDatPhong.setKhachHang(khachHang);

        //Kiểm tra số lượng hạng phòng trống có đủ để đặt
        for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
            if (!thongTinHangPhongService.kiemTraPhongHangPhongTrong(chiTietRequest.getIdHangPhong(), request.getNgayBatDau(),
                    request.getNgayTraPhong(), chiTietRequest.getSoLuong())) {
                throw new AppException(ErrorCode.HANGPHONG_NOT_ENOUGH);
            }
        }

        PhieuDatPhong newPhieuDat = repository.save(phieuDatPhong);
        //Luu chi tiet phieu dat
        List<ChiTietPhieuDat> chiTietPhieuDats = new ArrayList<>();
        for (ChiTietRequest chiTietRequest: request.getChiTietRequests()) {
            if(chiTietRequest.getSoLuong() > 0) {
                ChiTietPhieuDatRequest chiTietPhieuDatRequest = new ChiTietPhieuDatRequest();
                chiTietPhieuDatRequest.setIdPhieuDat(newPhieuDat.getIdPhieuDat());
                chiTietPhieuDatRequest.setIdHangPhong(chiTietRequest.getIdHangPhong());
                chiTietPhieuDatRequest.setSoLuong(chiTietRequest.getSoLuong());
                Long donGia = thongTinHangPhongService.getDonGiaThongTinHangPhongById(chiTietRequest.getIdHangPhong());
                chiTietPhieuDatRequest.setDonGia(donGia);

                chiTietPhieuDats.add(chiTietPhieuDatService.addNewChiTietPhieuDat(chiTietPhieuDatRequest));
            }
        }

        // Gửi mail thông báo
        sendMailThongBaoDatPhong(newPhieuDat.getIdPhieuDat(), khachHang.getEmail(),
                chiTietPhieuDats);

        return newPhieuDat;
    }

    @Override
    public ApiResponse bookingBySP(PhieuDatRequest request) throws Exception{
        if(CollectionUtils.isEmpty(request.getChiTietRequests())){
            throw new AppException(ErrorCode.PHIEUDAT_VALID);
        }
        StringBuilder idHangPhongList= new StringBuilder();
        StringBuilder soLuongList= new StringBuilder();
        StringBuilder donGiaList=new StringBuilder();
        for (ChiTietRequest chiTiet: request.getChiTietRequests()) {
            if(chiTiet.getSoLuong() > 0) {
                idHangPhongList.append(chiTiet.getIdHangPhong()).append(",");
                soLuongList.append(chiTiet.getSoLuong()).append(",");
                donGiaList.append(chiTiet.getDonGia()).append(",");
            }
        }

        idHangPhongList = new StringBuilder(idHangPhongList.substring(0, idHangPhongList.length() - 1));
        soLuongList = new StringBuilder(soLuongList.substring(0, soLuongList.length() - 1));
        donGiaList = new StringBuilder(donGiaList.substring(0, donGiaList.length() - 1));

        int idPhieuDat = getNewIdPhieuDat();
        LocalDate ngayTao = LocalDate.now();
        int trangThaiHuy = 0;
        long tienTraLai = 0;
        boolean result = runSPBooking(idHangPhongList.toString(), soLuongList.toString(), donGiaList.toString(),
                idPhieuDat, request.getIdKhachHang(), request.getIdNhanVien()
                ,request.getNgayBatDau(), request.getNgayTraPhong(), request.getGhiChu()
                ,ngayTao, request.getTienTamUng(), trangThaiHuy, tienTraLai);

        if(result){
            PhieuDatPhong phieuDatPhong = getPhieuDatPhongById(idPhieuDat);
            sendMailThongBaoDatPhong(phieuDatPhong.getIdPhieuDat(), phieuDatPhong.getKhachHang().getEmail(),phieuDatPhong.getChiTietPhieuDats());

            return ApiResponse.builder()
                    .code(200)
                    .message("Đặt phòng thành công")
                    .result(idPhieuDat)
                    .build();
        }else{
            throw new AppException(ErrorCode.HANGPHONG_NOT_ENOUGH);
        }
    }

    private int getNewIdPhieuDat() {
        List<PhieuDatPhong> phieuDatPhongs = repository.findAll();
        Collections.sort(phieuDatPhongs, new Comparator<PhieuDatPhong>() {
            @Override
            public int compare(PhieuDatPhong o1, PhieuDatPhong o2) {
                return o1.getIdPhieuDat() < o2.getIdPhieuDat() ? 1 : -1;
            }
        });

        return phieuDatPhongs.get(0).getIdPhieuDat() + 1;
    }


    public boolean runSPBooking(String idHangPhongList, String soLuongList, String donGiaList,
                                int idPhieuDat, int idKhachHang, Integer idNhanVien, LocalDate ngayNhanPhong,
                                LocalDate ngayTraPhong, String ghiChu, LocalDate ngayTao, long tienTamUng,
                                int trangThaiHuy, long tienTraLai) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_booking")
                .registerStoredProcedureParameter("input_idhangphongs", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("input_soluongs", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("input_dongias", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_idphieu_dat", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_idkhach_hang", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_idnhan_vien", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_ngay_bat_dau", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_ngay_tra_phong", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_ghi_chu", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_ngay_tao", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_tien_tam_ung", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_trang_thai_huy", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_tien_tra_lai", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("is_enough", Boolean.class, ParameterMode.OUT)
                .setParameter("input_idhangphongs", idHangPhongList)
                .setParameter("input_soluongs", soLuongList)
                .setParameter("input_dongias", donGiaList)
                .setParameter("in_idphieu_dat", idPhieuDat)
                .setParameter("in_idkhach_hang", idKhachHang)
                .setParameter("in_idnhan_vien", idNhanVien)
                .setParameter("in_ngay_bat_dau", ngayNhanPhong)
                .setParameter("in_ngay_tra_phong", ngayTraPhong)
                .setParameter("in_ghi_chu", ghiChu)
                .setParameter("in_ngay_tao", ngayTao)
                .setParameter("in_tien_tam_ung", tienTamUng)
                .setParameter("in_trang_thai_huy", trangThaiHuy)
                .setParameter("in_tien_tra_lai", tienTraLai);

        query.execute();

        return (boolean) query.getOutputParameterValue("is_enough");
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

    public PhieuDatUserResponse getPhieuDatUserResponse(PhieuDatPhong phieuDatPhong, long tongTien) throws Exception {
        return new PhieuDatUserResponse(
                phieuDatPhong.getIdPhieuDat(),
                phieuDatPhong.getNgayBatDau(),
                phieuDatPhong.getNgayTraPhong(),
                phieuDatPhong.getGhiChu(),
                phieuDatPhong.getNgayTao(),
                phieuDatPhong.getTienTamUng(),
                phieuDatPhong.getKhachHang().getIdKhachHang(),
                phieuDatPhong.getNhanVien() == null ? null :
                        phieuDatPhong.getNhanVien().getIdNhanVien(),
                phieuDatPhong.getTrangThaiHuy(),
                tongTien
        );
    }

    public QuanLyPhieuDatResponse getQuanLyPhieuDatResponse(PhieuDatPhong phieuDatPhong, long tongTien) throws Exception {
        return QuanLyPhieuDatResponse.builder()
                .idPhieuDat(phieuDatPhong.getIdPhieuDat())
                .ngayBatDau(phieuDatPhong.getNgayBatDau())
                .ngayTraPhong(phieuDatPhong.getNgayTraPhong())
                .ngayTao(phieuDatPhong.getNgayTao())
                .idKhachHang(phieuDatPhong.getKhachHang().getIdKhachHang())
                .tenKhachHang(phieuDatPhong.getKhachHang().getHoTen())
                .idNhanVien(null)
                .tienTamUng(phieuDatPhong.getTienTamUng())
                .tongTien(tongTien)
                .tienTra(phieuDatPhong.getTienTraLai())
                .trangThaiHuy(phieuDatPhong.getTrangThaiHuy())
                .build();
    }

    public PhieuDatDetailsResponse getPhieuDatDetailsResponse(PhieuDatPhong phieuDatPhong, long tongTien) throws Exception {
        List<ChiTietUserResponse> chiTietUserResponses = getChiTietUserByIdPhieuDat(phieuDatPhong.getIdPhieuDat());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String thongBao = "";
        LocalDate ngayHienTai = LocalDate.now();
        long khoangCachNgay = ChronoUnit.DAYS.between(phieuDatPhong.getNgayTao(), ngayHienTai);
        if(phieuDatPhong.getTrangThaiHuy() == 0 &&
                phieuDatPhong.getTienTamUng() <= 0
                && khoangCachNgay <= soNgayPhaiThanhToan && khoangCachNgay >= 0){
            thongBao = "Vui lòng thanh toán tạm ứng trước ngày "
                    + phieuDatPhong.getNgayTao().plusDays(3).format(formatter)
                    + ", nếu không phiếu đặt của bạn sẽ bị hủy.";
        }

        return new PhieuDatDetailsResponse(
                phieuDatPhong.getIdPhieuDat(),
                phieuDatPhong.getNgayBatDau(),
                phieuDatPhong.getNgayTraPhong(),
                phieuDatPhong.getGhiChu(),
                phieuDatPhong.getNgayTao(),
                phieuDatPhong.getTienTamUng(),
                phieuDatPhong.getKhachHang().getIdKhachHang(),
                phieuDatPhong.getKhachHang().getHoTen(),
                phieuDatPhong.getNhanVien() == null ? null :
                        phieuDatPhong.getNhanVien().getIdNhanVien(),
                phieuDatPhong.getTrangThaiHuy(),
                phieuDatPhong.getTienTraLai(),
                tongTien,
                thongBao,
                chiTietUserResponses
        );
    }

    public PhieuDatThoiGianResponse convertPhieuDatThoiGianResponse(PhieuDatPhong phieuDatPhong){
        String tenKhachHang = phieuDatPhong.getKhachHang().getHoTen();
        String cmnd = phieuDatPhong.getKhachHang().getCmnd();
        String sdt = phieuDatPhong.getKhachHang().getSdt();

        long soNgayThue = ChronoUnit.DAYS.between(phieuDatPhong.getNgayBatDau(), phieuDatPhong.getNgayTraPhong());
        long tongTien = 0;
        for (ChiTietPhieuDat chiTiet:phieuDatPhong.getChiTietPhieuDats()) {
            tongTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
        }

        tongTien = tongTien * soNgayThue;
        return new PhieuDatThoiGianResponse(
                phieuDatPhong.getIdPhieuDat(),
                phieuDatPhong.getNgayBatDau(),
                phieuDatPhong.getNgayTraPhong(),
                phieuDatPhong.getNgayTao(),
                soNgayThue,
                phieuDatPhong.getGhiChu(),
                phieuDatPhong.getTienTamUng(),
                tongTien,
                tenKhachHang,
                cmnd,
                sdt,
                phieuDatPhong.getTrangThaiHuy()
        );
    }

    public CapNhatPhieuDatResponse getCapNhatPhieuDatDetailsResponse(PhieuDatPhong phieuDatPhong, long tongTien, List<CapNhatChiTietPhieuDatResponse> chiTietPhieuDatResponses) throws Exception {
        return new CapNhatPhieuDatResponse(
                phieuDatPhong.getIdPhieuDat(),
                phieuDatPhong.getNgayBatDau(),
                phieuDatPhong.getNgayTraPhong(),
                phieuDatPhong.getGhiChu(),
                phieuDatPhong.getNgayTao(),
                phieuDatPhong.getTienTamUng(),
                phieuDatPhong.getKhachHang().getIdKhachHang(),
                phieuDatPhong.getKhachHang().getHoTen(),
                phieuDatPhong.getNhanVien() == null ? null :
                        phieuDatPhong.getNhanVien().getIdNhanVien(),
                phieuDatPhong.getTrangThaiHuy(),
                phieuDatPhong.getTienTraLai(),
                tongTien,
                chiTietPhieuDatResponses
        );
    }
}
