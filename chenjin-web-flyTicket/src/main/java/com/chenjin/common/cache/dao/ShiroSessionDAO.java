package com.chenjin.common.cache.dao;

import com.chenjin.common.cache.client.ClientTemplate;
import java.io.PrintStream;
import java.io.Serializable;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;

public class ShiroSessionDAO extends EnterpriseCacheSessionDAO
{
    private String parentPath = "/SESSION";
    private ClientTemplate clientTemplate;

    public void doUpdate(Session session)
    {
        System.out.println(session == null ? "null" : session.getId());
        if ((session == null) || (session.getId() == null)) {
            System.err.println("session argument cannot be null.");
        }
        String path = this.parentPath + '/' + session.getId().toString();

        this.clientTemplate.updateNode(path, session);
    }

    public void doDelete(Session session)
    {
        if ((session == null) || (session.getId() == null)) {
            System.err.println("session argument cannot be null.");
        }
        String path = this.parentPath + '/' + session.getId().toString();

        this.clientTemplate.deleteNode(path);
    }

    public Serializable doCreate(Session session)
    {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        String path = this.parentPath + '/' + session.getId().toString();
        this.clientTemplate.createNode(path, session);

        return sessionId;
    }

    public Session readSession(Serializable sessionId)
            throws UnknownSessionException
    {
        Session session = getCachedSession(sessionId);
        if ((session == null) ||
                (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null)) {
            session = doReadSession(sessionId);
            if (session == null) {
                throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
            }

            cache(session, session.getId());
        }

        return session;
    }

    public Session doReadSession(Serializable id)
    {
        if (id == null) {
            return null;
        }
        System.out.println("session id = " + id);
        String path = this.parentPath + '/' + id.toString();

        Object object = this.clientTemplate.getNode(path);
        if (object != null) {
            return (Session)object;
        }

        return null;
    }

    public String getParentPath() {
        return this.parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public ClientTemplate getClientTemplate() {
        return this.clientTemplate;
    }

    public void setClientTemplate(ClientTemplate clientTemplate) {
        this.clientTemplate = clientTemplate;
    }
}