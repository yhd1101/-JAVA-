package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SendMessage {
    private Scanner sc = new Scanner(System.in);

    public void send() {
        String url = "jdbc:mariadb://localhost:3306/member";
        String user = "root";
        String password = "123456";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.print("보내는 회원의 등록번호를 입력해주세요: ");
            int senderId = sc.nextInt();
            sc.nextLine();

            if (!isUserExists(conn, senderId)) {
                System.out.println("입력하신 회원 등록번호에 해당하는 회원이 존재하지 않습니다.");
                return;
            }

            System.out.print("쪽지를 받을 회원의 등록번호를 입력해주세요: ");
            int recipientId = sc.nextInt();
            sc.nextLine();

            if (!isUserExists(conn, recipientId)) {
                System.out.println("입력하신 회원 등록번호에 해당하는 회원이 존재하지 않습니다.");
                return;
            }
            System.out.print("쪽지 제목을 입력해주세요: ");
            String messageTitle = sc.nextLine();
            System.out.print("쪽지 내용을 입력해주세요: ");
            String messageBody = sc.nextLine();

            // 쪽지 발송
            String insertSql = "insert into messages (sender_id, recipient_id, message_title, message_body) values (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setInt(1, senderId);
                pstmt.setInt(2, recipientId);
                pstmt.setString(3, messageTitle);
                pstmt.setString(4, messageBody);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("쪽지가 정상적으로 발송되었습니다.");
                } else {
                    System.out.println("쪽지 발송에 실패했습니다.");
                }
            }

        } catch (SQLException e) {
            System.out.println("에러 " + e.getMessage());
        }finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) {} }
        }
    }

    private boolean isUserExists(Connection conn, int userId) throws SQLException {
        String query = "select count(*) from user where number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false; // 존재하지 않으면 false
    }
}
