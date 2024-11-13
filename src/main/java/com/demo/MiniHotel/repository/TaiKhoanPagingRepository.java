package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.PhieuThuePhong;
import com.demo.MiniHotel.model.TaiKhoan;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaiKhoanPagingRepository extends PagingAndSortingRepository<TaiKhoan, Integer> {

}
