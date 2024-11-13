package com.demo.MiniHotel.modules.bophan.implement;

import com.demo.MiniHotel.exception.AppException;
import com.demo.MiniHotel.exception.ErrorCode;
import com.demo.MiniHotel.modules.bophan.dto.BoPhanRequest;
import com.demo.MiniHotel.model.BoPhan;
import com.demo.MiniHotel.repository.BoPhanRepository;
import com.demo.MiniHotel.modules.bophan.service.IBoPhanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoPhanImplement implements IBoPhanService {
    private final BoPhanRepository repository;
    @Override
    public BoPhan addNewBoPhan(BoPhanRequest request) {
        if(repository.existsByTenBoPhan(request.getTenBoPhan()))
            throw new AppException(ErrorCode.TENBOPHAN_EXISTED);
        BoPhan boPhan = new BoPhan();
        boPhan.setTenBoPhan(request.getTenBoPhan());

        return repository.save(boPhan);
    }

    @Override
    public List<BoPhan> getAllBoPhan() {
        return repository.findAll();
    }

    @Override
    public BoPhan getBoPhanById(Integer id) throws Exception {
        Optional<BoPhan> boPhanOptional = repository.findById(id);
        if(boPhanOptional.isEmpty()){
            throw new Exception("BoPhan not found");
        }
        return boPhanOptional.get();
    }

    @Override
    public BoPhan updateBoPhan(BoPhanRequest request, Integer id) throws Exception {
        BoPhan boPhan = getBoPhanById(id);
        if(!boPhan.getTenBoPhan().trim().equals(request.getTenBoPhan())
                && repository.existsByTenBoPhan(request.getTenBoPhan().trim()))
            throw new AppException(ErrorCode.TENBOPHAN_EXISTED);

        boPhan.setTenBoPhan(request.getTenBoPhan());
        return repository.save(boPhan);
    }

    @Override
    public void deleteBoPhan(Integer id) throws Exception {
        BoPhan boPhan = getBoPhanById(id);
        if(boPhan.getNhanViens().size() > 0)
            throw new AppException(ErrorCode.BOPHAN_DANGSUDUNG);

        repository.deleteById(id);
    }
}
