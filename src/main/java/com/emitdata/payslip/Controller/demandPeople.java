package com.emitdata.payslip.Controller;

import com.alibaba.fastjson.JSONObject;
import com.emitdata.payslip.domain.People;
import com.emitdata.payslip.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/pay/people")
public class demandPeople {
    String mobile;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/list", method= RequestMethod.POST)
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        String username = request.getParameter("id");
//        String password = request.getParameter("password");
        try {
            //############################################################

            List list = new ArrayList();
            String sql="select * from user_people";

            Object[] args ={};
            list = jdbcTemplate.queryForList(sql,args);

//############################################################
            User user=new User();
            user.setError("0");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("list",list);

//            Map<String,String> map=new HashMap<>();
//            map.put("phone","1223456");
//            map.put("status","ok");
//            jsonObject.put("info",map);
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

//    class MyRowMapper implements RowMapper<People> {
//
//        @Override
//        public People mapRow(ResultSet rs, int num) throws SQLException {
//            //从结果集里把数据得到
//            String id=rs.getString("id");
//            String name=rs.getString("name");
//            //把数据封装到对象里
//            People people=new People();
//            people.setId(id);
//            people.setName(name);
//            return people;
//        }
//    }
}