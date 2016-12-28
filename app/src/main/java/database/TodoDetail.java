package database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

/**
 * Created by aminm on 12/14/16.
 */
@Table(database = ToDoDatabase.class)
@Parcel(analyze = {TodoDetail.class})
public class TodoDetail extends BaseModel {
    @Column
    @PrimaryKey
    int id;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    Todo todo;

    @Column
    String todoDetail;

    @Column
    boolean isCompleted;

    @Column
    TodoPriority todoPriority;

    @Column
    long todoDate;

    // Empty constructor for Parceler library
    public TodoDetail() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public String getTodoDetail() {
        return todoDetail;
    }

    public void setTodoDetail(String todoDetail) {
        this.todoDetail = todoDetail;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public TodoPriority getTodoPriority() {
        return todoPriority;
    }

    public void setTodoPriority(TodoPriority todoPriority) {
        this.todoPriority = todoPriority;
    }

    public long getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(long todoDate) {
        this.todoDate = todoDate;
    }
}
