package com.demo.MiniHotel.modules.nhanvien.implement;

import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienRequest;
import com.demo.MiniHotel.model.BoPhan;
import com.demo.MiniHotel.model.NhanVien;
import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.model.TaiKhoan;
import com.demo.MiniHotel.modules.taikhoan.service.ITaiKhoanService;
import com.demo.MiniHotel.repository.NhanVienRepository;
import com.demo.MiniHotel.modules.bophan.service.IBoPhanService;
import com.demo.MiniHotel.modules.nhanvien.service.INhanVienService;
import com.demo.MiniHotel.modules.nhomquyen.service.INhomQuyenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NhanVienImplement implements INhanVienService {
    private final NhanVienRepository repository;
    private final IBoPhanService boPhanService;
    private final INhomQuyenService nhomQuyenService;
    private final ITaiKhoanService taiKhoanService;
    @Override
    public NhanVien addNewNhanVien(NhanVienRequest request) throws Exception {
        BoPhan boPhan = boPhanService.getBoPhanById(request.getIdBoPhan());
        NhanVien nhanVien = new NhanVien();
        nhanVien.setHoTen(request.getHoTen());
        nhanVien.setEmail(request.getEmail());
        nhanVien.setSdt(request.getSdt());
        nhanVien.setGioiTinh(request.isGioiTinh());
        nhanVien.setNgaySinh(request.getNgaySinh());

        nhanVien.setBoPhan(boPhan);

        if(request.getIdTaiKhoan() != null){
            TaiKhoan taiKhoan = taiKhoanService.getTaiKhoanById(request.getIdTaiKhoan());
            nhanVien.setTaiKhoan(taiKhoan);
        }

        return repository.save(nhanVien);
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
    public NhanVien updateNhanVien(NhanVienRequest request, Integer id) throws Exception {
        BoPhan boPhan = boPhanService.getBoPhanById(request.getIdBoPhan());

        NhanVien nhanVien = getNhanVienById(id);
        nhanVien.setHoTen(request.getHoTen());
        nhanVien.setEmail(request.getEmail());
        nhanVien.setSdt(request.getSdt());
        nhanVien.setGioiTinh(request.isGioiTinh());
        nhanVien.setNgaySinh(request.getNgaySinh());

        nhanVien.setBoPhan(boPhan);

        return repository.save(nhanVien);
    }

    @Override
    public void deleteNhanVien(Integer id) throws Exception {
        Optional<NhanVien> nhanVienOptional = repository.findById(id);
        if(nhanVienOptional.isEmpty()){
            throw new Exception("NhanVien not found.");
        }

        repository.deleteById(id);
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
}
