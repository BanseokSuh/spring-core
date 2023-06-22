# Spring 핵심 원리 강의

<br>

## 요구사항

<br>

### 회원
- 등급은 일반과 VIP
- 회원 DB는 자체 구축할 수도 있고, 외부 시스템과 연동할 수도 있음 -> 인터페이스, 구현체로 설계

<br>

### 주문, 할인
- VIP 회원은 1000월 할인
- 할인 정책은 변경 가능성이 있음 -> 인터페이스, 구현체로 설계 

<br>

## 회원 도메인 설계
### 협력 관계
- 클라
- 회원 서비스
  - 회원가입
  - 회원조회
- 회원 저장소 - 미확정이기 때문에 데이터 접근 계층을 따로 만듦 (인터페이스)
  - 메모리 회원 저장서
  - DB 회원 저장소
  - 외부 시스템 회원 저장소

### 클래스 다이어그램
- 회원 서비스 (인터페이스)
- 회원 서비스 구현체
- 회원 저장소 (인터페이스)
  - 메모리 회원 저장소
  - DB 회원 저장소


 <br>

## 주문, 할인 도메인 설계
### 주문, 할인 정책
- 회원 등급에 따라 할인 정책이 적용됨
- VIP는 고정 금액으로 1000원 할인이 적용됨
- 할인 정책은 서비스 오픈 직전에도 변경될 수 있음

- 주문 도메인 협력, 역할, 책임
  1. 주문 생성
  2. 회원 조회
  3. 할인 적용
  4. 주문 결과 반환

- 역할과 책임으로 나누는 이유: 추후에 책임을 담당하는 구형체가 바뀔 가능성에 대비해 역할/책임으로 나누고, 책임 객체만 바꿔 끼울 수 있게 하기 위함
- 역할들의 협력 관계는 그대로 유지가 됨

 
