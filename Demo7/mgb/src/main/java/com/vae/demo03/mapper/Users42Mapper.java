package com.vae.demo03.mapper;

import com.vae.demo03.entity.Users42;
import com.vae.demo03.entity.Users42Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Users42Mapper {
    long countByExample(Users42Example example);

    int deleteByExample(Users42Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(Users42 record);

    int insertSelective(Users42 record);

    List<Users42> selectByExampleWithBLOBs(Users42Example example);

    List<Users42> selectByExample(Users42Example example);

    Users42 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Users42 record, @Param("example") Users42Example example);

    int updateByExampleWithBLOBs(@Param("record") Users42 record, @Param("example") Users42Example example);

    int updateByExample(@Param("record") Users42 record, @Param("example") Users42Example example);

    int updateByPrimaryKeySelective(Users42 record);

    int updateByPrimaryKeyWithBLOBs(Users42 record);

    int updateByPrimaryKey(Users42 record);
}