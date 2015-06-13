package controller;

import beans.Frequencia;
import beans.Pessoa;
import beans.Turma;
import hibernateUtil.ConnectDB;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.hibernate.Session;
import java.math.BigInteger;
import java.util.List;

public class FrequenciaController {

    public Label txtTitulo;
    public ComboBox<Pessoa> comboPessoa;
    public ComboBox<Turma> comboTurma;
    public ComboBox comboPresenca;
    public TextField fieldNAula;
    private Stage stage;

    public void init(Stage stage) {
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            stage.close();
        });

        Scene scene = txtTitulo.getScene();
        this.stage = stage;
        
        carregarAlunos();
        carregarTurmas();
        carregarPresencas();

    }

    private void carregarPresencas() {
        comboPresenca.getItems().add("Presente");
        comboPresenca.getItems().add("Falta");
        comboPresenca.getItems().add("Justificado");
    }

    private void carregarTurmas() {
        Session s = null;
        try {
            s = ConnectDB.getInstance();
            List<Turma> turmas = s.createQuery("from Turma order by codigo").list();

            if(!turmas.isEmpty()){
                comboTurma.getItems().addAll(turmas);
                comboTurma.getSelectionModel().select(0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            s.close();
        }
    }

    private void carregarAlunos() {
        Session s = null;
        try {
            s = ConnectDB.getInstance();
            List<Pessoa> alunos = s.createQuery("from Pessoa where tipo = '2' order by nome").list();

            if(!alunos.isEmpty()){
                comboPessoa.getItems().addAll(alunos);
                comboPessoa.getSelectionModel().select(0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            s.close();
        }
    }

    public void salvar(){
        if(fieldNAula.getText().equals("")){
            Dialogs.create().message("Ops! Preencha o numero de aulas!").actions(Dialog.Actions.OK).showConfirm();
            return;
        }

        Frequencia p = new Frequencia();
        
        try {
            BigInteger numero = new BigInteger(fieldNAula.getText());
            p.setAula(numero);

            if(numero.intValue()>comboTurma.getSelectionModel().getSelectedItem().getQuantidadeaulas().intValue()){
                Dialogs.create().message("Ops! Essa numero de aula não existe!").actions(Dialog.Actions.OK).showConfirm();
                return;
            }
        }catch (Exception e){
            Dialogs.create().message("Ops! Número da aula inválido!").actions(Dialog.Actions.OK).showConfirm();
            return;
        }

        p.setAluno(comboPessoa.getSelectionModel().getSelectedItem());
        p.setPresenca(comboPresenca.getSelectionModel().getSelectedIndex()==0?'P':comboPresenca.getSelectionModel().getSelectedIndex()==1?'F':'J');
        p.setTurma(comboTurma.getSelectionModel().getSelectedItem());

        Session s = null;
        try {
            s = ConnectDB.getInstance();
            s.getTransaction().begin();
            s.saveOrUpdate(p);
            s.getTransaction().commit();

            Dialogs.create().message("Aeee! Dados Salvos com sucesso!").actions(Dialog.Actions.OK).showConfirm();

            limparCampos();

        }catch (Exception e){
            Dialogs.create().message("Ops! Erro ao gravar!").actions(Dialog.Actions.OK).showConfirm();
            e.printStackTrace();
        }finally {
            s.close();
        }
    }

    private void limparCampos() {
        comboPessoa.getSelectionModel().select(0);
        comboPresenca.getSelectionModel().select(0);
        comboTurma.getSelectionModel().select(0);
        fieldNAula.setText("");
    }
}
