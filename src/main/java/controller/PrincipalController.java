package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PrincipalController {

    public Label txtTitulo;
    private Stage stage;

    public void init(Stage stage) {
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            System.exit(0);
        });

        Scene scene = txtTitulo.getScene();
        this.stage = stage;
    }

    public void pessoas(){
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalController.class.getResource("/layout/Pessoas.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setMinWidth(920);
            stage.setMinHeight(580);
            stage.setTitle("Cadastro de Pessoas");
            loader.<PessoaController>getController().init(stage);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void disciplinas(){
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalController.class.getResource("/layout/Disciplina.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setMinWidth(920);
            stage.setMinHeight(580);
            stage.setTitle("Cadastro de Disciplinas");
            loader.<DisciplinaController>getController().init(stage);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void turmas(){
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalController.class.getResource("/layout/Turma.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setMinWidth(920);
            stage.setMinHeight(580);
            stage.setTitle("Cadastro de Turmas");
            loader.<TurmaController>getController().init(stage);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void frequencia(){
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalController.class.getResource("/layout/Frequencia.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setMinWidth(920);
            stage.setMinHeight(580);
            stage.setTitle("Cadastro de Frequencias");
            loader.<FrequenciaController>getController().init(stage);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
