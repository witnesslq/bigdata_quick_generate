package com.tlcb.bdp.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.calanger.common.dao.AbstractDAO;
import com.tlcb.bdp.dao.DbInfoDAO;
import com.tlcb.bdp.model.DbInfo;
import com.tlcb.bdp.vo.DbInfoVO;

@Repository("dbInfoDAO")
public class MyBatisDbInfoDAO extends AbstractDAO<DbInfo, DbInfoVO, java.lang.Integer> implements DbInfoDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "dbInfoDAO";
    }
}
