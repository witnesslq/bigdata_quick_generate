package com.tlcb.bdp.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.calanger.common.dao.AbstractDAO;
import com.tlcb.bdp.dao.MissionUserLogDAO;
import com.tlcb.bdp.model.MissionUserLog;
import com.tlcb.bdp.vo.MissionUserLogVO;

@Repository("missionUserLogDAO")
public class MyBatisMissionUserLogDAO extends AbstractDAO<MissionUserLog, MissionUserLogVO, java.lang.String> implements MissionUserLogDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "missionUserLogDAO";
    }
}
