package edu.nju.onlineorder.tag;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class SessionCheckHandler extends BodyTagSupport {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public int doEndTag() throws JspException {
        HttpSession session = request.getSession(false);
        if(session.getAttribute("uid") == null){
            ServletContext context = request.getSession().getServletContext();
            try {
                context.getRequestDispatcher("/login/login.jsp").forward(request, response);
                session.invalidate();
                return SKIP_PAGE;
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
        return EVAL_PAGE;
    }
}
