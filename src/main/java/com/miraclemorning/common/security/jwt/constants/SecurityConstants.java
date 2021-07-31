package com.miraclemorning.common.security.jwt.constants;

// 상수값 정의 클래스
public class SecurityConstants {

    /** ====== 프론트에서 인증 요청하는 쪽은 이곳으로 ======
     * 로그인 인증 URL
        api/authenticate
        {username : username , password : password}
    */

    public static final String AUTH_LOGIN_URL = "/api/authenticate";

    // ====== JWT Secret (SH512) 생성링크 : http://www.allkeysgenerator.com/ ======//
    public static final String JWT_SECRET = "E(H+MbPeShVmYq3t6w9z$C&F)J@NcRfTjWnZr4u7x!A%D*G-KaPdSgVkYp2s5v8y";

    // ====== tokens ======//
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";

    // ====== end 인증 ========
    public static final String JWT_INVITE_SECRET = "(G+KbPdSgVkYp3s6v9y$B&E)H@McQfThWmZq4t7w!z%C*F-JaNdRgUkXn2r5u8x/";

}
