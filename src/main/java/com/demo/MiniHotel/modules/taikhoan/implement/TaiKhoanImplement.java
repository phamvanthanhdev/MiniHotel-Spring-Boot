package com.demo.MiniHotel.modules.taikhoan.implement;

import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.model.TaiKhoan;
import com.demo.MiniHotel.modules.nhomquyen.service.INhomQuyenService;
import com.demo.MiniHotel.modules.taikhoan.dto.TaiKhoanRequest;
import com.demo.MiniHotel.modules.taikhoan.exception.LoginWrongException;
import com.demo.MiniHotel.modules.taikhoan.service.ITaiKhoanService;
import com.demo.MiniHotel.repository.TaiKhoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaiKhoanImplement implements ITaiKhoanService {
    private final TaiKhoanRepository repository;
    private final INhomQuyenService nhomQuyenService;
    @Override
    public TaiKhoan addNewTaiKhoan(TaiKhoanRequest request) throws Exception {
        Optional<TaiKhoan> taiKhoanOptional = repository.findByTenDangNhap(request.getTenDangNhap());
        if(taiKhoanOptional.isEmpty()) {
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTenDangNhap(request.getTenDangNhap());
            taiKhoan.setMatKhau(request.getMatKhau());
            NhomQuyen nhomQuyen = nhomQuyenService.getNhomQuyenById(request.getIdNhomQuyen());
            taiKhoan.setNhomQuyen(nhomQuyen);

            return repository.save(taiKhoan);
        }

        throw new Exception("Tên đăng nhập đã tồn tại");
    }

    @Override
    public List<TaiKhoan> getAllTaiKhoan() {
        return repository.findAll();
    }

    @Override
    public TaiKhoan getTaiKhoanById(Integer id) throws Exception {
        Optional<TaiKhoan> taiKhoanOptional = repository.findById(id);
        if(taiKhoanOptional.isEmpty()){
            throw new Exception("TaiKhoan not found!");
        }
        return taiKhoanOptional.get();
    }

    @Override
    public TaiKhoan updateMatKhau(TaiKhoanRequest request) throws Exception {
        Optional<TaiKhoan> taiKhoanOptional = repository.findByTenDangNhap(request.getTenDangNhap());
        if(taiKhoanOptional.isPresent()) {
            TaiKhoan taiKhoan = taiKhoanOptional.get();
            taiKhoan.setMatKhau(request.getMatKhau());
            return repository.save(taiKhoan);
        }

        throw new Exception("Tài khoản không tồn tại.");
    }

    @Override
    public void deleteTaiKhoan(Integer id) throws Exception {
        Optional<TaiKhoan> taiKhoanOptional = repository.findById(id);
        if(taiKhoanOptional.isEmpty()){
            throw new Exception("TaiKhoan not found!");
        }
        repository.deleteById(id);
    }

    @Override
    public TaiKhoan checkLogin(TaiKhoanRequest request) {
        Optional<TaiKhoan> taiKhoanOptional = repository.findByTenDangNhap(request.getTenDangNhap());
        if(taiKhoanOptional.isPresent()) {
            TaiKhoan taiKhoan = taiKhoanOptional.get();
            if(taiKhoan.getMatKhau().trim().equals(request.getMatKhau().trim()))
                return taiKhoan;
            else
                throw new LoginWrongException("Mật khẩu không chính xác");
        }

        throw new RuntimeException("Tài khoản không tồn tại.");
    }
}
