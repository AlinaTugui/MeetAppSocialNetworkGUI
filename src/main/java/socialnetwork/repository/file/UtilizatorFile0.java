package socialnetwork.repository.file;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UtilizatorFile0 extends AbstractFileRepository0<Long, Utilizator> {

    public UtilizatorFile0(String fileName, Validator<Utilizator> validator) {
        super(fileName, validator);
    }

    @Override
    public Utilizator extractEntity(List<String> attributes) {
        return new Utilizator(Long.parseLong(attributes.get(0)),attributes.get(1),attributes.get(2));
//        String friends = attributes.get(3);
//        if(friends.equals("[]")) return new Utilizator(Long.parseLong(attributes.get(0)),attributes.get(1),attributes.get(2));
//        friends = friends.substring(1, friends.length()-1);
//        String[] friendsSplit = friends.split(", ");
//        List<Long> friendsLong = Arrays.stream(friendsSplit).map(Long::valueOf).collect(Collectors.toList());
//        Utilizator user = new Utilizator(Long.parseLong(attributes.get(0)),attributes.get(1),attributes.get(2), friendsLong);
//        return user;
    }

    @Override
    protected String createEntityAsString(Utilizator entity) {
        return entity.getId()+";"+entity.getFirstName()+";"+entity.getLastName();
    }
}
