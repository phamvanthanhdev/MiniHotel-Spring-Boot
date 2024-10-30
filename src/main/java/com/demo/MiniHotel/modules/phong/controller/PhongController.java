package com.demo.MiniHotel.modules.phong.controller;

import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.Phong;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phong.dto.PhongRequest;
import com.demo.MiniHotel.modules.phong.dto.PhongResponse;
import com.demo.MiniHotel.modules.phong.dto.ThemPhongResponse;
import com.demo.MiniHotel.modules.phong.service.IPhongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/phong")
public class PhongController {
    private final IPhongService PhongService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> addNewPhong(@RequestBody PhongRequest request) throws Exception {
        Phong phong = PhongService.addNewPhong(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(phong)
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllPhong() throws Exception {
        List<PhongResponse> responses = PhongService.getPhongResponses();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(responses)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{maPhong}")
    public ResponseEntity<ApiResponse> getPhongResponseById(@PathVariable("maPhong") String maPhong) throws Exception {
        ThemPhongResponse response = PhongService.getPhongResponseById(maPhong);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePhong(@PathVariable("id") String id,
                                                   @RequestBody PhongRequest request) throws Exception {
        ThemPhongResponse response = PhongService.updatePhong(request,id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(response)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhong(@PathVariable("id") String id) throws Exception {
        PhongService.deletePhong(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }

    @PutMapping("/trang-thai")
    public ResponseEntity<ApiResponse> capNhatTrangThaiPhong(@RequestParam("idTrangThai") int idTrangThai,
                                                             @RequestParam("maPhong") String maPhong) throws Exception {
        Phong phong = PhongService.capNhatTrangThai(maPhong, idTrangThai);

        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(phong)
                .build(), HttpStatus.OK);
    }
}
