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

<br>

## Tips!
- HashMap 대신에 ConcurrnetHashMap을 쓰자
  - ConcurrentHashMap은 동시성을 지원하는 HashMap 클래스
  - HashMap는 내부적으로 동기화되지 않고 스레드로부터 안전하지 않음
  - ConcurrentHashMap은 내부적으로 동기화되어 스레드로부터 안전 (추가 및 삭제와 같은 수정 작업만)
- psvm
  - Public Static Void main(String[] arg);

<br>
    
## 관심사의 분리
- 애플리케이션은 추상간의 협력?
- 조영호님의 Object를 읽어보자
- 구현체가 아닌 추상을 의존하게 하는 것이 좋은 설계. 구현체는 구현만 담당하고, 추상을 의존하게 해야 코드 유지보수에 용이하다.
- 코드 간에도 클라이언트 코드, 서버 코드가 있다.
- AppConfig: 애플리케이션의 전체 동작 방식을 구성. 구현 객체를 생성하고 연결하는 책임을 가짐
  - 클라이언트 객체는 자신의 역할을 실행하는 데에만 집중

<br>

## 좋은 객체 지향 설계  5가지 원칙

- S(Solid): SRP 단일 책임 원칙. 한 클래스는 하나의 책임만 가진다.
- O(OCP): 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
- L
- I
- D(DIP): 의존관계 역전 원칙. 프로그래머는 추상화에 의존해야지, 구체화에 의존하면 안 된다.

<br>

## IoC, DI, 컨테이너

### IoC
- 기존: 클라이언트 구현 객체가 필요한 객체를 직접 생성, 연결, 실행 -> 구현 객체가 프로그램 제어 흐름을 스스로 결정
- AppConfig 등장 후: 구현 객체를 로직만 담당, 객체 연결은 AppConfig에서 담당 -> 제어 흐름을 AppConfig가 가져감
=> 프로그램 제어 흐름을 직접 제어하는 것이 아닌 외부에서 제어하는 것이 제어의 역전

- 프레임워크: 내가 작성한 코드를 제어하고 대신 실행하면, 그것은 프레임워크
- 라이브러리: 내가 작성한 코드가 직접 제어권을 담당한다면, 그것은 라이브러리

<br>

### DI
- OrderServiceImpl은 DiscountPolicy 인터페이스에 의존하기 때문에 구체적인 할인 정책을 구현하는 구현 객체는 모름
- 의존 관계는 정적인 클래스 의존관계와, 실행 시점에 결정되는 동적인 인스턴스 의존관계를 분리해서 생각해야 함
- 정적: 소스코드 상에서 확인할 수 있는 의존관계
- 동적: 애플리케이션 실행 시점에서, 객체의 인스턴스의 참조가 연결된 의존관계
=> 의존관계 주입을 사용하면 정적 클래스 의존관계를 변경하지 않고, 동적 인스턴스 의존관계를 쉽개 변경할 수 있음

<br>

### IoC 컨테이너, DI 컨테이너
- AppConfig와 같이 객체를 생성 및 관리를 해주며 의존관계를 설정해주는 객체

<br>

### 스프링 컨테이너
- new AnnotationConfigApplicationContext(AppConfig.class);를 통해 만들어진 인스턴스를 스프링 컨테이너라 함
- AppConfig.class를 파라미터로 받아서 AppConfig 클래스에서 @Bean 어노테이션이 붙은 설정 정보를 스프링 빈 저장소에 등록
- 빈 이름은 중복되어서는 안 됨
- 빈이 등록이 되면, 의존관계를 설정해줌

<br>

### 컨테이너에 등록된 빈
- AppConfig도 빈으로 등록됨
- getRole() == ROLE_APPLICATION : 사용자가 정의한 빈
- getRole() == ROLE_INFRASTRUCTURE : 스프링 내부에서 사용하는 빈

<br>

### 동일한 타입의 빈이 둘 이상일 경우
- 타입으로만 빈을 조회했을 경우, 같은 타입의 빈이 둘 이상 등록되어 있으면 에러 발생
- 그럴 경우 빈 이름을 지정해서 조죄할 수 있음
- 둘 다 꺼내고 싶은 경우 getBeansOfType(); 메서드로 조회, Map 객체 리턴

<br>

### 스프링 빈 상속
- 스피링 빈을 조회할 때, 부모 타입을 조회하면 자식들도 다 끌려옴
- 모든 자바 객체의 최고 부모인 Object로 조회를 하면, 모든 스프링 빈이 조회됨

