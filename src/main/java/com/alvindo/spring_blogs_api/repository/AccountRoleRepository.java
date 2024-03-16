package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleRepository extends JpaRepository<AccountRole, String> {
}
