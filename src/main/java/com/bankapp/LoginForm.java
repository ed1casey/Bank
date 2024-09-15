package com.bankapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginForm {

    private SceneController sceneController;

    public LoginForm(SceneController sceneController) {
        this.sceneController = sceneController;
        createScene();
    }

    private void createScene() {
        // Создаем поля ввода
        TextField usernameField = new TextField();
        usernameField.setPromptText("Имя пользователя");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");

        Button loginButton = new Button("Войти");
        Button registerButton = new Button("Регистрация");

        // Обработка нажатия кнопки "Войти"
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            User user = UserData.findUser(username, password);

            if (user != null) {
                System.out.println("Пользователь вошел: " + username);
                MainDashboard dashboard = new MainDashboard(sceneController, user);
                sceneController.activate("dashboard");
            } else {
                showAlert("Ошибка входа", "Неправильное имя пользователя или пароль.");
            }
        });

        // Переход к окну регистрации
        registerButton.setOnAction(e -> {
            sceneController.activate("registration");
        });

        VBox vbox = new VBox(10, usernameField, passwordField, loginButton, registerButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(vbox, 300, 200);

        // Добавляем сцену в контроллер
        sceneController.addScene("login", scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
