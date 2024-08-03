package com.demo.MiniHotel.modules.phuthu.service;

import com.demo.MiniHotel.model.PhuThu;
import com.demo.MiniHotel.modules.phuthu.dto.PhuThuRequest;

import java.util.List;

public interface IPhuThuService {
    public PhuThu addNewPhuThu(PhuThuRequest request) throws Exception;
    public List<PhuThu> getAllPhuThu();
    public PhuThu getPhuThuById(Integer id) throws Exception;
    public PhuThu updatePhuThu(PhuThuRequest request, Integer id) throws Exception;
    public void deletePhuThu(Integer id) throws Exception;
}
