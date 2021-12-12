package socialnetwork.repository.file;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.memory.InMemoryRepository0;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


///Aceasta clasa implementeaza sablonul de proiectare Template Method; puteti inlucui solutia propusa cu un Factori (vezi mai jos)
public abstract class AbstractFileRepository0<ID, E extends Entity<ID>> extends InMemoryRepository0<ID, E> {
    String fileName;

    public AbstractFileRepository0(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while((line =  bufferedReader.readLine()) != null){
                String[] args = line.split(";");
                E entity = extractEntity(Arrays.asList(args));
                super.save(entity);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Path, Files, ->, extractEntity
//        Path path = Paths.get(fileName);
//        try {
//            List<String> lista = Files.readAllLines(path);
//            lista.forEach((line) -> {
//                String[] args = line.split(";");
//                E entity = extractEntity(Arrays.asList(args));
//                super.save(entity);
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * extract entity  - template method design pattern
     * creates an entity of type E having a specified list of @code attributes
     *
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);


    protected abstract String createEntityAsString(E entity);

    @Override
    public E save(E entity) {
        super.save(entity);
        writeToFile();
        return null;
    }

    @Override
    public E update(E entity){
        super.update(entity);
        writeToFile();
        return null;
    }

    @Override
    public E delete(ID id){
        E e = super.delete(id);
        writeToFile();
        return e;
    }

    protected void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter((new FileWriter(fileName)))) {
            super.findAll().forEach(entity->{
                String entityAsString = createEntityAsString(entity);
                try {
                    bufferedWriter.write(entityAsString);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

