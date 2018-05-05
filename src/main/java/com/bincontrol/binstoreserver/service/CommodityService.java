package com.bincontrol.binstoreserver.service;

import com.bincontrol.binstoreserver.entity.Commodity;
import com.bincontrol.binstoreserver.repository.CommodityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

@Service
public class CommodityService {

    @Resource
    private CommodityRepository commodityRepository;


    @Transactional
    public void save(Commodity commodity) {
        commodityRepository.save(commodity);
    }

    @Transactional
    public void update(Commodity commodity) {
        Commodity c = commodityRepository.findByCommodityId(commodity.getCommodityId());
        if (c == null) {
            save(commodity);
            return;
        }

        c.setCommodityId(commodity.getCommodityId());
        c.setPicture(commodity.getPicture());
        c.setTitle(commodity.getTitle());
        c.setPrice(commodity.getPrice());
        c.setCategory(commodity.getCategory());
        c.setUpdateTime(commodity.getUpdateTime());
    }

    @Transactional
    public boolean findByCommodityId(Long commodityId) {
        Commodity c = commodityRepository.findByCommodityId(commodityId);
        return c != null;
    }

    @Transactional
    public void delete(Long commodityId) {
        commodityRepository.deleteByCommodityId(commodityId);
    }

    @Transactional
    public void deleteAll() {
        commodityRepository.deleteAll();
    }

    @Transactional
    public void deleteAll(Date date) {
        commodityRepository.deleteCommoditiesByUpdateTimeBefore(date);
    }

    @Transactional
    public Iterable<Commodity> getAll() {
        return commodityRepository.findAll();
    }

    @Transactional
    public Iterable<Commodity> getAll(String category) {
        return commodityRepository.findCommoditiesByCategory(category);
    }

}
