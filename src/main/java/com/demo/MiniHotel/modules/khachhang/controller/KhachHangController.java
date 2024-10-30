package com.demo.MiniHotel.modules.khachhang.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.modules.khachhang.dto.FileUploadResponse;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangRequest;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangResponse;
import com.demo.MiniHotel.modules.khachhang.dto.KhachHangUpload;
import com.demo.MiniHotel.modules.khachhang.service.IKhachHangService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/khach-hang")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
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
    public ResponseEntity<ApiResponse> getKhachHangByCccd(@RequestParam("cccd") String cccd) throws Exception {
        KhachHangResponse khachHangResponse = khachHangResponse = KhachHangService.getKhachHangResponseByCCCD(cccd);

        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(khachHangResponse)
                .build(), HttpStatus.OK);
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
    public ResponseEntity<ApiResponse> uploadExcel(@RequestParam("file") MultipartFile file,
                                                   @RequestParam int idPhieuDat) {
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
            String newFilename = timestamp + "_" + idPhieuDat + "_" + originalFilename;

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

    // Đọc tên file trong folder
    @GetMapping("/name-excel")
    public ResponseEntity<ApiResponse> getNameExcel() {
        File folder = new File("src/main/resources/uploads");
        File[] listOfFiles = folder.listFiles();
        List<FileUploadResponse> responses = new ArrayList<>();
        if(listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    String fileName =  listOfFiles[i].getName();
                    // Tách tên file theo ký tự "_"
                    String[] parts = fileName.split("_");

                    // Phần đầu là thời gian
                    String timestamp = parts[0];

                    // Phần thứ hai là idPhieuDat
                    int idPhieuDat = Integer.parseInt(parts[1]);

                    // Phần thứ ba là tên file khi upload
                    String tenFile = parts[2];

                    // Định dạng thời gian để chuyển đổi lại về LocalDateTime
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    LocalDateTime dateTime = LocalDateTime.parse(timestamp, formatter);

                    // Định dạng thời gian theo dạng ss:mm:HH dd/MM/yyyy
                    DateTimeFormatter formatterResponse = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

                    // Chuyển đổi LocalDateTime sang chuỗi với định dạng mong muốn
                    String formattedDateTime = dateTime.format(formatterResponse);

                    responses.add(FileUploadResponse.builder()
                                    .tenFile(tenFile)
                                    .tenFileOriginal(fileName)
                                    .thoiGianOriginal(dateTime)
                                    .thoiGian(formattedDateTime)
                                    .idPhieuDat(idPhieuDat)
                            .build());
                }
            }
        }

        Collections.sort(responses, new Comparator<FileUploadResponse>() {
            @Override
            public int compare(FileUploadResponse o1, FileUploadResponse o2) {
                return o1.getThoiGianOriginal().isBefore(o2.getThoiGianOriginal()) ? 1 : -1;
            }
        });

        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/ten-file/{tenFile}")
    public ResponseEntity<ApiResponse> getDataFileTheoTen(@PathVariable("tenFile") String tenFile) throws IOException, InvalidFormatException {
        File f = new File("src/main/resources/uploads/" + tenFile);
        if(f.exists() && !f.isDirectory()) {
            // Đọc data trong file
            List<KhachHangUpload> khachHangUploads = new ArrayList<KhachHangUpload>();
            XSSFWorkbook workbook = new XSSFWorkbook(f);
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                KhachHangUpload khachHangUpload = new KhachHangUpload();

                XSSFRow row = worksheet.getRow(i);

                if(row.getCell(0).getStringCellValue().equals("")
                    && row.getCell(1).getStringCellValue().trim().equals("")
                    && row.getCell(2).getStringCellValue().trim().equals("")
                    && row.getCell(3).getStringCellValue().trim().equals("")
                    && row.getCell(4).getStringCellValue().trim().equals("")
                    && row.getCell(5).getStringCellValue().trim().equals("")
                    && row.getCell(6).getStringCellValue().trim().equals("")){
                 //
                }else{
                    khachHangUpload.setCccd(row.getCell(0).getStringCellValue());
                    khachHangUpload.setHoTen(row.getCell(1).getStringCellValue());
                    khachHangUpload.setGioiTinh(row.getCell(2).getStringCellValue());
                    khachHangUpload.setNgaySinh(row.getCell(3).getStringCellValue());
                    khachHangUpload.setSdt(row.getCell(4).getStringCellValue());
                    khachHangUpload.setEmail(row.getCell(5).getStringCellValue());
                    khachHangUpload.setDiaChi(row.getCell(6).getStringCellValue());

                    khachHangUploads.add(khachHangUpload);
                }
            }



            return new ResponseEntity<>(ApiResponse.builder()
                    .code(200)
                    .result(khachHangUploads)
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(ApiResponse.builder()
                .code(400)
                .message("File không tồn tại")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/import-khach-hang")
    public ResponseEntity<ApiResponse> importKhachHang(@RequestBody KhachHangUpload khachHangUpload) throws Exception {
        ApiResponse apiResponse = KhachHangService.importKhachHangUpload(khachHangUpload);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/import-toan-bo")
    public ResponseEntity<ApiResponse> importToanBoKhachHang(@RequestBody List<KhachHangUpload> khachHangUploads) throws Exception {
        KhachHangService.importToanBoKhachHang(khachHangUploads);

        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .message("Import thông tin khách hàng thành công")
                .build(), HttpStatus.OK);
    }
}
