package com.bankapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegistrationForm {

    private SceneController sceneController;

    public RegistrationForm(SceneController sceneController) {
        this.sceneController = sceneController;
        createScene();
    }

    private void createScene() {
        // Создаем поля ввода
        TextField usernameField = new TextField();
        usernameField.setPromptText("Имя пользователя");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");

        Button registerButton = new Button("Регистрация");
        Button backButton = new Button("Назад");

        // Обработка нажатия кнопки "Регистрация"
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
                MainDashboard dashboard = new MainDashboard(sceneController, newUser);
                sceneController.activate("dashboard");
            }
        });

        // Переход назад к окну логина
        backButton.setOnAction(e -> {
            sceneController.activate("login");
        });

        VBox vbox = new VBox(10, usernameField, passwordField, registerButton, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(vbox, 300, 200);

        // Добавляем сцену в контроллер
        sceneController.addScene("registration", scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
