package com;

import java.io.IOException;
import java.sql.*;

public class MemberList {
    public void displayMemberList() {
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

            String sql = "SELECT * FROM user";
            rs = stmt.executeQuery(sql);

            // 헤더 출력
            System.out.printf("%-6s\t%-6s\t%-15s\t%-20s\t%-6s\t%-10s\t%-10s\n",
                    "번호", "이름", "연락처", "이메일", "그룹", "생년월일", "등록일");
            System.out.println("========================================================================");

            int count = 0; // 총 회원 수를 계산하기 위한 변수

            while (rs.next()) {
                // 각 열의 데이터를 일정한 폭으로 출력
                System.out.printf("%06d\t%-6s\t%-15s\t%-20s\t%-6s\t%-10s\t%-10s\n",
                        rs.getInt("number"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("user_group"),
                        rs.getString("birthdate"),
                        rs.getString("registration_date"));

                count++; // 회원 수 증가
            }

            // 총 회원 수 출력
            System.out.println("========================================================================");
            System.out.printf("총 %d 명 ===================================================================\n", count);
        } catch (ClassNotFoundException e) {
            System.out.println("에러: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("에러: " + e.getMessage());
        } finally {
            if( rs != null) { try{rs.close();} catch (SQLException e) {}}
            if( stmt != null) { try {stmt.close();} catch (SQLException e) {}}
            if(conn != null) { try { conn.close();} catch (SQLException e) {}}
        }
    }
}
