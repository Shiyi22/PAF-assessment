package ibf2022.paf.assessment.server.models;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// TODO: Task 4

public class Task {

    private String userId;
    private String description;
    private Integer priority; // can only be 1, 2 or 3
    private Date dueDate; 

    public Task () {}

    // getter setter 
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public Integer getPriority() {return priority;}
    public void setPriority(Integer priority) {this.priority = priority;}
    public Date getDueDate() {return dueDate;}
    public void setDueDate(Date dueDate) {this.dueDate = dueDate;}


    public static Map<String, String> parseTaskMap(String payload) {
        Map<String, String> taskMap = new TreeMap<>();

        String[] params = payload.split("&");
        for (String param : params) {

            String[] parts = param.split("=");

            // convert "+" to " "
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].replace("+", " ");
            }

            // dont get the data if there is no key-value pair 
            if (parts.length != 2) {continue;}

            String name = parts[0];
            String value = parts[1];
            taskMap.put(name, value);
        }
        return taskMap;
    }

    public static List<Task> createTaskList(Integer num, Map<String, String> taskMap, String userId) throws ParseException {

        List<Task> taskList = new ArrayList<>(); 
        for (int i = 0; i < num; i++) {
            Task task = new Task(); 
            task.setUserId(userId);
            task.setDescription(taskMap.get("description-" + i));
            task.setPriority(Integer.parseInt(taskMap.get("priority-" + i)));
            task.setDueDate(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(taskMap.get("dueDate-" + i)).getTime()));
            taskList.add(task); 
        }
        return taskList; 
    }
}
