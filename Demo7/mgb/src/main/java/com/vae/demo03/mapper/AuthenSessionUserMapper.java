package com.vae.demo03.mapper;

import com.vae.demo03.entity.AuthenSessionUser;
import com.vae.demo03.entity.AuthenSessionUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthenSessionUserMapper {
    long countByExample(AuthenSessionUserExample example);

    int deleteByExample(AuthenSessionUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AuthenSessionUser record);

    int insertSelective(AuthenSessionUser record);

    List<AuthenSessionUser> selectByExample(AuthenSessionUserExample example);

    AuthenSessionUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AuthenSessionUser record, @Param("example") AuthenSessionUserExample example);

    int updateByExample(@Param("record") AuthenSessionUser record, @Param("example") AuthenSessionUserExample example);

    int updateByPrimaryKeySelective(AuthenSessionUser record);

    int updateByPrimaryKey(AuthenSessionUser record);
}