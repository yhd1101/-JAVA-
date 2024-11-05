package com;

import java.sql.*;
import java.util.Scanner;

public class UpdateMember {
    Scanner sc = new Scanner(System.in);

    public void updateUser() {
        String url = "jdbc:mariadb://localhost:3306/member";
        String user = "root";
        String password = "123456";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();

            System.out.print("수정할 회원번호를 입력해주세요: ");
            int memberId = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT * FROM user WHERE number = " + memberId;
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                System.out.println("회원정보:");
                System.out.println("이름: " + rs.getString("name"));
                System.out.println("연락처: " + rs.getString("phone"));
                System.out.println("이메일: " + rs.getString("email"));
                System.out.println("그룹: " + rs.getString("user_group"));
                System.out.println("생년월일: " + rs.getString("birthdate"));

                System.out.println("회원정보 수정을 계속하시겠습니까? (y/n): ");
                String answer = sc.nextLine();
                if (answer.equalsIgnoreCase("y")) {
                    System.out.println("** 입력하지 않으면 기존의 정보가 그대로 유지됩니다.");
                    System.out.print(">> 수정할 이름: ");
                    String name = sc.nextLine();
                    System.out.print(">> 수정할 연락처: ");
                    String phone = sc.nextLine();
                    System.out.print(">> 수정할 이메일: ");
                    String email = sc.nextLine();
                    System.out.print(">> 수정할 그룹: ");
                    String userGroup = sc.nextLine();
                    System.out.print(">> 수정할 생년월일: ");
                    String birthdate = sc.nextLine();

                    // 빈 입력일 경우 기존 값 유지
                    if (name.isEmpty()) name = rs.getString("name");
                    if (phone.isEmpty()) phone = rs.getString("phone");
                    if (email.isEmpty()) email = rs.getString("email");
                    if (userGroup.isEmpty()) userGroup = rs.getString("user_group");
                    if (birthdate.isEmpty()) birthdate = rs.getString("birthdate");

                    String updateSql = String.format(
                            "UPDATE user SET name='%s', phone='%s', email='%s', user_group='%s', birthdate='%s' WHERE number=%d",
                            name, phone, email, userGroup, birthdate, memberId
                    );
                    int rowUpdated = stmt.executeUpdate(updateSql);
                    if (rowUpdated > 0) {
                        System.out.println("회원정보를 정상적으로 수정하였습니다.");
                    } else {
                        System.out.println("회원정보 수정에 실패했습니다.");
                    }

                } else if (answer.equalsIgnoreCase("n")) {
                    System.out.println("회원정보 수정을 취소하였습니다.");
                } else {
                    System.out.println("잘못 입력하셨습니다.");
                }
            } else {
                System.out.println("해당 회원번호의 회원이 존재하지 않습니다.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("에러: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("에러: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }
}
