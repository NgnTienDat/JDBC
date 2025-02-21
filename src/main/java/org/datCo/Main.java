package org.datCo;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void getNoStatement(Connection con) throws SQLException {
        String sql = "select * from app_food";

        // Cung cap cac phuong thuc de thuc thi truy van
        Statement statement = con.createStatement();

        // Tra ve tap du lieu nhieu hang. Hoat dong nhu mot cursor tro den tung dong
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int price = resultSet.getInt("price");

            System.out.printf("%d - %s - %d\n", id, name, price);
        }

    }


    public static void insertWithStatement(Connection con) throws SQLException {
        String sql = "insert into app_maincategory(name, active) values(?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, "Hủ tếu");
        statement.setInt(2, 1);

        int rows = statement.executeUpdate();
        String notice = rows > 0 ? "Success":"Fail";
        System.out.println(notice);

    }

    public static void getCallableStatement(Connection con) throws SQLException {
//        CallableStatement callableStatement = con.prepareCall("{call get_products_by_kw(?)}");
//        callableStatement.setString(1, "bun bo");

        CallableStatement callableStatement = con.prepareCall("{ call count_orders_by_restaurant(?, ?) }");
        callableStatement.setInt(1, 1);
        callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);

        callableStatement.execute();

        int rows = callableStatement.getInt(2);
        System.out.println("Order quantity: "+rows);

//        while (resultSet.next()) {
//            int id = resultSet.getInt("id");
//            String name = resultSet.getString("name");
//            int price = resultSet.getInt("price");
//
//            System.out.printf("%d - %s - %d\n", id, name, price);
//        }

    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // Nap Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Thiet lap ket noi
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodapp4db", "root", "tiendatmySQL964@");

            getCallableStatement(connection);
//            insertWithStatement(connection);
//            getNoStatement(connection);


            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}