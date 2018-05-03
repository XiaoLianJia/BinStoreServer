package com.bincontrol.binstoreserver.repository;

import com.bincontrol.binstoreserver.entity.Commodity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityRepository extends CrudRepository<Commodity, Long> {
}
