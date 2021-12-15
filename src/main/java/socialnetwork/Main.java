package socialnetwork;

import socialnetwork.ui.UI;

public class Main {
    public static void main(String[] args) {

        //method reference
        //String fileName=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
       UI.init(args);
       // userFileRepository.findAll().forEach(System.out::println);
    }
}


