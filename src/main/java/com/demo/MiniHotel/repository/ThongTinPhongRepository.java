package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.ThongTinHangPhong;
import com.demo.MiniHotel.model.ThongTinPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThongTinPhongRepository extends JpaRepository<ThongTinPhong, String> {
    List<ThongTinPhong> findByIdHangPhong(int idHangPhong);
}
