package sample;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        int arr[][] = new int[50][50];

        Scanner scan = new Scanner(System.in);

        arr[1][24] = 1; // 구름 생성 지점

        String person = "0"; // 캐릭터 모양 저장 변수

        int point = 48; // 캐릭터 생성지점 정의

        boolean TF = true; // while문 제어변수

        // 배열에 계단 그리기

        // ☁ ( 1 ) ,🎈 ( 2 )

        for (int i = 2; i < arr.length; i++) { // 첫번째 줄 배열에 위에서 1을 선언 해놓았기 떄문에 두번째 줄부터 출력

            for (int j = 0; j < arr.length; j++) { // 배열의 맨왼쪽부터 오른쪽 끝까지 반복

                int a = (int) (Math.random() * 2); // 계단을 좌(0), 우(1)로 랜덤하게 생성하기위해 Math사용

                if (arr[i - 1][j] == 1 && (a == 0)) { // 윗칸에 (1) 즉 구름이 생성되어있고 (0) 랜덤에서 0이 나왔을시 왼쪽칸에 구름생성
                    arr[i][j - 1] = 1;
                } else if (arr[i - 1][j] == 1 && (a == 1)) { // 윗칸에 (1) 즉 구름이 생성되어있고 (1) 랜덤에서 1이 나왔을시 오른쪽칸에 구름생성
                    arr[i][j + 1] = 1;
                }
            }
        }

        // 캐릭터 생성

        for (int j = 0; j < arr.length; j++) { // 49 맨마지막줄을 스캔했을때 구름이 있는 j번째 바로 위인 48번째 j에 풍선(2) 생성

            if (arr[49][j] == 1) {

                arr[48][j] = 2;

            }

        }

        long start = System.currentTimeMillis(); // 게임시간을 측정하는 시작부분

        // 배열 시각화

        while (TF) {

            for (int i = 0; i < arr.length; i++) { // 배열에 입력했을 때는 미리 선언해놓은 부분이 있기때문에 2부터 시작했지만 출력은 처음부터 끝까지 진행

                for (int j = 0; j < arr.length; j++) {

                    if (arr[i][j] == 1) { // 배열에 1이 있을시 구름 출력

                        System.out.print("-");

                    } else if (arr[i][j] == 2) { // 배열에 2가 있을시 풍선 출력

                        System.out.print(person);

                    } else if (arr[i][j] == 0) { // 배열에 0이 있을시 공백 출력

                        System.out.print("  ");

                    }

                }

                System.out.println();

            }

            // 캐릭터 이동

            System.out.print("이동할 방향 [a]=왼쪽  [d]=오른쪽>>"); // 이동 커맨드 출력

            String input = scan.next(); // 이동 값 입력 받기

            if (input.equals("a")) { // a 를 입력 받았을시

                for (int j = 0; j < arr.length; j++) { // 위에서 point를 48로 선언해 주었었음

                    if (arr[point][j] == 2) { // arr[48][j]==2 일때 (캐릭터가 있는 곳일때)

                        arr[point][j] = 0; // 그 자리를 0 공백으로 초기화

                        arr[point - 1][j - 1] = 2; // arr[47][j-1] 한칸위의 왼쪽에 캐릭터 생성

                        if (arr[point][j - 1] == 0) { // 한칸위로 올리는데 그 캐릭터가 올라갈 위치의 바로 아랫칸이 공백일시 게임오버

                            for (int i = 0; i < 10; i++) { // 다잉메세지 출력

                                System.out.println("떨어져 죽음 D I E");

                            }

                            TF = false; // while문을 종료하기위해 변경

                            break; // 종료

                        }

                        if (point == 1) { // 맨 윗줄인 1번줄에 도착하면 클리어 하도록 조건

                            System.out.println("깼다 CLEAR");

                            TF = false;

                            break;

                        }

                        point--; // 캐릭터를 위로 이동시켰으니 point 값도 하나 줄여줘서 캐릭터위치와 맞춤

                        break;

                    }

                }

            } else if (input.equals("d")) { // d 를 입력 받았을시

                for (int j = 0; j < arr.length - 1; j++) { // 위에서 point를 48로 선언해 주었었음

                    if (arr[point][j] == 2) { // arr[48][j]==2 일때 (캐릭터가 있는 곳일때)

                        arr[point][j] = 0; // 그 자리를 0 공백으로 초기화

                        arr[point - 1][j + 1] = 2; // arr[47][j+1] 한칸위의 오른쪽에 캐릭터 생성

                        if (arr[point][j + 1] == 0) { // 한칸위로 올리는데 그 캐릭터가 올라갈 위치의 바로 아랫칸이 공백일시 게임오버

                            for (int i = 0; i < 10; i++) { // 다잉메세지 출력

                                System.out.println("떨어져 죽음 D I E");

                            }

                            TF = false; // while문을 종료하기위해 변경

                            break; // 종료

                        }

                        if (point == 1) { // 맨 윗줄인 1번줄에 도착하면 클리어 하도록 조건

                            System.out.println("깼다 CLEAR");

                            TF = false;

                            break;

                        }

                        point--; // 캐릭터를 위로 이동시켰으니 point 값도 하나 줄여줘서 캐릭터위치와 맞춤

                        break;

                    }

                }

            }

        }

        long end = System.currentTimeMillis(); // 게임 종료 시간 체크 -> 밀리초로 체크 돼서 밑에서 나누기 1000을 해줌

        System.out.println("총걸린시간 :" + (end - start) / 1000.0 + " 초"); // 출력되는시간이 실제 초와 오차는 있으나 보기편하기 위해 초라고 출력

    }

}