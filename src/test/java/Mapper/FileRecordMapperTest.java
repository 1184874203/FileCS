package Mapper;

import Pojo.filerecord;
import Utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class FileRecordMapperTest {

    @Test
    public void getRecord() {
    }

    @Test
    public void getRecordByName() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FileRecordMapper mapper = sqlSession.getMapper(FileRecordMapper.class);
        filerecord sss = mapper.getRecordByName("sss");
        System.out.println(sss);
    }

    @Test
    public void insertRecord() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FileRecordMapper mapper = sqlSession.getMapper(FileRecordMapper.class);
        Date time=new Date();
        SimpleDateFormat tf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        filerecord a = new filerecord();
        a.setFile_dir("2021-4-16");
        a.setFile_pre_name("syh");
        a.setFile_new_name("abcd");
        a.setFile_type(".txt");
        a.setFile_size(1584);
        a.setTime(tf.format(time));
        mapper.insertRecord(a);
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        File dir=new File("D:\\IJ\\fileupload_maven\\target\\fileupload_maven\\file");
        File[] allFiles=dir.listFiles();
        for(File f:allFiles){
            if(f.isDirectory()){
                File file=new File("D:\\IJ\\fileupload_maven\\target\\fileupload_maven\\file"+"\\"+f.getName()+"d8588e4baa51498aac5d2f1f38beeb03.txt");
//                File[] list=file.listFiles();
//                for(File ff:list){
//                    System.out.println(ff.getName());
//                }
                if(file.exists()){
                    FileInputStream fin=new FileInputStream(file);
                }
            }
        }
    }
}