# trello

## 팀원소개
> 팀장 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;김동민 (댓글)<br>
> 팀원 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;김재한 (보드)<br>
> 팀원 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;박상율 (카드)<br>
> 팀원 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;안태인 (컬럼)<br>
> 팀원 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;유민아 (사용자)<br>

<br>

## 디렉토리 구조
```
│  TrellorApplication.java
│
├─domain
│  │  dto.java
│  │
│  ├─board
│  │  ├─controller
│  │  │      BoardController.java
│  │  │
│  │  ├─dto
│  │  │      BoardCreateRequestDto.java
│  │  │      BoardCreateResponseDto.java
│  │  │      BoardInviteRequestDto.java
│  │  │      BoardInviteResponseDto.java
│  │  │      BoardReadAllResponseDto.java
│  │  │
│  │  ├─entity
│  │  │      Board.java
│  │  │      UserBoard.java
│  │  │
│  │  ├─repository
│  │  │      BoardRepository.java
│  │  │      UserBoardRepository.java
│  │  │
│  │  └─service
│  │          BoardService.java
│  │
│  ├─card
│  │  │  Test.java
│  │  │
│  │  ├─controller
│  │  │      CardController.java
│  │  │
│  │  ├─dto
│  │  │      CardRequestDto.java
│  │  │      CardResponseDto.java
│  │  │      CommonResponseDto.java
│  │  │
│  │  ├─entity
│  │  │      Card.java
│  │  │
│  │  ├─repository
│  │  │      CardRepository.java
│  │  │
│  │  └─service
│  │          CardService.java
│  │
│  ├─column
│  │  ├─controller
│  │  │      BoardColumnController.java
│  │  │
│  │  ├─dto
│  │  │      BoardColumnMoveRequestDto.java
│  │  │      BoardColumnMoveResponseDto.java
│  │  │      BoardColumnRequestDto.java
│  │  │      BoardColumnUpdateRequestDto.java
│  │  │      MessageDto.java
│  │  │      MessageUpdateDto.java
│  │  │
│  │  ├─entity
│  │  │      BoardColumn.java
│  │  │
│  │  ├─repository
│  │  │      BoardColumnRepository.java
│  │  │
│  │  └─service
│  │          BoardColumnService.java
│  │
│  ├─comment
│  │  │  Test.java
│  │  │
│  │  ├─controller
│  │  │      CommentController.java
│  │  │
│  │  ├─dto
│  │  │  ├─request
│  │  │  │      CommentRequestDto.java
│  │  │  │
│  │  │  └─response
│  │  │          CommentResponseDto.java
│  │  │
│  │  ├─entity
│  │  │      Comment.java
│  │  │
│  │  ├─repository
│  │  │      CommentRepository.java
│  │  │
│  │  └─service
│  │          CommentService.java
│  │
│  ├─user
│  │  ├─controller
│  │  │      UserController.java
│  │  │
│  │  ├─dto
│  │  │  └─request
│  │  │          EmailUpdateRequestDto.java
│  │  │          LoginRequestDto.java
│  │  │          PasswordUpdateRequestDto.java
│  │  │          SignupRequestDto.java
│  │  │
│  │  ├─entity
│  │  │      User.java
│  │  │      UserRoleEnum.java
│  │  │
│  │  ├─repository
│  │  │      UserRepository.java
│  │  │
│  │  └─service
│  │          UserService.java
│  │
│  └─utils
│          BaseTime.java
│
└─global
    │  Test.java
    │
    ├─config
    │      AuditingConfig.java
    │      WebSecurityConfig.java
    │
    ├─jwt
    │      JwtUtil.java
    │      SecurityUtil.java
    │
    └─security
            JwtAuthenticationFilter.java
            JwtAuthorizationFilter.java
            UserDetailsImpl.java
            UserDetailsServiceImpl.java
```

<br>
<br>

## API 명세서
### 사용자 기능
![image](https://github.com/moonnnnnnn2541354/trello/assets/96765898/f092824c-cae8-4f48-a7ba-1f9907bf68a5)
<br>
### 보드 기능
![image](https://github.com/moonnnnnnn2541354/trello/assets/96765898/8e120dae-6eb0-424b-a6d2-cc123fdbf342)
<br>
### 컬럼 기능
![image](https://github.com/moonnnnnnn2541354/trello/assets/96765898/f54af4c8-779f-40fe-93ac-1e6514b8c186)
<br>
### 카드 기능
![image](https://github.com/moonnnnnnn2541354/trello/assets/96765898/029c51d1-0459-4965-a359-71c63709f478)
![image](https://github.com/moonnnnnnn2541354/trello/assets/96765898/4ac1f7da-afbf-4b9d-aa38-cd2fc375d95e)
<br>
### 댓글 기능
![image](https://github.com/moonnnnnnn2541354/trello/assets/96765898/cab2bbe5-da3b-464d-9351-9a82094862ed)

<br>
<br>

# ERD
![image](https://github.com/moonnnnnnn2541354/trello/assets/96765898/39c0942b-3fe4-4ba8-9578-1874e4314ac1)

<br>
<br>

# 와이어프레임
![image](https://github.com/moonnnnnnn2541354/trello/assets/96765898/754d9859-9995-4de1-9102-e8896a93b04c)

