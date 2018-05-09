package com.bincontrol.binstoreserver.repository;

import com.bincontrol.binstoreserver.entity.CommodityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CommodityRepository extends CrudRepository<CommodityEntity, Long> {

    /**
     * 通过商品ID查找商品
     * @param commodityId 商品ID
     * @return 商品ID匹配的商品实体
     */
    CommodityEntity findByCommodityId(Long commodityId);

    /**
     * 通过商品类别查找该类别所有商品
     * @param category 商品类别
     * @return 商品类别下全部商品
     */
    Iterable<CommodityEntity> findCommodityEntitiesByCategory(String category);

    /**
     * 通过日期查找该日期之前入库的所有商品
     * @param date 分界日期
     * @return 分界日期前入库的全部商品
     */
    Iterable<CommodityEntity> findCommodityEntitiesByUpdateTimeBefore(Date date);

    /**
     * 通过商品ID删除商品
     * @param commodityId 商品ID
     */
    void deleteByCommodityId(Long commodityId);

    /**
     * 通过商品类别删除该类别所有商品
     * @param category 商品类别
     */
    void deleteCommodityEntitiesByCategory(String category);

    /**
     * 删除指定日期之前入库的所有商品
     * @param date 分界日期
     */
    void deleteCommodityEntitiesByUpdateTimeBefore(Date date);

}
