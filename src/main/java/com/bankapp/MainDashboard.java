package com.bankapp;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.application.Platform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainDashboard {

    private User currentUser;
    private ScheduledExecutorService scheduler;

    public MainDashboard(User user) {
        this.currentUser = user;
    }

    public void start(Stage primaryStage) {
        // Заголовки
        Label welcomeLabel = new Label("Добро пожаловать, " + currentUser.getUsername() + "!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Информация о накопительном счёте
        Label accountLabel = new Label("Накопительный счёт");
        accountLabel.setStyle("-fx-font-size: 16px; -fx-underline: true;");
        Label accountNumberLabel = new Label("Номер счёта: " + currentUser.getSavingsAccount().getAccountNumber());
        Label accountBalanceLabel = new Label("Баланс: $" + String.format("%.2f", currentUser.getSavingsAccount().getBalance()));

        // Поле для пополнения счёта
        TextField depositField = new TextField();
        depositField.setPromptText("Сумма пополнения");
        Button depositButton = new Button("Пополнить");

        depositButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(depositField.getText());
                if (amount > 0) {
                    currentUser.getSavingsAccount().deposit(amount);
                    accountBalanceLabel.setText("Баланс: $" + String.format("%.2f", currentUser.getSavingsAccount().getBalance()));
                    depositField.clear();
                } else {
                    showAlert("Ошибка", "Введите положительную сумму.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Ошибка", "Введите корректную сумму.");
            }
        });

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            // Начисление 1% к балансу
            double currentBalance = currentUser.getSavingsAccount().getBalance();
            double interest = currentBalance * 0.01;
            currentUser.getSavingsAccount().deposit(interest);

            Platform.runLater(() -> {
                accountBalanceLabel.setText("Баланс: $" + String.format("%.2f", currentUser.getSavingsAccount().getBalance()));
            });

            UserData.saveUsers();

        }, 60, 60, TimeUnit.SECONDS);

        // обработчик события закрытия окна
        primaryStage.setOnCloseRequest(e -> {
            scheduler.shutdownNow();
        });

        HBox depositBox = new HBox(10, depositField, depositButton);

        VBox accountBox = new VBox(10, accountLabel, accountNumberLabel, accountBalanceLabel, depositBox);
        accountBox.setStyle("-fx-padding: 15; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-color: #8B0000;");

        // Информация о дебетовой карте
        Label cardLabel = new Label("Дебетовая карта");
        cardLabel.setStyle("-fx-font-size: 16px; -fx-underline: true;");
        Label cardNumberLabel = new Label("Номер карты: " + currentUser.getDebitCard().getCardNumber());
        Label cardExpiryLabel = new Label("Срок действия: " + currentUser.getDebitCard().getExpiryDate());
        Label cardBalanceLabel = new Label("Баланс: $" + String.format("%.2f", currentUser.getDebitCard().getBalance()));

        // Поля для пополнения и снятия
        TextField cardDepositField = new TextField();
        cardDepositField.setPromptText("Сумма пополнения");
        Button cardDepositButton = new Button("Пополнить");

        cardDepositButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(cardDepositField.getText());
                if (amount > 0) {
                    currentUser.getDebitCard().deposit(amount);
                    cardBalanceLabel.setText("Баланс: $" + String.format("%.2f", currentUser.getDebitCard().getBalance()));
                    cardDepositField.clear();
                    UserData.saveUsers(); // Сохраняем данные
                } else {
                    showAlert("Ошибка", "Введите положительную сумму.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Ошибка", "Введите корректную сумму.");
            }
        });

        TextField cardWithdrawField = new TextField();
        cardWithdrawField.setPromptText("Сумма снятия");
        Button cardWithdrawButton = new Button("Снять");

        cardWithdrawButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(cardWithdrawField.getText());
                if (amount > 0) {
                    boolean success = currentUser.getDebitCard().withdraw(amount);
                    if (success) {
                        cardBalanceLabel.setText("Баланс: $" + String.format("%.2f", currentUser.getDebitCard().getBalance()));
                        cardWithdrawField.clear();
                        UserData.saveUsers(); // Сохраняем данные
                    } else {
                        showAlert("Ошибка", "Недостаточно средств на карте.");
                    }
                } else {
                    showAlert("Ошибка", "Введите положительную сумму.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Ошибка", "Введите корректную сумму.");
            }
        });

        HBox cardDepositBox = new HBox(10, cardDepositField, cardDepositButton);
        HBox cardWithdrawBox = new HBox(10, cardWithdrawField, cardWithdrawButton);

        VBox cardBox = new VBox(10, cardLabel, cardNumberLabel, cardExpiryLabel, cardBalanceLabel, cardDepositBox, cardWithdrawBox);
        cardBox.setStyle("-fx-padding: 15; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-color: #8B0000;");

        HBox infoBox = new HBox(20, accountBox, cardBox);
        infoBox.setStyle("-fx-alignment: center;");

        VBox mainLayout = new VBox(20, welcomeLabel, infoBox);
        mainLayout.setStyle("-fx-padding: 30; -fx-background-color: #F5F5F5; -fx-alignment: center;");

        Scene scene = new Scene(mainLayout, 700, 400);

        primaryStage.setTitle("Банковское приложение");
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
