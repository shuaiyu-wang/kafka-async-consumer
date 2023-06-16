package com.civip.csyy.service;

import com.civip.csyy.dao.VehicleLocBatchInsertDao;
import com.civip.csyy.model.VehicleLoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/16
 */
@Service
public class VehicleLocAbstractAsyncQueueService extends AbstractAsyncQueueService<VehicleLoc> {
    public VehicleLocAbstractAsyncQueueService() {
        super();
    }

    @Autowired
    private VehicleLocBatchInsertDao vehicleLocBatchInsertDao;

    @Override
    protected void doHandle(ArrayList<VehicleLoc> ts) throws Exception {
        vehicleLocBatchInsertDao.batchInsert(ts);
    }
}
