package com.hilbert.core.user.service;

import com.hilbert.core.user.model.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetailsImpl loadUserByUsername(String username) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER"));
        return new UserDetailsImpl(username, authorities);
    }
}
