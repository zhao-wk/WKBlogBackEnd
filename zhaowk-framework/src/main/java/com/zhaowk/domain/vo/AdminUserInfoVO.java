package com.zhaowk.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVO {
    private List<String> permissions;
    private List<String> roles;
    private UserInfoVO user;
}
