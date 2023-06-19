package com.civip.csyy.processor;

import com.civip.csyy.dao.VehicleLocBatchInsertDao;
import com.civip.csyy.model.VehicleLoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/16
 */
public class VehicleLocAsyncQueueBatchProcessor extends AbstractAsyncQueueBatchProcessor<VehicleLoc> {
    public VehicleLocAsyncQueueBatchProcessor() {
        super();
    }

    public VehicleLocAsyncQueueBatchProcessor(int queueSize, int batchSize, int batchIntervalOfMs) {
        super(queueSize, batchSize, batchIntervalOfMs);
    }

    @Autowired
    private VehicleLocBatchInsertDao vehicleLocBatchInsertDao;

    @Override
    protected void doHandle(ArrayList<VehicleLoc> ts) throws Exception {
        vehicleLocBatchInsertDao.batchInsert(ts);
    }
}
