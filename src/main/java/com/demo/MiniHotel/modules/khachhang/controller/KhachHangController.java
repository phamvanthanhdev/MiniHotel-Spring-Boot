package com.demo.MiniHotel.modules.khachhang.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangRequest;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangResponse;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/khach-hang")
@RequiredArgsConstructor
@CrossOrigin("*")
public class KhachHangController {
    private final IKhachHangService KhachHangService;
    @PostMapping("/")
    public ResponseEntity<KhachHangResponse> addNewKhachHang(@RequestBody @Valid KhachHangRequest request) throws Exception {
        KhachHangResponse response = KhachHangService.addNewKhachHang(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<KhachHang>> getAllKhachHang(){
        List<KhachHang> KhachHangs = KhachHangService.getAllKhachHang();
        return new ResponseEntity<>(KhachHangs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhachHangResponse> getKhachHangResponseById(@PathVariable("id") Integer id) throws Exception {
        KhachHangResponse khachHangResponse = KhachHangService.getKhachHangResponseById(id);
        return new ResponseEntity<>(khachHangResponse, HttpStatus.OK);
    }

    @GetMapping("/tim-kiem")
    public ResponseEntity<KhachHangResponse> getKhachHangBySdt(@RequestParam("sdt") String sdt) {
        KhachHangResponse khachHangResponse = null;
        try {
            khachHangResponse = KhachHangService.getKhachHangBySdt(sdt);
        } catch (Exception e) {
            return new ResponseEntity<>(new KhachHangResponse(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(khachHangResponse, HttpStatus.OK);
    }

    @GetMapping("/tim-kiem-cccd")
    public ResponseEntity<KhachHangResponse> getKhachHangByCccd(@RequestParam("cccd") String cccd) {
        KhachHangResponse khachHangResponse = null;
        try {
            khachHangResponse = KhachHangService.getKhachHangResponseByCCCD(cccd);
        } catch (Exception e) {
            return new ResponseEntity<>(new KhachHangResponse(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(khachHangResponse, HttpStatus.OK);
    }

    @GetMapping("/phieu-dat")
    public ResponseEntity<KhachHangResponse> getKhachHangByIdPhieuDat(@RequestParam("idPhieuDat") Integer idPhieuDat) {
        KhachHangResponse khachHangResponse = null;
        try {
            khachHangResponse = KhachHangService.getKhachHangByIdPhieuDat(idPhieuDat);
        } catch (Exception e) {
            return new ResponseEntity<>(new KhachHangResponse(), HttpStatus.NOT_FOUND);
        }
        if(khachHangResponse != null)
            return new ResponseEntity<>(khachHangResponse, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<KhachHang> updateKhachHang(@PathVariable("id") Integer id,
//                                                   @RequestBody KhachHangRequest request) throws Exception {
//        KhachHang KhachHang = KhachHangService.updateKhachHang(request,id);
//        return new ResponseEntity<>(KhachHang, HttpStatus.OK);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<KhachHangResponse> updateKhachHang(@PathVariable("id") Integer id,
                                                     @RequestBody KhachHangRequest request) throws Exception {
        KhachHangResponse response = KhachHangService.updateKhachHangResponse(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKhachHang(@PathVariable("id") Integer id) throws Exception {
        KhachHangService.deleteKhachHang(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }


    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
        // Tải file từ thư mục resources
        ClassPathResource excelFile = new ClassPathResource("files/saigon-hotel.xlsx");

        // Thiết lập tiêu đề và kiểu dữ liệu trả về
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(excelFile.getInputStream()));
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<ApiResponse> uploadExcel(@RequestParam("file") MultipartFile file) {
        // Kiểm tra xem file có trống không
        if (file.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.builder()
                    .code(400)
                    .message("Vui lòng chọn file")
                    .build(), HttpStatus.BAD_REQUEST);
        }

        try {
            // Lấy đường dẫn đến thư mục resource
            Path uploadPath = Paths.get("src/main/resources/uploads");

            // Tạo thư mục nếu nó chưa tồn tại
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Đặt tên file gốc
            String originalFilename = file.getOriginalFilename();

            // Lấy thời gian hiện tại
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            // Thêm thời gian vào tên file
            String timestamp = now.format(formatter);
            String newFilename = timestamp + "_" + originalFilename;

            // Lưu
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return new ResponseEntity<>(ApiResponse.builder()
                    .code(200)
                    .message("Tải file thành công")
                    .build(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ApiResponse.builder()
                    .code(400)
                    .message("Có lỗi xảy ra khi tải file")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
