package com.tlcb.bdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calanger.common.bo.AbstractBO;
import com.calanger.common.dao.DAO;
import com.tlcb.bdp.service.ColumnMetaService;
import com.tlcb.bdp.dao.ColumnMetaDAO;
import com.tlcb.bdp.model.ColumnMeta;
import com.tlcb.bdp.vo.ColumnMetaVO;

@Service("columnMetaService")
public class DefaultColumnMetaServiceImpl extends AbstractBO<ColumnMeta, ColumnMetaVO, java.lang.Integer> implements ColumnMetaService {
    @Autowired
    private ColumnMetaDAO columnMetaDAO;

    @Override
    protected DAO<ColumnMeta, ColumnMetaVO, java.lang.Integer> getDAO() {
        return columnMetaDAO;
    }
}
