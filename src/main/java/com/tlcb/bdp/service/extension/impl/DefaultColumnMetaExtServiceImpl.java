package com.tlcb.bdp.service.extension.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tlcb.bdp.dao.extension.ColumnMetaExtDAO;
import com.tlcb.bdp.dao.extension.TableMetaExtDAO;
import com.tlcb.bdp.service.extension.ColumnMetaExtService;
import com.tlcb.bdp.service.extension.TableMetaExtService;
import com.tlcb.bdp.vo.ColumnMetaVO;
import com.tlcb.bdp.vo.TableMetaVO;

@Service
public class DefaultColumnMetaExtServiceImpl implements ColumnMetaExtService {
	
	@Resource
	private ColumnMetaExtDAO columnMetaExtDAO;
	
	@Override
	public void updateVersion(ColumnMetaVO  tableMetaVO) {
		
		columnMetaExtDAO.updateVersion(tableMetaVO);
	}

}
