# com.kkamjidot.api.mono
#### v2.5.8(2022.11.1.)
- 퀴즈 개요 조회 및 내 퀴즈 조회에 문제 풀린 횟수 포함
  - 내가 작성한 퀴즈 주차별 개요 목록 조회 API
  - 퀴즈 개요 목록 조회 API
  - 좋아요한 문제들 개요 목록 조회 API
  - 내 퀴즈 전체 내용 조회 API
#### v2.5.7(2022.11.1.)
- jwt 인증 인터셉터 추가
#### v2.5.6(2022.10.26.)
- 포인트 기능 변경
  - 내역 관리 가능
#### v2.5.5(2022.10.18.) 
- 비밀번호 검증 기능 추가
- solve_id 오타 변경
- 포인트 테이블 추가
#### v2.5.4 포인트 조회 API 추가
- GET v1/my/point 포인트 조회 API 추가 
#### v2.5.3 푸시 알림용 FCM 토큰 등록 API 추가
- POST v1/notification/register 푸시 알림용 FCM 토큰 등록 API
#### v2.5.2 퀴즈 내용 조회 API에 문제 풀린 횟수 추가
- GET v1/quizzes/{quizId}/content 퀴즈 내용 조회 API 응답에 문제 풀린 횟수 추가
- Integer cntOfSolved
#### v2.5.1 퀴즈 정답 조회 API V2로 변경
- ~~GET v1/quizzes/{quizId}/grade~~ 퀴즈 풀기 채점 점수 제출 API V1 Deprecated
- GET v2/quizzes/{quizId}/solve 퀴즈 정답 조회 API 추가
- ```json
  {
    "quiz": {
      "quizId": 1,
      "answer": "R=8.3144J/mol",
      "explanation": "1atm = 101325N/m^2 이다.....",
      "rubric": "[{\"score\":\"5\",\"content....."
    },
    "solve": {
      "answer": "1liter = 10^-3m^-3",
      "score": 5,
      "rubric": "1liter = 10^-3m^-3 임을 알고 있다."
    }
  }
  ```
#### v2.5.0 퀴즈 조회 API -> 퀴즈 내용 조회 API 변경
- ~~GET v1/quizzes/{quizId}~~ 퀴즈 조회 API Deprecated
- GET v1/quizzes/{quizId}/content 퀴즈 내용 조회 API 추가
  ```json
  {
    "challengeId": 1,
    "quizId": 1,
    "quizTitle": "gas constant unit conversion (기체상수 단위 환산)",
    "quizWeek": 1,
    "quizContent": "R=0.082057 liter*atm/mol을 J/mol로 단위를 환산하시오\n",
    "quizCreatedDate": "2022-09-09T00:17:33",
    "quizModifiedDate": null,
    "writerName": "홍길동",
    "quizFiles": [],
    "cntOfGood": 1,
    "quizInfoByUser": {
      "userId": 1,
      "isMine": true,
      "didIRate": "GOOD",
      "solveAnswer": "정답"
    }
  }
  ```
#### v2.4.7 응답 객체 변경
- 범위
  - GET v1/challenges/{challengeId}/quizzes 퀴즈 개요 목록 조회 API
  - GET v1/challenges/{challengeId}/my/quizzes 내가 작성한 퀴즈 주차별 개요 조회 API
  - GET v1/challenges/{challengeId}/my-good-quizzes 좋아요한 문제들 조회 API
- 변경 내용
  - isScored로 제거
#### v2.4.6 응답 객체 변경
- 범위
  - GET v1/challenges/{challengeId}/quizzes 퀴즈 개요 목록 조회 API
  - GET v1/challenges/{challengeId}/my/quizzes 내가 작성한 퀴즈 주차별 개요 조회 API
  - GET v1/challenges/{challengeId}/my-good-quizzes 좋아요한 문제들 조회 API
- 변경 내용
  1. 기존 solveAnswer, solveScore는 deprecated 처리함. 삭제 요망
  2. 정답 제출 여부는 isSolved로, 채점 여부는 isScored로 처리
  - 코드
    ```java
    @Deprecated private final String solveAnswer; 
    @Deprecated private final Integer solveScore; 
    @Schema(description = "정답 제출 여부") private final Boolean isSolved; 
    @Schema(description = "채점 여부") private final Boolean isScored;	
    ```

#### v2.4.5 퀴즈 모든 주차 목록 조회
- GET v1/challenges/{challengeId}/quizzes 퀴즈 개요 목록 조회 API 모든 주차 조회 가능하게 변경
- week가 빠지면 모든 주차 반환
- 사용 예시
  - GET v1/challenges/{challengeId}/quizzes
  - GET v1/challenges/{challengeId}/quizzes?week
  - GET v1/challenges/{challengeId}/quizzes?week=
