package com.bincontrol.binstoreserver.service;

import com.bincontrol.binstoreserver.entity.CommodityEntity;
import com.bincontrol.binstoreserver.repository.CommodityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class CommodityService {

    @Resource
    private CommodityRepository commodityRepository;


    public void save(CommodityEntity commodityEntity) {
        commodityRepository.save(commodityEntity);
    }

    /**
     * 更新商品数据
     * @param commodityEntity 商品实体
     */
    public void update(CommodityEntity commodityEntity) {
        CommodityEntity c = commodityRepository.findByCommodityId(commodityEntity.getCommodityId());
        if (c == null) {
            commodityRepository.save(commodityEntity);
            return;
        }

        c.setCommodityId(commodityEntity.getCommodityId());
        c.setPicture(commodityEntity.getPicture());
        c.setTitle(commodityEntity.getTitle());
        c.setPrice(commodityEntity.getPrice());
        c.setCategory(commodityEntity.getCategory());
        c.setUpdateTime(commodityEntity.getUpdateTime());
    }

    /**
     * 在数据库中查找该商品ID
     * @param commodityId 商品ID
     * @return 商品ID匹配的商品是否存在
     */
    public boolean find(Long commodityId) {
        CommodityEntity c = commodityRepository.findByCommodityId(commodityId);
        return c != null;
    }

    /**
     * 删除该商品ID对应的商品
     * @param commodityId 商品ID
     */
    public void delete(Long commodityId) {
        commodityRepository.deleteByCommodityId(commodityId);
    }

    /**
     * 删除所有商品
     */
    public void deleteAll() {
        commodityRepository.deleteAll();
    }

    /**
     * 删除该日期之前入库的所有商品
     * @param date 分界日期
     */
    public void deleteAll(Date date) {
        commodityRepository.deleteCommodityEntitiesByUpdateTimeBefore(date);
    }

    /**
     * 获取所有商品
     * @return 所有商品
     */
    public Iterable<CommodityEntity> getAll() {
        return commodityRepository.findAll();
    }

    /**
     * 获取该类别下所有商品
     * @param category 商品类别
     * @return 商品类别下所有商品
     */
    public Iterable<CommodityEntity> getAll(String category) {
        return commodityRepository.findCommodityEntitiesByCategory(category);
    }

}
