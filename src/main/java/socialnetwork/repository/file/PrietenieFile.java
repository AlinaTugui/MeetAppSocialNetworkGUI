package socialnetwork.repository.file;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrietenieFile extends AbstractFileRepository0<Tuple<Long,Long>, Prietenie>{

    public PrietenieFile(String fileName, Validator<Prietenie> validator) {
        super(fileName, validator);
    }

    @Override
    public Prietenie extractEntity(List<String> attributes) {
        Prietenie p = new Prietenie(LocalDateTime.parse(attributes.get(2)));
        p.setId(new Tuple(Long.parseLong(attributes.get(0)), Long.parseLong(attributes.get(1))));
        return p;
    }

    @Override
    protected String createEntityAsString(Prietenie entity) {
        return entity.getId().getLeft() + ";" + entity.getId().getRight() + ";"
                + entity.getDateTime().toString();
    }
}
