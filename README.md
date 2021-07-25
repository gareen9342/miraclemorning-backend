# Miracle Morning - Back end 

> 나의 미라클 모닝 프로젝트! (백엔드)
Spring Boot + JPA 첫 도전기 🥸     


---
계획, 어떤 것이든 코드를 **매일** 작성하고 공부하고 정리하기. 일정관리는 노션으로 따로.    
다 할 수 있을까 ㅎㅎㅎ 그래도 일단 해보기🤾🏻‍♂️


## 기능
계획...

### 회원관련 
- [x] : 이메일 중복검사   
- [ ] : 회원가입시 비밀번호 해싱(Bcrypt)   
- [ ] : 로그인 JWT 토큰 발급
- [ ] : 유저별 권한 적용 및 API 호출 시 권한 확인, 없을시 기능 호출 못하게 (Spring Security 이용..?) 
- [ ] : 비밀번호 잊었을시 비밀코드 발급, 메일로 보내주기 (정책 고민 중...)   
- [ ] : 리프레쉬 토큰 관련 공부하고 고민하기. 정리하기.

### 개인 정보 관련
- [ ] : 맨 처음 화면에선 나의 투두리스트를 정리한 것과 / 최근 나의 게시물의 좋아요나 댓글 등을 정리하기  
- [ ] : 아바타 사진 업로드, 수정, 삭제 구현
- [ ] : 닉네임 수정
- [ ] : 비밀번호 수정 : 기존 비밀번호 확인 -> 인증메일 발송 -> 확인 될 시 수정 진행 // 이것에 대해 어떻게 진행할까에 대한 고민하기. 

### To do list (계획) 페이지
- [ ] : 이런 팀별 계획을 볼 수 있는 권한은 어떻게? 외부 사람도? 아니면 팀 멤버만?
- [ ] : 첫 화면에선 같은 팀의 멤버별 투두리스트, 순서에 대한 고민은 아직은 하지 않기로, 하지만 순서를 개인별 커스텀 할 수 있는 기능을 넣게 된다면..? 고민중..
- [ ] : 기본적인 CRUD
- [ ] : 재밋는 투두에 대한 것을 좋아요를 카운트해서 수집해 놓는 건 어떨까?     
- [ ] : 개인별 투두리스트는 하나의 정보로 분류시키고, 댓글을 달 수 있도록 

### Calendar(한 눈에 무엇을 했는지 볼 수 있는 것)
- [ ] : 외부 사람들도 볼 수 있고 없고를 바꿔주고 싶다. 어떻게 할까. 프론트단의 페이지를 하나 더 만들어 API를 두 개 만들기..?
- [ ] : 날짜별 할 일이나 개인 이벤트를 넣을 것인지, 말 것인지
- [ ] : 날짜별 개인이 달성할 일들이나 아침의 생각을 적을 수 있게
- [ ] : 각 게시물 별 좋아요
- [ ] : 각 게시물 별 덧글 

### dashboard 
- [ ] : 최근 순서로 게시물의 이미지와 아이디값을 프론트로 보냄
- [ ] : 일정 단위로 나누어 출력, 무한 스크롤링 
