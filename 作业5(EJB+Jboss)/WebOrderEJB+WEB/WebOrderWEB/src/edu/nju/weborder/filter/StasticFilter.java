package edu.nju.weborder.filter;


import edu.nju.weborder.Util.SessionContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class StasticFilter implements Filter {
    ServletContext context;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        BufferedResponse bufferedResponse = new BufferedResponse((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, bufferedResponse);

        if(bufferedResponse.getOutputType() == BufferedResponse.OT_WRITER){
            System.out.println("Add Statistic!");
            String respBody = new String(bufferedResponse.toByteArray(), bufferedResponse.getCharacterEncoding());
            int webCounter = Integer.parseInt((String) context.getAttribute("webCounter"));
            int visitCounter = 0;
            int onlineCounter = 0;
            HashMap<String, HttpSession> sessions = SessionContext.sessionMap;
            for (String sessionID: sessions.keySet()
                 ) {
                HttpSession tmp = sessions.get(sessionID);
                if(tmp.getAttribute("uid") != null){
                    onlineCounter++;
                }
            }
            visitCounter = sessions.size() - onlineCounter;

            respBody += "历史访问人数：" + webCounter + "<br>";
            respBody += "游客人数：" + visitCounter + "<br>";
            respBody += "登录人数：" + onlineCounter + "<br>";

            PrintWriter writer = servletResponse.getWriter();
            writer.println(respBody);
        }else if(bufferedResponse.getOutputType() == BufferedResponse.OT_OUTPUT_STREAM){

        }
    }

    @Override
    public void destroy() {

    }
}
