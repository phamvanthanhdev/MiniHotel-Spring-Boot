package com.demo.MiniHotel.modules.phuthu.controller;

import com.demo.MiniHotel.model.PhuThu;
import com.demo.MiniHotel.modules.phuthu.dto.PhuThuRequest;
import com.demo.MiniHotel.modules.phuthu.service.IPhuThuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phu-thu")
@RequiredArgsConstructor
public class PhuThuController {
    private final IPhuThuService PhuThuService;
    @PostMapping("/")
    public ResponseEntity<PhuThu> addNewPhuThu(@RequestBody PhuThuRequest request) throws Exception {
        PhuThu PhuThu = PhuThuService.addNewPhuThu(request);
        return new ResponseEntity<>(PhuThu, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PhuThu>> getAllPhuThu(){
        List<PhuThu> PhuThus = PhuThuService.getAllPhuThu();
        return new ResponseEntity<>(PhuThus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhuThu> getPhuThuById(@PathVariable("id") Integer id) throws Exception {
        PhuThu PhuThu = PhuThuService.getPhuThuById(id);
        return new ResponseEntity<>(PhuThu, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhuThu> updatePhuThu(@PathVariable("id") Integer id,
                                                     @RequestBody PhuThuRequest request) throws Exception {
        PhuThu PhuThu = PhuThuService.updatePhuThu(request,id);
        return new ResponseEntity<>(PhuThu, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhuThu(@PathVariable("id") Integer id) throws Exception {
        PhuThuService.deletePhuThu(id);
        return new ResponseEntity<>("Deleted No." + id + " successfully.", HttpStatus.OK);
    }
}
