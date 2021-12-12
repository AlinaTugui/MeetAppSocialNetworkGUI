package socialnetwork.domain.validators;

import socialnetwork.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String err="";
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        if( entity.getId() == null || entity.getId() <= 0 ) throw new ValidationException("Utilizator null!");
        if( firstName == null || !firstName.matches( "[A-Z]+([ '-]*[a-zA-Z]+)*" ) ) err += "Prenume invalid!";
        if( lastName == null || !lastName.matches( "[A-Z]+([ '-]*[a-zA-Z]+)*" ) ) err += "Nume invalid!";
        if( !err.equals("") ) throw new ValidationException(err);
    }
}
