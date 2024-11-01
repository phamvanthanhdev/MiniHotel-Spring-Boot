package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.PhieuDatPhong;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PhieuDatPhongPagingRepository extends PagingAndSortingRepository<PhieuDatPhong, Integer> {

}
