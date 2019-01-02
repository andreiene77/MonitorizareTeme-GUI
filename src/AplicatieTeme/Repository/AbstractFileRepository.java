package AplicatieTeme.Repository;

import AplicatieTeme.Domain.HasID;
import AplicatieTeme.Domain.Validators.Validator;

import java.io.*;

public abstract class AbstractFileRepository<ID, E extends HasID<ID>> extends AplicatieTeme.Repository.InMemoryRepository<ID, E> {
    private final String fileName;

    AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                E temp = extractEntity(line);
                super.save(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract E extractEntity(String line);

    @Override
    public E save(E entity) {
        E returnedEntity = super.save(entity);
        if (returnedEntity == null) {
            writeToFile(entity);
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

    private void writeToFile(E entity) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(entity.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAllToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            this.findAll().forEach(entity -> {
                try {
                    bw.write(entity.toString());
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
