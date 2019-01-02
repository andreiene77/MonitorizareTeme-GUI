package AplicatieTeme.Repository;

import AplicatieTeme.Domain.Nota;
import AplicatieTeme.Domain.Validators.Validator;
import AplicatieTeme.Utils.NotaID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.List;

public class XMLNoteRepository extends AbstractXMLRepository<NotaID<String, String>, Nota> {
    public XMLNoteRepository(String fileName, Validator<Nota> validator) {
        super(fileName, validator);
    }

    @Override
    protected Element createElementfromEntity(Document document, Nota en) {
        Element e = document.createElement("nota");
        e.setAttribute("notaID", String.valueOf(en.getID()));

        Element data = document.createElement("data");
        data.setTextContent(String.valueOf(en.getData()));
        e.appendChild(data);

        Element valoare = document.createElement("valoare");
        valoare.setTextContent(String.valueOf(en.getValoare()));
        e.appendChild(valoare);

        Element feeedback = document.createElement("feedback");
        feeedback.setTextContent(en.getFeedback());
        e.appendChild(feeedback);

        return e;
    }

    @Override
    protected Nota createEntityFromElement(Element element) {
        String notaId = element.getAttribute("notaID");
        List<String> attr = Arrays.asList(notaId.split(", "));
        String SID = attr.get(0).replace("(", "");
        String TID = attr.get(1).replace(")", "");
        String data = element.getElementsByTagName("data").item(0).getTextContent();

        String valoare = element.getElementsByTagName("valoare").item(0).getTextContent();

        String feedback = element.getElementsByTagName("feedback").item(0).getTextContent();

        return new Nota(SID, TID, Integer.parseInt(data), Float.parseFloat(valoare), feedback);
    }
}
