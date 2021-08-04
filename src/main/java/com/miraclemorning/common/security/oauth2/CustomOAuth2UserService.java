package com.miraclemorning.common.security.oauth2;

import com.miraclemorning.common.security.UserPrincipal;
import com.miraclemorning.common.security.oauth2.user.OAuth2UserInfo;
import com.miraclemorning.common.security.oauth2.user.OAuth2UserInfoFactory;
import com.miraclemorning.domain.AuthProvider;
import com.miraclemorning.domain.Member;
import com.miraclemorning.exception.OAuth2AuthenticationProcessingException;
import com.miraclemorning.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        
        if(StringUtils.hasLength(oAuth2UserInfo.getEmail())) { // isEmpty deprecated 되었길래 이걸로 바꿔봄 ...
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        // ====== db에서 멤버 정보 불러오는 단 ====== //
        Optional<Member> memberOptional = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
        Member member;
        if(memberOptional.isPresent()) {
            member = memberOptional.get();
            if(!member.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                //  스프링단에서 따로 에러처리를 할 때는 Exception Handler를 직접 만들어서 구현하게 된다
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        member.getProvider() + " account. Please use your " + member.getProvider() +
                        " account to login.");
            }
            member = updateExistingUser(member, oAuth2UserInfo);
        } else {
            member = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(member, oAuth2User.getAttributes());
    }

    private Member registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Member member = new Member();

        member.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        member.setProviderId(oAuth2UserInfo.getId());
        member.setName(oAuth2UserInfo.getName());
        member.setEmail(oAuth2UserInfo.getEmail());
        member.setAvatar(oAuth2UserInfo.getImageUrl());
        return memberRepository.save(member);
    }

    private Member updateExistingUser(Member existingMember, OAuth2UserInfo oAuth2UserInfo) {
        existingMember.setName(oAuth2UserInfo.getName());
        existingMember.setAvatar(oAuth2UserInfo.getImageUrl());
        return memberRepository.save(existingMember);
    }
}
