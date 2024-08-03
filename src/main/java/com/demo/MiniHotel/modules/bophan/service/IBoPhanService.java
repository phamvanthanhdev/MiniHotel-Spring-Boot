package com.demo.MiniHotel.modules.bophan.service;

import com.demo.MiniHotel.modules.bophan.dto.BoPhanRequest;
import com.demo.MiniHotel.model.BoPhan;

import java.util.List;

public interface IBoPhanService {
    public BoPhan addNewBoPhan(BoPhanRequest request);
    public List<BoPhan> getAllBoPhan();
    public BoPhan getBoPhanById(Integer id) throws Exception;
    public BoPhan updateBoPhan(BoPhanRequest request, Integer id) throws Exception;
    public void deleteBoPhan(Integer id) throws Exception;
}
