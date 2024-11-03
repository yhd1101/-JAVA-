package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class RegisterMember {
    Scanner sc = new Scanner(System.in);

    public void Register() {
        String url = "jdbc:mariadb://localhost:3306/member";
        String user = "root";
        String password = "123456";

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            System.out.println("등록할 회원정보를 입력해주세요: ");
            System.out.print(">> 이름: ");
            String name  = sc.nextLine();
            System.out.print(">> 연락처: ");
            String phone = sc.nextLine();
            System.out.print(">> 이메일: ");
            String email = sc.nextLine();
            System.out.print(">> 그룹: ");
            String user_group = sc.nextLine();
            System.out.print(">> 생년월일: ");
            String birthdate = sc.nextLine();

            String sql = String.format("insert into user(name, phone, email, user_group, birthdate) values ('%s', '%s', '%s', '%s', '%s')",
                    name, phone, email, user_group, birthdate);
            System.out.print("회원정보를 등록하시겠습니까?(y/n)");
            String answer = sc.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                int row = stmt.executeUpdate(sql);
                if (row > 0) {
                    System.out.println("회원정보를 정상적으로 등록했습니다.");
                } else {
                    System.out.println("회원정보 등록을 실패하였습니다.");
                }
            } else if (answer.equalsIgnoreCase("n")) {
                System.out.println("회원정보 등록을 취소하였습니다.");
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("에러: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("에러: " + e.getMessage());
        } finally {
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) {} }
            if (conn != null) { try { conn.close(); } catch (SQLException e) {} }
        }
    }
}
