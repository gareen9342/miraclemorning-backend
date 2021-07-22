package com.miraclemorning.common.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.miraclemorning.domain.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUser extends User {
    private static final long serialVersionUID = 1L;

    private Member member;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(Member member){

        super(member.getUserId()+"", member.getUserPassword(), Collections.singletonList(new SimpleGrantedAuthority(member.getRole())));

        this.member = member;
    }

    public Member getMember(){
        return member;
    }
}
