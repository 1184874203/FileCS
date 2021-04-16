package Client;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.mime.*;
import org.apache.http.entity.mime.content.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;

import java.io.*;
import java.nio.charset.Charset;

public class myClient {
    private static String URL_UPLOAD = "http://localhost:8080/fileupload_maven/servlet/UploadServlet";
    private static String URL_DOWNLOAD = "http://localhost:8080/fileupload_maven/servlet/DownloadServlet";
    private static String URL_SEARCH = "http://localhost:8080/fileupload_maven/servlet/SearchServlet";

    public static void upload(String localFile) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();

            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(URL_UPLOAD);

            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(new File(localFile));

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    // 相当于<input type="file" name="file"/>
                    .addPart("file", bin)
                    .build();

            httpPost.setEntity(reqEntity);

            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);


            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 打印响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                // 打印响应内容
                System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
            }

            // 销毁
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void downLoad(String remoteFileName, String localFileName) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        OutputStream out = null;
        InputStream in = null;

        try {
            HttpGet httpGet = new HttpGet(URL_DOWNLOAD);


            httpGet.addHeader("fileName", remoteFileName);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            in = entity.getContent();

            long length = entity.getContentLength();
            if (length <= 0) {
                System.out.println(httpResponse.getStatusLine());
                return;
            }


            File file = new File(localFileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            out = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength = in.read(buffer)) > 0) {
                out.write(buffer, 0, readLength);
            }

            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileRecord(String fileName) {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            HttpGet httpGet = new HttpGet(URL_SEARCH);
            httpGet.addHeader("fileName", fileName);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
