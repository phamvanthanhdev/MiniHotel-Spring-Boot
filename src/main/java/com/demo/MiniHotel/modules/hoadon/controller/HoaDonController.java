package com.demo.MiniHotel.modules.hoadon.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.hoadon.dto.DoanhThuQuyResponse;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonDetailsResponse;
import com.demo.MiniHotel.modules.hoadon.dto.HoaDonRequest;
import com.demo.MiniHotel.modules.hoadon.service.IHoaDonService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/hoa-don")
@RequiredArgsConstructor
public class HoaDonController {
    private final IHoaDonService HoaDonService;
    @PostMapping("/")
    public ResponseEntity<HoaDon> addNewHoaDon(@RequestBody HoaDonRequest request) throws Exception {
        HoaDon HoaDon = HoaDonService.addNewHoaDon(request);
        return new ResponseEntity<>(HoaDon, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HoaDon>> getAllHoaDon(){
        List<HoaDon> HoaDons = HoaDonService.getAllHoaDon();
        return new ResponseEntity<>(HoaDons, HttpStatus.OK);
    }

//    @GetMapping("/{soHoaDon}")
//    public ResponseEntity<HoaDon> getHoaDonById(@PathVariable("soHoaDon") String soHoaDon) throws Exception {
//        HoaDon HoaDon = HoaDonService.getHoaDonById(soHoaDon);
//        return new ResponseEntity<>(HoaDon, HttpStatus.OK);
//    }

    @GetMapping("/{soHoaDon}")
    public ResponseEntity<ApiResponse> getHoaDonDetailsById(@PathVariable("soHoaDon") String soHoaDon) throws Exception {
        HoaDonDetailsResponse hoaDonDetailsResponse = HoaDonService.getHoaDonDetailsById(soHoaDon);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(hoaDonDetailsResponse)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/{soHoaDon}")
    public ResponseEntity<HoaDon> updateHoaDon(@PathVariable("soHoaDon") String soHoaDon,
                                                     @RequestBody HoaDonRequest request) throws Exception {
        HoaDon HoaDon = HoaDonService.updateHoaDon(request,soHoaDon);
        return new ResponseEntity<>(HoaDon, HttpStatus.OK);
    }

    @DeleteMapping("/{soHoaDon}")
    public ResponseEntity<String> deleteHoaDon(@PathVariable("soHoaDon") String soHoaDon) throws Exception {
        HoaDonService.deleteHoaDon(soHoaDon);
        return new ResponseEntity<>("Deleted No." + soHoaDon + " successfully.", HttpStatus.OK);
    }

    @GetMapping("/chi-tiet-phieu-thue/{soHoaDon}")
    public ResponseEntity<List<ChiTietPhieuThue>> getChiTietPhieuThueBySoHoaDon(@PathVariable("soHoaDon") String soHoaDon) throws Exception {
        List<ChiTietPhieuThue>  chiTietPhieuThues = HoaDonService.getChiTietPhieuThueBySoHoaDon(soHoaDon);
        return new ResponseEntity<>(chiTietPhieuThues, HttpStatus.OK);
    }

    @GetMapping("/chi-tiet-dich-vu/{soHoaDon}")
    public ResponseEntity<List<ChiTietSuDungDichVu>> getChiTietDichVuBySoHoaDon(@PathVariable("soHoaDon") String soHoaDon) throws Exception {
        List<ChiTietSuDungDichVu>  chiTietSuDungDichVus = HoaDonService.getChiTietSuDungDichVuBySoHoaDon(soHoaDon);
        return new ResponseEntity<>(chiTietSuDungDichVus, HttpStatus.OK);
    }

    @GetMapping("/hoa-don-ngay")
    public ResponseEntity<List<HoaDonNgay>> getHoaDonNgaysHienTai(){
        List<HoaDonNgay> hoaDonNgays = HoaDonService.getHoaDonNgaysHienTai();
        return new ResponseEntity<>(hoaDonNgays, HttpStatus.OK);
    }

    @GetMapping("/theo-ngay")
    public ResponseEntity<List<HoaDonNgay>> getHoaDonNgaysTheoNgay(@RequestParam("ngay")LocalDate ngay){
        List<HoaDonNgay> hoaDonNgays = HoaDonService.getHoaDonNgaysTheoNgay(ngay);
        return new ResponseEntity<>(hoaDonNgays, HttpStatus.OK);
    }

    @GetMapping("/doanh-thu/theo-ngay")
    public ResponseEntity<List<DoanhThuTheoNgay>> getDoanhThuTheoNgay(@RequestParam("ngayBatDau")LocalDate ngayBatDau,
                                                                      @RequestParam("ngayKetThuc")LocalDate ngayKetThuc){
        List<DoanhThuTheoNgay> doanhThuTheoNgays = HoaDonService.getDoanhThuTheoNgay(ngayBatDau, ngayKetThuc);
        return new ResponseEntity<>(doanhThuTheoNgays, HttpStatus.OK);
    }

    @GetMapping("/doanh-thu/theo-thang")
    public ResponseEntity<List<DoanhThuTheoThang>> getDoanhThuTheoThang(@RequestParam("thangBatDau")int thangBatDau,
                                                                      @RequestParam("thangKetThuc")int thangKetThuc,
                                                                        @RequestParam("nam")int nam){
        List<DoanhThuTheoThang> doanhThuTheoThang = HoaDonService.getDoanhThuTheoThang(thangBatDau, thangKetThuc, nam);
        return new ResponseEntity<>(doanhThuTheoThang, HttpStatus.OK);
    }

    @GetMapping("/doanh-thu/theo-quy")
    public ResponseEntity<List<DoanhThuQuyResponse>> getDoanhThuTheoQuy(@RequestParam("quyBatDau")int quyBatDau,
                                                              @RequestParam("quyKetThuc")int quyKetThuc,
                                                                        @RequestParam("nam")int nam){
        List<DoanhThuQuyResponse> doanhThuQuyResponses = HoaDonService.getDoanhThuTheoQuy(quyBatDau, quyKetThuc, nam);
        return new ResponseEntity<>(doanhThuQuyResponses, HttpStatus.OK);
    }
    @GetMapping("/export-csv")
    public ResponseEntity<byte[]> exportCSV(@RequestParam("ngay") LocalDate ngay) {
        try {
            List<HoaDonNgay> hoaDonNgays = HoaDonService.getHoaDonNgaysTheoNgay(ngay);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write('\uFEFF');
            try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';').withHeader("Số hóa đơn", "Mã phiếu thuê", "Tổng tiền", "Ngày tạo", "Nhân viên tạo" ))) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                if(hoaDonNgays != null && !hoaDonNgays.isEmpty()) {
                    for (HoaDonNgay hoaDonNgay : hoaDonNgays) {
                        csvPrinter.printRecord(hoaDonNgay.getSoHoaDon(), hoaDonNgay.getIdPhieuThue(), hoaDonNgay.getTongTien().toString(), formatter.format(hoaDonNgay.getNgayTao()), hoaDonNgay.getTenNhanVien());
                    }
                }
                csvPrinter.flush();
            }
//            writer.flush();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hoaDonNgay.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
//            return ResponseEntity.status(500).build();
        }
    }

}
