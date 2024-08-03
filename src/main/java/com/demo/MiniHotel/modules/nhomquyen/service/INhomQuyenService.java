package com.demo.MiniHotel.modules.nhomquyen.service;

import com.demo.MiniHotel.modules.nhomquyen.dto.NhomQuyenRequest;
import com.demo.MiniHotel.model.NhomQuyen;

import java.util.List;

public interface INhomQuyenService {
    public NhomQuyen addNewNhomQuyen(NhomQuyenRequest request);
    public List<NhomQuyen> getAllNhomQuyen();
    public NhomQuyen getNhomQuyenById(Integer id) throws Exception;
    public NhomQuyen updateNhomQuyen(NhomQuyenRequest request, Integer id) throws Exception;
    public void deleteNhomQuyen(Integer id) throws Exception;
}
