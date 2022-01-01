package com.vae.demo03.mapper;

import com.vae.demo03.entity.AaSessionRole;
import com.vae.demo03.entity.AaSessionRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AaSessionRoleMapper {
    long countByExample(AaSessionRoleExample example);

    int deleteByExample(AaSessionRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AaSessionRole record);

    int insertSelective(AaSessionRole record);

    List<AaSessionRole> selectByExample(AaSessionRoleExample example);

    AaSessionRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AaSessionRole record, @Param("example") AaSessionRoleExample example);

    int updateByExample(@Param("record") AaSessionRole record, @Param("example") AaSessionRoleExample example);

    int updateByPrimaryKeySelective(AaSessionRole record);

    int updateByPrimaryKey(AaSessionRole record);
}