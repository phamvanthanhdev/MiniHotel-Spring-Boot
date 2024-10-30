package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaPhongEmb;
import com.demo.MiniHotel.model.ChiTietThayDoiGiaPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChiTietThayDoiGiaPhongRepository extends JpaRepository<ChiTietThayDoiGiaPhong, IdChiTietThayDoiGiaPhongEmb> {

}
