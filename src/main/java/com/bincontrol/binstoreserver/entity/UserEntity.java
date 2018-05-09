package com.bincontrol.binstoreserver.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_user")
public class UserEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 用户帐号
     */
    @NotNull
    @Column(name = "account")
    private String account;

    /**
     * 用户密码
     */
    @NotNull
    @Column(name = "password")
    private String password;

    /**
     * 用户专有的推广位，注册时系统分配
     */
    @NotNull
    @Column(name = "adzoneId")
    private Long adzoneId;

    /**
     * 邀请码，暨用户邀请人的推广位
     */
    @Column(name = "inviteCode")
    private Long inviteCode;

    /**
     * 用户积分
     */
    @Column(name = "integral")
    private Long integral;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAdzoneId() {
        return adzoneId;
    }

    public void setAdzoneId(Long adzoneId) {
        this.adzoneId = adzoneId;
    }

    public Long getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(Long inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

}
