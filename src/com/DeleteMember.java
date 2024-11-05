package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteMember {
    Scanner sc = new Scanner(System.in);

    public void deleteUser() {
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

            System.out.print("삭제할 회원의 등록번호를 입력해주세요: ");
            int memberId = sc.nextInt();
            sc.nextLine();
            String checkSql = "SELECT * FROM user WHERE number = " + memberId;
            rs = stmt.executeQuery(checkSql);

            if (rs.next()) {
                System.out.println("[ " + memberId + " ]님의 회원정보");
                System.out.println("이름: " + rs.getString("name"));
                System.out.println("연락처: " + rs.getString("phone"));
                System.out.println("이메일: " + rs.getString("email"));
                System.out.println("그룹: " + rs.getString("user_group"));
                System.out.println("생년월일: " + rs.getString("birthdate"));

                System.out.print("회원정보 삭제를 계속하시겠습니까? (y/n): ");
                String answer = sc.nextLine();

                if (answer.equalsIgnoreCase("y")) {
                    String deleteSql = "DELETE FROM user WHERE number = " + memberId;
                    int rowsDeleted = stmt.executeUpdate(deleteSql);
                    if (rowsDeleted > 0) {
                        System.out.println("회원정보를 정상적으로 삭제하였습니다.");
                    } else {
                        System.out.println("회원정보 삭제에 실패했습니다.");
                    }
                } else {
                    System.out.println("회원정보 삭제를 취소하였습니다.");
                }
            } else {
                System.out.println("입력하신 회원등록번호에 해당하는 회원은 존재하지 않습니다.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("에러: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("에러: " + e.getMessage());
        } finally {
            // 리소스 닫기
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }
}
