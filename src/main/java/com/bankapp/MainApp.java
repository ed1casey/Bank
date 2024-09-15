package com.bankapp;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    private SceneController sceneController;

    @Override
    public void start(Stage primaryStage) {

        // Загрузка данных пользователей
        UserData.loadUsers();

        // Инициализируем SceneController
        sceneController = new SceneController(primaryStage);

        // Создаём и добавляем сцены
        new LoginForm(sceneController);
        new RegistrationForm(sceneController);

        // Активируем начальную сцену (логин)
        sceneController.activate("login");

        // Настраиваем основное окно
        primaryStage.setTitle("Банковское приложение");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}