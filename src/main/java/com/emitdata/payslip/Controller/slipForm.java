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
@RequestMapping("/pay/payslip")
public class slipForm {
    String mobile;
    Integer ids;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void charReader(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        获取post中的 body
        BufferedReader br = request.getReader();

        String str, wholeStr = "";
        String confirme = "0";

        while((str = br.readLine()) != null){
            wholeStr += str;
        }
//        处理成 json
        JSONObject jsonObjects = JSON.parseObject(wholeStr);
        String id = jsonObjects.getString("id");
        String date = jsonObjects.getString("date");
        String name = jsonObjects.getString("name");
        String phone = jsonObjects.getString("phone");
        String baseWage = jsonObjects.getString("baseWage");
        String post = jsonObjects.getString("post");
        String prize = jsonObjects.getString("prize");
        String Performance = jsonObjects.getString("Performance");
        String subsidize = jsonObjects.getString("subsidize");
        String absent = jsonObjects.getString("absent");
        String leave = jsonObjects.getString("leave");
        String late = jsonObjects.getString("late");
        String other = jsonObjects.getString("other");
        String add = jsonObjects.getString("add");
        String security = jsonObjects.getString("security");
        String Provident = jsonObjects.getString("Provident");
        String tax = jsonObjects.getString("tax");
        String others = jsonObjects.getString("others");
        String RealHair = jsonObjects.getString("RealHair");

        try {
            String sql = "SELECT id FROM peopleinfo WHERE date_user = ? and name_user = ?";
            // 通过jdbcTemplate查询数据库
            mobile = (String) jdbcTemplate.queryForObject(sql, new Object[]{date,name}, String.class);

            String sqls = "DELETE FROM  peopleinfo WHERE id = ? ";
            // 通过jdbcTemplate查询数据库
            ids = jdbcTemplate.update(sqls, mobile);

            jdbcTemplate.update("INSERT INTO confirmestart( id, date_user ,name_user,confirme) VALUES (?,?,?,?)",
                    mobile, date, name, confirme);

            jdbcTemplate.update("INSERT INTO peopleinfo( id, date_user, name_user, phone_user, baseWage_user, post_user, prize_user," +
                            " Performance_user, subsidize_user, absent_user, leave_user, late_user, other_user, add_user, security_user, Provident_user, tax_user, others_user, RealHair_user) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    mobile, date, name, phone, baseWage,post,prize,Performance,subsidize,absent,leave,late,other,add, security,Provident,tax,others,RealHair);


            // 通过jdbcTemplate查询数据库


            User user=new User();
            user.setError("0");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("start","插入成功");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());

        } catch (EmptyResultDataAccessException e) {
            jdbcTemplate.update("INSERT INTO confirmestart(  date_user ,name_user,confirme) VALUES (?,?,?)",
                     date, name, confirme);

            String sql = "SELECT id FROM confirmestart WHERE date_user = ? and name_user = ?";
            // 通过jdbcTemplate查询数据库
            mobile = (String) jdbcTemplate.queryForObject(sql, new Object[]{date,name}, String.class);

            jdbcTemplate.update("INSERT INTO peopleinfo( id, date_user, name_user, phone_user, baseWage_user, post_user, prize_user," +
                            " Performance_user, subsidize_user, absent_user, leave_user, late_user, other_user, add_user, security_user, Provident_user, tax_user, others_user, RealHair_user) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    mobile,date, name, phone, baseWage,post,prize,Performance,subsidize,absent,leave,late,other,add, security,Provident,tax,others,RealHair);
            User user=new User();
            user.setError("0");
            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
            jsonObject.put("start","插入成功");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }
}