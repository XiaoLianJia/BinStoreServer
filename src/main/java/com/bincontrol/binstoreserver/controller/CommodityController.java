package com.bincontrol.binstoreserver.controller;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bincontrol.binstoreserver.common.ServerConstant.*;

@RestController
@RequestMapping(path = "/commodity")
public class CommodityController {

    @GetMapping(path = "/coupon")
    public String getCouponComodity() {

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
    }
}