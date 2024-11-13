package com.demo.MiniHotel.modules.nhomquyen.service;

import com.demo.MiniHotel.modules.nhomquyen.dto.NhomQuyenRequest;
import com.demo.MiniHotel.model.NhomQuyen;
import com.demo.MiniHotel.modules.nhomquyen.dto.NhomQuyenResponse;

import java.util.List;

public interface INhomQuyenService {
    public NhomQuyen addNewNhomQuyen(NhomQuyenRequest request);
    public List<NhomQuyen> getAllNhomQuyen();
    public NhomQuyen getNhomQuyenById(Integer id) throws Exception;
    public NhomQuyenResponse updateNhomQuyen(NhomQuyenRequest request, Integer id) throws Exception;
    public void deleteNhomQuyen(Integer id) throws Exception;
    public List<NhomQuyenResponse> getAllNhomQuyenResponse();
    public NhomQuyenResponse getNhomQuyenResponseById(Integer id) throws Exception;
}
