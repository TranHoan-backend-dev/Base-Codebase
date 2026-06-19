package com.common.config.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Lớp chứa thông tin chi tiết của người dùng hiện tại phục vụ ThreadLocal.<br/>
 * Created at 19/06/2026
 *
 * @author txhoan
 */
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserContext {
    String userId;
    String username;
    List<String> roles;
}
