package com.demo.MiniHotel.modules.khachhang.implement;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.chitiet_phuthu.dto.ChiTietPhuThuPhongResponse;
import com.demo.MiniHotel.modules.chitiet_sudung_dichvu.dto.ChiTietDichVuPhongResponse;
import com.demo.MiniHotel.modules.khachhang.dto.*;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.phieudatphong.service.IPhieuDatService;
import com.demo.MiniHotel.modules.phieuthuephong.dto.PhieuThueResponse;
import com.demo.MiniHotel.modules.taikhoan.service.ITaiKhoanService;
import com.demo.MiniHotel.repository.KhachHangPagingRepository;
import com.demo.MiniHotel.repository.KhachHangRepository;
import com.demo.MiniHotel.repository.PhieuDatPhongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhachHangImplement implements IKhachHangService {
    private final KhachHangRepository repository;
    private final ITaiKhoanService taiKhoanService;
    private final PhieuDatPhongRepository phieuDatPhongRepository;
    private final KhachHangPagingRepository khachHangPagingRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public KhachHangResponse addNewKhachHang(KhachHangRequest request) throws Exception {
        if(repository.existsByCmnd(request.getCmnd()))
            throw new RuntimeException("CCCD đã tồn tại");

        KhachHang khachHang = new KhachHang();
        khachHang.setCmnd(request.getCmnd());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMaSoThue(null);
        khachHang.setEmail(request.getEmail());
        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setGioiTinh(request.isGioiTinh());

        if(request.getIdTaiKhoan() != null){
            TaiKhoan taiKhoan = taiKhoanService.getTaiKhoanById(request.getIdTaiKhoan());
            khachHang.setTaiKhoan(taiKhoan);
        }
        KhachHang newKhachHang = repository.save(khachHang);
        KhachHangResponse response = convertKhachHangToResponse(newKhachHang);

        return response;
    }

    @Override
    public KhachHangResponse addKhachHangDatPhong(KhachHangRequest request) throws Exception {
        Optional<KhachHang> khachHangOptional = repository.findByCmnd(request.getCmnd());
        if(khachHangOptional.isPresent()){
            KhachHang khachHangExist = khachHangOptional.get();
            if(request.getHoTen() != null && !request.getHoTen().equals(""))
                khachHangExist.setHoTen(request.getHoTen());
            if(request.getSdt() != null && !request.getSdt().equals(""))
                khachHangExist.setSdt(request.getSdt());
            if(request.getEmail() != null && !request.getEmail().equals(""))
                khachHangExist.setEmail(request.getEmail());
            if(request.getDiaChi() != null && !request.getDiaChi().equals(""))
                khachHangExist.setDiaChi(request.getDiaChi());
            khachHangExist.setGioiTinh(request.isGioiTinh());
            khachHangExist.setNgaySinh(request.getNgaySinh());
            if(request.getNgaySinh() != null && !request.getNgaySinh().equals(""))
                khachHangExist.setNgaySinh(request.getNgaySinh());

            return convertKhachHangToResponse(repository.save(khachHangExist));
        }
        KhachHang khachHang = new KhachHang();
        khachHang.setCmnd(request.getCmnd());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setEmail(request.getEmail());
        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setGioiTinh(request.isGioiTinh());

        return convertKhachHangToResponse(repository.save(khachHang));
    }

    @Override
    public List<KhachHang> getAllKhachHang() {
        return repository.findAll();
    }

    @Override
    public KhachHang getKhachHangById(Integer id) throws Exception {
        Optional<KhachHang> KhachHangOptional = repository.findById(id);
        if(KhachHangOptional.isEmpty()){
            throw new Exception("KhachHang not found.");
        }
        return KhachHangOptional.get();
    }

    @Override
    public KhachHangResponse getKhachHangBySdt(String sdt) throws Exception {
        Optional<KhachHang> khachHangOptional = repository.findBySdt(sdt);
        if(khachHangOptional.isEmpty()){
            throw new Exception("KhachHang not found.");
        }
        KhachHang khachHang = khachHangOptional.get();
        return convertKhachHangToResponse(khachHang);
    }

    @Override
    public KhachHangResponse getKhachHangResponseByCCCD(String cccd) throws Exception {
        Optional<KhachHang> khachHangOptional = repository.findByCmnd(cccd);
        if(khachHangOptional.isEmpty()){
            throw new AppException(ErrorCode.KHACHHANG_NOT_FOUND);
        }
        KhachHang khachHang = khachHangOptional.get();
        return convertKhachHangToResponse(khachHang);
    }

    @Override
    public KhachHang getKhachHangByCccd(String cccd) throws RuntimeException {
        Optional<KhachHang> khachHangOptional = repository.findByCmnd(cccd);
        if(khachHangOptional.isEmpty()){
            throw new AppException(ErrorCode.CCCD_NOT_EXISTED);
        }
        return khachHangOptional.get();
    }

    @Override
    public KhachHangResponse getKhachHangByIdPhieuDat(int idPhieuDat) throws Exception {
        Optional<PhieuDatPhong> phieuDatPhongOptional = phieuDatPhongRepository.findById(idPhieuDat);
        if(phieuDatPhongOptional.isEmpty()){
            throw new Exception("PhieuDat not found.");
        }
        PhieuDatPhong phieuDatPhong = phieuDatPhongOptional.get();
        if(phieuDatPhong.getKhachHang() != null) {
            KhachHang khachHang = phieuDatPhong.getKhachHang();
            return convertKhachHangToResponse(khachHang);
        }
        return null;
    }

    private KhachHangResponse convertKhachHangToResponse(KhachHang khachHang) {
        return new KhachHangResponse(khachHang.getIdKhachHang(), khachHang.getCmnd(),
                khachHang.getHoTen(), khachHang.getSdt(),
                khachHang.getDiaChi(), khachHang.getEmail(),
                khachHang.isGioiTinh(), khachHang.getNgaySinh()
        );
    }

    @Override
    public KhachHang updateKhachHang(KhachHangRequest request, Integer id) throws Exception {
        KhachHang khachHang = getKhachHangById(id);

        khachHang.setCmnd(request.getCmnd());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMaSoThue(null);
        khachHang.setEmail(request.getEmail());

        if(request.getIdTaiKhoan() != null){
            TaiKhoan taiKhoan = taiKhoanService.getTaiKhoanById(request.getIdTaiKhoan());
            khachHang.setTaiKhoan(taiKhoan);
        }

        return repository.save(khachHang);
    }

    @Override
    public void deleteKhachHang(Integer id) throws Exception {
        KhachHang khachHang = getKhachHangById(id);
        if(khachHang.getPhieuDatPhongs().size() > 0
                || khachHang.getPhieuThuePhongs().size() > 0
        || khachHang.getChiTietPhieuThues().size() > 0){
            throw new AppException(ErrorCode.KHACHHANG_DADATTHUE);
        }

        repository.deleteById(id);
    }

    @Override
    public KhachHangResponse getKhachHangResponseById(Integer id) throws Exception {
        KhachHang khachHang = getKhachHangById(id);
        return convertKhachHangToResponse(khachHang);
    }

    @Override
    public KhachHangResponse updateKhachHangResponse(int id, KhachHangRequest request) throws Exception {
        KhachHang khachHang = getKhachHangById(id);

        khachHang.setCmnd(request.getCmnd());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMaSoThue(null);
        khachHang.setEmail(request.getEmail());

        KhachHang newKhachHang = repository.save(khachHang);
        return convertKhachHangToResponse(newKhachHang);
    }

    @Override
    public ApiResponse importKhachHangUpload(KhachHangUpload request) throws Exception {
        Optional<KhachHang> khachHangOptional = repository.findByCmnd(request.getCccd());
        if(khachHangOptional.isPresent()){
            KhachHang khachHangExist = khachHangOptional.get();

            if(request.getGioiTinh().trim().equalsIgnoreCase("nam")
                    || request.getGioiTinh().trim().equalsIgnoreCase("nữ"))
                khachHangExist.setGioiTinh(request.getGioiTinh().trim().equalsIgnoreCase("nữ"));
            else
                throw new AppException(ErrorCode.DATA_IMPORT_NOT_VALID);

            if(!khachHangExist.getEmail().equals(request.getEmail()) && repository.existsByEmail(request.getEmail()))
                throw new AppException(ErrorCode.EMAIL_EXISTED);

            try{
                khachHangExist.setHoTen(request.getHoTen());
                khachHangExist.setSdt(request.getSdt());
                khachHangExist.setEmail(request.getEmail());
                khachHangExist.setDiaChi(request.getDiaChi());
                List<String> ngaySinhList = List.of(request.getNgaySinh().split("/"));
                khachHangExist.setNgaySinh(LocalDate.parse(ngaySinhList.get(2) + "-" + ngaySinhList.get(1) + "-" + ngaySinhList.get(0)));
            }catch (Exception e){
                throw new AppException(ErrorCode.DATA_IMPORT_NOT_VALID);
            }
            repository.save(khachHangExist);

            return ApiResponse.builder()
                    .code(200)
                    .message("Cập nhật thông tin khách hàng thành công")
                    .build();
        }
        KhachHang khachHang = new KhachHang();

        if(request.getGioiTinh().trim().equalsIgnoreCase("nam")
            || request.getGioiTinh().trim().equalsIgnoreCase("nữ"))
            khachHang.setGioiTinh(request.getGioiTinh().trim().equalsIgnoreCase("nữ"));
        else
            throw new AppException(ErrorCode.DATA_IMPORT_NOT_VALID);

        if(repository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        try{
            khachHang.setCmnd(request.getCccd());
            khachHang.setHoTen(request.getHoTen());
            khachHang.setSdt(request.getSdt());
            khachHang.setDiaChi(request.getDiaChi());
            khachHang.setEmail(request.getEmail());
            List<String> ngaySinhList = List.of(request.getNgaySinh().split("/"));
            khachHang.setNgaySinh(LocalDate.parse(ngaySinhList.get(2) + "-" + ngaySinhList.get(1) + "-" + ngaySinhList.get(0)));
        }catch (Exception e){
            throw new AppException(ErrorCode.DATA_IMPORT_NOT_VALID);
        }
        repository.save(khachHang);

        return ApiResponse.builder()
                .code(200)
                .message("Thêm mới khách hàng thành công")
                .build();
    }

    @Override
    public void importToanBoKhachHang(List<KhachHangUpload> khachHangUploads) throws Exception {
        for (KhachHangUpload khachHangUpload: khachHangUploads) {
            importKhachHangUpload(khachHangUpload);
        }
    }

    @Override
    public Integer getTongTrangKhachHang(int pageSize){
        int tongKhachHang = repository.findAll().size();
        int tongTrang = tongKhachHang / pageSize;
        if(tongKhachHang % pageSize != 0)
            tongTrang += 1;

        return tongTrang;
    }

    @Override
    public List<KhachHangResponse> getKhachHangTheoTrang(int pageNumber, int pageSize) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("idKhachHang").descending());
        Page<KhachHang> khachHangPage = khachHangPagingRepository.findAll(pageable);
        List<KhachHang> khachHangs = khachHangPage.stream().toList();

        return khachHangs.stream().map(this::convertKhachHangToResponse).collect(Collectors.toList());
    }

    @Override
    public KhachHangResponse themKhachHang(KhachHangRequest request) {
        if(repository.existsByCmnd(request.getCmnd().trim()))
            throw new AppException(ErrorCode.CCCD_EXISTED);

        if(repository.existsByEmail(request.getEmail().trim()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        KhachHang khachHang = new KhachHang();
        khachHang.setCmnd(request.getCmnd().trim());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMaSoThue(null);
        khachHang.setEmail(request.getEmail().trim());
        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setGioiTinh(request.isGioiTinh());

        if(request.getIdTaiKhoan() != null){
            TaiKhoan taiKhoan = taiKhoanService.getTaiKhoanById(request.getIdTaiKhoan());
            khachHang.setTaiKhoan(taiKhoan);
        }
        KhachHang newKhachHang = repository.save(khachHang);

        return convertKhachHangToResponse(newKhachHang);
    }

    @Override
    public KhachHangResponse capNhatKhachHang(KhachHangRequest request, Integer id) throws Exception {
        KhachHang khachHang = getKhachHangById(id);


        if(!khachHang.getCmnd().trim().equals(request.getCmnd().trim()) && repository.existsByCmnd(request.getCmnd()))
            throw new AppException(ErrorCode.CCCD_EXISTED);

        if(!khachHang.getEmail().trim().equals(request.getEmail().trim()) && repository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        khachHang.setCmnd(request.getCmnd().trim());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMaSoThue(null);
        khachHang.setEmail(request.getEmail().trim());

        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setGioiTinh(request.isGioiTinh());

        return convertKhachHangToResponse(repository.save(khachHang));
    }

    @Override
    public KhachHangProfileResponse getKhachHangProfileByToken() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        KhachHang khachHang = repository.findByTaiKhoan_TenDangNhap(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return convertKhachHangProfileResponse(khachHang);
    }

    @Override
    public KhachHangProfileResponse capNhatProfileKhachHang(KhachHangProfileResquest request) throws Exception {
        KhachHang khachHang = getKhachHangById(request.getIdKhachHang());

        if(!khachHang.getCmnd().trim().equals(request.getCmnd().trim()) && repository.existsByCmnd(request.getCmnd()))
            throw new AppException(ErrorCode.CCCD_EXISTED);

        if(!khachHang.getEmail().trim().equals(request.getEmail().trim()) && repository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        khachHang.setCmnd(request.getCmnd().trim());
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMaSoThue(null);
        khachHang.setEmail(request.getEmail().trim());

        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setGioiTinh(request.isGioiTinh());


        if(request.getMatKhauCu() != null && request.getMatKhauMoi() != null
                && !request.getMatKhauCu().equals("") && !request.getMatKhauMoi().equals("")){
            boolean isMatches = passwordEncoder.matches(request.getMatKhauCu(), khachHang.getTaiKhoan().getMatKhau());
            if(!isMatches){
                throw new AppException(ErrorCode.MATKHAUCU_NOT_MATCH);
            }
            taiKhoanService.capNhatMatKhau(khachHang.getTaiKhoan().getIdTaiKhoan(), request.getMatKhauMoi());
        }

        return convertKhachHangProfileResponse(repository.save(khachHang));
    }

    public KhachHangProfileResponse convertKhachHangProfileResponse(KhachHang khachHang){
        return KhachHangProfileResponse.builder()
                .idKhachHang(khachHang.getIdKhachHang())
                .cmnd(khachHang.getCmnd())
                .email(khachHang.getEmail())
                .hoTen(khachHang.getHoTen())
                .diaChi(khachHang.getDiaChi())
                .gioiTinh(khachHang.isGioiTinh())
                .ngaySinh(khachHang.getNgaySinh())
                .sdt(khachHang.getSdt())
                .build();
    }
}
