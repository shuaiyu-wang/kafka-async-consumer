package com.civip.csyy.repository;

import com.civip.csyy.model.VehicleLoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/15
 */
@Repository
public interface VehicleLocRepository extends JpaRepository<VehicleLoc, Long> {

}
