package com.alice.pet.base.shiro;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * @author xudong.cheng
 * @date 2018/1/19 下午1:02
 */
public class RandomSessionIdGenerator implements SessionIdGenerator {
    /**
     * Generates a new ID to be applied to the specified {@code Session} instance.
     *
     * @param session the {@link Session} instance to which the ID will be applied.
     * @return the id to assign to the specified {@link Session} instance before adding a record to the EIS data store.
     */
    @Override
    public Serializable generateId(Session session) {
        return RandomStringUtils.randomAlphanumeric(32);
    }
}
