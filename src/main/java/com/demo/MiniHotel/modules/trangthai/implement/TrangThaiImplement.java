package com.demo.MiniHotel.modules.trangthai.implement;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.model.TrangThai;
import com.demo.MiniHotel.modules.trangthai.dto.TrangThaiRequest;
import com.demo.MiniHotel.modules.trangthai.service.ITrangThaiService;
import com.demo.MiniHotel.repository.TrangThaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrangThaiImplement implements ITrangThaiService {
    private final TrangThaiRepository repository;
    @Override
    public TrangThai addNewTrangThai(TrangThaiRequest request) {
        if(repository.existsByTenTrangThai(request.getTenTrangThai()))
            throw new AppException(ErrorCode.TENTRANGTHAI_TONTAI);

        TrangThai TrangThai = new TrangThai();
        TrangThai.setTenTrangThai(request.getTenTrangThai());

        return repository.save(TrangThai);
    }

    @Override
    public List<TrangThai> getAllTrangThai() {
        List<TrangThai> trangThais = repository.findAll();
        Collections.sort(trangThais, new Comparator<TrangThai>() {
            @Override
            public int compare(TrangThai o1, TrangThai o2) {
                return o1.getIdTrangThai() > o2.getIdTrangThai() ? 1 : -1;
            }
        });
        return trangThais;
    }

    @Override
    public TrangThai getTrangThaiById(Integer id) throws Exception {
        Optional<TrangThai> TrangThaiOptional = repository.findById(id);
        if(TrangThaiOptional.isEmpty()){
            throw new Exception("TrangThai not found");
        }
        return TrangThaiOptional.get();
    }

    @Override
    public TrangThai updateTrangThai(TrangThaiRequest request, Integer id) throws Exception {
        TrangThai trangThai = getTrangThaiById(id);
        if(trangThai.getTenTrangThai().trim().equals("Sạch sẽ")
        || trangThai.getTenTrangThai().trim().equals("Đang dọn dẹp")
        || trangThai.getTenTrangThai().trim().equals("Đang sửa chữa")) {
            throw new AppException(ErrorCode.TRANGTHAI_BATBUOC);
        }
        trangThai.setTenTrangThai(request.getTenTrangThai());
        return repository.save(trangThai);
    }

    @Override
    public void deleteTrangThai(Integer id) throws Exception {
        TrangThai trangThai = getTrangThaiById(id);
        if(trangThai.getTenTrangThai().trim().equals("Sạch sẽ")
            || trangThai.getTenTrangThai().trim().equals("Đang dọn dẹp")
            || trangThai.getTenTrangThai().trim().equals("Đang sửa chữa")) {
            throw new AppException(ErrorCode.TRANGTHAI_BATBUOC);
        }
        if(trangThai.getPhongs().size() > 0)
            throw new AppException(ErrorCode.TRANGTHAI_DANGSUDUNG);
        repository.deleteById(id);
    }
}
