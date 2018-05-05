package com.bincontrol.binstoreserver.repository;

import com.bincontrol.binstoreserver.entity.Commodity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommodityRepository extends CrudRepository<Commodity, Long> {

    // 通过商品ID查找单个商品
    Commodity findByCommodityId(Long commodityId);
    // 通过商品类别查找该类别所有商品
    Iterable<Commodity> findCommoditiesByCategory(String category);
    // 通过日期查找该日期之前入库的所有商品
    Iterable<Commodity> findCommoditiesByUpdateTimeBefore(Date date);

    // 通过商品ID删除指定商品
    void deleteByCommodityId(Long commodityId);
    // 通过商品类别删除该类别所有商品
    void deleteCommoditiesByCategory(String category);
    // 删除指定日期之前入库的所有商品
    void deleteCommoditiesByUpdateTimeBefore(Date date);
}
