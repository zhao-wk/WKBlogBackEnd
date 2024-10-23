package com.zhaowk.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoVO {
    private Long id;
    private String nickName;
    private String avatar;
    private String sex;
    private String email;

}
