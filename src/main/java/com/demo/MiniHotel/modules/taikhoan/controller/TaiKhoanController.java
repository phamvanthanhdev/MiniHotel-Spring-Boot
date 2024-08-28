package com.demo.MiniHotel.modules.taikhoan.controller;

import com.demo.MiniHotel.model.TaiKhoan;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangRequest;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangResponse;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import com.demo.MiniHotel.modules.nhanvien.dto.NhanVienResponse;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.taikhoan.dto.DangKyRequest;
import com.demo.MiniHotel.modules.taikhoan.dto.TaiKhoanRequest;
import com.demo.MiniHotel.modules.taikhoan.exception.LoginWrongException;
import com.demo.MiniHotel.modules.taikhoan.service.ITaiKhoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tai-khoan")
@RequiredArgsConstructor
public class TaiKhoanController {
    private final ITaiKhoanService taiKhoanService;
    private final IKhachHangService khachHangService;

    @PostMapping("/register")
    public ResponseEntity<TaiKhoan> registerAccount(@RequestBody TaiKhoanRequest  request){
        try {
            TaiKhoan taiKhoan = taiKhoanService.addNewTaiKhoan(request);
            return new ResponseEntity<>(taiKhoan, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*@PostMapping("/login")
    public ResponseEntity<TaiKhoan> login(@RequestBody TaiKhoanRequest request){
        try{
            TaiKhoan taiKhoan = taiKhoanService.checkLogin(request);
            return new ResponseEntity<>(taiKhoan, HttpStatus.OK);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }*/

    @PostMapping("/login")
    public ResponseEntity<KhachHangResponse> login(@RequestParam("tenDangNhap") String tenDangNhap,
                                                   @RequestParam("matKhau") String matKhau){
        try{
            KhachHangResponse khachHangResponse = taiKhoanService.getKhachHangByTaiKhoan(tenDangNhap, matKhau);
            return new ResponseEntity<>(khachHangResponse, HttpStatus.OK);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/nhan-vien/login")
    public ResponseEntity<NhanVienResponse> nhanVienLogin(@RequestParam("tenDangNhap") String tenDangNhap,
                                                          @RequestParam("matKhau") String matKhau){
        try{
            NhanVienResponse nhanVienResponse = taiKhoanService.getNhanVienByTaiKhoan(tenDangNhap, matKhau);
            return new ResponseEntity<>(nhanVienResponse, HttpStatus.OK);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/dang-ky")
    public ResponseEntity<ResultResponse> dangKyTaiKhoan(@RequestBody DangKyRequest request){
        ResultResponse response = null;
        try{
            TaiKhoan taiKhoan = taiKhoanService.dangKyTaiKhoan(request);
            KhachHangRequest khachHangRequest = new KhachHangRequest(
                    request.getCmnd(), request.getHoTen(), request.getSdt(), request.getDiaChi(),
                    request.getEmail(), taiKhoan.getIdTaiKhoan()
            );
            khachHangService.addNewKhachHang(khachHangRequest);
            response = new ResultResponse(200, "Đăng ký tài khoản thành công");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResultResponse(400, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaiKhoan>> getAllTaiKhoans(){
        List<TaiKhoan> taiKhoans = taiKhoanService.getAllTaiKhoan();
        return new ResponseEntity<>(taiKhoans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaiKhoan> getTaiKhoanById(@PathVariable("id") Integer id) throws Exception {
        TaiKhoan taiKhoan = taiKhoanService.getTaiKhoanById(id);
        return new ResponseEntity<>(taiKhoan, HttpStatus.OK);
    }

    @PutMapping("/change-pw")
    public ResponseEntity<TaiKhoan> changePassword(@RequestBody TaiKhoanRequest request) throws Exception {
        TaiKhoan taiKhoan = taiKhoanService.updateMatKhau(request);
        return new ResponseEntity<>(taiKhoan, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaiKhoan(@PathVariable("id") Integer id) throws Exception {
        taiKhoanService.deleteTaiKhoan(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }
}
