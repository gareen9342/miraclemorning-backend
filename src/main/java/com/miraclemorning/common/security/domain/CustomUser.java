package com.miraclemorning.common.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.miraclemorning.domain.Member;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUser extends User {
    private static final long serialVersionUID = 1L;

    private Member member;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(Member member){
        super(member.getEmail(), member.getPassword(), member.getAuthList().stream().map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));

        this.member = member;
    }

    public CustomUser(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getEmail(), member.getPassword(), authorities);

        this.member = member;
    }

    public Long getMemberId(){
        return member.getId();
    }

    public String getMemberEmail(){
        return member.getEmail();
    }

    public Member getMember(){
        return member;
    }
}
