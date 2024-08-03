package com.demo.MiniHotel.modules.taikhoan.controller;

import com.demo.MiniHotel.model.TaiKhoan;
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

    @PostMapping("/register")
    public ResponseEntity<TaiKhoan> registerAccount(@RequestBody TaiKhoanRequest  request){
        try {
            TaiKhoan taiKhoan = taiKhoanService.addNewTaiKhoan(request);
            return new ResponseEntity<>(taiKhoan, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TaiKhoan> login(@RequestBody TaiKhoanRequest request){
        try{
            TaiKhoan taiKhoan = taiKhoanService.checkLogin(request);
            return new ResponseEntity<>(taiKhoan, HttpStatus.OK);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
