package org.vae.service;

import com.github.pagehelper.PageInfo;
import org.vae.domain.UserEventLog;
import org.vae.viewmodel.admin.user.UserEventPageRequestVM;

import java.util.List;

public interface UserEventLogService extends BaseService<UserEventLog> {

    List<UserEventLog> getUserEventLogByUserId(Integer id);

    PageInfo<UserEventLog> page(UserEventPageRequestVM requestVM);

    List<Integer> selectMothCount();
}
