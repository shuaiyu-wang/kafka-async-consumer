package com.civip.csyy.kafka.handler;

import com.civip.csyy.annotation.KafkaMsgHandler;
import com.civip.csyy.common.enums.MsgTypeEnum;
import com.civip.csyy.domain.CsyyVehicleLocDTO;
import com.civip.csyy.model.VehicleLoc;
import com.civip.csyy.processor.VehicleLocAsyncQueueBatchProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/14
 */
@Component
@KafkaMsgHandler(msgType = MsgTypeEnum.LOW_SPEED_VEHICLE, name = "低速作业车数据处理")
public class LowSpeedVehicleMsgHandler extends AbstractKafkaMsgHandler{

    private final static Logger logger = LoggerFactory.getLogger(LowSpeedVehicleMsgHandler.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private VehicleLocAsyncQueueBatchProcessor vehicleLocAbstractAsyncQueueService;

    @Override
    protected void doHandle(String msg) throws Exception{
        logger.info("recv msg: {}", msg);
        CsyyVehicleLocDTO csyyVehicleLocDTO = mapper.readValue(msg, CsyyVehicleLocDTO.class);
        VehicleLoc vehicleLoc = new VehicleLoc();
        transfer(csyyVehicleLocDTO, vehicleLoc);
        vehicleLocAbstractAsyncQueueService.put(vehicleLoc);
    }

    private void transfer(CsyyVehicleLocDTO csyyVehicleLocDTO, VehicleLoc vehicleLoc) {
        vehicleLoc.setDeviceId(csyyVehicleLocDTO.getDeviceId());
        vehicleLoc.setLongitude(csyyVehicleLocDTO.getLongitude());
        vehicleLoc.setLatitude(csyyVehicleLocDTO.getLatitude());
        vehicleLoc.setSpeed(csyyVehicleLocDTO.getSpeed());
        vehicleLoc.setDirection(csyyVehicleLocDTO.getDirection());
        vehicleLoc.setTypeId(csyyVehicleLocDTO.getTypeId());
        vehicleLoc.setCollectTime(csyyVehicleLocDTO.getCollectTime());
        vehicleLoc.setUpdateTime(csyyVehicleLocDTO.getUpdateTime());
        vehicleLoc.setOnJob(csyyVehicleLocDTO.getOnJob());
        vehicleLoc.setRecvTime(System.currentTimeMillis());
    }
}
