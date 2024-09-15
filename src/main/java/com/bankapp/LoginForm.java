package com.bankapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Создаем поля ввода
        TextField usernameField = new TextField();
        usernameField.setPromptText("Имя пользователя");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");

        Button loginButton = new Button("Войти");

        // Обработка нажатия кнопки
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            User user = UserData.findUser(username, password);

            if (user != null) {
                System.out.println("Пользователь вошел: " + username);
                MainDashboard dashboard = new MainDashboard(user);
                try {
                    dashboard.start(new Stage());
                    primaryStage.close(); // Закрываем окно логина
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                showAlert("Ошибка входа", "Неправильное имя пользователя или пароль.");
            }
        });

        Button backButton = new Button("Назад");
        backButton.setOnAction(e -> {
            MainApp mainApp = new MainApp();
            try {
                mainApp.start(new Stage());
                primaryStage.close(); // Закрываем окно регистрации
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Размещение элементов в интерфейсе
        VBox vbox = new VBox(10, usernameField, passwordField, loginButton, backButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setTitle("Вход");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

