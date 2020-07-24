package com.emitdata.payslip.Controller;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/wxapp")
public class wxPCmanage {
    String mobile;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/list", method= RequestMethod.POST)
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            //############################################################

            List list = new ArrayList();
            String sql="select * from staff_login ";

            Object[] args ={};
            list = jdbcTemplate.queryForList(sql,args);

//############################################################
            User user=new User();
            user.setError("0");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("list",list);

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