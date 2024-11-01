package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.PhieuDatPhong;
import com.demo.MiniHotel.model.PhieuThuePhong;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PhieuThuePhongPagingRepository extends PagingAndSortingRepository<PhieuThuePhong, Integer> {

}
