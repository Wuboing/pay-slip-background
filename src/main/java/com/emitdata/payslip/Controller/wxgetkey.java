package com.emitdata.payslip.Controller;

import com.alibaba.fastjson.JSONObject;
import com.emitdata.payslip.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class wxgetkey {
    @Value("${weixin.app_id}") // spring配置文件配置了appID
    public String appId;

    @Value("${weixin.app_secret}") // spring配置文件配置了secret
    public String secret;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/code", method= RequestMethod.POST)
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String code = request.getParameter("code");

        String appId = "wxb14413a2903531d6";
        String secret = "155bd3d9a1d0a3eb64d212de319d8f28";
        String s=httpRequest("https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code","GET",null);
        System.out.println(s);
        User user=new User();
        user.setError("0");
        JSONObject jsonObject= (JSONObject) JSONObject.toJSON(user);
        jsonObject.put("list",s);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());

    }

    public static String httpRequest(String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer=null;
        try{
            URL url=new URL(requestUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(requestMethod);
            conn.connect();
            //往服务器端写内容 也就是发起http请求需要带的参数
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }
//    public static void main(String[] args){
//        String appId = "wxb14413a2903531d6";
//        String secret = "155bd3d9a1d0a3eb64d212de319d8f28";
//        String s=httpRequest("https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+secret+"&js_code=081038Pe0OESdv192QNe0aacPe0038Ps&grant_type=authorization_code","GET",null);
//        System.out.println(s);
//    }
}
