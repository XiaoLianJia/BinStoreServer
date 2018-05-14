package com.bincontrol.binstoreserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bincontrol.binstoreserver.common.ServerErrorCode;
import com.bincontrol.binstoreserver.entity.UserEntity;
import com.bincontrol.binstoreserver.service.AdZoneService;
import com.bincontrol.binstoreserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Logger;

import static com.bincontrol.binstoreserver.common.ServerConstant.REQUEST_PARAM_ACCOUNT;
import static com.bincontrol.binstoreserver.common.ServerConstant.REQUEST_PARAM_INVITECODE;
import static com.bincontrol.binstoreserver.common.ServerConstant.REQUEST_PARAM_PASSWORD;

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
    public void register(HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.info("请求：" + request.getRequestURL().toString());
        logger.info("参数：" + request.getQueryString());

        String account = null;
        String password = null;
        String inviteCode = null;

        if (request.getMethod().equals("POST")) {
            InputStreamReader inputStreamReader = new InputStreamReader(request.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();
            JSONObject jsonObject = JSON.parseObject(result);

            account = jsonObject.getString(REQUEST_PARAM_ACCOUNT);
            password = jsonObject.getString(REQUEST_PARAM_PASSWORD);
            inviteCode = jsonObject.getString(REQUEST_PARAM_INVITECODE);

        } else if (request.getQueryString() != null) {
            account = request.getParameter(REQUEST_PARAM_ACCOUNT);
            password = request.getParameter(REQUEST_PARAM_PASSWORD);
            inviteCode = request.getParameter(REQUEST_PARAM_INVITECODE);
        }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();

            if (account == null) {
                logger.warning("错误：缺少必要参数（帐号）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_MISSING_ACCOUNT.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_MISSING_ACCOUNT.getMsg());

            } else if (password == null) {
                logger.warning("错误：缺少必要参数（密码）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_MISSING_PASSWORD.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_MISSING_PASSWORD.getMsg());

            } else if (account.isEmpty()) {
                logger.warning("错误：参数值为空（帐号）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_EMPTY_ACCOUNT.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_EMPTY_ACCOUNT.getMsg());

            } else if (password.isEmpty()){
                logger.warning("错误：参数值为空（密码）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_EMPTY_PASSWORD.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_EMPTY_PASSWORD.getMsg());

            } else if (userService.find(account)) {
                logger.warning("错误：帐号已存在");
                json.put("status", ServerErrorCode.BIN_ERR_USER_ACCOUNT_EXIST.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_USER_ACCOUNT_EXIST.getMsg());

            } else if (inviteCode != null && adZoneService.find(inviteCode)) {
                logger.warning("错误：参数无效（邀请码）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_INVALID_INVITECODE.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_INVALID_INVITECODE.getMsg());

            } else {
                Long adZoneId = adZoneService.getFreeAdZoneId(account, true);
                if (adZoneId == null) {
                    logger.severe("错误：无空闲推广位");
                    json.put("status", ServerErrorCode.BIN_ERR_NO_FREE_ADZONE.getCode());
                    json.put("msg", ServerErrorCode.BIN_ERR_NO_FREE_ADZONE.getMsg());

                } else {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setAccount(account);
                    userEntity.setPassword(password);
                    userEntity.setIntegral(0L);
                    userEntity.setInviteCode(inviteCode != null ? Long.parseLong(inviteCode) : null);
                    userEntity.setAdzoneId(adZoneId);
                    userService.save(userEntity);

                    logger.info("成功：用户[" + account + "]完成注册");
                    json.put("status", ServerErrorCode.BIN_OK.getCode());
                    json.put("msg", ServerErrorCode.BIN_OK.getMsg());
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
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.info("请求：" + request.getRequestURL().toString());
        logger.info("参数：" + request.getQueryString());

        String account = null;
        String password = null;

        if (request.getMethod().equals("POST")) {
            InputStreamReader inputStreamReader = new InputStreamReader(request.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();
            JSONObject jsonObject = JSON.parseObject(result);

            account = jsonObject.getString(REQUEST_PARAM_ACCOUNT);
            password = jsonObject.getString(REQUEST_PARAM_PASSWORD);

        } else if (request.getQueryString() != null) {
            account = request.getParameter(REQUEST_PARAM_ACCOUNT);
            password = request.getParameter(REQUEST_PARAM_PASSWORD);
        }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();

            if (account == null) {
                logger.warning("错误：缺少必要参数（帐号）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_MISSING_ACCOUNT.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_MISSING_ACCOUNT.getMsg());

            } else if (password == null) {
                logger.warning("错误：缺少必要参数（密码）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_MISSING_PASSWORD.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_MISSING_PASSWORD.getMsg());

            } else if (account.isEmpty()) {
                logger.warning("错误：参数值为空（帐号）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_EMPTY_ACCOUNT.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_EMPTY_ACCOUNT.getMsg());

            } else if (password.isEmpty()){
                logger.warning("错误：参数值为空（密码）");
                json.put("status", ServerErrorCode.BIN_ERR_PARAM_EMPTY_PASSWORD.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_PARAM_EMPTY_PASSWORD.getMsg());

            } else if (!userService.find(account)) {
                logger.severe("错误：帐号不存在");
                json.put("status", ServerErrorCode.BIN_ERR_USER_ACCOUNT_NOT_EXIST.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_USER_ACCOUNT_NOT_EXIST.getMsg());

            } else if (!userService.find(account, password)) {
                logger.severe("错误：密码错误");
                json.put("status", ServerErrorCode.BIN_ERR_USER_PASSWORD_MISMATCH.getCode());
                json.put("msg", ServerErrorCode.BIN_ERR_USER_PASSWORD_MISMATCH.getMsg());

            } else {
                logger.info("成功：用户[" + account + "]完成登录");
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
     * 查询所有用户
     * @param request Http请求
     * @param response Http响应
     */
    @RequestMapping(value = "/all")
    public void getAllUser(HttpServletRequest request, HttpServletResponse response) {

        logger.info("请求：" + request.getRequestURL().toString());
        logger.info("参数：" + request.getQueryString());

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            JSONArray jsonArray = new JSONArray();
            Iterable<UserEntity> userEntities = userService.getAll();

            for (UserEntity userEntity : userEntities) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("account", userEntity.getAccount());
                jsonObject.put("adzone_id", userEntity.getAdzoneId());
                jsonObject.put("invite_code", userEntity.getInviteCode());
                jsonObject.put("integral", userEntity.getIntegral());
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
