package com.emitdata.payslip.Dao;

import com.emitdata.payslip.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Array;

public class Userdao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    public String  getList(){
        String sql = "SELECT id FROM user_login WHERE username = ? and passworad = ?";
        // 通过jdbcTemplate查询数据库

        String mobile = (String)jdbcTemplate.queryForObject(sql, new Object[] { "admin" ,"123456" }, String.class);
        return mobile;
    }
}
