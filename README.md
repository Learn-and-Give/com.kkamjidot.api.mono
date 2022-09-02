# com.kkamjidot.api.mono
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
  - status: READABLE, UNREADABLE, CLOSED
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