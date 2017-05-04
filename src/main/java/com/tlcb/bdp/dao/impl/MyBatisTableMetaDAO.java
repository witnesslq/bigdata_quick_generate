package com.tlcb.bdp.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.calanger.common.dao.AbstractDAO;
import com.tlcb.bdp.dao.TableMetaDAO;
import com.tlcb.bdp.model.TableMeta;
import com.tlcb.bdp.vo.TableMetaVO;

@Repository("tableMetaDAO")
public class MyBatisTableMetaDAO extends AbstractDAO<TableMeta, TableMetaVO, java.lang.Integer> implements TableMetaDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "tableMetaDAO";
    }
}
