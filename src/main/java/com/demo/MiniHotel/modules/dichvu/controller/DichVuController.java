package com.demo.MiniHotel.modules.dichvu.controller;

import com.demo.MiniHotel.model.DichVu;
import com.demo.MiniHotel.modules.dichvu.dto.DichVuRequest;
import com.demo.MiniHotel.modules.dichvu.service.IDichVuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dich-vu")
@RequiredArgsConstructor
public class DichVuController {
    private final IDichVuService DichVuService;
    @PostMapping("/")
    public ResponseEntity<DichVu> addNewDichVu(@RequestBody DichVuRequest request) throws Exception {
        DichVu DichVu = DichVuService.addNewDichVu(request);
        return new ResponseEntity<>(DichVu, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DichVu>> getAllDichVu(){
        List<DichVu> DichVus = DichVuService.getAllDichVu();
        return new ResponseEntity<>(DichVus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DichVu> getDichVuById(@PathVariable("id") Integer id) throws Exception {
        DichVu DichVu = DichVuService.getDichVuById(id);
        return new ResponseEntity<>(DichVu, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DichVu> updateDichVu(@PathVariable("id") Integer id,
                                                     @RequestBody DichVuRequest request) throws Exception {
        DichVu DichVu = DichVuService.updateDichVu(request,id);
        return new ResponseEntity<>(DichVu, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDichVu(@PathVariable("id") Integer id) throws Exception {
        DichVuService.deleteDichVu(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }
}
