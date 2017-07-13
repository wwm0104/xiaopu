package com.chinaxiaopu.xiaopuMobi.security.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuwei
 * date: 16/8/29
 */
@Slf4j
public class RedisSessionDAO extends AbstractSessionDAO {

    private static final String ACTIVE_SESSION = "session:";
    private RedisTemplate<Serializable, Session> redisTemplate;
    private ValueOperations<Serializable, Session> sessionOperations;

    public RedisSessionDAO(RedisTemplate<Serializable, Session> redisTemplate) {
        this.redisTemplate = redisTemplate;
        sessionOperations = redisTemplate.opsForValue();
    }

    @Override
    protected Serializable doCreate(Session session) {
        final Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        sessionOperations.set(ACTIVE_SESSION+sessionId, session, 30, TimeUnit.MINUTES);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        final Session session = sessionOperations.get(ACTIVE_SESSION+sessionId);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        sessionOperations.set(ACTIVE_SESSION+session.getId(), session, 30, TimeUnit.MINUTES);
    }

    @Override
    public void delete(Session session) {
        final Serializable sessionId = session.getId();
        redisTemplate.delete(ACTIVE_SESSION+sessionId);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return Collections.emptySet();
    }
}
