package com.chenjin.testPCQueue.sys.service.realm;

import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter
        implements Filter
{
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        String servletPath = request.getServletPath();
        if ((servletPath.indexOf("/auth.htmlx") <= -1) &&
                (servletPath.indexOf("/vcimage.htmlx") <= -1) &&
                (servletPath.indexOf("/app/") <= -1))
        {
            if ((servletPath.indexOf(".htmlx") > -1) &&
                    (!servletPath.equals("/sys/logout.htmlx"))) {
                System.out.println("servletPath = " + servletPath);
                System.out.println("request.getSession() = " + request.getSession());
                System.out.println("request.getSession().getId() = " + request.getSession().getId());

                if (request.getSession().getAttribute("currentUser") == null) {
                    System.out.println("in null");
                }

            }

        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig)
            throws ServletException
    {
    }

    public void destroy()
    {
    }
}