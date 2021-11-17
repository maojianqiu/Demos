package org.vae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.vae.domain.UserEventLog;
import org.vae.domain.other.KeyValue;
import org.vae.viewmodel.admin.user.UserEventPageRequestVM;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserEventLogMapper extends BaseMapper<UserEventLog> {

    List<UserEventLog> getUserEventLogByUserId(Integer id);

    List<UserEventLog> page(UserEventPageRequestVM requestVM);

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
