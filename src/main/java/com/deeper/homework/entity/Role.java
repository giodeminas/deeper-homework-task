package com.deeper.homework.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
  ROLE_BASIC("BASIC"),
  ROLE_PREMIUM("PREMIUM");

  private final String value;

  Role(String value) {
    this.value = value;
  }

  @Override
  public String getAuthority() {
    return name();
  }
}