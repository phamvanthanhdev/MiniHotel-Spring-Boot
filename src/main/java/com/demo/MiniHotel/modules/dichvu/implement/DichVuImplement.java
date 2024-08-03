package com.demo.MiniHotel.modules.dichvu.implement;

import com.demo.MiniHotel.model.BoPhan;
import com.demo.MiniHotel.model.DichVu;
import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.model.TaiKhoan;
import com.demo.MiniHotel.modules.dichvu.dto.DichVuRequest;
import com.demo.MiniHotel.modules.dichvu.service.IDichVuService;
import com.demo.MiniHotel.repository.DichVuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DichVuImplement implements IDichVuService {
    private final DichVuRepository repository;
    @Override
    public DichVu addNewDichVu(DichVuRequest request) throws Exception {
        DichVu dichVu = new DichVu();
        dichVu.setTenDichVu(request.getTenDichVu());
        dichVu.setDonGia(request.getDonGia());

        return repository.save(dichVu);
    }

    @Override
    public List<DichVu> getAllDichVu() {
        return repository.findAll();
    }

    @Override
    public DichVu getDichVuById(Integer id) throws Exception {
        Optional<DichVu> DichVuOptional = repository.findById(id);
        if(DichVuOptional.isEmpty()){
            throw new Exception("DichVu not found.");
        }

        return DichVuOptional.get();
    }

    @Override
    public DichVu updateDichVu(DichVuRequest request, Integer id) throws Exception {
        DichVu dichVu = getDichVuById(id);
        dichVu.setTenDichVu(request.getTenDichVu());
        dichVu.setDonGia(request.getDonGia());

        return repository.save(dichVu);
    }

    @Override
    public void deleteDichVu(Integer id) throws Exception {
        Optional<DichVu> DichVuOptional = repository.findById(id);
        if(DichVuOptional.isEmpty()){
            throw new Exception("DichVu not found.");
        }

        repository.deleteById(id);
    }
}
