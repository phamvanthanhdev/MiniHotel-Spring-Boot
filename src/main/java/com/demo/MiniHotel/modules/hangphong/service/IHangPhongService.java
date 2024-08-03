package com.demo.MiniHotel.modules.hangphong.service;


import com.demo.MiniHotel.model.HangPhong;
import com.demo.MiniHotel.model.ThongTinHangPhong;
import com.demo.MiniHotel.modules.hangphong.dto.HangPhongRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IHangPhongService {
    public HangPhong addNewHangPhong(Integer idLoaiPhong, Integer idKieuPhong,
                                     String tenHangPhong,String moTa,
                                      MultipartFile file) throws Exception;
    public List<HangPhong> getAllHangPhong();
    public HangPhong getHangPhongById(Integer id) throws Exception;
    public String getHinhAnhByIdHangPhong(Integer id) throws Exception;
    public HangPhong updateHangPhong(Integer idLoaiPhong, Integer idKieuPhong,
                                     String tenHangPhong,String moTa,
                                     MultipartFile file, Integer id) throws Exception;
    public void deleteHangPhong(Integer id) throws Exception;

}
