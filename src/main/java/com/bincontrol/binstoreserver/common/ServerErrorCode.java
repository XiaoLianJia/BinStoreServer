package com.bincontrol.binstoreserver.common;

public enum ServerErrorCode {

    BIN_OK(200, "OK"),

    BIN_ERR_PARAM_MISSING_ACCOUNT(201, "ERROR: 参数缺失（用户帐号）"),
    BIN_ERR_PARAM_MISSING_PASSWORD(202, "ERROR: 参数缺失（用户密码）"),
    BIN_ERR_PARAM_MISSING_ADZONENAME(203, "ERROR: 参数缺失（推广位名称）"),
    BIN_ERR_PARAM_MISSING_ADZONEID(204, "ERROR: 参数缺失（推广位编号）"),
    BIN_ERR_PARAM_MISSING_CATEGORY(205, "ERROR: 参数缺失（商品类目）"),

    BIN_ERR_PARAM_EMPTY_ACCOUNT(301, "ERROR: 参数值为空（用户帐号）"),
    BIN_ERR_PARAM_EMPTY_PASSWORD(302, "ERROR: 参数值为空（用户密码）"),
    BIN_ERR_PARAM_EMPTY_ADZONENAME(303, "ERROR: 参数值为空（推广位名称）"),
    BIN_ERR_PARAM_EMPTY_ADZONEID(304, "ERROR: 参数值为空（推广位编号）"),
    BIN_ERR_PARAM_EMPTY_CATEGORY(305, "ERROR: 参数值为空（商品类目）"),

    BIN_ERR_PARAM_INVALID_ACCOUNT(401, "ERROR: 参数无效（用户帐号）"),
    BIN_ERR_PARAM_INVALID_PASSWORD(402, "ERROR: 参数无效（用户密码）"),
    BIN_ERR_PARAM_INVALID_ADZONENAME(403, "ERROR: 参数无效（推广位名称）"),
    BIN_ERR_PARAM_INVALID_ADZONEID(404, "ERROR: 参数无效（推广位编号）"),
    BIN_ERR_PARAM_INVALID_INVITECODE(405, "ERROR: 参数无效（邀请码）"),

    BIN_ERR_PARAM_EXIST_ADZONENAME(501, "ERROR: 推广位名称已存在"),
    BIN_ERR_PARAM_EXIST_ADZONEID(502, "ERROR: 推广位编号已存在"),
    BIN_ERR_NO_FREE_ADZONE(503, "ERROR: 无空闲推广位"),

    BIN_ERR_USER_ACCOUNT_EXIST(601, "ERROR：用户帐号已存在"),
    BIN_ERR_USER_ACCOUNT_NOT_EXIST(602, "ERROR：用户帐号不存在"),
    BIN_ERR_USER_PASSWORD_MISMATCH(603, "ERROR: 密码错误");


    private int code;
    private String msg;

    ServerErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

}
