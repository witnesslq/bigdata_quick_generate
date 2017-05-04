package com.tlcb.bdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calanger.common.bo.AbstractBO;
import com.calanger.common.dao.DAO;
import com.tlcb.bdp.service.TableMetaService;
import com.tlcb.bdp.dao.TableMetaDAO;
import com.tlcb.bdp.model.TableMeta;
import com.tlcb.bdp.vo.TableMetaVO;

@Service("tableMetaService")
public class DefaultTableMetaServiceImpl extends AbstractBO<TableMeta, TableMetaVO, java.lang.Integer> implements TableMetaService {
    @Autowired
    private TableMetaDAO tableMetaDAO;

    @Override
    protected DAO<TableMeta, TableMetaVO, java.lang.Integer> getDAO() {
        return tableMetaDAO;
    }
}
