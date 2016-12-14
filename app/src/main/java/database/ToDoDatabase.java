package database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by aminm on 12/14/16.
 */
@Database(name = ToDoDatabase.NAME, version = ToDoDatabase.VERSION)
public class ToDoDatabase {
    public static final String NAME = "ToDoDatabase";
    public static final int VERSION = 1;
}
