package com.emitdata.payslip.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emitdata.payslip.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


@Controller
@RequestMapping("/pay/register")
public class wxregister {
    String mobile;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public void charReader(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String account = request.getParameter("account");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        try {
            String sql = "SELECT uphone FROM staff_login WHERE uphone = ? ";
            // 通过jdbcTemplate查询数据库
            mobile = (String) jdbcTemplate.queryForObject(sql, new Object[]{phone}, String.class);
            // 通过jdbcTemplate查询数据库


            User user=new User();
            user.setError("1");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("start","此账号已经申请，请联系管理员进行重置");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());

        } catch (EmptyResultDataAccessException e) {
            jdbcTemplate.update("INSERT INTO staff_login( uaccount, uname, uphone, upassword) VALUES (?,?,?,?)",
                    account, name, phone,password);
            User user=new User();
            user.setError("0");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("start","注册成功");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }
}
