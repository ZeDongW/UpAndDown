package cn.zedongw.upanddown;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author ZeDongW
 * @ClassName UDServlet
 * @Description java上传与下载
 * @Version 1.0
 * @date ：Created in 2019/7/17 7:35
 * @modified By：
 */

public class UDServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求参数
        String method = req.getParameter("method");

        if ("upload".equals(method)){
            //上传
            upload(req, resp);
        } else if ("downList".equals(method)){
            //下载列表
            downList(req, resp);
        } else if ("down".equals(method)){
            //下载
            down(req, resp);
        }
    }

    /**
     * @Author: ZeDongW
     * @Description: 根据文件名下载文件
     * @Date: 2019/7/19/0019 9:28
     * @Param: [req, resp]
     * @return: void
     */
    private void down(HttpServletRequest req, HttpServletResponse resp){
        //获取文件名
        String fileName = req.getParameter("fileName");

        //设置下载响应头
        resp.setHeader("content-disposition","attachment;fileName=" + fileName);

        //获取输入流
        FileInputStream fileInputStream = null;

        //获取输出流
        ServletOutputStream outputStream = null;

        try {
            //文件路径
            String path = UDServlet.class.getClassLoader().getResource("/upload/").getPath();

            fileInputStream = new FileInputStream(new File(path, fileName));

            outputStream = resp.getOutputStream();

            //缓冲数组
            byte[] bytes = new byte[1024*8];

            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (fileInputStream != null){
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    /**
     * @Author: ZeDongW
     * @Description: 文件下载列表
     * @Date: 2019/7/17 8:25
     * @Param: [req, resp]
     * @return: void
     */
    private void downList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建文件下载Map
        Map<String, String> fileNames = new HashMap<>();

        //文件路径
        String path = UDServlet.class.getClassLoader().getResource("/upload/").getPath();

        //获取目录下所有文件名
        String[] list = new File(path).list();

        //将所有文件封装到Map中
        if (list != null && list.length > 0){
            for (int i = 0; i < list.length; i++){
                //获取文件全名
                String name = list[i];

                //获取文件名
                String sortName = name.substring(36);
                fileNames.put(name, sortName);
            }
        }
        req.setAttribute("fileNames",fileNames);

        req.getRequestDispatcher("downlist.jsp").forward(req, resp);
    }

    /**
     * @Author: ZeDongW
     * @Description: 文件上传
     * @Date: 2019/7/17 7:55
     * @Param: [req, resp]
     * @return: void
     */
    private void upload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //创建工厂对象
        DiskFileItemFactory fac = new DiskFileItemFactory();

        //文件上传核心工具类
        ServletFileUpload upload = new ServletFileUpload(fac);

        //设置文件上传参数
        upload.setFileSizeMax(50*1024*1024); //单个文件上传最大50M
        upload.setSizeMax(100*1024*1024);  //总上传文件大小100M
        upload.setHeaderEncoding("UTF-8"); //对上传文件中文编码处理

        //判断是否为文件上传表单
        if (upload.isMultipartContent(req)){  //文件上传表单
            List<FileItem> fileItems;
            try {
                //将请求数据转换为list集合
                fileItems = upload.parseRequest(req);
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            }
            fileItems.forEach(item ->{

                    if (!item.isFormField()){ //文件上传
                        //获取文件名称
                        String name = item.getName();

                        //获取UUID
                        String id = UUID.randomUUID().toString();

                        //拼接唯一文件名
                        name = id + name;

                        //文件路径
                        String path = UDServlet.class.getClassLoader().getResource("/upload/").getPath();

                        //需要上传的文件对象
                        File file = new File(path, name);

                        //上传
                        try {
                            item.write(file);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        //删除临时文件
                        item.delete();
                    }
                }
            );

        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
