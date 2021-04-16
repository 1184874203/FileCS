package Servlet;

import Mapper.FileRecordMapper;
import Pojo.filerecord;
import Utils.MybatisUtils;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class getFileRecordServlet extends HttpServlet {
    private String searchPath = "D:\\IJ\\fileupload_maven\\target\\fileupload_maven\\file";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getHeader("fileName");

        File dir = new File(searchPath);
        File[] allFiles = dir.listFiles();
        for (File f : allFiles) {
            if (f.isDirectory()) {
                File file = new File(searchPath + "\\" + f.getName() + "\\" + fileName);
                if (!file.exists()) continue;
                filerecord filerecord = new filerecord();
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                FileRecordMapper mapper = sqlSession.getMapper(FileRecordMapper.class);
                filerecord = mapper.getRecordByName(fileName);
                sqlSession.close();
                PrintWriter out=response.getWriter();
                String json= JSON.toJSONString(filerecord);
                out.print(json);
                out.close();
                break;
            }
        }
    }
}
