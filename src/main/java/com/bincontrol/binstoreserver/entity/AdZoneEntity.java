package com.bincontrol.binstoreserver.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_adzone")
public class AdZoneEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 推广位名称，由系统管理员指定
     */
    @NotNull
    @Column(name = "adzone_name")
    private String adzoneName;

    /**
     * 推广位编号，由系统管理员指定
     */
    @NotNull
    @Column(name = "adzone_id")
    private Long adzoneId;

    /**
     * 推广位是否已被占用
     */
    @NotNull
    @Column(name = "occupied")
    private boolean occupied;

    /**
     * 推广位绑定的用户，在用户注册时绑定，值为用户帐号
     */
    @Column(name = "owner")
    private String owner;


    public String getAdzoneName() {
        return adzoneName;
    }

    public void setAdzoneName(String adzoneName) {
        this.adzoneName = adzoneName;
    }

    public Long getAdzoneId() {
        return adzoneId;
    }

    public void setAdzoneId(Long adzoneId) {
        this.adzoneId = adzoneId;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
