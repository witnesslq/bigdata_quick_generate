package com.tlcb.bdp.dao.extension.impl;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tlcb.bdp.dao.extension.ColumnMetaExtDAO;
import com.tlcb.bdp.dao.extension.TableMetaExtDAO;
import com.tlcb.bdp.vo.ColumnMetaVO;
import com.tlcb.bdp.vo.TableMetaVO;

@Repository("columnMetaExtDAO")
public class MyBatisColumnMetaExtDAOImpl implements ColumnMetaExtDAO {
	
	    @Autowired
	    private SqlSessionTemplate sqlSessionTemplate;

	    protected SqlSessionTemplate getSqlSessionTemplate() {
	        return sqlSessionTemplate;
	    }

	    protected String getNamespace() {
	        return "columnMetaExtDAO";
	    }
	
	@Override
	public void updateVersion(ColumnMetaVO columnMetaVO) {
		Map map = new HashMap();
		
		map.put("id", columnMetaVO.getId());
		
		sqlSessionTemplate.update(getNamespace()+".updateVersion", map);
	}

}
