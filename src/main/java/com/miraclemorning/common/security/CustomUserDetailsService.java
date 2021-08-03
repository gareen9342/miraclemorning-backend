package com.miraclemorning.common.security;

import com.miraclemorning.common.security.domain.CustomUser;
import com.miraclemorning.domain.Member;


import com.miraclemorning.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Log
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        log.info(useremail);

        Member member = memberRepository.findByEmail(useremail).get();

        log.info("member" + member);
        return UserPrincipal.create(member);
    }

    @Transactional
    public UserDetails loadUserById(Long id){
        Member member = memberRepository.getById(id);
        return UserPrincipal.create(member);
    }
}
