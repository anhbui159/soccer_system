package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.InOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InOrderDetailRepository extends JpaRepository<InOrderDetail, Long> {

}
