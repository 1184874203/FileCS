package Mapper;

import Pojo.filerecord;

import java.util.List;

public interface FileRecordMapper {
    public List<filerecord> getRecord();
    public filerecord getRecordByName(String file_new_name);
    public void insertRecord(filerecord filerecord);
}
