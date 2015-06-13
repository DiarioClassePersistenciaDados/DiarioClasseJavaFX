package executar;

import controller.PrincipalController;
import hibernateUtil.ConnectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class Executar extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ConnectDB.conecta();

        FXMLLoader loader = new FXMLLoader(Executar.class.getResource("/layout/Principal.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(920);
        stage.setMinHeight(580);
        stage.setTitle("");
        loader.<PrincipalController>getController().init(stage);
        stage.show();

    }
}