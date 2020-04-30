package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/*
@JsonView主要作用：
在指定视图下显示指定字段，
子类视图会继承父类视图所显示的字段
 * @JsonView使用步骤：
 * 1.使用接口来声明多个视图
 * 2.在值对象的get方法上指定视图
 * 3.在Controller方法上指定视图
 */
public class User {

    // 1.使用接口来声明多个视图
    public interface UserSimpleView {}
    public interface UserDetailView extends UserSimpleView {}

    private String id;

    private String username;

    @NotBlank
    private String password;

    private Date birthday;

    // 2.在值对象的get方法上指定视图
    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
