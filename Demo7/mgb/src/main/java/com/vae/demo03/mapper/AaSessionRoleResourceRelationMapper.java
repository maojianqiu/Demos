package com.vae.demo03.mapper;

import com.vae.demo03.entity.AaSessionRoleResourceRelation;
import com.vae.demo03.entity.AaSessionRoleResourceRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AaSessionRoleResourceRelationMapper {
    long countByExample(AaSessionRoleResourceRelationExample example);

    int deleteByExample(AaSessionRoleResourceRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AaSessionRoleResourceRelation record);

    int insertSelective(AaSessionRoleResourceRelation record);

    List<AaSessionRoleResourceRelation> selectByExample(AaSessionRoleResourceRelationExample example);

    AaSessionRoleResourceRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AaSessionRoleResourceRelation record, @Param("example") AaSessionRoleResourceRelationExample example);

    int updateByExample(@Param("record") AaSessionRoleResourceRelation record, @Param("example") AaSessionRoleResourceRelationExample example);

    int updateByPrimaryKeySelective(AaSessionRoleResourceRelation record);

    int updateByPrimaryKey(AaSessionRoleResourceRelation record);
}