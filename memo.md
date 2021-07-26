# 메모할 것 여기서 정리

## Entity 관련
### cascade란 
출처 https://velog.io/@max9106/JPA%EC%97%94%ED%8B%B0%ED%8B%B0-%EC%83%81%ED%83%9C-Cascade   
: _OneToMany 나 ManyToOne에 옵션으로 줄 수 있는 값_   
Entity의 상태 변화를 전파시키는 옵션    
Entity에 상태 변화가 있으면 연관되어 있는 Entity에도 상태 변화를 전이시키는 옵션   
기본적으로는 아무 것도 전이시키지 않음

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



