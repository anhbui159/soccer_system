package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.OutOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutOrderDetailRepository extends JpaRepository<OutOrderDetail, Long> {
}
