package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getUserFiles(User user);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Update("UPDATE FILES SET filename = #{filename}, filesize = #{filesize}, filedata = #{filedata} WHERE fileId = #{fileId}")
    void updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(File file);
}
