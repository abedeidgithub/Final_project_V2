package com.example.abedeid.myapplication.utils;


import android.app.Activity;
import android.content.Intent;


import com.example.abedeid.myapplication.activites.LoginActivity;
import com.example.abedeid.myapplication.model.users;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Session {
        // define single instance
        private static Session instance;
        // define realm
        private Realm realm;

        // Session constructor
        private Session() {
            RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(realmConfig);
        }

        // get singletone from session
        public static Session getInstance() {
            if (instance == null) {
                instance = new Session();
            }
            return instance;
        }

        // get new Instance (new Object) from this class
        public static Session newInstance() {
            return new Session();
        }

        // login user take user and add it to realm
        public void loginUser(final users user) {

            if (realm.where(users.class).findFirst() == null) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(user);
                    }
                });

            } else {
                logout();
                loginUser(user);
            }
        }

        // logout
        public void logout() {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(users.class);
                    realm.deleteAll();
                }
            });
        }

        public Boolean isUserLoggedIn() {
            return realm.where(users.class).findFirst() != null;
        }

        public users getUser() {
            return realm.where(users.class).findFirst();
        }
        public void logoutAndGoToLogin(Activity activity) {
            logout();
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
        }
}