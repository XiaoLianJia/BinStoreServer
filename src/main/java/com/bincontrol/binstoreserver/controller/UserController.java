package com.bincontrol.binstoreserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.bincontrol.binstoreserver.entity.UserEntity;
import com.bincontrol.binstoreserver.service.AdZoneService;
import com.bincontrol.binstoreserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AdZoneService adZoneService;


    /**
     * 用户注册
     * @param request Http请求
     * @param response Http响应
     */
    @RequestMapping(value = "/register")
    public void register(HttpServletRequest request, HttpServletResponse response){

        logger.info("请求：" + request.getRequestURL().toString());
        logger.info("参数：" + request.getQueryString());

        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String inviteCode = request.getParameter("invitecode");

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();

            if (account == null || password == null || password2 == null) {
                logger.warning("错误：缺少必要参数");
                json.put("status", "201");
                json.put("msg", "ERROR");

            } else if (account.isEmpty()) {
                logger.warning("错误：参数值（帐号）为空");
                json.put("status", "202");
                json.put("msg", "ERROR");

            } else if (password.isEmpty()) {
                logger.warning("错误：参数值（密码）为空");
                json.put("status", "203");
                json.put("msg", "ERROR");

            } else if (password2.isEmpty()) {
                logger.warning("错误：参数值（密码2）为空");
                json.put("status", "204");
                json.put("msg", "ERROR");

            } else if (!password.equals(password2)) {
                logger.warning("错误：两次输入的密码不一致");
                json.put("status", "205");
                json.put("msg", "ERROR");

            } else if (userService.find(account)) {
                logger.warning("错误：帐号已存在");
                json.put("status", "206");
                json.put("msg", "ERROR");

            } else if (inviteCode != null && adZoneService.find(inviteCode)) {
                logger.warning("错误：邀请码无效");
                json.put("status", "207");
                json.put("msg", "ERROR");

            } else {
                Long adZoneId = adZoneService.getFreeAdZoneId(account, true);
                if (adZoneId == null) {
                    logger.severe("错误：无可用推广位");
                    json.put("status", "208");
                    json.put("msg", "ERROR");

                } else {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setAccount(account);
                    userEntity.setPassword(password);
                    userEntity.setIntegral(0L);
                    userEntity.setInviteCode(inviteCode != null ? Long.parseLong(inviteCode) : null);
                    userEntity.setAdzoneId(adZoneId);
                    userService.save(userEntity);

                    logger.info("成功：用户[" + account + "]完成注册");
                    json.put("status", "200");
                    json.put("msg", "OK");
                }
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
     * 用户登录
     * @param request Http请求
     * @param response Http响应
     */
    @RequestMapping(path="/login")
    public void login(HttpServletRequest request, HttpServletResponse response) {

        logger.info("请求：" + request.getRequestURL().toString());
        logger.info("参数：" + request.getQueryString());

        String account = request.getParameter("account");
        String password = request.getParameter("password");

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();

            if (account == null || password == null) {
                logger.warning("错误：缺少必要参数");
                json.put("status", "201");
                json.put("msg", "ERROR");

            } else if (account.isEmpty() || password.isEmpty()) {
                logger.warning("错误：参数值为空");
                json.put("status", "202");
                json.put("msg", "ERROR");

            } else if (!userService.find(account)) {
                logger.severe("错误：帐号不存在");
                json.put("status", "203");
                json.put("msg", "ERROR");

            } else if (!userService.find(account, password)) {
                logger.severe("错误：密码错误");
                json.put("status", "204");
                json.put("msg", "ERROR");

            } else {
                logger.info("成功：用户[" + account + "]完成登录");
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
