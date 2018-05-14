package com.bincontrol.binstoreserver.repository;

import com.bincontrol.binstoreserver.entity.AdZoneEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdZoneRepository extends CrudRepository<AdZoneEntity, Long> {

    /**
     * 通过推广位名称查找推广位
     * @param adZoneName 推广位名称
     * @return 推广位名称匹配的推广位实体
     */
    AdZoneEntity findByAdzoneName(String adZoneName);

    /**
     * 通过推广位编号查找推广位
     * @param adZoneId 推广位编号
     * @return 推广位编号匹配的推广位实体
     */
    AdZoneEntity findByAdzoneId(Long adZoneId);

    /**
     * 通过用户帐号查找与该用户绑定的推广位
     * @param owner 推广位绑定的用户帐号
     * @return 用户帐号匹配的推广位实体
     */
    AdZoneEntity findByOwner(String owner);

    /**
     * 查找尚未被占用的推广位
     * @return 空闲推广位实体
     */
    Iterable<AdZoneEntity> findByOccupied(boolean isOccupied);

}
