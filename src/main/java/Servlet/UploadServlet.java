package Servlet;

import Mapper.FileRecordMapper;
import Pojo.filerecord;
import Utils.MybatisUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UploadServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String savePath = this.getServletContext().getRealPath("/file");
        /**
         * 创建年月日命名的文件夹
         */
        File maindir = new File(savePath);
        if (!maindir.exists()) maindir.mkdir();
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String dirname = savePath + "\\" + ft.format(date) + "\\";
        File dir = new File(dirname);
        System.out.println(dirname);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String msg = "";
        try {
            //创建一个disk工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");

            //使用
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                if (item.isFormField()) {  //是普通表单
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                } else {  //是文件
                    String filename = item.getName();
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    /**
                     * 有的浏览器上传的文件是带路径的，将路径处理掉
                     * type是定位最后一个.之后的字符
                     */
                    String type = filename.substring(filename.lastIndexOf("."));
                    /**
                     * 利用UUID生成文件名，生成的UUID把所有-删除
                     */
                    filename = UUID.randomUUID().toString().replaceAll("-", "");
                    InputStream is = item.getInputStream();
                    FileOutputStream fos = new FileOutputStream(dirname + filename + type);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = is.read(bytes)) != -1) {
                        fos.write(bytes, 0, len);
                    }
                    is.close();
                    fos.close();
                    item.delete();
                    ServletOutputStream sos = response.getOutputStream();
                    sos.print(filename + type);
                    /**
                     * 上传成功之后将记录插入数据库
                     */
                    SqlSession sqlSession = MybatisUtils.getSqlSession();
                    FileRecordMapper mapper = sqlSession.getMapper(FileRecordMapper.class);
                    //插入数据库带具体时间
                    SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    filerecord rc = new filerecord();
                    rc.setTime(tf.format(date));
                    rc.setFile_size(item.getSize());
                    rc.setFile_type(type);
                    rc.setFile_pre_name(item.getName());
                    rc.setFile_new_name(filename + type);
                    rc.setFile_dir(dirname);
                    mapper.insertRecord(rc);
                    sqlSession.commit();
                    sqlSession.close();
                }
            }
        } catch (Exception e) {
            msg = "文件上传失败！";
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
