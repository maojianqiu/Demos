package com.vae.demo03.mapper;

import com.vae.demo03.entity.AaSessionUserRoleRelation;
import com.vae.demo03.entity.AaSessionUserRoleRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AaSessionUserRoleRelationMapper {
    long countByExample(AaSessionUserRoleRelationExample example);

    int deleteByExample(AaSessionUserRoleRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AaSessionUserRoleRelation record);

    int insertSelective(AaSessionUserRoleRelation record);

    List<AaSessionUserRoleRelation> selectByExample(AaSessionUserRoleRelationExample example);

    AaSessionUserRoleRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AaSessionUserRoleRelation record, @Param("example") AaSessionUserRoleRelationExample example);

    int updateByExample(@Param("record") AaSessionUserRoleRelation record, @Param("example") AaSessionUserRoleRelationExample example);

    int updateByPrimaryKeySelective(AaSessionUserRoleRelation record);

    int updateByPrimaryKey(AaSessionUserRoleRelation record);
}