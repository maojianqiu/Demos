package com.vae.demo03.mapper;

import com.vae.demo03.entity.AaSessionMenu;
import com.vae.demo03.entity.AaSessionMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AaSessionMenuMapper {
    long countByExample(AaSessionMenuExample example);

    int deleteByExample(AaSessionMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AaSessionMenu record);

    int insertSelective(AaSessionMenu record);

    List<AaSessionMenu> selectByExample(AaSessionMenuExample example);

    AaSessionMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AaSessionMenu record, @Param("example") AaSessionMenuExample example);

    int updateByExample(@Param("record") AaSessionMenu record, @Param("example") AaSessionMenuExample example);

    int updateByPrimaryKeySelective(AaSessionMenu record);

    int updateByPrimaryKey(AaSessionMenu record);
}