package com.bincontrol.binstoreserver.service;

import com.bincontrol.binstoreserver.entity.Commodity;
import com.bincontrol.binstoreserver.repository.CommodityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class CommodityService {

    @Resource
    private CommodityRepository commodityRepository;


    @Transactional
    public void save(Commodity commodity) {
        commodityRepository.save(commodity);
    }

    @Transactional
    public void delete(Long commodityId) {
        commodityRepository.deleteByCommodityId(commodityId);
    }

}
