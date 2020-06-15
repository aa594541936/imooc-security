package com.imooc.security.browser;

import com.imooc.security.browser.authentication.ImoocAuthenticationFailureHandler;
import com.imooc.security.browser.authentication.ImoocAuthenticationSuccessHandler;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    ImoocAuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;

    // 用于加密密码
    // 传入明文密码，返回加密后的密码
    // String encode(CharSequence rawPassword);

    // rawPassword：用户登录的时候输入的明文密码
    // encodedPassword：加密后的密码（也就是上面那个encode方法加密后返回的密码），这个密码是存在数据库中的
    // boolean matches(CharSequence rawPassword, String encodedPassword);
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);



        http    .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 表单登录
                .formLogin()
                // 设置登录页面
//                .loginPage("/imooc-signIn.html")
                .loginPage("/authentication/require")
                // 设置处理登录请求的url
                .loginProcessingUrl("/authentication/form")
                .successHandler(imoocAuthenticationSuccessHandler)
                .failureHandler(imoocAuthenticationFailureHandler)
                // Basic登录
//                .httpBasic()
                .and()
                // 对请求进行授权，在这句话下面的都是授权的配置
                // 所有的请求都需要身份认证后才能进行访问
                .authorizeRequests()
                // 登录页面不需要认证就可以访问
//                .antMatchers("/imooc-signIn.html", securityProperties.getBrowser().getLoginPage()).permitAll()
                .antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage(), "/code/image").permitAll()
                // 任何请求
                .anyRequest()
                // 都需要身份验证（认证）
                .authenticated()
                .and()
                // 把跨域保护先关闭，SpringSecurity默认是开启的
                .csrf().disable();
    }
}
