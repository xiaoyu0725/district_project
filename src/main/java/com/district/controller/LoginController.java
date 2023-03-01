package com.district.controller;

import com.alibaba.fastjson.JSONObject;
import com.district.bean.TblUserRecord;
import com.district.returnJson.Permission;
import com.district.returnJson.Permissions;
import com.district.returnJson.ReturnObject;
import com.district.returnJson.UserInfo;
import com.district.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*",allowedHeaders = "*",methods = {},allowCredentials = "true")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/auth/2step-code")
    public boolean test() {
        System.out.println("前端框架自带的一个验证规则，写不写无所谓");
        return true;
    }

    @RequestMapping("/auth/login")
    public String  login(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("login");
        TblUserRecord tblUserRecord = loginService.login(username, password);
        tblUserRecord.setToken(tblUserRecord.getUserName());
        //将用户数据写入到session中
        session.setAttribute("userRecord",tblUserRecord);
        System.out.println(session.getId());
        ReturnObject returnObject = new ReturnObject(tblUserRecord);
        return JSONObject.toJSONString(returnObject);
    }

    @RequestMapping("/user/info")
    public String getInfo(HttpSession session) {
        //获取用户数据
        TblUserRecord userRecord = (TblUserRecord) session.getAttribute("userRecord");
        //获取对应用户需要账务的功能模块
        String[] rolePrivileges = userRecord.getTblRole().getRolePrivileges().split("-");
        // 拼接需要返回的数据对象的格式
        Permissions permissions = new Permissions();
        List<Permission> permissionList = new ArrayList<>();
        for (String rolePrivilege : rolePrivileges) {
            permissionList.add(new Permission(rolePrivilege));
        }

        permissions.setPermissions(permissionList);
        UserInfo userInfo = new UserInfo(userRecord.getUserName(),permissions);
        return JSONObject.toJSONString(new ReturnObject(userInfo));
    }

    @RequestMapping("/auth/logout")
    public void logOut(HttpSession session) {
        System.out.println("logout");
        session.invalidate();
    }

}
