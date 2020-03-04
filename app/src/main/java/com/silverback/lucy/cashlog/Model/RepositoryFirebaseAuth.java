package com.silverback.lucy.cashlog.Model;


/**
 * Abstraction layer that gets data from sources and sending it to viewModel
 */
public class RepositoryFirebaseAuth {

    FirebaseSource firebase;

    public RepositoryFirebaseAuth() {
        this.firebase = new FirebaseSource();
    }

    public void register(String email, String password){
        firebase.register(email, password);
    }

    public void login(String email, String password){
        firebase.login(email, password);
    }

    public void logout(){
        firebase.logout();
    }


}       //end class


