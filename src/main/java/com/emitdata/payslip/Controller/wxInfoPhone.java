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
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pay/wxlist")
public class wxInfoPhone {
    String mobile;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/info", method= RequestMethod.POST)
    public void test(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String phone = request.getParameter("phone");
        try {
            List list = new ArrayList();
            List lists = new ArrayList();
//            String sql="select * from peopleinfo LEFT JOIN confirmestart ON peopleinfo.id=confirmestart.id UNION ALL SELECT * FROM peopleinfo RIGHT JOIN confirmestart ON peopleinfo.id=confirmestart.id WHERE peopleinfo.id IS NULL";
            String sql="select * from peopleinfo  LEFT JOIN confirmestart ON peopleinfo.id=confirmestart.id WHERE phone_user =" + phone;

            Object[] args ={};
            list = jdbcTemplate.queryForList(sql,args);



            User user=new User();
            user.setError("0");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("list",list);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());

        } catch (EmptyResultDataAccessException e) {
            System.out.println(mobile+"cscss");
            User user=new User();
            user.setError("1");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }

    }
}