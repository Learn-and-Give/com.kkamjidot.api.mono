# com.kkamjidot.api.mono
### v1.0.3 - 2022-08-30
- GET /v1/quizzes/{quizId} 내 퀴즈 전체 내용 조회 API 설명 수정
  - 퀴즈 전체 내용 조회 -> 내 퀴즈 전체 내용 조회
- GET /v1/quizzes/{quizId}/rubric 퀴즈 루브릭 조회 API 예외처리 수정
  - 퀴즈 정답을 제출한 적이 없으면 403 에러를 반환(열람 가능 주차의 문제만 정답을 제출 가능)
  - 작성자가 본인이어도 자신의 문제를 풀 수 있으니 작성자에 따른 예외 처리 제외

### v1.0.2 - 2022-08-26
- v1/challenges/{challengeId} 챌린지 조회 API 추가
- v1/challenges/{challengeId}/quizzes 퀴즈 개요 목록 조회 API 요청 수정
  - ?week="1,2,3,4,5,10,11,15"와 같이 콤마로 나누어서 표현
- v1/challenges/{challengeId}/now 현재 주차 반환 API 응답 수정
  - 응답에 챌린지 시작 날짜 추가
- v1/challenges/{challengeId}/week 챌린지 주차 정보 목록 조회 API 응답 수정
  - private final Integer totalWeeks: 총 주차
  - private final Map<Integer, Boolean> weeks: 주차: 열람가능여부
    - 예) 1주차: true, 2주차: true, 3주차: false, ...