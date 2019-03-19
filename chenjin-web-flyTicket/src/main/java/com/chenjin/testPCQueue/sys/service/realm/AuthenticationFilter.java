package com.chenjin.testPCQueue.sys.service.realm;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

public class AuthenticationFilter extends FormAuthenticationFilter
{
    private String verifyCodeParam = "verifyCodeParam";
    private String loginUrl = "/login.jsp";

    public String getVerifyCodeParam() { return this.verifyCodeParam;
    }

    public void setVerifyCodeParam(String verifyCodeParam)
    {
        this.verifyCodeParam = verifyCodeParam;
    }

    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse)
    {
        String username = getUsername(servletRequest);
        String password = getPassword(servletRequest);
        String vcode = WebUtils.getCleanParam(servletRequest, "verifyCode");
        String type = WebUtils.getCleanParam(servletRequest, "loginType");
        String clientCert = WebUtils.getCleanParam(servletRequest, "UserCert");
        String UserSignedData = WebUtils.getCleanParam(servletRequest, "UserSignedData");

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String loginUrl = request.getServletPath();
        String userIP = request.getServerName();
        String projectCode = WebUtils.getCleanParam(servletRequest, "projectCode");

        this.loginUrl = loginUrl;
        return new ShiroMyToken(username, password, vcode, type, true, clientCert, UserSignedData, userIP, projectCode);
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception
    {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response))
            {
                return executeLogin(request, response);
            }

            return true;
        }

        HttpServletRequest req = (HttpServletRequest)request;
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(
                "<script type='text/javascript'>window.parent.location='" +
                        req.getContextPath() +
                        this.loginUrl + "'</script>");

        return false;
    }

    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
            throws Exception
    {
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        Session session = subject.getSession();
        if ((savedRequest != null) && (savedRequest.getMethod().equalsIgnoreCase("GET")))
            session.setAttribute("toPath", savedRequest.getRequestUrl());
        else {
            session.setAttribute("toPath", ((HttpServletRequest)request).getContextPath() + "/index.jsp");
        }

        WebUtils.issueRedirect(request, response, super.getSuccessUrl(), null);

        return false;
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        System.out.println("e:" + e.getClass().getName());

        if (e.getClass().isInstance(new IncorrectCredentialsException())) {
            request.setAttribute("FAILMSG", "验证码错误");
        } else if (e.getClass().isInstance(new ExcessiveAttemptsException())) {
            request.setAttribute("FAILMSG", "CA证书认证失败");
        } else if (e.getClass().isInstance(new UnknownAccountException())) {
            request.setAttribute("FAILMSG", "用户名或密码错误");
        } else if (e.getClass().isInstance(new DisabledAccountException())) {
            System.out.println("您的帐号已被禁用");
            request.setAttribute("FAILMSG", "您的帐号已被禁用");
        } else if (e.getClass().isInstance(new LockedAccountException())) {
            System.out.println("密码错误5次，您的帐号已被锁定3分钟");
            request.setAttribute("FAILMSG", "密码错误5次，您的帐号已被锁定3分钟");
        } else if (e.getClass().isInstance(new ExpiredCredentialsException())) {
            System.out.println("密码已经过期，请先修改密码");
            request.setAttribute("FAILMSG", "密码已经过期，请先修改密码");
        }
        e.printStackTrace();

        return true;
    }
}