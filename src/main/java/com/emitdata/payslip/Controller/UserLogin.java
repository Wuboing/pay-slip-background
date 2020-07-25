package com.emitdata.payslip.Controller;

import com.alibaba.fastjson.JSONObject;
import com.emitdata.payslip.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/pay/login")
public class UserLogin {
    String mobile;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/test", method= RequestMethod.POST)
    public void test(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            String sql = "SELECT id FROM user_login WHERE username = ? and password = ?";
            // 通过jdbcTemplate查询数据库
            mobile = (String) jdbcTemplate.queryForObject(sql, new Object[]{username,password}, String.class);

            String sql2 = "SELECT nickname FROM userinfo WHERE id = ?";
            String mobile2 = (String) jdbcTemplate.queryForObject(sql2, new Object[]{ mobile }, String.class);

            User user=new User();
            user.setPassword(username);
            user.setError("0");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("id",mobile);
//            Map<String,String> map=new HashMap<>();
//            map.put("phone","1223456");
//            map.put("status","ok");
//            jsonObject.put("info",map);
            jsonObject.put("userid",mobile2);
            jsonObject.put("token","token36s21054sdwf");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());

        } catch (EmptyResultDataAccessException e) {
            User user=new User();
            user.setError("1");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }

    }
}
