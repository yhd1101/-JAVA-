package com;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MemberList memberList = new MemberList();
        RegisterMember registerMember = new RegisterMember();
        System.out.println("\t\t\t\t\t\t 회원 관리 프로그램");

        while (true) {
            System.out.println("========================================================================");
            System.out.println("1. 회원정보목록");
            System.out.println("2. 회원정보등록");
            System.out.println("3. 회원정보수정");
            System.out.println("4. 회원정보삭제");
            System.out.println();
            System.out.println("5.쪽지 보내기");

            System.out.println("6.종료");
            System.out.println("========================================================================");
            System.out.print("메뉴를 입력하세요: ");
            int a = sc.nextInt();

            switch (a) {
                case 1 :
                    memberList.displayMemberList();
                    break;
                case 2 :
                    registerMember.Register();
                    break;
                case 3 :
                    System.out.println("회원정보 수정입니다.");
                    break;
                case 4 :
                    System.out.println("회원정보 삭제입니다.");
                    break;
                case 5 :
                    System.out.println("쪽지보내기.");
                    break;
                case 6 :
                    System.out.println("종료.");
                    sc.close();
                    return;
                default:
                    System.out.println("잘못된 입력값입니다. 다시입력해주세요");
                    break;
            }
        }
    }
}
