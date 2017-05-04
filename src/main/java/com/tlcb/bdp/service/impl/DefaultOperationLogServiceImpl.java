package com.tlcb.bdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calanger.common.bo.AbstractBO;
import com.calanger.common.dao.DAO;
import com.tlcb.bdp.dao.OperationLogDAO;
import com.tlcb.bdp.model.DbInfo;
import com.tlcb.bdp.model.OperationLog;
import com.tlcb.bdp.service.OperationLogService;
import com.tlcb.bdp.vo.DbInfoVO;
import com.tlcb.bdp.vo.OperationVO;

@Service("operationLogService")
public class DefaultOperationLogServiceImpl extends AbstractBO<OperationLog, OperationVO, java.lang.Integer> implements OperationLogService {
    @Autowired
    private OperationLogDAO operationLogDAO;

    @Override
    protected DAO<OperationLog, OperationVO, java.lang.Integer> getDAO() {
        return operationLogDAO;
    }
}
