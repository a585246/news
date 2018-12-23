package com.cskaoyan.news.shiro.shiroSession;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;

public class MySessionManager  implements SessionManager {
    @Override
    public Session start(SessionContext sessionContext) {
        return null;
    }

    @Override
    public Session getSession(SessionKey sessionKey) throws SessionException {
        return null;
    }
}
