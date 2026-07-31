package com.common.model.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Entity đại diện cho Hồ sơ người dùng (User Profile).
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Entity
@Table(name = "sys_user_profiles")
@Getter
@Setter
@ToString(callSuper = true, exclude = "user")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile extends BaseSoftDeleteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100)
    String firstName;

    @Column(length = 100)
    String lastName;

    @Column(length = 20)
    String phoneNumber;

    @Column(length = 500)
    String avatarUrl;

    @Column(length = 100)
    String position;

    @Column(length = 100)
    String department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    User user;
}
