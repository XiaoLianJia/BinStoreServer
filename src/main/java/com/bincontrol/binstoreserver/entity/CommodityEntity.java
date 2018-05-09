package com.bincontrol.binstoreserver.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "tb_commodity")
public class CommodityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品ID
     */
    @NotNull
    @Column(name = "commodity_id")
    private Long commodityId;

    /**
     * 商品图片链接
     */
    @NotNull
    @Column(name = "picture")
    private String picture;

    /**
     * 商品标题
     */
    @NotNull
    @Column(name = "title")
    private String title;

    /**
     * 商品价格
     */
    @NotNull
    @Column(name = "price")
    private String price;

    /**
     * 商品类别
     */
    @Column(name = "category")
    private String category;

    /**
     * 商品数据更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
