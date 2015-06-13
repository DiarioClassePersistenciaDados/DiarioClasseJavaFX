package controller;

import beans.Pessoa;
import hibernateUtil.ConnectDB;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.*;
import org.controlsfx.dialog.Dialog;
import org.hibernate.Session;

import java.util.List;

public class PessoaController {

    public Label txtTitulo;
    public ComboBox comboTipoPessoa;
    public TextField fieldNome;
    public TextField fieldDDD;
    public TextField fieldTelefone;
    public TextField fieldEmail;
    public PasswordField fieldSenha;
    public TableView tabelaDados;
    public Button btnSalvar;
    public Button btnExcluir;
    public TextField fieldEndereco;
    private Stage stage;

    public void init(Stage stage) {
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            stage.close();
        });

        Scene scene = txtTitulo.getScene();
        this.stage = stage;

        comboTipoPessoa.getItems().add("1 - Professor");
        comboTipoPessoa.getItems().add("2 - Estudante");
        comboTipoPessoa.getSelectionModel().select(1);

        carregarPessoas();
    }

    private void carregarPessoas() {
        Session s = null;
        try {
            tabelaDados.getItems().clear();
            s = ConnectDB.getInstance();
            List<Pessoa> pessoas = s.createQuery("from Pessoa order by nome").list();

            if(!pessoas.isEmpty()){
                tabelaDados.getItems().addAll(pessoas);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            s.close();
        }
    }

    public void salvar(){
        if(fieldDDD.getText().equals("")||fieldEmail.getText().equals("")
                ||fieldNome.getText().equals("")||fieldSenha.getText().equals("")
                ||fieldTelefone.getText().equals("")||fieldEndereco.getText().equals("")){
            Dialogs.create().message("Ops! Preencha todos os campos!").actions(Dialog.Actions.OK).showConfirm();
            return;
        }

        Pessoa p = new Pessoa();
        p.setNome(fieldNome.getText());
        p.setSenha(fieldSenha.getText());
        p.setDdd(fieldDDD.getText());
        p.setEmail(fieldEmail.getText());
        p.setEndereco(fieldEndereco.getText());
        p.setTelefone(fieldTelefone.getText());
        p.setTipo(comboTipoPessoa.getSelectionModel().getSelectedIndex()==0?'1':'2');

        Session s = null;
        try {
            s = ConnectDB.getInstance();
            s.getTransaction().begin();
            s.saveOrUpdate(p);
            s.getTransaction().commit();

            Dialogs.create().message("Aeee! Dados Salvos com sucesso!").actions(Dialog.Actions.OK).showConfirm();

            carregarPessoas();
            limparCampos();

        }catch (Exception e){
            Dialogs.create().message("Ops! Erro ao gravar!").actions(Dialog.Actions.OK).showConfirm();
            e.printStackTrace();
        }finally {
            s.close();
        }
    }

    private void limparCampos() {
        comboTipoPessoa.getSelectionModel().select(1);
        fieldDDD.setText("");
        fieldSenha.setText("");
        fieldEndereco.setText("");
        fieldTelefone.setText("");
        fieldNome.setText("");
        fieldEmail.setText("");
    }

    public void excluir(){
        if(tabelaDados.getSelectionModel().getSelectedItem()==null){
            return;
        }

        Action action = Dialogs.create().message("Deseja realmente deletar?").actions(Dialog.Actions.YES, Dialog.Actions.NO).showConfirm();
        if(action == Dialog.Actions.NO){
            return;
        }

        Pessoa p = (Pessoa) tabelaDados.getSelectionModel().getSelectedItem();

        Session s = null;
        try {
            s = ConnectDB.getInstance();

            p = (Pessoa) s.get(Pessoa.class, p.getCodigo());

            s.getTransaction().begin();
            s.delete(p);
            s.getTransaction().commit();

            carregarPessoas();
            limparCampos();

        }catch (Exception e){
            Dialogs.create().message("Ops! Erro ao deletar!").actions(Dialog.Actions.OK).showConfirm();
            e.printStackTrace();
        }finally {
            s.close();
        }
    }
}
