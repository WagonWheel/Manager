package manager;

import org.json.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.joda.time.*;

/**
 * Created by Nick on 5/26/2015.
 * @author Nick
 * @version 1.0
 * @since 6-12-2015
 */
public class ToDoShell{
    private static final String DEFAULT_TZ = "America/Toronto";
    private static final String[] commands = {"create", "save", "migrate", "display", "select", "load"};
    private ArrayList<ToDo> current = new ArrayList<>();
    public static boolean run(String[] cmd){
        if (cmd.length > 0 && cmd[0].toLowerCase().compareTo("todo") != 0){
            Main.printError("Wrong Feature");
            return false;
        }
        if (cmd.length - 1 > 0){
            String[] args = new String[cmd.length - 1];
            for(int i = 0; i < cmd.length-1; i++){
                args[i] = cmd[i+1];
            }
            return parse(args);
        }
        else{
            Main.printError("No commands given");
            return false;
        }
    }

    private static boolean parse(String[] args){
        for(int i = 0; i < commands.length; i++){
            if(args[0].toLowerCase().compareTo(commands[i]) == 0){

            }
        }
    }

    private final class ToDo extends Feature{
        private ArrayList<Task> tasks;
        private ArrayList<Task> completedTasks;
        private String description;

        private ToDo(){
            tasks = new ArrayList<Task>();
            completedTasks = new ArrayList<Task>();
        }
        private ToDo(String desc){
            tasks = new ArrayList<Task>();
            completedTasks = new ArrayList<Task>();
            description = desc;
        }

        public JSONObject toJSON(){
            JSONObject json = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            try {
                json.put("description", this.description);
                for(Task element: this.tasks) {
                    jsonArray.put(element.toJSON());
                }
                json.put("tasks",jsonArray);
                jsonArray = new JSONArray();
                for(Task element: this.completedTasks){
                    jsonArray.put(element.toJSON());
                }
                json.put("completed tasks", jsonArray);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return json;
        }

        private boolean completeTask(String desc){
            for (int i = 0; i < tasks.size(); i++){
                if (tasks.get(i).getDescription().equals(desc)){
                    tasks.get(i).completeTask();
                    completedTasks.add(tasks.remove(i));
                    return true;
                }
            }
            return false;
        }
        private boolean completeTask(int index){
            if (index >= tasks.size()){
                return false;
            }
            tasks.get(index).completeTask();
            completedTasks.add(tasks.remove(index));
            return true;
        }

        private void displayOverdueTasks(){
            int i = binarySearch(tasks.toArray(new Task[tasks.size()]),DateTime.now(),0,tasks.size()-1);
            if (i > 0){
                while(i > 0){
                    i--;
                    displayTask(i);
                }
            }
            return;
        }
        private void displayTasks(){
            for (int i = 0; i < tasks.size(); i++){
                displayTask(i);
            }
            return;
        }
        private void displayCompletedTasks(){
            for (int i = 0; i < completedTasks.size(); i++){
                displayCompletedTask(i);
            }
            return;
        }
        private void displayTask(int index){
            if (index >= tasks.size()){
                return;
            }
            System.out.println(tasks.get(index));
            return;
        }
        private void displayTask(String desc){
            for (int i = 0; i < tasks.size(); i++){
                if (tasks.get(i).getDescription().equals(desc)){
                    System.out.println(tasks.get(i));
                }
            }
            return;
        }
        private void displayCompletedTask(int index){
            if (index >= completedTasks.size()){
                return;
            }
            System.out.println(completedTasks.get(index));
            return;
        }
        private void displayCompletedTask(String desc){
            for (int i = 0; i < completedTasks.size(); i++){
                if (completedTasks.get(i).getDescription().equals(desc)){
                    System.out.println(completedTasks.get(i));
                }
            }
            return;
        }

        private int insertTask(String desc, DateTime due){
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
        private int insertTask(String desc){
            return insertTask(desc,null);
        }
        private boolean deleteTask(int index){
            if (index >= tasks.size()){
                return false;
            }
            tasks.remove(index);
            return true;
        }
        private boolean deleteTask(String desc){
            for (int i = 0; i < tasks.size(); i++){
                if (tasks.get(i).getDescription().equals(desc)){
                    tasks.remove(i);
                    return true;
                }
            }
            return false;
        }

        private int binarySearch(Task[] tasks, DateTime key, int left, int right){
            int mid = (right + left)/2;
            if (left > right){
                return left;
            }
            else if (tasks[mid].getDueDate() == null || key.isAfter(tasks[mid].getDueDate()) == true) {
                return binarySearch(tasks,key,left, mid-1);
            }

            else  {
                return binarySearch(tasks, key, mid + 1, right);
            }
        }
    }

    private final class Task extends Feature{
        private String description;
        private DateTime created;
        private DateTime due;
        private DateTime completed;

        private Task(Task t) {
            this.description = t.description;;
            this.created = t.created;
            this.due = t.due;
            this.completed = t.completed;
        }
        private Task(String desc){
            this.description = desc;
            this.created = DateTime.now();
        }
        private Task(String desc, DateTime due){
            this.description = desc;
            this.created = DateTime.now();
            this.due = due;
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

        private DateTime getDueDate(){
            return this.due;
        }
        private DateTime getStartDate(){
            return this.created;
        }
        private DateTime getCompletionDate(){
            return this.completed;
        }
        private String getDescription(){
            return this.description;
        }
        private void completeTask(){
            this.completed = DateTime.now();
            return;
        }


    }
}
