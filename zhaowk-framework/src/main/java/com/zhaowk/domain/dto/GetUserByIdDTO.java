package com.zhaowk.domain.dto;

import com.zhaowk.domain.entity.Role;
import com.zhaowk.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByIdDTO {
    private List<Long> roleIds;
    private List<Role> roles;
    private User user;
}
