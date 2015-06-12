package manager;

import org.json.*;
import java.util.ArrayList;
import org.joda.time.*;

/**
 * Created by Nick on 5/26/2015.
 * @author Nick
 * @version 1.0
 * @since 6-12-2015
 */
public class ToDo extends Feature{
    public static final String DEFAULT_TZ = "America/Toronto";
    private ArrayList<Task> tasks;
    private String description;

    public ToDo(){
        tasks = new ArrayList<Task>();
    }
    public ToDo(String desc){
        tasks = new ArrayList<Task>();
        description = desc;
    }

    public JSONObject toJSON(){
        Task[] taskArray = this.tasks.toArray(new Task[tasks.size()]);
        JSONObject json = new JSONObject();
        JSONArray tasks = new JSONArray();
        try {
            json.put("description", this.description);
            for(Task element: taskArray){
                tasks.put(element.toJSON());
            }
            json.put("tasks",tasks);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return json;
    }

    public Task[] getOverdueTasks(){
        Task[] result = null;
        int i = binarySearch(tasks.toArray(new Task[tasks.size()]),DateTime.now(),0,tasks.size()-1);
        if (i > 0){
            result = new Task[i];
            while(i > 0){
                i--;
                result[i] = tasks.get(i);
            }
        }
        return result;
    }
    public Task[] getTasks(){
        return tasks.toArray(new Task[tasks.size()]);
    }
    public Task deleteTask(int index){
        if (index >= tasks.size()){
            return null;
        }
        return tasks.remove(index);
    }
    public Task getTask(int index){
        if (index >= tasks.size()){
            return null;
        }
        return new Task(tasks.get(index));
    }
    public int insertTask(String desc, DateTime due){
        Task task = new Task(desc, due);
        Task[] taskArray = tasks.toArray(new Task[tasks.size()]);
        int i=0;
        if (task.getDueDate() != null) {
            i = binarySearch(taskArray, task.getDueDate(), 0, tasks.size() - 1);
        }
        else{
            i = tasks.size();
        }
        tasks.add(i,task);
        return i;
    }
    public int insertTask(String desc){
        return insertTask(desc,null);
    }
    private int binarySearch(Task[] tasks, DateTime key, int left, int right){
        int mid = (right + left) / 2;
        if (left > right){
            return left;
        }
        else if (tasks[mid].getDueDate() == null || key.compareTo(tasks[mid].getDueDate()) < 0) {
            return binarySearch(tasks,key,left, mid-1);
        }

        else  {
            return binarySearch(tasks, key, mid + 1, right);
        }
    }

    static private final class Task extends Feature{
        private String description;
        private DateTime created;
        private DateTime due;
        private DateTime completed;

        public Task(Task t) {
            this.description = t.description;;
            this.created = t.created;
            this.due = t.due;
            this.completed = t.completed;
        }
        public Task(String desc){
            this.description = desc;
            this.created = DateTime.now();
        }
        public Task(String desc, DateTime due){
            this.description = desc;
            this.created = DateTime.now();
            this.due = due;
        }

        public DateTime getDueDate(){
            return this.due;
        }
        public DateTime getStartDate(){
            return this.created;
        }
        public DateTime getCompletionDate(){
            return this.completed;
        }
        public String getDescription(){
            return this.description;
        }
        public void completeTask(){
            this.completed = DateTime.now();
            return;
        }

        public JSONObject toJSON(){
            JSONObject json = new JSONObject();
            try {
                json.put("description", this.description);
                json.put("created", this.created);
                json.put("due",this.due);
                json.put("completed", this.completed);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return json;
        }
        public String toString(){
            return String.format("Task: %s, created at: %s, due at: %s, completed at: %s",description, created, due, completed);
        }
    }
}
