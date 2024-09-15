package com.bankapp;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;

public class SceneController {

    private Stage primaryStage;
    private HashMap<String, Scene> scenes = new HashMap<>();

    public SceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public void activate(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.out.println("Сцена " + name + " не найдена!");
        }
    }
}
