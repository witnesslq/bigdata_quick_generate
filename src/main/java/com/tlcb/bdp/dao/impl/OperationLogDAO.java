package com.tlcb.bdp.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.calanger.common.dao.AbstractDAO;
import com.tlcb.bdp.model.OperationLog;
import com.tlcb.bdp.vo.OperationVO;

@Repository("operationLogDAO")
public class OperationLogDAO extends AbstractDAO<OperationLog, OperationVO, java.lang.Integer> implements com.tlcb.bdp.dao.OperationLogDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "operationLogDAO";
    }
}
