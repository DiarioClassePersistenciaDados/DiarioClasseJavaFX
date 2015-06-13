package controller;

import beans.Disciplina;
import beans.Pessoa;
import hibernateUtil.ConnectDB;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.hibernate.Session;

import java.util.List;

public class DisciplinaController {

    public Label txtTitulo;
    public TextField fieldNome;
    public TableView tabelaDados;
    private Stage stage;

    public void init(Stage stage) {
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            stage.close();
        });

        Scene scene = txtTitulo.getScene();
        this.stage = stage;

        carregar();
    }

    private void carregar() {
        Session s = null;
        try {
            tabelaDados.getItems().clear();
            s = ConnectDB.getInstance();
            List<Disciplina> disciplinas = s.createQuery("from Disciplina order by nome").list();

            if(!disciplinas.isEmpty()){
                tabelaDados.getItems().addAll(disciplinas);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            s.close();
        }
    }

    public void salvar(){
        if(fieldNome.getText().equals("")){
            Dialogs.create().message("Ops! Preencha o nome!").actions(Dialog.Actions.OK).showConfirm();
            return;
        }

        Disciplina p = new Disciplina();
        p.setNome(fieldNome.getText());

        Session s = null;
        try {
            s = ConnectDB.getInstance();
            s.getTransaction().begin();
            s.saveOrUpdate(p);
            s.getTransaction().commit();

            Dialogs.create().message("Aeee! Dados Salvos com sucesso!").actions(Dialog.Actions.OK).showConfirm();

            carregar();
            limparCampos();

        }catch (Exception e){
            Dialogs.create().message("Ops! Erro ao gravar!").actions(Dialog.Actions.OK).showConfirm();
            e.printStackTrace();
        }finally {
            s.close();
        }
    }

    private void limparCampos() {
        fieldNome.setText("");
    }

    public void excluir(){
        if(tabelaDados.getSelectionModel().getSelectedItem()==null){
            return;
        }

        Action action = Dialogs.create().message("Deseja realmente deletar?").actions(Dialog.Actions.YES, Dialog.Actions.NO).showConfirm();
        if(action == Dialog.Actions.NO){
            return;
        }

        Disciplina p = (Disciplina) tabelaDados.getSelectionModel().getSelectedItem();

        Session s = null;
        try {
            s = ConnectDB.getInstance();

            p = (Disciplina) s.get(Disciplina.class, p.getCodigo());

            s.getTransaction().begin();
            s.delete(p);
            s.getTransaction().commit();

            carregar();
            limparCampos();

        }catch (Exception e){
            Dialogs.create().message("Ops! Erro ao deletar!").actions(Dialog.Actions.OK).showConfirm();
            e.printStackTrace();
        }finally {
            s.close();
        }
    }
}
