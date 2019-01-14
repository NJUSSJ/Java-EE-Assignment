package edu.nju.onlineorder.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EncodeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=utf-8");
        BufferedResponse repWrapper = new BufferedResponse((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, repWrapper);

        if(repWrapper.getOutputType() == BufferedResponse.OT_WRITER){
            String resBody = new String(repWrapper.toByteArray(), repWrapper.getCharacterEncoding());
            PrintWriter writer = servletResponse.getWriter();
            writer.println(resBody);
            writer.close();
        }else if(repWrapper.getOutputType() == BufferedResponse.OT_OUTPUT_STREAM){
            ServletOutputStream out = servletResponse.getOutputStream();
            out.write(repWrapper.toByteArray());
            out.close();
        }
    }

    @Override
    public void destroy() {

    }
}
