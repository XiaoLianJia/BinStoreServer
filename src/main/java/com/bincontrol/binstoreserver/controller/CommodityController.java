package com.bincontrol.binstoreserver.controller;

import com.bincontrol.binstoreserver.entity.CommodityEntity;
import com.bincontrol.binstoreserver.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @GetMapping(path = "/category")
    public String getCommodityByCategory(String category) {

        Iterable<CommodityEntity> commodities = commodityService.getAll();
        for (CommodityEntity commodityEntity : commodities) {
            System.out.print(commodityEntity.getCommodityId());
        }

        return "Finish";
/**
        TaobaoClient client = new DefaultTaobaoClient(ALI_SERVER_URL, ALI_SERVER_APP_KEY, ALI_SERVER_APP_SECRET);
        TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
        req.setAdzoneId(ALI_SERVER_ADZONE_ID);
        //req.setQ(category);

        try {
            TbkDgItemCouponGetResponse rsp = client.execute(req);
            //System.out.println(rsp.getBody());
            return rsp.getBody();
        } catch (ApiException e) {
            System.out.print(e.getErrCode() + e.getErrMsg());
            return e.getErrCode() + e.getErrMsg();
        }
 */
    }

}
