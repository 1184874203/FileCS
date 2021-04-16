package Servlet;

import javax.servlet.http.*;
import java.io.*;

public class DownloadServlet extends HttpServlet {
    private String downloadPath = "D:\\IJ\\fileupload_maven\\target\\fileupload_maven\\file";

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;


        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");


            String fileName = request.getHeader("fileName");


            System.out.println("fileName:" + fileName);

            File dir = new File(downloadPath);
            File[] allFiles = dir.listFiles();
            //设置状态码410
            response.setStatus(HttpServletResponse.SC_GONE);
            for (File f : allFiles) {
                if (f.isDirectory()) {
                    File file = new File(downloadPath + "\\" + f.getName() + "\\" + fileName);
                    if (!file.exists()) continue;
                    response.setContentLength((int) file.length());
                    response.setHeader("Accept-Ranges", "bytes");
                    int readLength = 0;

                    in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
                    out = new BufferedOutputStream(response.getOutputStream());

                    byte[] buffer = new byte[BUFFER_SIZE];
                    while ((readLength = in.read(buffer)) > 0) {
                        out.write(buffer, 0, readLength);
                    }
                    out.flush();
                    //如果文件插入成功了设置状态码200，否则就是默认的410
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
