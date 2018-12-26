package com.chenjin.sys.service.realm;

import com.chenjin.common.ca.CommonCA;
import com.chenjin.common.cache.dao.ShiroSessionDAO;
import com.chenjin.common.cache.dao.VerifyCodeDAO;
import com.chenjin.common.util.DateUtil;
import com.chenjin.sys.entity.ActionLog;
import com.chenjin.sys.entity.AttributeItem;
import com.chenjin.sys.entity.Organization;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IActionLogService;
import com.chenjin.sys.service.IAttributeItemService;
import com.chenjin.sys.service.IOrganizationService;
import com.chenjin.sys.service.IPermissionService;
import com.chenjin.sys.service.IResourceService;
import com.chenjin.sys.service.IUserService;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class ShiroMyRealm extends AuthorizingRealm
{

    @Resource
    private IUserService userService;

    @Resource
    private IPermissionService permissionService;

    @Resource
    private IResourceService resourceService;

    @Resource
    private IOrganizationService organizationService;

    @Resource
    private VerifyCodeDAO verifyCodeDAO;

    @Resource
    private ShiroSessionDAO shiroSessionDAO;

    @Resource
    private ShiroChainDefinitionsManager shiroChainDefinitionsManager;

    @Resource
    private IActionLogService actionLogService;

    @Resource
    private IAttributeItemService attributeItemService;
    private static Logger logger = LoggerFactory.getLogger(ShiroMyRealm.class);

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("in Authoriz授权检查ationInfo======");

        if (principals == null) {
            throw new AuthorizationException(
                    "PrincipalCollection method argument cannot be null.");
        }

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }

        Long id = (Long)principals.fromRealm(getName()).iterator().next();

        this.userService.clear();
        User user = (User)this.userService.getById("SZ", id);

        if (user != null)
        {
            this.shiroChainDefinitionsManager.updateFilterChains(this.resourceService.getAllForShiro(user.getProjectCode()));
            List authorities = new ArrayList();
            List roles = new ArrayList();

            List list = new ArrayList();

            if (user.getEmpId().equals("admin"))
                authorities.add("*");
            else {
                list = this.permissionService.getMyPermission(user.getProjectCode(), user.getId());
            }

            System.out.println("拥有权限list = " + list.size());
            for (int i = 0; i < list.size(); i++) {
                Map m = (Map)list.get(i);
                if (m.get("RESOURCEID") == null)
                    continue;
                Long resourceId = Long.valueOf(Long.parseLong((String)m.get("RESOURCEID")));
                com.chenjin.sys.entity.Resource r = (com.chenjin.sys.entity.Resource)this.resourceService.getById(resourceId);
                if ((r.getPermCode() != null) && (!r.getPermCode().equals(""))) {
                    authorities.add(r.getPermCode());
                }

            }

            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addStringPermissions(authorities);
            authorizationInfo.addRoles(roles);

            return authorizationInfo;
        }

        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException
    {
        System.out.println("in auth=" + authcToken.getPrincipal() + "-" + authcToken.getCredentials());

        ShiroMyToken token = (ShiroMyToken)authcToken;
        System.out.println("shiro登录验证=" + token.isAuth() + token.getEmpId() + "." + token.getPwd());

        Subject currentUser = SecurityUtils.getSubject();
        Session session = null;
        if (currentUser != null) {
            session = currentUser.getSession();
        }
        if (session == null) {
            throw new IncorrectCredentialsException();
        }
        User user = new User();
        user.setEmpId(token.getEmpId());
        user.setPwd(token.getPwd());
        user.setProjectCode(token.getProjectCode());
        System.out.println("#############################################" + token.getProjectCode());
        String loginType = token.getType();

        if (token.isAuth()) {
            checkVerifyCode(session, token);
        }

        User userWithoutPwd = checkEmpId(user, loginType);

        if ((userWithoutPwd.getIsLocked() != null) && (userWithoutPwd.getIsLocked().equals(Integer.valueOf(1)))) {
            throw new LockedAccountException();
        }

        if ((userWithoutPwd.getIsDisabled() != null) && (userWithoutPwd.getIsDisabled().equals(Integer.valueOf(1)))) {
            throw new DisabledAccountException();
        }

        if ((userWithoutPwd.getPasswdDate() != null) && (userWithoutPwd.getPasswdDate() != null)) {
            Date afterDate = DateUtil.getSpecifiedDay(userWithoutPwd.getPasswdDate(), 180);
            if (DateUtil.compareDate(new Date(), afterDate) == 1) {
                throw new ExpiredCredentialsException();
            }

        }

        if (token.isAuth()) {
            AttributeItem attributeItem = this.attributeItemService.queryByAttrAndItemNo(userWithoutPwd.getProjectCode(), "publicUser", "ISCAUSED");
            if ((attributeItem != null) && (attributeItem.getField3().equals("1")) &&
                    (userWithoutPwd.getOrganization() != null) &&
                    ((userWithoutPwd.getOrganization().getOrgType().intValue() == 1) || (userWithoutPwd.getOrganization().getOrgType().intValue() == 3)) &&
                    (!checkCA(userWithoutPwd, session, token))) {
                throw new ExcessiveAttemptsException();
            }

        }

        User userDB = new User();
        if (token.isAuth()) {
            System.out.println("密码yanzheng登录");
            userDB = this.userService.getLogin(user.getProjectCode(), user);
        } else {
            System.out.println("免密码登录");
            userDB = userWithoutPwd;
        }

        if (userDB == null)
        {
            Integer errTimes = userWithoutPwd.getErrTimes();
            errTimes = Integer.valueOf(errTimes == null ? 0 : errTimes.intValue());
            userWithoutPwd.setErrTimes(errTimes = Integer.valueOf(errTimes.intValue() + 1));
            if (errTimes.intValue() >= 5) {
                userWithoutPwd.setIsLocked(Integer.valueOf(1));
            }
            this.userService.update(user.getProjectCode(), userWithoutPwd);
            throw new UnknownAccountException();
        }

        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(userDB.getId(), token.getPassword(), getName());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!" + userDB.getProjectCode());
        session.setAttribute("currentUser", userDB);

        if ((userDB.getOrganization().getOrgType() != null) && (userDB.getOrganization() != null)) {
            Organization o = userDB.getOrganization();
            String orgname = o.getOrgName();
            int orgtype = o.getOrgType().intValue();
            session.setAttribute("orgType", Integer.valueOf(orgtype));
            session.setAttribute("orgName", orgname);
        }

        String oldSessionId = userDB.getCurrentSessionId();
        if (oldSessionId != null) {
            AttributeItem attributeItem = this.attributeItemService.queryByAttrAndItemNo(userWithoutPwd.getProjectCode(), "publicUser", "ISUSERONE");
            if ((attributeItem != null) && (attributeItem.getField3().equals("1")) && (
                    (attributeItem.getField4() == null) || (!attributeItem.getField4().contains(userWithoutPwd.getEmpId())))) {
                try {
                    String path = this.shiroSessionDAO.getParentPath() + "/" + oldSessionId;
                    Object obj = this.shiroSessionDAO.getClientTemplate().getNode(path);
                    if (obj != null) {
                        Session oldSession = null;
                        try {
                            oldSession = this.shiroSessionDAO.readSession(oldSessionId);
                        } catch (UnknownSessionException localUnknownSessionException) {
                        }
                        if (oldSession != null) {
                            this.shiroSessionDAO.delete(oldSession);
                        }
                    }
                }
                catch (RuntimeException localRuntimeException)
                {
                }
            }
        }

        userDB.setErrTimes(Integer.valueOf(0));

        userDB.setLastDate(new Date());
        userDB.setCurrentSessionId(session.getId().toString());
        this.userService.update(userDB.getProjectCode(), userDB);

        logger.info("[" + userDB.getEmpId() + "] 登录成功！");
        ActionLog al = new ActionLog();
        al.setCreateUser(userDB.getEmpId());
        al.setCreateDate(new Date());
        al.setAppId("ShiroMyRealm");
        al.setAction("login");
        al.setKeyId(String.valueOf(userDB.getId()));
        al.setMemo(userDB.getEmpId() + "登录！");
        this.actionLogService.save(userDB.getProjectCode(), al);

        return authcInfo;
    }

    private User checkEmpId(User user, String loginType) {
        if (loginType == null)
            throw new UnknownAccountException();
        User userWithoutPwd = this.userService.findByEmpId(user.getProjectCode(), user.getEmpId());
        System.out.println("#####1####" + userWithoutPwd.getProjectCode());
        System.out.println("#####2####" + user.getProjectCode());
        if ((userWithoutPwd == null) || (userWithoutPwd.getOrganization() == null) || (userWithoutPwd.getOrganization().getOrgType() == null)) {
            throw new UnknownAccountException();
        }
        if (loginType.equals("1236")) {
            Integer orgType = userWithoutPwd.getOrganization().getOrgType();
            if ((orgType.intValue() == 1) || (orgType.intValue() == 2) || (orgType.intValue() == 3) || (orgType.intValue() == 6)) {
                return userWithoutPwd;
            }
            throw new UnknownAccountException();
        }

        if (loginType.equals("59")) {
            Integer orgType = userWithoutPwd.getOrganization().getOrgType();
            if ((orgType.intValue() == 5) || (orgType.intValue() == 9)) {
                return userWithoutPwd;
            }
            throw new UnknownAccountException();
        }

        if (loginType.equals("5")) {
            Integer orgType = userWithoutPwd.getOrganization().getOrgType();
            if (orgType.intValue() == 5) {
                return userWithoutPwd;
            }
            throw new UnknownAccountException();
        }

        if (loginType.equals("13")) {
            Integer orgType = userWithoutPwd.getOrganization().getOrgType();
            if ((orgType.intValue() == 1) || (orgType.intValue() == 3)) {
                return userWithoutPwd;
            }
            throw new UnknownAccountException();
        }

        throw new UnknownAccountException();
    }

    private void checkVerifyCode(Session session, ShiroMyToken token)
    {
        if (token.getUsername().startsWith("cp_")) {
            return;
        }
        String verifyCode = this.verifyCodeDAO.get(session.getId().toString());
        if (!verifyCode.equals(token.getVcode()))
            throw new IncorrectCredentialsException();
    }

    private boolean checkCA(User user, Session session, ShiroMyToken token)
    {
        if ((user == null) || (StringUtils.isBlank(user.getClientCert()))) {
            return true;
        }

        String userCert = token.getUserCert();
        String userSignedData = token.getUserSignedData();
        String caSignRandom = "";
        System.out.println("取ca随机码");
        Object object = this.verifyCodeDAO.get("pdfSignRandom/" + session.getId());
        System.out.println("object=" + object);
        if (object != null) {
            caSignRandom = (String)object;
        }

        logger.info("caSignRandom=" + caSignRandom);
        try {
            AttributeItem attributeItem = this.attributeItemService.queryByAttrAndItemNo(user.getProjectCode(), "publicUser", "CANAME_SIGN");
            Map resultMap = CommonCA.checkSign(attributeItem.getField3(), userCert, caSignRandom, userSignedData, token.getUserIP());
            System.out.println("resultMap.code=" + (String)resultMap.get("code"));
            System.out.println("resultMap.oid=" + (String)resultMap.get("oid"));

            if ("true".equals(resultMap.get("code"))) {
                String oid = (String)resultMap.get("oid");
                if (user.getClientCert() == null) {
                    System.out.println("user.getClientCert() == null");
                    return false;
                }

                if ((user != null) && (!StringUtils.isBlank(oid)) && (!StringUtils.isBlank(oid)) &&
                        (!StringUtils.isBlank(user.getClientCert())) && (oid.endsWith(user.getClientCert()))) {
                    return true;
                }
                System.out.println(oid + "!=");
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void removeUserCache(String userId)
    {
        SimplePrincipalCollection pc = new SimplePrincipalCollection();
        pc.add(userId, super.getName());
        super.clearCachedAuthorizationInfo(pc);
    }
}