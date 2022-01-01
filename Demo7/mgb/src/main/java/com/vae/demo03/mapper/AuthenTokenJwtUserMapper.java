package com.vae.demo03.mapper;

import com.vae.demo03.entity.AuthenTokenJwtUser;
import com.vae.demo03.entity.AuthenTokenJwtUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthenTokenJwtUserMapper {
    long countByExample(AuthenTokenJwtUserExample example);

    int deleteByExample(AuthenTokenJwtUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AuthenTokenJwtUser record);

    int insertSelective(AuthenTokenJwtUser record);

    List<AuthenTokenJwtUser> selectByExample(AuthenTokenJwtUserExample example);

    AuthenTokenJwtUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AuthenTokenJwtUser record, @Param("example") AuthenTokenJwtUserExample example);

    int updateByExample(@Param("record") AuthenTokenJwtUser record, @Param("example") AuthenTokenJwtUserExample example);

    int updateByPrimaryKeySelective(AuthenTokenJwtUser record);

    int updateByPrimaryKey(AuthenTokenJwtUser record);
}