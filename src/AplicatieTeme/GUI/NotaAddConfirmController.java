package AplicatieTeme.GUI;

import AplicatieTeme.Business.NotaBusiness;
import AplicatieTeme.Domain.Nota;
import AplicatieTeme.Domain.Validators.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class NotaAddConfirmController {
    @FXML
    Label labelNume;
    @FXML
    Label labelTema;
    @FXML
    Label labelNota;
    @FXML
    Label labelPenalizare;
    @FXML
    Label labelAbsent;
    private Stage dialogStage;
    private NotaBusiness notaBuss;
    private Nota nota;

    @FXML
    public void initialize() {
    }

    void setNotaBusiness(NotaBusiness notaBusiness, Stage stage) {
        this.notaBuss = notaBusiness;
        this.dialogStage = stage;
    }

    @FXML
    public void setNota(Nota nota) {
        this.nota = nota;
        labelNume.setText("Nume: " + notaBuss.getStudBuss().findStud(nota.getID().getStudentID()).getNumele());
        labelTema.setText("Tema: " + nota.getID().getTemaID());
        labelNota.setText("Nota: " + nota.getValoare());
        labelPenalizare.setText("Penalizare: " + notaBuss.getPenalizare(nota).calculatePenalizare());
    }

    @FXML
    private void handleSaveNota() {
        try {
            this.notaBuss.addNota(nota);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Added", "Nota a fost adaugata cu succes!");
            dialogStage.close();
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }

    }

    @FXML
    public void handleCancel() {
        dialogStage.close();
    }
}
