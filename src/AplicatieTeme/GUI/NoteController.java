package AplicatieTeme.GUI;

import AplicatieTeme.Business.NotaBusiness;
import AplicatieTeme.Domain.Nota;
import AplicatieTeme.Utils.NoteChangeEvent;
import AplicatieTeme.Utils.Observer;
import AplicatieTeme.Utils.Penalizare;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NoteController implements Observer<NoteChangeEvent> {

    private Stage dialogStage;
    private NotaBusiness notaBuss;
    private ObservableList<Nota> model = FXCollections.observableArrayList();
    private ObservableList<String> modelTemeID = FXCollections.observableArrayList();
    private ObservableList<String> modelStudNames = FXCollections.observableArrayList();
    @FXML
    private TextField textFieldStud;
    @FXML
    private VBox dropDownMenu;
    @FXML
    private ComboBox<String> comboTema;
    @FXML
    private ComboBox<String> comboGrupa;
    @FXML
    private TextField textFieldValoare;
    @FXML
    private TextArea textAreaFeedback;
    @FXML
    private TableView<Nota> tableNote;


    @FXML
    public void initialize() {
        textFieldStud.textProperty().addListener((observable, oldValue, newValue) -> {
            if (dropDownMenu.getChildren().size() > 1) {
                dropDownMenu.getChildren().clear();
            }
            populateDropDownMenu(newValue, modelStudNames);
        });
        comboTema.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Penalizare penalizare = notaBuss.getPenalizare(extractNota());
                if (penalizare.calculateNotaMax() == 10) {
                    textAreaFeedback.clear();
                } else {
                    textAreaFeedback.setText("NOTA A FOST DIMINUATĂ CU " + penalizare.calculatePenalizare() + " PUNCTE DATORITĂ ÎNTÂRZIERILOR");
                }
            } catch (Exception e) {
            }
        });
        comboGrupa.setItems(FXCollections.observableArrayList("221", "222", "223", "224", "225", "226", "227"));
        comboGrupa.getSelectionModel().select("221");

    }

    @Override
    public void update(NoteChangeEvent noteChangeEvent) {
        model.setAll(StreamSupport.stream(notaBuss.getAll().spliterator(), false)
                .collect(Collectors.toList()));
        initModel();
    }

    public void setNotaBusiness(NotaBusiness notaBusiness, Stage stage) {
        this.notaBuss = notaBusiness;
        this.dialogStage = stage;
        notaBuss.addObserver(this);
        initModel();
        defaultTable();
    }


    private void initModel() {
        List<Nota> list = StreamSupport.stream(notaBuss.getAll().spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(list);


        modelTemeID.clear();
        notaBuss.getTemaBuss().getAllTeme().forEach(e -> modelTemeID.add(e.getID()));
        comboTema.setItems(modelTemeID);
        setDefaultComboTema();
        modelStudNames.clear();
        notaBuss.getStudBuss().getAll().forEach(e -> modelStudNames.add(e.getNumele()));
    }


    private void showNotaAddConfirmDialog(Nota nota) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NotaAddConfirmDialog.fxml"));

            AnchorPane root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Confirm Nota");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            NotaAddConfirmController notaAddConfirmController = loader.getController();
            notaAddConfirmController.setNotaBusiness(notaBuss, dialogStage);
            notaAddConfirmController.setNota(nota);

            dialogStage.show();
        } catch (IOException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void handleSave() {
        showNotaAddConfirmDialog(extractNota());
        clearFields();
    }

    private Nota extractNota() {
        String SID = notaBuss.getStudBuss().findStudName(textFieldStud.getText()).getID();
        if (SID.equals("")) {
            SID = "0";
        }
        String TID = comboTema.getValue();
        Integer data = notaBuss.getSaptCurenta();
        Float valoare = Float.valueOf(textFieldValoare.getText());
        String feedback = textAreaFeedback.getText();
        return new Nota(SID, TID, data, valoare, feedback);
    }

    private void clearFields() {
        textFieldStud.setText("");
        setDefaultComboTema();
        textFieldValoare.setText("");
        textAreaFeedback.setText("");
    }

    private void setDefaultComboTema() {
        modelTemeID.forEach(e -> {
            if (notaBuss.getTemaBuss().findTem(e).getDeadlineWeek().equals(notaBuss.getSaptCurenta())) {
                comboTema.getSelectionModel().select(e);
            }
        });
    }

    private void populateDropDownMenu(String text, ObservableList<String> options) {
        options.forEach(e -> {
            if (!text.replace(" ", "").isEmpty() && e.toUpperCase().contains(text.toUpperCase()) && !e.toUpperCase().equals(text.toUpperCase())) {
                Label label = new Label(e);
                label.setStyle("-fx-border-color: #ececec #ececec #5f5f5f #ececec; -fx-padding: 7.5 50 7.5 10; -fx-pref-width: 225");
                label.setOnMouseClicked(event -> textFieldStud.setText(label.getText()));
                dropDownMenu.getChildren().add(label);
            }
        });
    }

    private TableColumn<Nota, String> getNotaColumn() {
        TableColumn<Nota, String> tableColumnNota = new TableColumn<>();
        tableColumnNota.setText("Nota");
        tableColumnNota.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getValoare().toString()));
        return tableColumnNota;
    }

    private TableColumn<Nota, String> getTemaColumn() {
        TableColumn<Nota, String> tableColumnTema = new TableColumn<>();
        tableColumnTema.setText("Tema");
        tableColumnTema.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getID().getTemaID()));
        return tableColumnTema;
    }

    private TableColumn<Nota, String> getStudentColumn() {
        TableColumn<Nota, String> tableColumnStudent = new TableColumn<>();
        tableColumnStudent.setText("Student");
        tableColumnStudent.setCellValueFactory(c -> new SimpleStringProperty(notaBuss.getStudBuss().findStud(c.getValue().getID().getStudentID()).getNumele()));
        return tableColumnStudent;
    }

    private void defaultTable() {
        tableNote.getItems().addAll(model);

        TableColumn<Nota, String> tableColumnStudent = getStudentColumn();
        TableColumn<Nota, String> tableColumnTema = getTemaColumn();
        TableColumn<Nota, String> tableColumnNota = getNotaColumn();

        tableNote.getColumns().addAll(tableColumnStudent, tableColumnTema, tableColumnNota);
    }

    @FXML
    public void filterTema() {
        tableNote.getColumns().clear();

        String tema = comboTema.getValue();
        Predicate<Nota> byTemaPredicate = n -> n.getID().getTemaID().equals(tema);

        model.setAll(StreamSupport.stream(notaBuss.getAll().spliterator(), false)
                .filter(byTemaPredicate).collect(Collectors.toList()));

        tableNote.getItems().setAll(model);

        TableColumn<Nota, String> tableColumnStudent = getStudentColumn();
        TableColumn<Nota, String> tableColumnNota = getNotaColumn();

        tableNote.getColumns().addAll(tableColumnStudent, tableColumnNota);
    }

    @FXML
    public void filterStudent() {
        tableNote.getColumns().clear();

        String student = textFieldStud.getText();
        Predicate<Nota> byStudentPredicate = n -> n.getID().getStudentID().equals(notaBuss.getStudBuss().findStudName(student).getID());

        model.setAll(StreamSupport.stream(notaBuss.getAll().spliterator(), false)
                .filter(byStudentPredicate).collect(Collectors.toList()));

        tableNote.getItems().setAll(model);

        TableColumn<Nota, String> tableColumnTema = getTemaColumn();
        TableColumn<Nota, String> tableColumnNota = getNotaColumn();

        tableNote.getColumns().addAll(tableColumnTema, tableColumnNota);
    }

    @FXML
    public void filterGrupa() {
        tableNote.getColumns().clear();

        String grupa = comboGrupa.getValue();
        Predicate<Nota> byGrupaPredicate = n -> notaBuss.getStudBuss().findStud(n.getID().getStudentID()).getGrupa().equals(grupa);

        model.setAll(StreamSupport.stream(notaBuss.getAll().spliterator(), false)
                .filter(byGrupaPredicate).collect(Collectors.toList()));

        tableNote.getItems().setAll(model);

        TableColumn<Nota, String> tableColumnStudent = getStudentColumn();
        TableColumn<Nota, String> tableColumnTema = getTemaColumn();
        TableColumn<Nota, String> tableColumnNota = getNotaColumn();

        tableNote.getColumns().addAll(tableColumnStudent, tableColumnTema, tableColumnNota);
    }

    @FXML
    public void filterGrupaTema() {
        tableNote.getColumns().clear();

        String grupa = comboGrupa.getValue();
        Predicate<Nota> byGrupaPredicate = n -> notaBuss.getStudBuss().findStud(n.getID().getStudentID()).getGrupa().equals(grupa);
        String tema = comboTema.getValue();
        Predicate<Nota> byTemaPredicate = n -> n.getID().getTemaID().equals(tema);

        model.setAll(StreamSupport.stream(notaBuss.getAll().spliterator(), false)
                .filter(byGrupaPredicate.and(byTemaPredicate)).collect(Collectors.toList()));

        tableNote.getItems().setAll(model);

        TableColumn<Nota, String> tableColumnStudent = getStudentColumn();
        TableColumn<Nota, String> tableColumnNota = getNotaColumn();

        tableNote.getColumns().addAll(tableColumnStudent, tableColumnNota);
    }

}
