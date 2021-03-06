package com.vae.demo03.mapper;

import com.vae.demo03.entity.Authorities;
import com.vae.demo03.entity.AuthoritiesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthoritiesMapper {
    long countByExample(AuthoritiesExample example);

    int deleteByExample(AuthoritiesExample example);

    int insert(Authorities record);

    int insertSelective(Authorities record);

    List<Authorities> selectByExample(AuthoritiesExample example);

    int updateByExampleSelective(@Param("record") Authorities record, @Param("example") AuthoritiesExample example);

    int updateByExample(@Param("record") Authorities record, @Param("example") AuthoritiesExample example);
}