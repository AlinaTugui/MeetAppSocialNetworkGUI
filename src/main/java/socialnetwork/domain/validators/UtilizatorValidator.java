package socialnetwork.domain.validators;

import socialnetwork.domain.Utilizator;

public class UtilizatorValidator extends Throwable implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String err="";
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        String email = entity.getEmail();
        if( firstName == null || !firstName.matches( "[A-Z]+([ '-]*[a-zA-Z]+)*" ) ) err += "Prenume invalid!";
        if( lastName == null || !lastName.matches( "[A-Z]+([ '-]*[a-zA-Z]+)*" ) ) err += "Nume invalid!";
        if( email == null || !email.contains("@") || (!email.endsWith(".com") && !email.endsWith(".ro"))) err +="Email invalid";
        if( !err.equals("") ) throw new ValidationException(err);
    }
}
