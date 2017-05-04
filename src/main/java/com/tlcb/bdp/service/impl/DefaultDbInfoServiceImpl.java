package com.tlcb.bdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calanger.common.bo.AbstractBO;
import com.calanger.common.dao.DAO;
import com.tlcb.bdp.service.DbInfoService;
import com.tlcb.bdp.dao.DbInfoDAO;
import com.tlcb.bdp.model.DbInfo;
import com.tlcb.bdp.vo.DbInfoVO;

@Service("dbInfoService")
public class DefaultDbInfoServiceImpl extends AbstractBO<DbInfo, DbInfoVO, java.lang.Integer> implements DbInfoService {
    @Autowired
    private DbInfoDAO dbInfoDAO;

    @Override
    protected DAO<DbInfo, DbInfoVO, java.lang.Integer> getDAO() {
        return dbInfoDAO;
    }
}
