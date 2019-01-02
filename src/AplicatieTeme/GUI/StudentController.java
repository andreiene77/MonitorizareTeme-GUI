package AplicatieTeme.GUI;

import AplicatieTeme.Business.StudentBusiness;
import AplicatieTeme.Domain.Student;
import AplicatieTeme.Domain.Validators.ValidationException;
import AplicatieTeme.Utils.Observer;
import AplicatieTeme.Utils.StudentChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentController implements Observer<StudentChangeEvent> {
    private final StudentBusiness studBuss;
    private final ObservableList<Student> model;

    private StudentView view;

    public StudentController(StudentBusiness studBuss) {
        this.studBuss = studBuss;
        List<Student> list = StreamSupport.stream(studBuss.getAll().spliterator(), false)
                .collect(Collectors.toList());
        model = FXCollections.observableArrayList(list);
        studBuss.addObserver(this);
    }

    private static void showMessage(Alert.AlertType type, String header, String text) {
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    private static void showErrorMessage(String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    @Override
    public void update(StudentChangeEvent studentChangeEvent) {
        model.setAll(StreamSupport.stream(studBuss.getAll().spliterator(), false)
                .collect(Collectors.toList()));
    }

    public StudentView getView() {
        return view;
    }

    public void setView(StudentView view) {
        this.view = view;
    }

    ObservableList<Student> getModel() {
        return model;
    }

    void showStudentDetails(Student value) {
        if (value == null) {
            view.textFieldId.setText("");
            view.textFieldNume.setText("");
            view.textFieldGrupa.setText("");
            view.textFieldEmail.setText("");
            view.textFieldProf.setText("");
        } else {
            view.textFieldId.setText(value.getID());
            view.textFieldNume.setText(value.getNumele());
            view.textFieldGrupa.setText(value.getGrupa());
            view.textFieldEmail.setText(value.getEmail());
            view.textFieldProf.setText(value.getProf());
        }
    }

    void handleAddStudent(ActionEvent actionEvent) {
        Student s = extractStudent();
        try {
            Student saved = studBuss.addStud(s);
            if (saved == null) {
                showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Studentul a fost adaugat!");
                showStudentDetails(null);
            } else
                showErrorMessage("Exista deja un student cu acest id!");
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    void handleUpdateStudent(ActionEvent actionEvent) {
        Student s = extractStudent();
        try {
            Student modified = studBuss.updateStud(s);
            if (modified == null) {
                showMessage(Alert.AlertType.INFORMATION, "Modificare cu succes", "Studentul a fost modificat!");
                showStudentDetails(null);
            } else
                showErrorMessage("Nu exista un student cu acest id!");
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    void handleDeleteStudent(ActionEvent actionEvent) {
        Student s = extractStudent();
        try {
            Student deleted = studBuss.removeStud(s.getID());
            if (deleted != null) {
                showMessage(Alert.AlertType.INFORMATION, "Stergere cu succes", "Studentul a fost sters!");
                showStudentDetails(null);
            } else
                showErrorMessage("Nu exista un student cu acest id!");
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    void handleClearFields(ActionEvent actionEvent) {
        showStudentDetails(null);
        view.textFieldId.setEditable(true);
    }

    private Student extractStudent() {
        String id = view.textFieldId.getText();
        String nume = view.textFieldNume.getText();
        String grupa = view.textFieldGrupa.getText();
        String email = view.textFieldEmail.getText();
        String prof = view.textFieldProf.getText();
        return new Student(id, nume, grupa, email, prof);
    }
}
