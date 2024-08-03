package com.demo.MiniHotel.modules.phong.implement;

import com.demo.MiniHotel.model.*;
import com.demo.MiniHotel.modules.hangphong.service.IHangPhongService;
import com.demo.MiniHotel.modules.phieudatphong.dto.ResultResponse;
import com.demo.MiniHotel.modules.phong.dto.PhongRequest;
import com.demo.MiniHotel.modules.phong.service.IPhongService;
import com.demo.MiniHotel.modules.trangthai.service.ITrangThaiService;
import com.demo.MiniHotel.repository.PhongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhongImplement implements IPhongService {
    private final PhongRepository repository;
    private final IHangPhongService hangPhongService;
    private final ITrangThaiService trangThaiService;
    @Override
    public Phong addNewPhong(PhongRequest request) throws Exception {
        Optional<Phong> phongOptional = repository.findById(request.getMaPhong());
        if(phongOptional.isEmpty()) {
            HangPhong hangPhong = hangPhongService.getHangPhongById(request.getIdHangPhong());
            TrangThai trangThai = trangThaiService.getTrangThaiById(request.getIdTrangThai());

            Phong phong = new Phong();
            phong.setMaPhong(request.getMaPhong());
            phong.setTang(request.getTang());
            phong.setMoTa(request.getMoTa());
            phong.setNgayTao(request.getNgayTao());
            phong.setNgayCapNhat(request.getNgayCapNhat());

            phong.setHangPhong(hangPhong);
            phong.setTrangThai(trangThai);

            return repository.save(phong);
        }

        throw new Exception("MaPhong is existed!");
    }

    @Override
    public List<Phong> getAllPhong() {
        return repository.findAll();
    }

    @Override
    public Phong getPhongById(String maPhong) throws Exception {
        Optional<Phong> PhongOptional = repository.findById(maPhong);
        if(PhongOptional.isEmpty()){
            throw new Exception("Phong not found.");
        }

        return PhongOptional.get();
    }

    @Override
    public Phong updatePhong(PhongRequest request, String maPhong) throws Exception {
        Optional<Phong> phongOptional = repository.findById(maPhong);
        if(phongOptional.isPresent()) {
            HangPhong hangPhong = hangPhongService.getHangPhongById(request.getIdHangPhong());
            TrangThai trangThai = trangThaiService.getTrangThaiById(request.getIdTrangThai());

            Phong phong = phongOptional.get();

            phong.setTang(request.getTang());
            phong.setMoTa(request.getMoTa());
            phong.setNgayCapNhat(request.getNgayCapNhat());

            phong.setHangPhong(hangPhong);
            phong.setTrangThai(trangThai);

            return repository.save(phong);
        }

        throw new Exception("Phong not found!");
    }

    @Override
    public void deletePhong(String maPhong) throws Exception {
        Optional<Phong> PhongOptional = repository.findById(maPhong);
        if(PhongOptional.isEmpty()){
            throw new Exception("Phong not found.");
        }

        repository.deleteById(maPhong);
    }

    @Override
    public Phong capNhatTrangThai(String maPhong, int idTrangThai) throws Exception {
        TrangThai trangThai = trangThaiService.getTrangThaiById(idTrangThai);
        Phong phong = getPhongById(maPhong);
        phong.setTrangThai(trangThai);
        return repository.save(phong);
    }
}
