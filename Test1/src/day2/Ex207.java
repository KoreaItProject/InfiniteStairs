package day2;

public class Ex207 {
    public static void main(String[] args) {
        int n=4;
        System.out.println(n>3&&n<10);
        System.out.println(n%2==0&&n>0);

        System.out.println(n%2!=0||n>=5);
        System.out.println(n<0||n%2==0);
        System.out.println(n%7==0);

        for(int i=0;i<10;i++){
            System.out.println((int)(Math.random()*2));
        }
        
    }
}
