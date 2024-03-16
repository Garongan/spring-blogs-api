package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
