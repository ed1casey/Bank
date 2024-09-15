package com.bankapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RegistrationForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Создаем поля ввода
        TextField usernameField = new TextField();
        usernameField.setPromptText("Имя пользователя");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");

        Button registerButton = new Button("Регистрация");

        // Обработка нажатия кнопки
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Ошибка регистрации", "Все поля должны быть заполнены.");
            } else if (UserData.userExists(username)) {
                showAlert("Ошибка регистрации", "Пользователь с таким именем уже существует.");
            } else {
                User newUser = new User(username, password);
                UserData.addUser(newUser);

                System.out.println("Пользователь зарегистрирован: " + username);

                // Автоматический вход после регистрации
                MainDashboard dashboard = new MainDashboard(newUser);
                try {
                    dashboard.start(new Stage());
                    primaryStage.close(); // Закрываем окно регистрации
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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

        // Добавляем кнопку в интерфейс
        VBox vbox = new VBox(10, usernameField, passwordField, registerButton, backButton);

        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setTitle("Регистрация");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }

    // Метод для отображения сообщений об ошибках
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

