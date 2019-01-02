package AplicatieTeme;

import AplicatieTeme.Business.NotaBusiness;
import AplicatieTeme.Business.StudentBusiness;
import AplicatieTeme.Business.TemeBusiness;
import AplicatieTeme.GUI.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private StudentBusiness studBuss;
    private TemeBusiness temaBuss;
    private NotaBusiness notaBuss;
    private StudentController sCtr;
    private StudentView sView;
    private Stage notaStage;
    private Stage temaStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        studBuss = new StudentBusiness();
        temaBuss = new TemeBusiness();
        notaBuss = new NotaBusiness(studBuss, temaBuss);
        sCtr = new StudentController(studBuss);
        sView = new StudentView(sCtr);
        sCtr.setView(sView);

        try {
            init(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        primaryStage.setTitle("Student CRUD Operations");
//        primaryStage.setScene(new Scene(sView.getView(), 1070, 500));
//        primaryStage.show();
    }

    private void init(Stage primaryStage) throws IOException {

        //main menu

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GUI/MainMenu.fxml"));
        VBox rootLayout = loader.load();
        MainMenuController controller = loader.getController();
        controller.setsCtr(sCtr);
        controller.setsView(sView);
        controller.setNotaBuss(notaBuss);
        Scene mainMenuScene = new Scene(rootLayout);
        primaryStage.setScene(mainMenuScene);
        controller.setPrimaryStage(primaryStage);


        //2.Nota view
        FXMLLoader notaLoader = new FXMLLoader();
        notaLoader.setLocation(getClass().getResource("GUI/Note.fxml"));
        AnchorPane notaLayout = notaLoader.load();
        NoteController noteController = notaLoader.getController();
        notaStage = new Stage();
        Scene notaScene = new Scene(notaLayout);
        notaStage.setScene(notaScene);
        noteController.setNotaBusiness(notaBuss, notaStage);
        controller.setNotaStage(notaStage);

        //3.Tema view
        FXMLLoader temaLoader = new FXMLLoader();
        temaLoader.setLocation(getClass().getResource("GUI/Teme.fxml"));
        AnchorPane temaLayout = temaLoader.load();
        TemaController temaController = temaLoader.getController();
        temaStage = new Stage();
        Scene temaScene = new Scene(temaLayout);
        temaStage.setScene(temaScene);
        temaController.setTemaBusiness(temaBuss, temaStage);
        temaController.setNotaBuss(notaBuss);
        controller.setTemaStage(temaStage);
//
//
//        //2.Grade view
//        FXMLLoader gradeLoader = new FXMLLoader();
//        gradeLoader.setLocation(getClass().getResource("/views/gradesView.fxml"));
//        AnchorPane gradeLayout = gradeLoader.load();
//        GradeController gradeController = gradeLoader.getController();
//        //gradeController.setGradeTaskService(messageGradeService);
//        controller.setCenterGradeLayout(gradeLayout);


        primaryStage.show();
//        primaryStage.setWidth(500);
//        primaryStage.setHeight(500);

    }
}
