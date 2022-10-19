package Product;

public class Main {

  public static void main(String[] args) {
    //SOSGui GUI = new SOSGui();
    System.out.println(type_test(1));
    System.out.println(type_test(1.4));
    System.out.println(type_test('h'));
    System.out.println(type_test("hello"));
  }

  public static <T> String type_test(T data){
    return data.getClass().getSimpleName();
  }

}
