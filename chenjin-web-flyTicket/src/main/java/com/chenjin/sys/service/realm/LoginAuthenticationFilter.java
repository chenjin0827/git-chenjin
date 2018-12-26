package com.chenjin.sys.service.realm;

import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginAuthenticationFilter extends AuthenticatingFilter
{
    public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";
    public static final String DEFAULT_USERNAME_PARAM = "username";
    public static final String DEFAULT_PASSWORD_PARAM = "password";
    public static final String DEFAULT_REMEMBER_ME_PARAM = "rememberMe";
    public static final String DEFAULT_INDEX_PATH = "/index.jsp";
    private static final Logger log = LoggerFactory.getLogger(LoginAuthenticationFilter.class);

    private String usernameParam = "username";
    private String passwordParam = "password";
    private String rememberMeParam = "rememberMe";

    private String indexPath = "/index.jsp";

    private String failureKeyAttribute = "shiroLoginFailure";

    public LoginAuthenticationFilter() {
        setLoginUrl("/login.jsp");
    }

    public void setLoginUrl(String loginUrl)
    {
        String previous = getLoginUrl();
        if (previous != null) {
            this.appliedPaths.remove(previous);
        }
        super.setLoginUrl(loginUrl);
        if (log.isTraceEnabled()) {
            log.trace("Adding login url to applied paths.");
        }
        this.appliedPaths.put(getLoginUrl(), null);
    }

    public String getUsernameParam() {
        return this.usernameParam;
    }

    public String getIndexPath()
    {
        return this.indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }

    public void setUsernameParam(String usernameParam)
    {
        this.usernameParam = usernameParam;
    }

    public String getPasswordParam() {
        return this.passwordParam;
    }

    public void setPasswordParam(String passwordParam)
    {
        this.passwordParam = passwordParam;
    }

    public String getRememberMeParam() {
        return this.rememberMeParam;
    }

    public void setRememberMeParam(String rememberMeParam)
    {
        this.rememberMeParam = rememberMeParam;
    }

    public String getFailureKeyAttribute() {
        return this.failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            }
            if (log.isTraceEnabled()) {
                log.trace("Login page view.");
            }

            return true;
        }

        if (log.isTraceEnabled()) {
            log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" +
                    getLoginUrl() + "]");
        }

        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response)
    {
        return ((request instanceof HttpServletRequest)) && (WebUtils.toHttp(request).getMethod().equalsIgnoreCase("POST"));
    }

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);

        return new ShiroMyToken(username, password, true);
    }

    protected boolean isRememberMe(ServletRequest request) {
        return WebUtils.isTrue(request, getRememberMeParam());
    }

    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
            throws Exception
    {
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        Session session = subject.getSession();
        if ((savedRequest != null) && (savedRequest.getMethod().equalsIgnoreCase("GET")))
            session.setAttribute("toPath", savedRequest.getRequestUrl());
        else {
            session.setAttribute("toPath", ((HttpServletRequest)request).getContextPath() + getIndexPath());
        }

        WebUtils.issueRedirect(request, response, super.getSuccessUrl(), null);

        return false;
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response)
    {
        setFailureAttribute(request, e);

        return true;
    }

    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae)
    {
        request.setAttribute(getFailureKeyAttribute(), ae.getMessage());
    }

    protected String getUsername(ServletRequest request) {
        return WebUtils.getCleanParam(request, getUsernameParam());
    }

    protected String getPassword(ServletRequest request) {
        return WebUtils.getCleanParam(request, getPasswordParam());
    }
}