package Client;

import org.junit.Test;

import static org.junit.Assert.*;

public class myClientTest {
    @Test
    public void upload() {
        /**
         * 测试上传文件接口，上传文件的位置在
         * D:\IJ\fileupload_maven\src\UploadFile\test.txt
         *
         * 上传成功，本次上传得到的文件UUID为
         * bce2fc5880a7479e93b207184f96a671.txt
         * 存放位置为
         * D:\IJ\fileupload_maven\target\fileupload_maven\file\2021-04-16\bce2fc5880a7479e93b207184f96a671.txt
         */
        myClient.upload("D:\\IJ\\fileupload_maven\\src\\UploadFile\\test.txt");
    }

    @Test
    public void downLoad() {
        /**
         * 本次测试文件UUID为
         * bce2fc5880a7479e93b207184f96a671.txt
         * 下载成功，存放在
         * D:\IJ\fileupload_maven\src\downloadFile\download.txt
         */
        myClient.downLoad("bce2fc5880a7479e93b207184f96a671.txt","D:\\IJ\\fileupload_maven\\src\\downloadFile\\download.txt");
    }

    @Test
    public void getFileRecord() {
        /**
         * 本次测试返回结果：json字符串
         * {"file_dir":"D:\\IJ\\fileupload_maven\\target\\fileupload_maven\\file\\2021-04-16\\",
         * "file_new_name":"bce2fc5880a7479e93b207184f96a671.txt",
         * "file_pre_name":"test.txt","file_size":102,"file_type":".txt","id_record":1,"time":"2021-04-16 22:35:46"}
         */
        System.out.println(myClient.getFileRecord("bce2fc5880a7479e93b207184f96a671.txt"));
    }
}