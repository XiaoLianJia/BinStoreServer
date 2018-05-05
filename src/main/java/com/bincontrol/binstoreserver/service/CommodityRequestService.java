package com.bincontrol.binstoreserver.service;

import com.bincontrol.binstoreserver.entity.Commodity;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.bincontrol.binstoreserver.common.ServerConstant.*;

@Component
public class CommodityRequestService {

    @Autowired
    private CommodityService commodityService;
    private TaobaoClient mClient;
    private TbkDgItemCouponGetRequest mRequest;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void getCommodity() {

        mClient = new DefaultTaobaoClient(ALI_SERVER_URL, ALI_SERVER_APP_KEY, ALI_SERVER_APP_SECRET);
        mRequest = new TbkDgItemCouponGetRequest();
        mRequest.setAdzoneId(ALI_SERVER_ADZONE_ID);
        mRequest.setPageSize(100L);

        System.out.print("===================================================\r\n");
        String log ="请求商品数据开始：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss\r\n").format(new Date());
        System.out.print(log);

        for (String category : COMMODITY_CATEGORY) {
            updateCommodity(category);
        }

        log ="请求商品数据完成：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss\r\n").format(new Date());
        System.out.print(log);
        System.out.print("===================================================\r\n");
    }


    private void updateCommodity(String category) {

        mRequest.setQ(category);
        try {
            TbkDgItemCouponGetResponse response = mClient.execute(mRequest);
            List<TbkDgItemCouponGetResponse.TbkCoupon> tbkCouponList = response.getResults();
            for (TbkDgItemCouponGetResponse.TbkCoupon tbkCoupon : tbkCouponList) {

                Commodity commodity = new Commodity();
                commodity.setCommodityId(tbkCoupon.getNumIid());
                commodity.setPicture(tbkCoupon.getPictUrl());
                commodity.setTitle(tbkCoupon.getTitle());
                commodity.setPrice(tbkCoupon.getZkFinalPrice());
                commodity.setCategory(category);
                commodity.setUpdateTime(new Date());
                commodityService.update(commodity);
            }

            String log = "请求（" + category +"）商品成功\r\n";
            System.out.print(log);

        } catch (ApiException e) {

            String log ="请求（" + category +"）商品失败，异常信息[" + e.getErrCode() + "]：" + e.getErrMsg() + "\r\n";
            System.out.print(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Scheduled(cron = "0 0/5 * * * ?")
    public void cleanCommodity() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        Date date = calendar.getTime();
        commodityService.deleteAll(date);

        System.out.print("===================================================\r\n");
        String log ="清理[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "]之前入库商品数据\r\n";
        System.out.print(log);
        System.out.print("===================================================\r\n");
    }

}
