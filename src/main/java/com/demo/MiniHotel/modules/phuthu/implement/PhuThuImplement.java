package com.demo.MiniHotel.modules.phuthu.implement;

import com.demo.MiniHotel.model.PhuThu;
import com.demo.MiniHotel.modules.phuthu.dto.PhuThuRequest;
import com.demo.MiniHotel.modules.phuthu.service.IPhuThuService;
import com.demo.MiniHotel.repository.PhuThuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhuThuImplement implements IPhuThuService {
    private final PhuThuRepository repository;
    @Override
    public PhuThu addNewPhuThu(PhuThuRequest request) throws Exception {
        PhuThu PhuThu = new PhuThu();
        PhuThu.setNoiDung(request.getNoiDung());
        PhuThu.setDonGia(request.getDonGia());

        return repository.save(PhuThu);
    }

    @Override
    public List<PhuThu> getAllPhuThu() {
        return repository.findAll();
    }

    @Override
    public PhuThu getPhuThuById(Integer id) throws Exception {
        Optional<PhuThu> PhuThuOptional = repository.findById(id);
        if(PhuThuOptional.isEmpty()){
            throw new Exception("PhuThu not found.");
        }

        return PhuThuOptional.get();
    }

    @Override
    public PhuThu updatePhuThu(PhuThuRequest request, Integer id) throws Exception {
        PhuThu PhuThu = getPhuThuById(id);
        PhuThu.setNoiDung(request.getNoiDung());
        PhuThu.setDonGia(request.getDonGia());

        return repository.save(PhuThu);
    }

    @Override
    public void deletePhuThu(Integer id) throws Exception {
        Optional<PhuThu> PhuThuOptional = repository.findById(id);
        if(PhuThuOptional.isEmpty()){
            throw new Exception("PhuThu not found.");
        }

        repository.deleteById(id);
    }
}
