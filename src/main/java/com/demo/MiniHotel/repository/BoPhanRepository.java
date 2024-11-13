package com.demo.MiniHotel.repository;

import com.demo.MiniHotel.model.BoPhan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoPhanRepository extends JpaRepository<BoPhan, Integer> {

    boolean existsByTenBoPhan(String tenBoPhan);
}
