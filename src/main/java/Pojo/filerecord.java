package Pojo;

import lombok.Data;

@Data
public class filerecord {
    private int id_record;
    private long file_size;
    private String file_type;
    private String file_pre_name;
    private String file_new_name;
    private String time;
    private String file_dir;
}
