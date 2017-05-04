package com.tlcb.bdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calanger.common.bo.AbstractBO;
import com.calanger.common.dao.DAO;
import com.tlcb.bdp.service.MissionUserLogService;
import com.tlcb.bdp.dao.MissionUserLogDAO;
import com.tlcb.bdp.model.MissionUserLog;
import com.tlcb.bdp.vo.MissionUserLogVO;

@Service("missionUserLogService")
public class DefaultMissionUserLogServiceImpl extends AbstractBO<MissionUserLog, MissionUserLogVO, java.lang.String> implements MissionUserLogService {
    @Autowired
    private MissionUserLogDAO missionUserLogDAO;

    @Override
    protected DAO<MissionUserLog, MissionUserLogVO, java.lang.String> getDAO() {
        return missionUserLogDAO;
    }
}
