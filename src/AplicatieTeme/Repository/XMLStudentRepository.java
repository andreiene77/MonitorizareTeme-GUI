package AplicatieTeme.Repository;

import AplicatieTeme.Domain.Student;
import AplicatieTeme.Domain.Validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLStudentRepository extends AbstractXMLRepository<String, Student> {
    public XMLStudentRepository(String fileName, Validator<Student> validator) {
        super(fileName, validator);
    }

    @Override
    protected Element createElementfromEntity(Document document, Student en) {
        Element e = document.createElement("student");
        e.setAttribute("studentID", en.getID());

        Element nume = document.createElement("nume");
        nume.setTextContent(en.getNumele());
        e.appendChild(nume);

        Element grupa = document.createElement("grupa");
        grupa.setTextContent(en.getGrupa());
        e.appendChild(grupa);

        Element email = document.createElement("email");
        email.setTextContent(en.getEmail());
        e.appendChild(email);

        Element prof = document.createElement("prof");
        prof.setTextContent(en.getProf());
        e.appendChild(prof);

        return e;
    }

    @Override
    protected Student createEntityFromElement(Element element) {
        String studentId = element.getAttribute("studentID");
        String nume = element.getElementsByTagName("nume").item(0).getTextContent();

        String grupa = element.getElementsByTagName("grupa").item(0).getTextContent();

        String email = element.getElementsByTagName("email").item(0).getTextContent();

        String prof = element.getElementsByTagName("prof").item(0).getTextContent();
        return new Student(studentId, nume, grupa, email, prof);
    }
}
