package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.embedded.IdChiTietThayDoiGiaPhongEmb;
import com.demo.MiniHotel.model.ChiTietThayDoiGiaPhong;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChiTietThayDoiGiaPhongRepository extends JpaRepository<ChiTietThayDoiGiaPhong, IdChiTietThayDoiGiaPhongEmb> {
//    Optional<ChiTietThayDoiGiaPhong> findByIdAndGiaCapNhat
//            (IdChiTietThayDoiGiaPhongEmb idChiTietThayDoiGiaPhongEmb, long giaCapNhat);


    List<ChiTietThayDoiGiaPhong> findByHangPhong_IdHangPhong(int idHangPhong);
}
