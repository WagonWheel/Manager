package manager;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Main {

    public static void main(String[] args) {
        Task test = new Task("test task", new DateTime(2015,6,3,15,30, DateTimeZone.forID(ToDo.DEFAULT_TZ)));

        Task test2 = new Task("2nd test task", DateTime.now().plus(120000));
        Task test3 = new Task("3rd test task", null);
        Task test4 = new Task("4th test task", null);
        x.insertTask(test);
        x.insertTask(test2);
        x.insertTask(test3);
        x.insertTask(test4);
        System.out.println(x.getTask(0));
        System.out.println(x.getTask(1));
        System.out.println(x.getTask(2));
        System.out.println(x.getTask(3));
        System.out.println(x.toJSON());
    }
    private static ToDo createToDo(String desc){
        return new ToDo("desc");
    }
    private static ToDo createToDo(){
        return new ToDo();
    }
}
