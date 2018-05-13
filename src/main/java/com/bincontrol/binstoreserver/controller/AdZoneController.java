package com.bincontrol.binstoreserver.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bincontrol.binstoreserver.common.ServerErrorCode;
import com.bincontrol.binstoreserver.entity.AdZoneEntity;
import com.bincontrol.binstoreserver.service.AdZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/adzone")
public class AdZoneController {

    private static Logger logger = Logger.getLogger(AdZoneController.class.getName());

    @Autowired
    private AdZoneService adZoneService;


    /**
     * 新建一个推广位
     * @param request Http请求
     * @param response Http响应
     */
    @RequestMapping(value = "/add")
    public void add(HttpServletRequest request, HttpServletResponse response) {

        logger.info("请求：" + request.getRequestURL().toString());
        logger.info("参数：" + request.getQueryString());

        String adZoneName = request.getParameter("name");
        String adZoneId = request.getParameter("id");

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();

            if (adZoneName == null) {
                logger.warning("错误：缺少必要参数（推广位名称）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_MISSING_ADZONENAME.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_MISSING_ADZONENAME.getMsg());

            } else if (adZoneId == null) {
                logger.warning("错误：缺少必要参数（推广位编号）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_MISSING_ADZONEID.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_MISSING_ADZONEID.getMsg());

            } else if (adZoneName.isEmpty()) {
                logger.warning("错误：参数值为空（推广位名称）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_EMPTY_ADZONENAME.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_EMPTY_ADZONENAME.getMsg());

            } else if (adZoneId.isEmpty()) {
                logger.warning("错误：参数值为空（推广位编号）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_EMPTY_ADZONEID.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_EMPTY_ADZONEID.getMsg());

            } else if (adZoneService.find(adZoneName)) {
                logger.warning("错误：推广位已存在（推广位名称）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_EXIST_ADZONENAME.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_EXIST_ADZONENAME.getMsg());

            } else if (adZoneService.find(adZoneId)) {
                logger.warning("错误：推广位已存在（推广位编号）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_EXIST_ADZONEID.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_EXIST_ADZONEID.getMsg());

            } else {
                adZoneService.add(adZoneName, Long.parseLong(adZoneId));
                logger.info("成功：推广位已增加");
                json.put("status", ServerErrorCode.BIN_OK.getCode());
                json.put("msg", ServerErrorCode.BIN_OK.getMsg());
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


    /**
     * 查询所有推广位
     * @param request Http请求
     * @param response Http响应
     */
    @RequestMapping(value = "/all")
    public void getAllAdzone(HttpServletRequest request, HttpServletResponse response) {

        logger.info("请求：" + request.getRequestURL().toString());
        logger.info("参数：" + request.getQueryString());

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            JSONArray jsonArray = new JSONArray();
            Iterable<AdZoneEntity> adZoneEntities = adZoneService.getAll();

            for (AdZoneEntity adZoneEntity : adZoneEntities) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("adzone_name", adZoneEntity.getAdzoneName());
                jsonObject.put("adzone_id", adZoneEntity.getAdzoneId());
                jsonObject.put("occupied", adZoneEntity.isOccupied());
                jsonObject.put("owner", adZoneEntity.getOwner() == null ? "" : adZoneEntity.getOwner());
                jsonArray.add(jsonObject);
            }

            out.print(jsonArray);
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
