package com.cg.proxyim.Controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cg.proxyim.Controller.DTO.UserDTO;
import com.cg.proxyim.Entity.User;
import com.cg.proxyim.Utils.TokenUtils;
import com.cg.proxyim.common.Constants;
import com.cg.proxyim.common.Result;
import com.cg.proxyim.service.IUserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 青哥哥
 * @since 2022-01-26
 */
@RestController
@RequestMapping("/")
public class UserController {

//    @Value("${files.upload.path}")
//    private String filesUploadPath;

    @Resource
    private IUserService userService;

    @PostMapping("/api/admin/login")
    @ResponseBody
    public Result login(@RequestParam LinkedHashMap<String, String> params) {
        UserDTO userDTO = JSON.parseObject(JSON.toJSONString(params), new TypeReference<UserDTO>() { });
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
//        UserDTO dto = userService.login(userDTO);

        userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
        User one = userService.getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            // 设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
//            userDTO.setToken(token);

//            String role = one.getRole(); // ROLE_ADMIN
            // 设置用户的菜单列表
//            List<Menu> roleMenus = getRoleMenus(role);
//            userDTO.setMenus(roleMenus);
            userDTO.setPassword("");
            return Result.success(userDTO);
        } else {
            return Result.error(400,"用户名或密码错误");
//            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
//        return Result.success(dto);
    }

    @DeleteMapping("/api/admin/delete")
    public Result deleteUser(@RequestParam(value="user_name" ,required =true ) String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        boolean remove = userService.remove(queryWrapper);
        if (remove) {
            return Result.success();
        } else {
            return Result.error(500,"删除失败，没有这个用户");
        }

    }

    @GetMapping("/api/admin/show-all-account")
    @ResponseBody
    public Result fetchOtnerAccount(@RequestParam(value="admin_name" ,required =true ) String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("username", username);
        List<User> userList = userService.list(queryWrapper);
        if (!userList.isEmpty()) {
            return Result.success(userList);
        } else {
            return Result.error(500,"查找失败，没有其它管理员");
        }

    }

    @PostMapping("/api/admin/create")
    @ResponseBody
    public Result register(@RequestParam LinkedHashMap<String, String> params) {
        UserDTO userDTO = JSON.parseObject(JSON.toJSONString(params), new TypeReference<UserDTO>() { });
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "用户名或密码不能为空");
        }

        User one = userService.getUserInfo(userDTO);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            // 默认一个普通用户的角色
//            one.setRole(RoleEnum.ROLE_STUDENT.toString());
//            if (one.getNickname() == null) {
//                one.setNickname(one.getUsername());
//            }
            save(one);  // 把 copy完之后的用户对象存储到数据库
        } else {
            return Result.error(400, "用户已存在");
//            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return Result.success(true);
//        return Result.success(userService.register(userDTO));
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody User user) {
        if (user.getId() == null && user.getPassword() == null) {  // 新增用户默认密码
            user.setPassword( SecureUtil.md5("123"));
        }
        return Result.success(userService.saveOrUpdate(user));
    }

//    @PostMapping("/password")
//    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
//        userPasswordDTO.setPassword(SecureUtil.md5(userPasswordDTO.getPassword()));
//        userPasswordDTO.setNewPassword(SecureUtil.md5(userPasswordDTO.getNewPassword()));
//        userService.updatePassword(userPasswordDTO);
//        return Result.success();
//    }

//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        return Result.success(userService.removeById(id));
//    }
//
//    @PostMapping("/del/batch")
//    public Result deleteBatch(@RequestBody List<Integer> ids) {
//        return Result.success(userService.removeByIds(ids));
//    }

    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

//    @GetMapping("/role/{role}")
//    public Result findUsersByRole(@PathVariable String role) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("role", role);
//        List<User> list = userService.list(queryWrapper);
//        return Result.success(list);
//    }
//
//    @GetMapping("/{id}")
//    public Result findOne(@PathVariable Integer id) {
//        return Result.success(userService.getById(id));
//    }
//
//    @GetMapping("/username/{username}")
//    public Result findByUsername(@PathVariable String username) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", username);
//        return Result.success(userService.getOne(queryWrapper));
//    }




}

