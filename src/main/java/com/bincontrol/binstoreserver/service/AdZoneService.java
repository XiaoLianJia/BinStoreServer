package com.bincontrol.binstoreserver.service;

import com.bincontrol.binstoreserver.entity.AdZoneEntity;
import com.bincontrol.binstoreserver.repository.AdZoneRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AdZoneService {

    @Resource
    private AdZoneRepository adZoneRepository;

    /**
     * 增加一个推广位
     * @param adZoneName 推广位名称
     * @param adZoneId 推广位编号
     */
    public void add(String adZoneName, Long adZoneId) {
        AdZoneEntity adZoneEntity = new AdZoneEntity();
        adZoneEntity.setAdzoneName(adZoneName);
        adZoneEntity.setAdzoneId(adZoneId);
        adZoneEntity.setOccupied(false);
        adZoneRepository.save(adZoneEntity);
    }

    /**
     * 在数据库中查找推广位名称
     * @param adZoneName 推广位名称
     * @return 推广位名称匹配的推广位是否存在
     */
    public boolean find(String adZoneName) {
        AdZoneEntity adZoneEntity = adZoneRepository.findByAdzoneName(adZoneName);
        return adZoneEntity != null;
    }

    /**
     * 在数据库中查找推广位编号
     * @param adZoneId 推广位编号
     * @return 推广位编号匹配的推广位是否存在
     */
    public boolean find(Long adZoneId) {
        AdZoneEntity adZoneEntity = adZoneRepository.findByAdzoneId(adZoneId);
        return adZoneEntity != null;
    }

    /**
     * 查找空闲推广位，并返回其推广位编号
     * @param owner 与推广位绑定的用户帐号
     * @param setOccupied 是否占用该推广位
     * @return 返回空闲推广位编号
     */
    public Long getFreeAdZoneId(String owner, boolean setOccupied) {
        Iterable<AdZoneEntity> adZoneEntities = adZoneRepository.findByOccupied(false);
        for (AdZoneEntity adZoneEntity : adZoneEntities) {
            if (adZoneEntity != null) {
                adZoneEntity.setOccupied(setOccupied);
                adZoneEntity.setOwner(owner);
                return adZoneEntity.getAdzoneId();
            }
        }
        return null;
    }


    /**
     * 查询所有推广位
     * @return 所有推广位
     */
    public Iterable<AdZoneEntity> getAll() {
        return adZoneRepository.findAll();
    }

}
