package com.tlcb.bdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calanger.common.bo.AbstractBO;
import com.calanger.common.dao.DAO;
import com.tlcb.bdp.service.UserTableRelationService;
import com.tlcb.bdp.dao.UserTableRelationDAO;
import com.tlcb.bdp.model.UserTableRelation;
import com.tlcb.bdp.vo.UserTableRelationVO;

@Service("userTableRelationService")
public class DefaultUserTableRelationServiceImpl extends AbstractBO<UserTableRelation, UserTableRelationVO, java.lang.String> implements UserTableRelationService {
    @Autowired
    private UserTableRelationDAO userTableRelationDAO;

    @Override
    protected DAO<UserTableRelation, UserTableRelationVO, java.lang.String> getDAO() {
        return userTableRelationDAO;
    }
}
