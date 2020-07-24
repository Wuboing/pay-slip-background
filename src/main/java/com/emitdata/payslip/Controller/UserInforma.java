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
@RequestMapping("/people")
public class UserInforma {
    String mobile;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void charReader(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        获取post中的 body
        BufferedReader br = request.getReader();

        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }
//        处理成 json
        JSONObject jsonObjects = JSON.parseObject(wholeStr);
        String message = jsonObjects.getString("data");
        JSONObject list = JSON.parseObject(message);
        String names = list.getString("name");
        String nums = list.getString("num");
        String phones = list.getString("phone");
        String offices = list.getString("office");
        String regions = list.getString("region");

        try {
            // 通过jdbcTemplate查询数据库
            jdbcTemplate.update("INSERT INTO user_people( name, num, phone, position, start) VALUES (?,?,?,?,?)",
                    names, nums, phones,offices,regions);

            User user=new User();
            user.setError("0");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("start","插入成功");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());

        } catch (EmptyResultDataAccessException e) {
            User user=new User();
            user.setError("1");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("start","插入失败");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }
}