#### v2.4.4 퀴즈 제출할 때 선택된 루브릭 같이 제출
- POST v2/quizzes/{quizId}/grade 퀴즈 풀기 채점 점수 제출 API V2
- solveRubric 필수 입력
  ```java
  @Schema(description = "점수", example = "0", required = true)  
  private Integer score;  
    
  @NotBlank @Size(max = 3500)  
  @Schema(description = "선택된 루브릭", example = "선택된 루브릭 내용입니다.", required = true)  
  private String solveRubric;
  ```
#### v2.4.3 퀴즈 조회할 때 풀 때 선택된 루브릭 같이 조회
- GET v1/quizzes/{quizId} 퀴즈 조회 API
- solveRubric 추가
#### v2.4.2: 토큰 등록 기능 추가(2022.10.14.)
### 2022.10.13. 시험 기간 챌린지 운영 관련 대규모 업데이트
#### v2.4.1 - 챌린지 주차 정보 목록 조회 API 복구(2022.10.13.)
- GET v1/challenges/{challengeId}/weeks 챌린지 주차 정보 목록 조회 API Deprecated 제거
#### v2.4.0 - 로그인, 비밀번보 변경 API 수정(2022.10.13.)
#### v2.3.7 - 퀴즈 조회 규칙 변경(2022.10.13.)
- 앞으로는 주차별 퀴즈 열람 가능하다는 규칙이 제거되고 참여중인 모든 퀴즈를 조회할 수 있습니다.
- GET v1/challenges/{challengeId}/weeks 챌린지 주차 정보 목록 조회 API Deprecated
- GET v1/challenges/{challengeId}/quizzes 퀴즈 개요 목록 조회 API 인가 변경
- GET v1/quizzes/{quizId} 퀴즈 조회 API 인가 변경
- GET v1/quizzes/{quizId}/rubric 퀴즈 루브릭 조회 API 삭제
- POST v1/quizzes/{quizId}/solve 퀴즈 정답 제출 API 인가 변경
- POST v1/quizzes/{quizId}/grade 퀴즈 풀기 채점 점수 제출 API 인가 변경 및 반복 채점 오류 해결
- PUT v1/quizzes/{quizId}/rate 퀴즈 평가 API 인가 변경
#### v2.2.0 - 인가 방식 변경(2022.10.13.)
- 인가 방식 변경(코드 -> jwt)
  - api 호출 방식 변경(코드 -> jwt)
#### v2.1.0 - 2022.10.13.
- PATCH v1/users/password 비밀번호 변경 API 추가
#### v2.0.0 - 2022.10.12.
- POST v2/user/login 로그인 API 방식 변경
  - 요청: email*(string), password*(string), isAutoLogin*(bool), platform*(string), fcmToken(string)
  - 응답: token(string)

### v1.5.0 - 2022-10-05
- 금,토,일 독려 알림 발송
- 커스텀 알림 발송
- GET v1/my/quizzes/count 모든 챌린지에서 내가 작성한 퀴즈 주차별 개수 조회 API 추가
  - <details  markdown="1">
    <summary>ChallengeSummaryResponse 응답 DTO 세부 내용</summary>

    ```
    [
      {
        "week": 1,
        "count": 4
      },
      {
        "week": 2,
        "count": 3
      },
      {
        "week": 3,
        "count": 2
      },
      {
        "week": 4,
        "count": 1
      }
    ]
    ```
    </details>

### v1.4.3 - 2022-09-22
- GET v1/challenges/{challengeId}/my-good-quizzes 좋아요한 문제들 조회 API 추가
  - 내가 좋아요한 문제들의 개요 리스트를 반환한다. 수강중이거나 수강했던 챌린지가 아니라면 403 에러를 반환한다.
- GET v1/challenges 챌린지 목록 조회 API, GET v1/my/challenges 내가 참여한 챌린지 목록 조회 API의 응답 수정
  - <details  markdown="1">
    <summary>ChallengeSummaryResponse 응답 DTO 세부 내용</summary>

    ```
    @Schema(description = "현재 주차 미션 성공 챌린지원 수") private int numberOfChallengerWhoCompleted;@Schema(description = "챌린지 ID") private final Long challengeId;
    @Schema(description = "챌린지 제목") private final String title;
    @Schema(description = "챌린지가 진행되는 주차 수") private final Integer totalWeeks;
    @Schema(description = "주차별 최소 제출 문제 수") private final Integer minNumOfQuizzesByWeek;
    @Schema(description = "챌린지 참가비(없으면 0)") private final Integer cost;
    @Schema(description = "대상 대학") private final String university;
    @Schema(description = "대상 학과") private final String department;
    @Schema(description = "교수명") private final String professorName;
    @Schema(description = "챌린지 기수") private final Integer chapter;
    @Schema(description = "챌린지 대상") private final String target;
    @Schema(description = "챌린지 시작일시") private final LocalDateTime startDate;
    @Schema(description = "챌린지 종료일시") private final LocalDateTime endDate;
    @Schema(description = "신청시작일시") private final LocalDateTime applicationStartDate;
    @Schema(description = "신청종료일시") private final LocalDateTime applicationEndDate;
    @Schema(description = "챌린지 이미지 경로") private final String imageUrl;
    @Schema(description = "챌린지 신청 상태") private ApplicationStatus applicationStatus;
    ```
    </details>
