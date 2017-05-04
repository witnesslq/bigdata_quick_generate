package com.tlcb.bdp.dao.extension.impl;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tlcb.bdp.dao.extension.TableMetaExtDAO;
import com.tlcb.bdp.vo.TableMetaVO;

@Repository()
public class MyBatisTableMetaExtDAOImpl implements TableMetaExtDAO {
	
	    @Autowired
	    private SqlSessionTemplate sqlSessionTemplate;

	    protected SqlSessionTemplate getSqlSessionTemplate() {
	        return sqlSessionTemplate;
	    }

	    protected String getNamespace() {
	        return "tableMetaExtDAO";
	    }
	
	@Override
	public void updateVersion(TableMetaVO tableMetaVO) {
		Map map = new HashMap();
		map.put("id", tableMetaVO.getId());
		sqlSessionTemplate.update(getNamespace()+".updateVersion", map);
	}

}
