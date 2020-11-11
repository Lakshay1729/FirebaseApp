package com.example.ui.RealmDB;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {



   @Required

   @Index
    private String firstName;
   private long partitionValue;
    @Required
    private String lastName;
    @Required
    private String phoneNumber;
    @Required

    @Index
    @PrimaryKey
    private String email;
    @Required
    private String password;

//   private RealmList<Characters> characters;

    public User()
    {

    }


    public User(String firstName, String lastName, String phoneNumber, String email, String password, RealmList<Characters> characters) {
        //this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;

//        this.characters = characters;
    }

    public User(long partition) {
        this.partitionValue=partition;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPartitionValue() {
        return partitionValue;
    }

    public void setPartitionValue(long partitionValue) {
        this.partitionValue = partitionValue;
    }

//    public RealmList getCharacters() {
//        return characters;
//    }
//
//    public void setCharacters(RealmList characters) {
//        this.characters = characters;
//    }


//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
}
