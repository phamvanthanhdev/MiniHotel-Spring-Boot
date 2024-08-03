package com.demo.MiniHotel.modules.bophan.implement;

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
        if(request.getTenBoPhan() != null)
            boPhan.setTenBoPhan(request.getTenBoPhan());
        return repository.save(boPhan);
    }

    @Override
    public void deleteBoPhan(Integer id) throws Exception {
        Optional<BoPhan> boPhanOptional = repository.findById(id);
        if(boPhanOptional.isEmpty()){
            throw new Exception("BoPhan not found");
        }
        repository.deleteById(id);
    }
}
