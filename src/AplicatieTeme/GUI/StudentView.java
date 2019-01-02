package AplicatieTeme.GUI;

import AplicatieTeme.Domain.Student;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StudentView {
    final TextField textFieldId = new TextField();
    final TextField textFieldNume = new TextField();
    final TextField textFieldGrupa = new TextField();
    final TextField textFieldEmail = new TextField();
    final TextField textFieldProf = new TextField();
    private final StudentController sCtr;
    private BorderPane borderPane;
    private TableView<Student> studTableView = new TableView<>();

    public StudentView(StudentController sCtr) {
        this.sCtr = sCtr;
        initView();
    }

    private void initView() {
        borderPane = new BorderPane();
//        //top AnchorPane
//        borderPane.setTop(initTop());
        //left
        borderPane.setLeft(initLeft());
        //center
        borderPane.setCenter(initCenter());

        Label copyright = new Label("@Andrei Ene");
        AnchorPane a = new AnchorPane(copyright);
        AnchorPane.setBottomAnchor(copyright, 10d);
        AnchorPane.setLeftAnchor(copyright, 525d);
        copyright.setFont(new Font(10));
        borderPane.setBottom(a);
    }

    private AnchorPane initLeft() {
        AnchorPane leftAnchorPane = new AnchorPane();
        VBox studentInfoBox = new VBox();
        GridPane gridPane = createGridPane();
        studentInfoBox.getChildren().add(gridPane);
        AnchorPane.setLeftAnchor(studentInfoBox, 20d);
        AnchorPane.setTopAnchor(studentInfoBox, 50d);

        HBox buttonsGroups = createButtons();
//        AnchorPane.setBottomAnchor(buttonsGroups,50d);
//        AnchorPane.setLeftAnchor(buttonsGroups,20d);
        studentInfoBox.getChildren().add(buttonsGroups);
        leftAnchorPane.getChildren().add(studentInfoBox);
        studentInfoBox.setSpacing(20d);
        return leftAnchorPane;
    }

    private AnchorPane initCenter() {
        AnchorPane centerAnchorPane = new AnchorPane();
        studTableView = createStudentTable();
        centerAnchorPane.getChildren().add(studTableView);
        AnchorPane.setLeftAnchor(studTableView, 20d);
        AnchorPane.setTopAnchor(studTableView, 50d);
        return centerAnchorPane;
    }

    private GridPane createGridPane() {
        GridPane gridPaneStudentDetails = new GridPane();

        gridPaneStudentDetails.setHgap(5);
        gridPaneStudentDetails.setVgap(5);

        Label labelID = createLabel("ID:");
        Label labelNume = createLabel("Nume:");
        Label labelGrupa = createLabel("Grupa:");
        Label labelEmail = createLabel("E-mail:");
        Label labelProf = createLabel("Profesor:");

        gridPaneStudentDetails.add(labelID, 0, 0);
        gridPaneStudentDetails.add(labelNume, 0, 1);
        gridPaneStudentDetails.add(labelGrupa, 0, 2);
        gridPaneStudentDetails.add(labelEmail, 0, 3);
        gridPaneStudentDetails.add(labelProf, 0, 4);

        gridPaneStudentDetails.add(textFieldId, 1, 0);
        gridPaneStudentDetails.add(textFieldNume, 1, 1);
        gridPaneStudentDetails.add(textFieldGrupa, 1, 2);
        gridPaneStudentDetails.add(textFieldEmail, 1, 3);
        gridPaneStudentDetails.add(textFieldProf, 1, 4);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPrefWidth(80d);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPrefWidth(250d);

        gridPaneStudentDetails.getColumnConstraints().addAll(c1, c2);

        return gridPaneStudentDetails;
    }

    private TableView<Student> createStudentTable() {
        TableColumn<Student, String> nameColumn = new TableColumn<>("Nume");
        TableColumn<Student, String> grupaColumn = new TableColumn<>("Grupa");
        TableColumn<Student, String> emailColumn = new TableColumn<>("E-mail");
        TableColumn<Student, String> profColumn = new TableColumn<>("Profesor");
        nameColumn.setMinWidth(160d);
        grupaColumn.setMinWidth(55d);
        emailColumn.setMinWidth(300d);
        profColumn.setMinWidth(160d);

        studTableView.getColumns().addAll(nameColumn, grupaColumn, emailColumn, profColumn);

        //render data
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("numele"));
        grupaColumn.setCellValueFactory(new PropertyValueFactory<>("grupa"));

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        profColumn.setCellValueFactory(new PropertyValueFactory<>("prof"));

        //bind data
        studTableView.setItems(sCtr.getModel());

        //add listener
        studTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    sCtr.showStudentDetails(newValue);
                    textFieldId.setEditable(false);
                });
        studTableView.setMaxWidth(680d);
        return studTableView;
    }

    private HBox createButtons() {
        Button buttonAdd = new Button("Add");
        Button buttonUpdate = new Button("Update");
        Button buttonDelete = new Button("Delete");
        Button buttonClear = new Button("Clear");
        buttonAdd.setMinWidth(80d);
        buttonUpdate.setMinWidth(80d);
        buttonDelete.setMinWidth(80d);
        buttonClear.setMinWidth(80d);

        HBox hb = new HBox(5, buttonAdd, buttonUpdate, buttonDelete, buttonClear);
        buttonAdd.setOnAction(sCtr::handleAddStudent);
        buttonUpdate.setOnAction(sCtr::handleUpdateStudent);
        buttonClear.setOnAction(sCtr::handleClearFields);
        buttonDelete.setOnAction(sCtr::handleDeleteStudent);
        return hb;
    }

    private Label createLabel(String s) {
        Label l = new Label(s);
        l.setFont(new Font(15));
        l.setTextFill(Color.BLUE);
        l.setStyle("-fx-font-weight: bold");
        return l;
    }

    BorderPane getView() {
        return borderPane;
    }
}
