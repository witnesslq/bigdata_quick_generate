package com.tlcb.bdp.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.calanger.common.dao.AbstractDAO;
import com.tlcb.bdp.dao.UserTableRelationDAO;
import com.tlcb.bdp.model.UserTableRelation;
import com.tlcb.bdp.vo.UserTableRelationVO;

@Repository("userTableRelationDAO")
public class MyBatisUserTableRelationDAO extends AbstractDAO<UserTableRelation, UserTableRelationVO, java.lang.String> implements UserTableRelationDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "userTableRelationDAO";
    }
}
