package com.tlcb.bdp.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.calanger.common.dao.AbstractDAO;
import com.tlcb.bdp.dao.ColumnMetaDAO;
import com.tlcb.bdp.model.ColumnMeta;
import com.tlcb.bdp.vo.ColumnMetaVO;

@Repository("columnMetaDAO")
public class MyBatisColumnMetaDAO extends AbstractDAO<ColumnMeta, ColumnMetaVO, java.lang.Integer> implements ColumnMetaDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "columnMetaDAO";
    }
}
