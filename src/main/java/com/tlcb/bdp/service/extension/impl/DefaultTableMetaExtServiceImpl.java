package com.tlcb.bdp.service.extension.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tlcb.bdp.dao.extension.TableMetaExtDAO;
import com.tlcb.bdp.service.extension.TableMetaExtService;
import com.tlcb.bdp.vo.TableMetaVO;

@Service
public class DefaultTableMetaExtServiceImpl implements TableMetaExtService {
	
	@Resource
	private TableMetaExtDAO tableMetaExtDAO;
	
	@Override
	public void updateVersion(TableMetaVO  tableMetaVO) {
		
		tableMetaExtDAO.updateVersion(tableMetaVO);
	}

}
