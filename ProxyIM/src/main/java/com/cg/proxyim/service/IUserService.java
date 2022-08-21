package com.cg.proxyim.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cg.proxyim.Controller.DTO.UserDTO;
import com.cg.proxyim.Entity.User;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 青哥哥
 * @since 2022-01-26
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

//    void updatePassword(UserPasswordDTO userPasswordDTO);
//
//    Page<User> findPage(Page<User> objectPage, String username, String email, String address);

    User getUserInfo(UserDTO userDTO);
}
