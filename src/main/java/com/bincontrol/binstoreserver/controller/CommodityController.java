package com.bincontrol.binstoreserver.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bincontrol.binstoreserver.common.ServerErrorCode;
import com.bincontrol.binstoreserver.entity.CommodityEntity;
import com.bincontrol.binstoreserver.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.logging.Logger;

import static com.bincontrol.binstoreserver.common.ServerConstant.REQUEST_PARAM_CATEGORY;

@RestController
@RequestMapping(path = "/commodity")
public class CommodityController {

    private static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private CommodityService commodityService;


    @GetMapping(path = "/category")
    public void getCommoditiesByCategory(HttpServletRequest request, HttpServletResponse response) {

        logger.info("请求：" + request.getRequestURL().toString());
        logger.info("参数：" + request.getQueryString());

        String category = request.getParameter(REQUEST_PARAM_CATEGORY);

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            if (category == null) {
                logger.warning("错误：缺少必要参数（商品类目）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_MISSING_CATEGORY.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_MISSING_CATEGORY.getMsg());

            } else if (category.isEmpty()) {
                logger.warning("错误：参数值为空（商品类目）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_EMPTY_CATEGORY.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_EMPTY_CATEGORY.getMsg());

            } else {
                Iterable<CommodityEntity> commodityEntities = commodityService.getAll(category);
                for (CommodityEntity commodityEntity : commodityEntities) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("commodity_id", commodityEntity.getCommodityId());
                    jsonObject.put("picture", commodityEntity.getPicture());
                    jsonObject.put("title", commodityEntity.getTitle());
                    jsonObject.put("price", commodityEntity.getPrice());
                    jsonObject.put("volume", commodityEntity.getVolume());
                    jsonArray.add(jsonObject);
                }
                json.put("status", ServerErrorCode.BIN_OK.getCode());
                json.put("msg", ServerErrorCode.BIN_OK.getMsg());
                json.put("data", jsonArray);
            }

            out.print(json);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("异常：" + e.getMessage());

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
