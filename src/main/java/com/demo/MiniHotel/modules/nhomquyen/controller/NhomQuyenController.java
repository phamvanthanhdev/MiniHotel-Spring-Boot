package com.demo.MiniHotel.modules.nhomquyen.controller;


import com.demo.MiniHotel.dto.ApiResponse;
import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.modules.nhomquyen.dto.NhomQuyenRequest;
import com.demo.MiniHotel.modules.nhomquyen.dto.NhomQuyenResponse;
import com.demo.MiniHotel.modules.nhomquyen.service.INhomQuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhom-quyen")
@RequiredArgsConstructor
public class NhomQuyenController {
    private final INhomQuyenService nhomQuyenService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> addNewNhomQuyen(@RequestBody NhomQuyenRequest request){
        NhomQuyen nhomQuyen = nhomQuyenService.addNewNhomQuyen(request);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(nhomQuyen)
                .build(), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllNhomQuyen(){
        List<NhomQuyen> nhomQuyens = nhomQuyenService.getAllNhomQuyen();
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(nhomQuyens)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getNhomQuyenById(@PathVariable("id") Integer id) throws Exception {
        NhomQuyenResponse nhomQuyen = nhomQuyenService.getNhomQuyenResponseById(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(nhomQuyen)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateNhomQuyen(@PathVariable("id") Integer id,
                                               @RequestBody NhomQuyenRequest request) throws Exception {
        NhomQuyenResponse nhomQuyen = nhomQuyenService.updateNhomQuyen(request,id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .result(nhomQuyen)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteNhomQuyen(@PathVariable("id") Integer id) throws Exception {
        nhomQuyenService.deleteNhomQuyen(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .code(200)
                .build(), HttpStatus.OK);
    }
}
