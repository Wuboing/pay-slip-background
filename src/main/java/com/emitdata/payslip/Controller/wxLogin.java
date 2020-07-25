package com.emitdata.payslip.Controller;

import com.alibaba.fastjson.JSONObject;
import com.emitdata.payslip.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/pay/wxlogin")
public class wxLogin {
    String mobile;
    String mobiles;
    String mobilees;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void charReader(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String start = request.getParameter("start");

        String account = request.getParameter("account");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        try {
            if ("0".toString().equals(start)){
                String sql = "SELECT uphone FROM staff_login WHERE uaccount = ?  and upassword = ?";
                mobile = (String) jdbcTemplate.queryForObject(sql, new Object[]{account,password}, String.class);
                String sqls = "SELECT uname FROM staff_login WHERE uaccount = ?  and upassword = ?";
                mobilees = (String) jdbcTemplate.queryForObject(sqls, new Object[]{account,password}, String.class);
            }else {
                String sql = "SELECT uphone FROM staff_login WHERE uphone = ? and upassword = ?";
                mobile = (String) jdbcTemplate.queryForObject(sql, new Object[]{phone,password}, String.class);
                String sqls = "SELECT uname FROM staff_login WHERE uaccount = ?  and upassword = ?";
                mobilees = (String) jdbcTemplate.queryForObject(sqls, new Object[]{account,password}, String.class);
            }

            User user=new User();
            user.setError("0");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("start","登陆成功");
            jsonObject.put("phone",mobile);
            jsonObject.put("name",mobilees);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());

        } catch (EmptyResultDataAccessException e) {
            User user=new User();
            user.setError("1");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("start","登陆失败");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }
}