<br>

### BeanFactory와 ApplicationContext
BeanFactory (interface)<br>
ㄴ(상속) ApplicationContext (interface) : BeanFactory에 부가기능을 추가한 거라고 이해할 수 있음<br>
ㄴ(상속) AnnotationConfigApplicationContext : 구현객체


- BeanFactory
  - 스프링 컨테이너의 최상위 인터페이스
  - 스프링 빈을 관리하고 조회하는 기능을 갖고 있음
  - getBean() 메서드 등

- ApplicationContext
  - BeanFactory의 기능을 모두 상속받아서 제공
  - 부가 기능
    - MessageSource(메시지소스): 한국어 요청이면 한국어 데이터, 영어 요청이면 영어 데이터 출력
    - EnvironmentCapable(환경변수): 로컬, 개발, 운영 환경을 구분해서 처리
    - ApplicationEventPublisher(애플리케이션 이벤트): 이벤트를 발생하고 구독하는 모델을 편리하게 지원
    - ResourceLoader(편리한 리소스): 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회

- BeanFactory나 ApplicationContext를 스프링 컨테이너라고 하고, 우리는 ApplicationContext만 사용한다고 해도 무방함

<br>

### 설정 정보
- 요즘은 대부분 자바 코드로 설정 사용
- xml을 사용하면 컴파일 없이 빈 설정 정보를 변경할 수 있음. 하지만 레거시.
- 스프링에 빈 등록하는 두 가지 방법
  - 직접 등록 (xml 파일)
  - Factory 메서드를 사용하여 등록 (Java 코드) - 외부에서 메서드를 호출해서 생성하는 방식을 의미

<br>

## 싱글톤 컨테이너

### 웹 어플리케이션과 싱글톤
- 웹 어플리케이션 특성상 여러 고객이 동시에 요청을 함
- 많은 수의 사용자가 한 번씩 요청을 할 때마다 객체를 여러 개 만들 필요는 없음
- jvm 메모리에 계속 객체가 쌓이는 것은 불필요
- 객체가 바라보고 있는 다른 객체도 생성하기 때문에 비효율적임
- 싱글톤은 객체를 한 번 만드는 패턴으로, 한 번 생성된 객체를 공유하는 방식

### 싱글톤 패턴
- jvm 위에 클래스 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
- private 생성자를 사용해서 외부에서 임의로 new 키워드로 인스턴스를 생성하지 못하도록 막음

- new 키워드를 사용하여 자기 자신을 내부에 private로 선언하여 instance를 생성
- static으로 선언 -> 클래스 레벨로 올라감
- 그러면 외부에서 new 키워드를 사용하여 인스턴스 생성 시도 시에 컴파일 에러 발생
- static으로 선언된 instance를 리턴하는 getInstance() 메서드를 생성
- 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있음. 항상 같은 instance를 리턴.

- 잘 설계한 객체는 컴파일 오류만으로 잡히는 객체

- assertThat(A).isSameAs(B); -> 참조값을 비교하는 것 (==)
- assertThat(A).isEqualTo(B); -> 값을 비교하는 것 (equals())

- 문제점
  - 구현 코드 자체가 많이 들어감
  - 클라이언트가 구체 코드에 의존함 -> DIP 위반
  - 내부 속성 변경 및 초기화가 어려움
  - 유연성이 떨어짐
  - 안티패턴으로 불리기도 함

- 스프링은 위 문제점을 모두 해결해주면서 객체를 싱글톤으로 관리해줌

### 싱글톤 컨테이너
- 싱글톤 패턴의 문제점을 해결하면서, 객체 인스턴스를 싱글톤으로 관리함
- 지금까지 학습한 빈이 싱글톤 컨테이너로 관리되는 빈
- 지저분한 코드 사용하지 않아도 됨
- DIP, OCP, 테스트, private 생성자 등으로부터 자유롭게 사용할 수 있음


### 싱글톤 방식의 주의점
- 여러 클라이언트가 공유하는 객체이기 때문에, 상태를 유지하게 설계하면 안 되고, 무상태로 설계해야 함
- 특정 클라이언트에 의존적인 필드가 있으면 안 됨. 값 변경이 가능하게 하면 안 됨
- 읽기만 가능하게 설계해야 함
- 필드 대신에 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal 등을 사용해야 함



<br>
