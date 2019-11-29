package com.service;

import com.domain.SysLog;

import java.util.List;

public interface SysLogService {

    List<SysLog> findAll() throws Exception;

    void save(SysLog log) throws Exception;
}
