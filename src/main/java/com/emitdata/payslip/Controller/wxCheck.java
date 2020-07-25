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
@RequestMapping("/pay/wxinfo")
public class wxCheck {
    String mobile;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/check", method= RequestMethod.POST)
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String date = request.getParameter("date");
        try {
            List list = new ArrayList();
//            String sql="select * from peopleinfo LEFT JOIN confirmestart ON peopleinfo.id=confirmestart.id UNION ALL SELECT * FROM peopleinfo RIGHT JOIN confirmestart ON peopleinfo.id=confirmestart.id WHERE peopleinfo.id IS NULL";
//            String sql ="UPDATE confirmestart SET confirme='1' WHERE name_user = "+ "'"+ name+ "'";
//            Object[] args ={};
//            list = jdbcTemplate.queryForList(sql,args);
            jdbcTemplate.update("UPDATE confirmestart SET confirme = ? WHERE name_user = ? AND date_user = ?", new Object[] {1, name,date});

            User user=new User();
            user.setError("0");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
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