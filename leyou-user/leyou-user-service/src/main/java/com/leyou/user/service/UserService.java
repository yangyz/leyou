package com.leyou.user.service;

/**
 * @Author: 98050
 * @Time: 2018-10-21 18:41
 * @Feature:
 */
public interface UserService {
    /**
     * 检查用户名和手机号是否可用
     * @param data
     * @param type
     * @return
     */
    Boolean checkData(String data, Integer type);

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    Boolean sendVerifyCode(String phone);
}
