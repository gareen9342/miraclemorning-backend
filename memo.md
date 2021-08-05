# 메모할 것 여기서 정리

## Entity 관련
### cascade란 
출처 https://velog.io/@max9106/JPA%EC%97%94%ED%8B%B0%ED%8B%B0-%EC%83%81%ED%83%9C-Cascade   
: _OneToMany 나 ManyToOne에 옵션으로 줄 수 있는 값_   
Entity의 상태 변화를 전파시키는 옵션    
Entity에 상태 변화가 있으면 연관되어 있는 Entity에도 상태 변화를 전이시키는 옵션   
기본적으로는 아무 것도 전이시키지 않음

## Validations
: _validation annotations : request parameter의 입력값을 검증한다. spring mvc의 validation을 애터테이션을 이용해서 쉽게 관리가능_   

- Size : max, min등으로 길이를 지정할 수 있
- NotMull : 빈 값이 아닌지를 검사
- NotBlank : 문자열이 null이 아니고 trim 한 길이가 0보다 크다는 것을 검사
- Size : 글자 수나 컬렉션의 요소 개수 검사
- Email : 이메일 주소 형식인지를 검사함
- Past : 과거 날짜인지 검사

## 라이브러리

: Jackson ObjectMapper   
Jackson에서 제공하는 것.... Parsing 과정에 필요한 커스터 마이징을 제공함.    
Java Object <-> Json     

예
Convert "Java Object" to "JSON"   
Convert "JSON" to "Java Object"   
Convert "JSON" to "Jackson JsonNode"   
Convert "JSON Array String" to "Java List"   
Convert "JSON String" to "Java Map"   


출처: https://interconnection.tistory.com/137 [라이언 서버]   

## Spring Security 관련
자세한 부분은 따로 TIL에 적어서 공부해야 할 듯..    

### 표현식      
- hasRole([role]) : 현재 사용자의 권한이 role 과 같으면 true    
- hasAnyRole([role1, role2]) : 여러 롤들 중에서 하나라도 해당하는 롤이 있으면 true    
- principal : 인증된 사용자의 사용자 정보(UserDetails 인터페이스를 구현한 클래스의 객체)에 직접 접근이 가능하다.    
- authentication : 인증된 사용자의 인증정보. SecurityContext에 있는 authentication 객체에 접근할 수 있다.    
- permitAll : 모든 사용자에게 허용     
- denyAll : 모든 사용자에게 거부   
- isRememberMe() : RememberMe 사용자인지
- isAuthenticated() : 인증된 사용자면 true - _로그인한 사용자를 뜻함.._   
- isFullyAuthenticated : RememberMe 로 인증된 것이 아닌 일반적인 방법으로 인증된 사용자인 경우 true   


### 사용
- Secured : 시큐리티 모듈을 지원하기 위한 애너테이션으로 초기부터 사용됨    
- PreAuthorize : 함수를 실행 전 / 요청이 들어와 **함수를 실행하기 전**에 권한을 검사함.   
- PostAuthorize : 함수를 실행 후 / 클라이언트 응답 직전에 권한을 검사함    

_여기서 returnObject란 함수가 반환하는 데이터 오브젝트를 의미_    
_설정에 prePostEnabled속성을 true로 설정하면 @PreAuthorize, @PosrAuthorize 를 사용할 수 있고 securedEnabled속성을 true 로 설정하면 @Secured를 사용할 수 있다._


참고 : https://steemit.com/kr-dev/@igna84/spring-security-preauthorize-postauthorize    
```java
@PostAuthorize("isAuthenticated() and (( returnObject.name == principal.name ) or hasRole('ROLE_ADMIN'))")
@RequestMapping(value="", method=RequestMethod.PUT)
public User getUser(@PathVariable("seq") logn seq){
    return userService.findOne(seq);
}
```

### CORS 관련

내가 걷은 이슈 메세지 : "When allowCredentials is true, allowedOrigins cannot contain the special value "*" since that cannot be set on the "Access-Control-Allow-Origin" response header. To allow credentials to a set of origin"    
allow credentials true 로 설정하면 allowedOrigin를 "*"로 설정할 수 없다고 한다.

요청의 자격증명 모드 ( Request.credentials ) 가 include이면 Access-Control-Allow-Credentials 값이 true 일 경우에만 브라우저들은 응답을 노출한다.       
-> 이렇게 했는데 왜 안될까 -> 는 에러메세지에 나와있다     
-> 해당하는 자격증명들은 쿠키, authorization headers or TLS 클라이언트 인증서      
-> TLS란 SSL Handshaking 의 업그레이드된 버전이라고 생각하면 될 듯?       


https://heowc.dev/2018/03/13/spring-boot-cors/

https://engineering.linecorp.com/ko/blog/best-practices-to-secure-your-ssl-tls/
https://m.blog.naver.com/sung_mk1919/221598350824     //SSL handshaking, TLS에 대한 내용, 너무 어렵다 그래도 이게 자세함

https://m.blog.naver.com/sung_mk1919/221467675499 // HTTPS 

### credentialed Request

인증된 요청을 사용하는 방법    
브라우저의 비동기 리소스 요청 API인 XMLHttpRequest나 fetch API는 별도의 옵션 없이 브라우저의 쿠키 정보나 인증과 관련된 헤더를 함부로 요청에 담지 않는다    
이떄 요청에 인증과 관련된 정보를 담을 수 있게 해주는 옵션이 바로 credentials 옵션      

이 옵션에는 세 가지 값이 들어간다.    
- same-origin : 같은 출처간 요청에만 인증정보를 담을 수 있다.     
- include : 모든 요청에 인증정보를 담을 수 있다.    
- omit : 모든 요청에 인증 정보를 담지 않는다.    

**이 옵션들이 들어가면 브라우저는 리소스를 요청할 때 단순히 Access-Control-Allow-Origin 만 확인하는 것이 아니라 좀 더 빡빡한 검사 조건을 건다.**
그리고 이 경우가 나의 에러 메세지의 원인... 

-> 자격증명모드가 include일 경우?    

1. Access-Control-Allow-Origin에는 *사용 불가, 명시적인 URL이어야 함.
2. 응답 헤더에는 반드시 Access-Control-Allow-Credentials가 true 여야 함. 


### allowedOriginPatterns 
-> allowedOrigins에 확장성있게 추가된 기능. -> allowedOrigin은 특정한 도메인만 받을 수 있다. -> cors스펙상 "*"가 허용되지 않음. 
-> setAllowedOriginPatterns를 대신 사용하는 것이 권장되고 있다. 

-> 와일드카드를 이용해서 좀 더 확장성 있는 도메인 패턴을 적용할 수 있도록 도와준다. 

-> 이슈 설명 링크
https://github.com/spring-projects/spring-framework/issues/26111#issuecomment-729805112

-> 좀 더 자세한 설명
https://github.com/spring-projects/spring-framework/commit/0e4e25d227dedd1a3ecddc4e40c263f190ca1c2b
_위에서 부터 쭉 읽어보기. 166번째 줄의 setAllowedOriginPatterns를 적용할 것_ 