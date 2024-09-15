package com.bankapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        UserData.loadUsers();

        Button loginButton = new Button("Войти");
        Button registerButton = new Button("Регистрация");

        loginButton.setOnAction(e -> {
            LoginForm loginForm = new LoginForm();
            try {
                loginForm.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        registerButton.setOnAction(e -> {
            RegistrationForm regForm = new RegistrationForm();
            try {
                regForm.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox vbox = new VBox(10, loginButton, registerButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 200, 150);

        primaryStage.setTitle("Добро пожаловать");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
