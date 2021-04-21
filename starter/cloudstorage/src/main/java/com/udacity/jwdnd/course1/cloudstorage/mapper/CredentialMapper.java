package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getUserCredentials(User user);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(Integer credentialid);

    @Insert("INSERT INTO CREDENTIALS (url, username, salt, password, userid) VALUES (#{url}, #{username}, #{salt}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialId = #{credentialId}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteCredentials(Credential credential);
}
