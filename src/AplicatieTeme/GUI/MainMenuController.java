package AplicatieTeme.GUI;

import AplicatieTeme.Business.NotaBusiness;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class MainMenuController {
    private Stage studentStage;
    private Stage notaStage;
    private Stage temaStage;
    private NotaBusiness notaBuss;
    //    TODO: Filters
    private Stage primaryStage;
    private StudentController sCtr;
    private StudentView sView;
    @FXML
    private Label labelSaptCurenta;
    @FXML
    private Slider sliderSaptCurenta;

    @FXML
    public void initialize() {
        labelSaptCurenta.textProperty().bind(
                Bindings.format(
                        "Sapt. curenta: %#.1f",
                        sliderSaptCurenta.valueProperty()
                )
        );
        sliderSaptCurenta.valueProperty().addListener(
                (observable, oldValue, newValue) -> notaBuss.setSaptCurenta(String.valueOf(newValue.intValue())));
    }

    public void setsCtr(StudentController sCtr) {
        this.sCtr = sCtr;
    }

    public void setsView(StudentView sView) {
        this.sView = sView;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setStudentStage();
    }

    private void setStudentStage() {
        studentStage = new Stage();
        studentStage.setScene(new Scene(sView.getView(), 1070, 500));
    }

    public void setTemaStage(Stage temaStage) {
        this.temaStage = temaStage;
    }

    public void setNotaStage(Stage notaStage) {
        this.notaStage = notaStage;
    }

    public void setNotaBuss(NotaBusiness notaBuss) {
        this.notaBuss = notaBuss;
    }

    public void openStudentView() {
        this.studentStage.show();
    }

    public void openTemaView() {
        this.temaStage.show();
    }

    public void openNotaView() {
        this.notaStage.show();
    }

}
