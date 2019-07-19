package cn.zedongw.myupload;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author ZeDongW
 * @ClassName MyUploadServlet
 * @Description 自定义上传
 * @Version 1.0
 * @date ：Created in 2019/7/16 22:53
 * @modified By：
 */

public class MyUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取IO
        ServletInputStream inputStream = req.getInputStream();
        /*//缓冲读取
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] bytes = new byte[2];
        int len = 0;
        StringBuilder sb = new StringBuilder();
        while ((len = bufferedInputStream.read(bytes)) != -1){
            sb.append(new String(bytes));
        }
        System.out.println(sb.toString());*/
        //转换流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        //缓冲数组
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String str =null;
        while ((str = bufferedReader.readLine()) != null){
            System.out.println(str);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