- GET v1/challenges/{challengeId} 챌린지 조회 API 응답 수정
  - 현재 주차 미션 성공 챌린지원 수 추가
  - ```private int numberOfChallengerWhoCompleted```


### v1.3.0 - 2022-09-11
- PUT v1/quizzes/{quizId}/rate 퀴즈 평가 API 추가
  - 퀴즈에 좋아요(GOOD)/싫어요(BAD)/취소(null)로 평가한다. 열람 가능 주차의 문제가 아니면 403 에러를 반환한다.
- 퀴즈 개요 리스트 조회, 내 퀴즈 조회, 퀴즈 조회에 cntOfGood(그 문제 좋아요 수), didIRate(내 평가 내용 GOOD,BAD,null) 추가


### v1.2.0 - 2022-09-04
- GET v1/quizzes/{quizId} 퀴즈 조회 API 추가
  - 기존 /content, /rubric 제거하고 통합
- POST v1/quizzes/{quizId}/grade HTTP Method 변경
  - 기존 PATCH -> POST
- GET v1/quizzes/{quizId}/comments 댓글 목록 조회 API 응답 추가
  - isQuizWriter: boolean 퀴즈 작성자의 댓글인지 여부 포함

### v1.1.5 - 2022-09-03
- GET v1/challenges/{challengeId}/weeks 챌린지 주차 정보 목록 조회 API 응답 추가
  - WeekStatus에 READABLE_CLOSED 추가

### v1.1.4 - 2022-09-02
- 내 퀴즈 전체 내용 조회 API URI 수정
  - GET v1/quizzes/{quizId} -> v1/my/quizzes/{quizId}로 변경
- GET v1/quizzes/{quizId}/content 퀴즈 문제 조회 API 예외처리 수정
  - 내 퀴즈일 경우 문제를 볼 수 있던 처리를 제거
    - 설명 중 "내가 작성한 퀴즈가 아니라면 403 에러를 반환한다." 제거
    - 작성자가 본인이어도 자신의 문제를 풀 수 있으니 작성자에 따른 예외 처리 제외


### v1.1.3 - 2022-09-02
- GET v1/quizzes/{quizId}/comments 댓글 조회 API 응답 수정
  - 작성자 이름, 퀴즈 ID 추가

### v1.1.2 - 2022-08-31
- GET v1/challenges/{challengeId}/weeks 챌린지 주차 정보 목록 조회 API 응답 수정
  - 주차 정보를 "int": "status" 형태로 변경
  - WeekStatus: READABLE, UNREADABLE, CLOSED
  - <details  markdown="1">
    <summary>예시</summary>
    
    ```
    {
      "challengeId": 1,
      "totalWeeks": 14,
      "weeks": {
        "1": "READABLE",
        "2": "UNREADABLE",
        "3": "UNREADABLE",
        "4": "READABLE",
        "5": "CLOSED",
        "6": "CLOSED",
        "7": "CLOSED",
        "8": "CLOSED",
        "9": "CLOSED",
        "10": "CLOSED",
        "11": "CLOSED",
        "12": "CLOSED",
        "13": "CLOSED",
        "14": "CLOSED"
      }
    }
    ```
    </details>

### v1.1.1 - 2022-08-30
- GET v1/quizzes/{quizId} 내 퀴즈 전체 내용 조회 API 설명 수정
  - 퀴즈 전체 내용 조회 -> 내 퀴즈 전체 내용 조회
- GET v1/quizzes/{quizId}/rubric 퀴즈 루브릭 조회 API 예외처리 수정
  - 퀴즈 정답을 제출한 적이 없으면 403 에러를 반환(열람 가능 주차의 문제만 정답을 제출 가능)
  - 작성자가 본인이어도 자신의 문제를 풀 수 있으니 작성자에 따른 예외 처리 제외

### v1.1.0 - 2022-08-26
- v1/challenges/{challengeId} 챌린지 조회 API 추가
- v1/challenges/{challengeId}/quizzes 퀴즈 개요 목록 조회 API 요청 수정
  - ?week="1,2,3,4,5,10,11,15"와 같이 콤마로 나누어서 표현
- v1/challenges/{challengeId}/now 현재 주차 반환 API 응답 수정
  - 응답에 챌린지 시작 날짜 추가
- v1/challenges/{challengeId}/week 챌린지 주차 정보 목록 조회 API 응답 수정
  - private final Integer totalWeeks: 총 주차
  - private final Map<Integer, Boolean> weeks: 주차: 열람가능여부
    - 예시) 1주차: true, 2주차: true, 3주차: false, ...
