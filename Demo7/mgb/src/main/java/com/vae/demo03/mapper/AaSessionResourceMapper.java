package com.vae.demo03.mapper;

import com.vae.demo03.entity.AaSessionResource;
import com.vae.demo03.entity.AaSessionResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AaSessionResourceMapper {
    long countByExample(AaSessionResourceExample example);

    int deleteByExample(AaSessionResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AaSessionResource record);

    int insertSelective(AaSessionResource record);

    List<AaSessionResource> selectByExample(AaSessionResourceExample example);

    AaSessionResource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AaSessionResource record, @Param("example") AaSessionResourceExample example);

    int updateByExample(@Param("record") AaSessionResource record, @Param("example") AaSessionResourceExample example);

    int updateByPrimaryKeySelective(AaSessionResource record);

    int updateByPrimaryKey(AaSessionResource record);
}