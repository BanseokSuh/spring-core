package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService(); //자기 자신을 내부에서 static으로 가지고 있음 -> 클래스 레벨에 올라가기 때문에 하나만 존재함

    public static SingletonService getInstance() {
        return instance;
    }

    // private 생성자를 사용함으로써 외부에서 생성하는 것을 막아버림. 외부 생성하려고 할 시 컴파일 에러
    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
