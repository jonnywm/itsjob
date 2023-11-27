package br.com.ist.job.security.service;

import br.com.ist.job.security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    User findByEmail(String string);
}
