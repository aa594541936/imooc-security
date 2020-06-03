package com.imooc.security.browser;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名查找用户信息
        System.out.println("登录的用户名：" + username);

        // 第一个参数：用户名
        // 第二个参数：数据库存的密码
        // 第三个参数：用户所拥有的权限
        // isAccountNonExpired()：账户是否过期
        // isAccountNonLocked()：账户是否锁定（冻结）
        // isCredentialsNonExpired()：账户的密码是否过期了
        // isEnabled()：账户是否启用（可以表示为用户是否被删除了）
//        return new User(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        // 这里passwordEncoder.encode("123456")应该在注册时候进行加密并且存入数据库，然后下次使用的时候直接从数据库中拿那个密文密码出来就可以了
        return new User(username, passwordEncoder.encode("123456"), true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
