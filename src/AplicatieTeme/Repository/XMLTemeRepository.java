package AplicatieTeme.Repository;

import AplicatieTeme.Domain.Tema;
import AplicatieTeme.Domain.Validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLTemeRepository extends AbstractXMLRepository<String, Tema> {
    public XMLTemeRepository(String fileName, Validator<Tema> validator) {
        super(fileName, validator);
    }

    @Override
    protected Element createElementfromEntity(Document document, Tema en) {
        Element e = document.createElement("tema");
        e.setAttribute("temaID", en.getID());

        Element descriere = document.createElement("descriere");
        descriere.setTextContent(en.getDescriere());
        e.appendChild(descriere);

        Element deadline = document.createElement("deadline");
        deadline.setTextContent(String.valueOf(en.getDeadlineWeek()));
        e.appendChild(deadline);

        Element startWeek = document.createElement("startWeek");
        startWeek.setTextContent(String.valueOf(en.getStartWeek()));
        e.appendChild(startWeek);

        return e;
    }

    @Override
    protected Tema createEntityFromElement(Element element) {
        String temaID = element.getAttribute("temaID");
        String descreiere = element.getElementsByTagName("descriere").item(0).getTextContent();

        Integer deadline = Integer.valueOf(element.getElementsByTagName("deadline").item(0).getTextContent());

        Integer startWeek = Integer.valueOf(element.getElementsByTagName("startWeek").item(0).getTextContent());
        return new Tema(temaID, descreiere, deadline, startWeek);
    }
}
