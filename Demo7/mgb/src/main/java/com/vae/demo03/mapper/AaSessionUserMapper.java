package com.vae.demo03.mapper;

import com.vae.demo03.entity.AaSessionUser;
import com.vae.demo03.entity.AaSessionUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AaSessionUserMapper {
    long countByExample(AaSessionUserExample example);

    int deleteByExample(AaSessionUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AaSessionUser record);

    int insertSelective(AaSessionUser record);

    List<AaSessionUser> selectByExample(AaSessionUserExample example);

    AaSessionUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AaSessionUser record, @Param("example") AaSessionUserExample example);

    int updateByExample(@Param("record") AaSessionUser record, @Param("example") AaSessionUserExample example);

    int updateByPrimaryKeySelective(AaSessionUser record);

    int updateByPrimaryKey(AaSessionUser record);
}