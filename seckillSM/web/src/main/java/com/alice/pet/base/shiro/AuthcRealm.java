package com.alice.pet.base.shiro;

import com.alice.pet.base.domain.Menu;
import com.alice.pet.base.domain.User;
import com.alice.pet.base.enums.UserStateEnum;
import com.alice.pet.base.service.MenuService;
import com.alice.pet.base.service.UserService;
import com.alice.pet.base.utils.PasswordUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义Realm
 *
 * @author xudong.cheng
 * @date 2018/1/16 下午4:42
 */
public class AuthcRealm extends AuthorizingRealm {

    public static final String REALM_NAME = "AuthcRealm";

    @Lazy
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private MenuService menuService;

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
        User user = userService.getBasicForLogin(token.getPrincipal().toString());
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (UserStateEnum.DISABLED.state == user.getState()) {
            throw new DisabledAccountException();
        }
        return new SimpleAuthenticationInfo(
                user.getUserId(),
                user.getPassword(),
                PasswordUtil.SALT_BYTE_SOURCE,
                this.getName());
    }

    @Override
    public String getName() {
        return REALM_NAME;
    }

}
