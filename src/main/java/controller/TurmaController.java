package controller;

import beans.Disciplina;
import beans.Pessoa;
import beans.Turma;
import hibernateUtil.ConnectDB;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;

public class TurmaController {

    public Label txtTitulo;
    public TableView tabelaDados;
    public TextField fieldDataFim;
    public TextField fieldQuantidadeAulas;
    public ComboBox<Disciplina> comboDisciplina;
    public ComboBox<Pessoa> comboProfessor;
    public TextField fieldDataInicio;
    private Stage stage;

    public void init(Stage stage) {
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            stage.close();
        });

        Scene scene = txtTitulo.getScene();
        this.stage = stage;

        carregar();
        carregarProfessores();
        carregarDisciplinas();
    }

    private void carregarDisciplinas() {
        Session s = null;
        try {
            s = ConnectDB.getInstance();
            List<Disciplina> disciplinas = s.createQuery("from Disciplina order by nome").list();

            if(!disciplinas.isEmpty()){
                comboDisciplina.getItems().addAll(disciplinas);
                comboDisciplina.getSelectionModel().select(0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            s.close();
        }
    }

    private void carregarProfessores() {
        Session s = null;
        try {
            s = ConnectDB.getInstance();
            List<Pessoa> profs = s.createQuery("from Pessoa where tipo = '1' order by nome").list();

            if(!profs.isEmpty()){
                comboProfessor.getItems().addAll(profs);
                comboProfessor.getSelectionModel().select(0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            s.close();
        }
    }

    private void carregar() {
        Session s = null;
        try {
            tabelaDados.getItems().clear();
            s = ConnectDB.getInstance();
            List<Turma> turmas = s.createQuery("from Turma order by id").list();

            if(!turmas.isEmpty()){
                tabelaDados.getItems().addAll(turmas);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            s.close();
        }
    }

    public void salvar(){
        if(fieldDataFim.getText().equals("")||fieldDataInicio.getText().equals("")||fieldQuantidadeAulas.getText().equals("")){
            Dialogs.create().message("Ops! Preencha o nome!").actions(Dialog.Actions.OK).showConfirm();
            return;
        }

        Turma p = new Turma();

        try {
            Integer ano = Integer.parseInt(fieldDataInicio.getText().substring(fieldDataInicio.getText().lastIndexOf('/') + 1, fieldDataInicio.getText().length()));
            Integer mes = Integer.parseInt(fieldDataInicio.getText().substring(fieldDataInicio.getText().indexOf('/') + 1, fieldDataInicio.getText().lastIndexOf('/')));
            Integer dia = Integer.parseInt(fieldDataInicio.getText().substring(0, fieldDataInicio.getText().indexOf('/')));

            p.setDatainicio(fieldDataInicio.getText());

        }catch (Exception e){
            Dialogs.create().message("Ops! Data de início digitada é inválida!").actions(Dialog.Actions.OK).showConfirm();
            return;
        }

        try {

            Integer.parseInt(fieldDataFim.getText().substring(fieldDataFim.getText().lastIndexOf('/') + 1, fieldDataFim.getText().length()));
            Integer.parseInt(fieldDataFim.getText().substring(fieldDataFim.getText().indexOf('/') + 1, fieldDataFim.getText().lastIndexOf('/')));
            Integer.parseInt(fieldDataFim.getText().substring(0, fieldDataFim.getText().indexOf('/')));

            p.setDatafim(fieldDataFim.getText());

        }catch (Exception e){
            Dialogs.create().message("Ops! Data de fim digitada é inválida!").actions(Dialog.Actions.OK).showConfirm();
            return;
        }

        p.setDisciplina(comboDisciplina.getSelectionModel().getSelectedItem());
        p.setProfessor(comboProfessor.getSelectionModel().getSelectedItem());

        try {
            p.setQuantidadeaulas(new BigInteger(fieldQuantidadeAulas.getText()));
        }catch (Exception e){
            Dialogs.create().message("Ops! Quantidade de aulas inválida!").actions(Dialog.Actions.OK).showConfirm();
            return;
        }


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
        fieldDataFim.setText("");
        fieldQuantidadeAulas.setText("");
        comboDisciplina.getSelectionModel().select(0);
        comboProfessor.getSelectionModel().select(0);
        fieldDataInicio.setText("");
    }

    public void excluir(){
        if(tabelaDados.getSelectionModel().getSelectedItem()==null){
            return;
        }

        Action action = Dialogs.create().message("Deseja realmente deletar?").actions(Dialog.Actions.YES, Dialog.Actions.NO).showConfirm();
        if(action == Dialog.Actions.NO){
            return;
        }

        Turma p = (Turma) tabelaDados.getSelectionModel().getSelectedItem();

        Session s = null;
        try {
            s = ConnectDB.getInstance();

            p = (Turma) s.get(Turma.class, p.getCodigo());

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
