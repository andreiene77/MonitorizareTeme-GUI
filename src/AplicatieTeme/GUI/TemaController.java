package AplicatieTeme.GUI;

import AplicatieTeme.Business.NotaBusiness;
import AplicatieTeme.Business.TemeBusiness;
import AplicatieTeme.Domain.Tema;
import AplicatieTeme.Domain.Validators.ValidationException;
import AplicatieTeme.Utils.Observer;
import AplicatieTeme.Utils.TemaChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TemaController implements Observer<TemaChangeEvent> {
    private Stage dialogStage;
    private TemeBusiness temeBuss;
    private NotaBusiness notaBuss;
    private ObservableList<Tema> model = FXCollections.observableArrayList();
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldDeadline;
    @FXML
    private TextField textFieldStart;
    @FXML
    private TextArea textAreaDescriere;
    @FXML
    private TextField textFieldPostpone;


    @FXML
    private TableView<Tema> tableView;
    @FXML
    private TableColumn<Tema, String> tableColumnID;
    @FXML
    private TableColumn<Tema, String> tableColumnDesc;
    @FXML
    private TableColumn<Tema, Integer> tableColumnDeadline;
    @FXML
    private TableColumn<Tema, Integer> tableColumnStart;


    @FXML
    public void initialize() {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tableColumnDesc.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        tableColumnDeadline.setCellValueFactory(new PropertyValueFactory<>("deadlineWeek"));
        tableColumnStart.setCellValueFactory(new PropertyValueFactory<>("startWeek"));
        tableView.setItems(model);
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setFields(newValue));
    }

    @Override
    public void update(TemaChangeEvent temaChangeEvent) {
        model.setAll(StreamSupport.stream(temeBuss.getAllTeme().spliterator(), false)
                .collect(Collectors.toList()));
    }

    public void setTemaBusiness(TemeBusiness temaBusiness, Stage stage) {
        this.temeBuss = temaBusiness;
        this.dialogStage = stage;
        temeBuss.addObserver(this);
        initModel();
    }

    public void setNotaBuss(NotaBusiness notaBuss) {
        this.notaBuss = notaBuss;
    }


    private void initModel() {
        List<Tema> list = StreamSupport.stream(temeBuss.getAllTeme().spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(list);
    }

    @FXML
    public void handleSave() {
        String ID = textFieldID.getText();
        String descriere = textAreaDescriere.getText();
        Integer deadline = Integer.valueOf(textFieldDeadline.getText());
        Integer start = Integer.valueOf(textFieldStart.getText());
        Tema t = new Tema(ID, descriere, deadline, start);
        saveTema(t);
    }


    private void saveTema(Tema t) {
        try {
            this.temeBuss.addTem(t);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Saved", "Tema a fost salvata cu succes!");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void handlePostpone() {
        Tema selected = extractTema();
        postponeTema(selected, textFieldPostpone.getText());
        clearFields();
    }

    private void postponeTema(Tema t, String nrSapt) {
        try {
            this.temeBuss.postpone(t.getID(), nrSapt, notaBuss.getSaptCurenta());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Postponed", "Tema a fost amanata cu succes!");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    private void clearFields() {
        textFieldID.setText("");
        textFieldDeadline.setText("");
        textFieldStart.setText("");
        textAreaDescriere.setText("");
        textFieldPostpone.setText("");
        textFieldID.setEditable(true);
        textFieldDeadline.setEditable(true);
        textFieldStart.setEditable(true);
        textAreaDescriere.setEditable(true);
    }

    private void setFields(Tema t) {
        if (t == null) {
            textFieldID.setText("");
            textAreaDescriere.setText("");
            textFieldDeadline.setText("");
            textFieldStart.setText("");
        } else {
            textFieldID.setText(t.getID());
            textFieldDeadline.setText(String.valueOf(t.getDeadlineWeek()));
            textFieldStart.setText(String.valueOf(t.getStartWeek()));
            textAreaDescriere.setText(t.getDescriere());
            textFieldID.setEditable(false);
            textFieldDeadline.setEditable(false);
            textFieldStart.setEditable(false);
            textAreaDescriere.setEditable(false);
        }
    }

    @FXML
    public void handleCancel() {
        dialogStage.close();
    }

    void showTemaDetails(Tema value) {
        if (value == null) {
            textFieldID.setText("");
            textAreaDescriere.setText("");
            textFieldDeadline.setText("");
            textFieldStart.setText("");
        } else {
            textFieldID.setText(value.getID());
            textAreaDescriere.setText(value.getDescriere());
            textFieldDeadline.setText(String.valueOf(value.getDeadlineWeek()));
            textFieldStart.setText(String.valueOf(value.getStartWeek()));
        }
    }

    private Tema extractTema() {
        String id = textFieldID.getText();
        String descriere = textAreaDescriere.getText();
        String deadline = textFieldDeadline.getText();
        String start = textFieldStart.getText();
        return new Tema(id, descriere, Integer.parseInt(deadline), Integer.parseInt(start));
    }
}
