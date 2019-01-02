package AplicatieTeme.Repository;

import AplicatieTeme.Domain.HasID;
import AplicatieTeme.Domain.Validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public abstract class AbstractXMLRepository<ID, E extends HasID<ID>> extends InMemoryRepository<ID, E> {
    private final String fileName;

    AbstractXMLRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.fileName);

            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node element = children.item(i);
                if (element instanceof Element) {
                    E en = createEntityFromElement((Element) element);
                    super.save(en);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeAllToFile() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("inbox");
            document.appendChild(root);
            super.findAll().forEach(en -> {
                Element el = createElementfromEntity(document, en);
                root.appendChild(el);
            });

            // write Document to file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(fileName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract Element createElementfromEntity(Document document, E en);

    protected abstract E createEntityFromElement(Element element);

    @Override
    public E save(E entity) {
        E returnedEntity = super.save(entity);
        if (returnedEntity == null) {
            writeAllToFile();
        }
        return returnedEntity;
    }

    @Override
    public E delete(ID id) {
        E returnedEntity = super.delete(id);
        if (returnedEntity != null) {
            writeAllToFile();
        }
        return returnedEntity;
    }

    @Override
    public E update(E entity) {
        E returnedEntity = super.update(entity);
        if (returnedEntity == null) {
            writeAllToFile();
        }
        return returnedEntity;
    }
}
