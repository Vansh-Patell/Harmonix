/*****************************
 * Database class
 * Using this class the we change
 * from our stub database to
 * a real HSQL database
 ****************************/
package com.example.harmonix.PersistenceLayer;

public class Database {
    private static final boolean USE_REAL_DATABASE = true; // use the real database or not
    private static IDatabase instance;

    public static synchronized IDatabase getInstance() {
        if (instance == null) {
            if (USE_REAL_DATABASE) {
                instance = new RealDatabase();
            } else {
                instance = new StubDatabase();
            }
        }
        return instance;
    }
}
