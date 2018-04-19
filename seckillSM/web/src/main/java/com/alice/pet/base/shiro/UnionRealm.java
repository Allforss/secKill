package com.alice.pet.base.shiro;

import com.alice.pet.base.depend.client.OaClient;
import com.alice.pet.base.depend.client.SsoClient;
import com.alice.pet.base.depend.model.OaModel;
import com.alice.pet.base.depend.model.SsoModel;
import com.alice.pet.base.domain.Menu;
import com.alice.pet.base.domain.User;
import com.alice.pet.base.enums.UserStateEnum;
import com.alice.pet.base.service.MenuService;
import com.alice.pet.base.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义Realm
 *
 * @author xudong.cheng
 * @date 2018/1/16 下午4:42
 */
@Slf4j
public class UnionRealm extends AuthorizingRealm {

    public static final String REALM_NAME = "UnionRealm";

//    @Value("${sso.user.defaultRole}")
//    private String defaultRole;

    @Lazy
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private MenuService menuService;
//
//    @Lazy
//    @Autowired
//    private SsoClient ssoClient;

//    @Lazy
//    @Autowired
//    private OaClient oaClient;

    /**
     * Retrieves the AuthorizationInfo for the given principals from the underlying data store.  When returning
     * an instance from this method, you might want to consider using an instance of
     * {@link SimpleAuthorizationInfo SimpleAuthorizationInfo}, as it is suitable in most cases.
     *
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return the AuthorizationInfo associated with this principals.
     * @see SimpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = ShiroUtil.getCurrentUser();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(user.getRoleIds());
        Set<String> permCodes;
        if (user.getRoleCodes().contains("admin")) {
            permCodes = menuService.findList(null).stream().map(Menu::getPerms).filter(code -> !StringUtils.isEmpty(code)).collect(Collectors.toSet());
        } else {
            permCodes = menuService.findPermCodes(user.getUserId()).stream().filter(code -> !StringUtils.isEmpty(code)).collect(Collectors.toSet());
        }
        authorizationInfo.setStringPermissions(permCodes);
        return authorizationInfo;
    }

    /**
     * Retrieves authentication data from an implementation-specific datasource (RDBMS, LDAP, etc) for the given
     * authentication token.
     * <p/>
     * For most datasources, this means just 'pulling' authentication data for an associated subject/user and nothing
     * more and letting Shiro do the rest.  But in some systems, this method could actually perform EIS specific
     * log-in logic in addition to just retrieving data - it is up to the Realm implementation.
     * <p/>
     * A {@code null} return value means that no account could be associated with the specified token.
     *
     * @param token the authentication token containing the user's principal and credentials.
     * @return an {@link AuthenticationInfo} object containing account data resulting from the
     * authentication ONLY if the lookup is successful (i.e. account exists and is valid, etc.)
     * @throws AuthenticationException if there is an error acquiring data or performing
     *                                 realm-specific authentication logic for the specified <tt>token</tt>
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String account = token.getPrincipal().toString();
        AuthcToken authcToken = (AuthcToken) token;
//        SsoModel ssoModel = ssoClient.login(account, DigestUtils.md5DigestAsHex(new String((char[]) token.getCredentials()).getBytes()), authcToken.getCaptcha(), "commonsafety.oa.com");
//        ssoClient.verifty(ssoModel);
        User user = userService.getBasicForLogin(account);
//        if (user == null) {
//            user = new User(account);
//            OaModel oaModel = null;
//            try {
//                oaModel = oaClient.query(account);
//                oaClient.verifty(oaModel);
//                user.setName(oaModel.getData().getName());
//            } catch (feign.codec.DecodeException e) {
//                log.warn("OA 服务不可用");
//                user.setName(account.contains("@") ? account.substring(0, account.indexOf("@")) : account);
//            }
//            user.setState(UserStateEnum.ENABLE.state);
//            user.setRoleIds(Collections.singleton(defaultRole));
//            this.saveNewUser(user);
//        } else
        if (UserStateEnum.DISABLED.state == user.getState()) {
            throw new DisabledAccountException();
        }
        return new SimpleAuthenticationInfo(
                user.getUserId(),
                user.getPassword(),
                this.getName());
    }

    /**
     * 对未入库的统一认证用户进行入库操作
     */
    protected void saveNewUser(User user) {
        // TODO 可为异步操作，该步骤不影响正常登录
        try {
            userService.addWithRoles(user);
        } catch (RuntimeException e) {
            log.error("新增统一登陆人员失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return REALM_NAME;
    }

}
