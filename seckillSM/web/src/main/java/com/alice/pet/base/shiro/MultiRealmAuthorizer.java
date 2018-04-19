package com.alice.pet.base.shiro;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;

import java.util.Map;

/**
 * @author xudong.cheng
 * @date 2018/1/25 下午2:12
 */
public class MultiRealmAuthorizer extends ModularRealmAuthorizer {

    private Map<String, Realm> definedRealms;

    public void setDefinedRealms(Map<String, Realm> definedRealms) {
        this.definedRealms = definedRealms;
    }

    /**
     * Used by the {@link Authorizer Authorizer} implementation methods to ensure that the {@link #setRealms realms}
     * has been set.  The default implementation ensures the property is not null and not empty.
     *
     * @throws IllegalStateException if the <tt>realms</tt> property is configured incorrectly.
     */
    @Override
    protected void assertRealmsConfigured() throws IllegalStateException {
        if (CollectionUtils.isEmpty(this.definedRealms)) {
            throw new ShiroException("No realms have been configured!");
        }
    }

    /**
     * Returns <code>true</code> if any of the configured realms'
     * {@link #isPermitted(org.apache.shiro.subject.PrincipalCollection, String)} returns <code>true</code>,
     * <code>false</code> otherwise.
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        if (principals.getRealmNames().contains(AuthcRealm.REALM_NAME)) {
            Realm realm = definedRealms.get(AuthcRealm.REALM_NAME);
            return ((Authorizer) realm).isPermitted(principals, permission);
        } else if (principals.getRealmNames().contains(UnionRealm.REALM_NAME)) {
            Realm realm = definedRealms.get(UnionRealm.REALM_NAME);
            return ((Authorizer) realm).isPermitted(principals, permission);
        }
        return false;
    }

    /**
     * Returns <code>true</code> if any of the configured realms'
     * {@link #isPermitted(org.apache.shiro.subject.PrincipalCollection, Permission)} call returns <code>true</code>,
     * <code>false</code> otherwise.
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        assertRealmsConfigured();
        if (principals.getRealmNames().contains(AuthcRealm.REALM_NAME)) {
            Realm realm = definedRealms.get(AuthcRealm.REALM_NAME);
            return ((Authorizer) realm).isPermitted(principals, permission);
        } else if (principals.getRealmNames().contains(UnionRealm.REALM_NAME)) {
            Realm realm = definedRealms.get(UnionRealm.REALM_NAME);
            return ((Authorizer) realm).isPermitted(principals, permission);
        }
        return false;
    }


}