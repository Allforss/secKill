package com.alice.pet.base.shiro;

import com.alice.pet.base.enums.LoginUserTypeEnum;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;

import java.util.Map;

/**
 * @author xudong.cheng
 * @date 2018/1/24 下午6:48
 */
public class MultiRealmAuthenticator extends ModularRealmAuthenticator {

    private Map<String, Realm> definedRealms;

    public void setDefinedRealms(Map<String, Realm> definedRealms) {
        this.definedRealms = definedRealms;
    }

    /**
     * 判断登录类型执行操作
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        this.assertRealmsConfigured();
        Realm realm;
        AuthcToken token = (AuthcToken) authenticationToken;
        //判断是否是后台用户
        if (LoginUserTypeEnum.manager.equals(token.getType())) {
            realm = this.definedRealms.get(AuthcRealm.REALM_NAME);
        } else {
            realm = this.definedRealms.get(UnionRealm.REALM_NAME);
        }
        return this.doSingleRealmAuthentication(realm, authenticationToken);
    }

    /**
     * 判断realm是否为空
     */
    @Override
    protected void assertRealmsConfigured() throws IllegalStateException {
        if (CollectionUtils.isEmpty(this.definedRealms)) {
            throw new ShiroException("No realms have been configured!");
        }
    }

}
