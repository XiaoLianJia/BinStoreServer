package com.bincontrol.binstoreserver.controller;

import com.alibaba.fastjson.JSONObject;
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

            if (adZoneName == null || adZoneId == null) {
                logger.warning("错误：缺少必要参数");
                json.put("status", "201");
                json.put("msg", "ERROR");

            } else if (adZoneName.isEmpty() || adZoneId.isEmpty()) {
                logger.warning("错误：参数值为空");
                json.put("status", "202");
                json.put("msg", "ERROR");

            } else if (adZoneService.find(adZoneName) || adZoneService.find(adZoneId)) {
                logger.warning("错误：推广位已存在");
                json.put("status", "203");
                json.put("msg", "ERROR");

            } else {
                adZoneService.add(adZoneName, Long.parseLong(adZoneId));
                logger.info("成功：推广位已增加");
                json.put("status", "200");
                json.put("msg", "OK");
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
