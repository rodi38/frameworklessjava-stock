package org.example.dao;

import org.example.model.dto.NewUser;
import org.example.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDao {

    private String url;

    private String username;
    private String password;

    public UserDao(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }


    public User createUser(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        User user1 = new User();

        try(Connection conn = connect();
            PreparedStatement prepareStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getEmail());
            prepareStatement.setString(3, user.getPassword());
            prepareStatement.executeUpdate();

            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            if(resultSet.next()) {
                user1.setId(resultSet.getInt(1));
                user1.setName(resultSet.getString("name"));
                user1.setEmail(resultSet.getString("email"));
                user1.setPassword(resultSet.getString("password"));
                System.out.println(user1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user1;
    }

    public User readUser(int id) {
        String sql = "SELECT id, name, email, password FROM users WHERE id = ?";

        User user = new User();

        try (Connection conn = connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            // o 1 significa que está pegando o index 1 da aparição da interrogação na string sql.
            preparedStatement.setInt(1, id);

            ResultSet resultSet =  preparedStatement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public List<User> readAllUsers(){
        String sql = "SELECT id, name, email, password FROM users";

        List<User> users = new ArrayList<>();

        try(Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void update(User user) {

        String sql = "UPDATE users SET name =?, email =?, password =? WHERE id =?";


        try(Connection conn = connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}
