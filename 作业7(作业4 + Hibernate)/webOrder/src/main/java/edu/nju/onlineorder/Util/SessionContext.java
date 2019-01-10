package edu.nju.onlineorder.Util;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class SessionContext {

    public static HashMap<String, HttpSession> sessionMap = new HashMap();

    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return (HttpSession) sessionMap.get(session_id);
    }

}
