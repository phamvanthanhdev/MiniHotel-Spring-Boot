package com.demo.MiniHotel.modules.dichvu.service;

import com.demo.MiniHotel.model.DichVu;
import com.demo.MiniHotel.modules.dichvu.dto.DichVuRequest;

import java.util.List;

public interface IDichVuService {
    public DichVu addNewDichVu(DichVuRequest request) throws Exception;
    public List<DichVu> getAllDichVu();
    public DichVu getDichVuById(Integer id) throws Exception;
    public DichVu updateDichVu(DichVuRequest request, Integer id) throws Exception;
    public void deleteDichVu(Integer id) throws Exception;
}
