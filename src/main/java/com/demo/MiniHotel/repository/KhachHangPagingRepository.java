package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.KhachHang;
import com.demo.MiniHotel.model.PhieuThuePhong;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface KhachHangPagingRepository extends PagingAndSortingRepository<KhachHang, Integer> {

}
