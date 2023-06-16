package com.civip.csyy.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/15
 */
public abstract class AbstractBatchInsertDao<T> {

    private final static Logger logger = LoggerFactory.getLogger(AbstractBatchInsertDao.class);

    @Autowired
    private EntityManager em;

    @Transactional
    public void batchInsert(List<T> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                em.persist(list.get(i));
                if (i % 200 == 0) {
                    em.flush();
                    em.clear();
                }
            }
            // 提交剩余数据
            em.flush();
            em.clear();
        } catch (Exception e) {
            logger.error("batch insert data failure, data is: {}",list, e);
        }
    }
}
