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

@Controller
@RequestMapping("/people")
public class delPeople {
    Integer mobile;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/del", method= RequestMethod.POST)
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String ids = request.getParameter("id");
        System.out.println(ids);
        try {
            String sql = "DELETE FROM  user_people WHERE id = ? ";
            // 通过jdbcTemplate查询数据库
            mobile = jdbcTemplate.update(sql, ids);

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
