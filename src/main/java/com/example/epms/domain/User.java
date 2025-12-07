package com.example.epms.domain;

import com.example.epms.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "`User`",
       uniqueConstraints = @UniqueConstraint(name = "uk_user_email", columnNames = "email"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;

  @Email @NotBlank
  @Column(nullable = false, length = 255, unique = true)
  private String email;

  @NotBlank
  @Column(nullable = false, length = 255)
  @JsonIgnore
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private Role role;
}