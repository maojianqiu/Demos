package com.vae.demo03.mapper;

import com.vae.demo03.entity.AaSessionRoleMenuRelation;
import com.vae.demo03.entity.AaSessionRoleMenuRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AaSessionRoleMenuRelationMapper {
    long countByExample(AaSessionRoleMenuRelationExample example);

    int deleteByExample(AaSessionRoleMenuRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AaSessionRoleMenuRelation record);

    int insertSelective(AaSessionRoleMenuRelation record);

    List<AaSessionRoleMenuRelation> selectByExample(AaSessionRoleMenuRelationExample example);

    AaSessionRoleMenuRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AaSessionRoleMenuRelation record, @Param("example") AaSessionRoleMenuRelationExample example);

    int updateByExample(@Param("record") AaSessionRoleMenuRelation record, @Param("example") AaSessionRoleMenuRelationExample example);

    int updateByPrimaryKeySelective(AaSessionRoleMenuRelation record);

    int updateByPrimaryKey(AaSessionRoleMenuRelation record);
}