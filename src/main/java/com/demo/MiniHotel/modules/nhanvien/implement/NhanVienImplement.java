package com.demo.MiniHotel.modules.nhanvien.implement;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienDangNhapResponse;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienDetailsResponse;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienRequest;
import com.demo.MiniHotel.model.BoPhan;
import com.demo.MiniHotel.model.NhanVien;
import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.model.TaiKhoan;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienResponse;
import com.demo.MiniHotel.modules.taikhoan.service.ITaiKhoanService;
import com.demo.MiniHotel.repository.NhanVienRepository;
import com.demo.MiniHotel.modules.bophan.service.IBoPhanService;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import com.demo.MiniHotel.modules.nhomquyen.service.INhomQuyenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NhanVienImplement implements INhanVienService {
    private final NhanVienRepository repository;
    private final IBoPhanService boPhanService;
    private final INhomQuyenService nhomQuyenService;
    private final ITaiKhoanService taiKhoanService;
    @Override
    public NhanVienDetailsResponse addNewNhanVien(NhanVienRequest request) throws Exception {
        NhanVien nhanVien = new NhanVien();

        // Tại vì thiết kế csdl quên thêm AutoIncrement nên phải tìm Nhân viên có id cao nhất + 1
        List<NhanVien> nhanViens = repository.findAll();
        Collections.sort(nhanViens, new Comparator<NhanVien>() {
            @Override
            public int compare(NhanVien o1, NhanVien o2) {
                return o1.getIdNhanVien() < o2.getIdNhanVien() ? 1 : -1;
            }
        });
        nhanVien.setIdNhanVien(nhanViens.get(0).getIdNhanVien() + 1);

        if(repository.existsByCccd(request.getCccd()))
            throw new AppException(ErrorCode.CCCD_EXISTED);

        nhanVien.setCccd(request.getCccd());
        nhanVien.setHoTen(request.getHoTen());

        if(repository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        nhanVien.setEmail(request.getEmail());
        nhanVien.setSdt(request.getSdt());
        nhanVien.setGioiTinh(request.isGioiTinh());
        nhanVien.setNgaySinh(request.getNgaySinh());

        BoPhan boPhan = boPhanService.getBoPhanById(request.getIdBoPhan());
        nhanVien.setBoPhan(boPhan);

        TaiKhoan taiKhoan = taiKhoanService.taoTaiKhoanNhanVien(request.getTenDangNhap(), request.getMatKhau() , request.getIdNhomQuyen());
        nhanVien.setTaiKhoan(taiKhoan);

        return convertNhanVienDetailsResponse(repository.save(nhanVien));
    }

    @Override
    public List<NhanVienResponse> getAllNhanVienResponse() {
        return repository.findAll().stream().map(this::convertNhanVienResponse).collect(Collectors.toList());
    }

    @Override
    public List<NhanVien> getAllNhanVien() {
        return repository.findAll();
    }

    @Override
    public NhanVien getNhanVienById(Integer id) throws Exception {
        Optional<NhanVien> nhanVienOptional = repository.findById(id);
        if(nhanVienOptional.isEmpty()){
            throw new Exception("NhanVien not found.");
        }

        return nhanVienOptional.get();
    }

    @Override
    public NhanVienDetailsResponse updateNhanVien(NhanVienRequest request, Integer id) throws Exception {
        NhanVien nhanVien = getNhanVienById(id);

        if(!request.getCccd().equals(nhanVien.getCccd()) && repository.existsByCccd(request.getCccd()))
            throw new AppException(ErrorCode.CCCD_EXISTED);

        nhanVien.setCccd(request.getCccd());
        nhanVien.setHoTen(request.getHoTen());

        if(!request.getEmail().equals(nhanVien.getEmail()) && repository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        nhanVien.setEmail(request.getEmail());
        nhanVien.setSdt(request.getSdt());
        nhanVien.setGioiTinh(request.isGioiTinh());
        nhanVien.setNgaySinh(request.getNgaySinh());

        BoPhan boPhan = boPhanService.getBoPhanById(request.getIdBoPhan());
        nhanVien.setBoPhan(boPhan);

        return convertNhanVienDetailsResponse(repository.save(nhanVien));
    }

    @Override
    public void deleteNhanVien(Integer id) throws Exception {
        NhanVien nhanVien = getNhanVienById(id);
        if(nhanVien.getChuongTrinhKhuyenMais().size() > 0
        ||nhanVien.getPhieuDatPhongs().size() > 0
        || nhanVien.getChiTietThayDoiGiaDichVus().size() > 0
        || nhanVien.getChiTietThayDoiGiaPhuThus().size() > 0
        || nhanVien.getChiTietThayDoiGiaPhongs().size() > 0
        || nhanVien.getHoaDons().size() > 0){
            throw new AppException(ErrorCode.NHANVIEN_DAHOATDONG);
        }
        int idTaiKhoan = nhanVien.getTaiKhoan().getIdTaiKhoan();
        repository.deleteById(id);

        taiKhoanService.deleteTaiKhoan(idTaiKhoan);
    }

    @Override
    public NhanVien getNhanVienByToken() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String tenDangNhap = authentication.getName();

        Optional<NhanVien> nhanVienOptional = repository.findByTaiKhoan_TenDangNhap(tenDangNhap);
        if(nhanVienOptional.isEmpty()){
            throw new Exception("NhanVien not found.");
        }
        return nhanVienOptional.get();
    }

    @Override
    public NhanVienDetailsResponse getNhanVienDetailsById(Integer id) throws Exception {
        NhanVien nhanVien = getNhanVienById(id);

        return convertNhanVienDetailsResponse(nhanVien);
    }

    @Override
    public NhanVienDangNhapResponse getThongTinNhanVienDangNhapByToken() throws Exception {
        return convertNhanVienDangNhapResponse(getNhanVienByToken());
    }

    private NhanVienDangNhapResponse convertNhanVienDangNhapResponse(NhanVien nhanVien){
        return NhanVienDangNhapResponse.builder()
                .idNhanVien(nhanVien.getIdNhanVien())
                .hoTen(nhanVien.getHoTen())
                .tenNhomQuyen(nhanVien.getTaiKhoan().getNhomQuyen().getTenNhomQuyen())
                .build();
    }

    private NhanVienResponse convertNhanVienResponse(NhanVien nhanVien){
        return NhanVienResponse.builder()
                .idNhanVien(nhanVien.getIdNhanVien())
                .cccd(nhanVien.getCccd())
                .hoTen(nhanVien.getHoTen())
                .gioiTinh(nhanVien.isGioiTinh())
                .ngaySinh(nhanVien.getNgaySinh())
                .sdt(nhanVien.getSdt())
                .email(nhanVien.getEmail())
                .tenBoPhan(nhanVien.getBoPhan().getTenBoPhan())
                .build();
    }

    private NhanVienDetailsResponse convertNhanVienDetailsResponse(NhanVien nhanVien){
        return NhanVienDetailsResponse.builder()
                .idNhanVien(nhanVien.getIdNhanVien())
                .cccd(nhanVien.getCccd())
                .hoTen(nhanVien.getHoTen())
                .gioiTinh(nhanVien.isGioiTinh())
                .ngaySinh(nhanVien.getNgaySinh())
                .sdt(nhanVien.getSdt())
                .email(nhanVien.getEmail())
                .idBoPhan(nhanVien.getBoPhan().getIdBoPhan())
                .tenBoPhan(nhanVien.getBoPhan().getTenBoPhan())
                .idTaiKhoan(nhanVien.getTaiKhoan().getIdTaiKhoan())
                .tenDangNhap(nhanVien.getTaiKhoan().getTenDangNhap())
                .build();
    }
}
