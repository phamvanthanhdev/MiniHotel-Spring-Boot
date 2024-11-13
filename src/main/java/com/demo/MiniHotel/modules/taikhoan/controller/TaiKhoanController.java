package com.demo.MiniHotel.modules.taikhoan.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.TaiKhoan;
import com.demo.MiniHotel.modules.phieuthuephong.dto.PhieuThueResponse;
import com.demo.MiniHotel.modules.taikhoan.dto.*;
import com.demo.MiniHotel.modules.taikhoan.service.ITaiKhoanService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/tai-khoan")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class TaiKhoanController {
    private final ITaiKhoanService taiKhoanService;
    @PostMapping("/dang-ky")
    public ResponseEntity<?> dangKyTaiKhoan(@RequestBody @Valid DangKyRequest request) throws Exception {
        TaiKhoanKhachHangResponse taiKhoan = taiKhoanService.dangKyTaiKhoan(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .code(200)
                .result(taiKhoan)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/dang-nhap")
    public ResponseEntity<ApiResponse<LoginResponse>> dangNhap(@RequestBody LoginRequest request){
        LoginResponse result = taiKhoanService.authentication(request);
        return new ResponseEntity<>(ApiResponse.<LoginResponse>builder()
                .code(200)
                .result(result)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse response = taiKhoanService.introspect(request);
        return new ResponseEntity<>(ApiResponse.<IntrospectResponse>builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaiKhoanResponse>> getAllTaiKhoans(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info(authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        List<TaiKhoanResponse> taiKhoans = taiKhoanService.getAllTaiKhoan();
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
    public ResponseEntity<ApiResponse> deleteTaiKhoan(@PathVariable("id") Integer id) throws Exception {
        taiKhoanService.deleteTaiKhoan(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/details/all")
    public ResponseEntity<ApiResponse> getAllTaiKhoansDetails(){
        List<TaiKhoanDetailsResponse> taiKhoans = taiKhoanService.getAllTaiKhoanDetails();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(taiKhoans)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/theo-trang")
    public ResponseEntity<ApiResponse> getTaiKhoanTheoTrang(@RequestParam("pageNumber") int pageNumber,
                                                                  @RequestParam("pageSize") int pageSize) throws Exception {
        List<TaiKhoanDetailsResponse> responses = taiKhoanService.getTaiKhoanDetailsTheoTrang(pageNumber, pageSize);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/tong-trang")
    public ResponseEntity<ApiResponse> getTongTrangTaiKhoan(@RequestParam("pageSize") int pageSize) throws Exception {
        int tongTrang = taiKhoanService.getTongTrangTaiKhoan(pageSize);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(tongTrang)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/{idTaiKhoan}")
    public ResponseEntity<ApiResponse> capNhatTaiKhoan(@PathVariable int idTaiKhoan,
                                                       @RequestBody TaiKhoanRequest request) throws Exception {
        TaiKhoanResponse response = taiKhoanService.capNhatThaiKhoan(request, idTaiKhoan);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse> getTaiKhoanDetailsById(@PathVariable("id") Integer id) throws Exception {
        TaiKhoanDetailsResponse taiKhoan = taiKhoanService.getTaiKhoanDetailsById(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(taiKhoan)
                .build(), HttpStatus.OK);
    }
}
