package ibf2022.paf.assessment.server.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// TODO: Task 4, Task 8

@Controller
public class TasksController {

    @Autowired
    private UserRepository userRepo; 

    @PostMapping("/task")
    public String processSave(@RequestBody String payload) throws ParseException {
        
        // >>> payload: username=fred&description-0=exercise&priority-0=1&dueDate-0=2023-04-072023-04-05T10:12:50.956+08:00
        // username=fred&description-0=exercise&priority-0=1&dueDate-0=2023-04-07&description-1=boxing&priority-1=3&dueDate-1=2023-04-08
        // System.out.printf(">>> payload: %s", payload); 

        // convert payload to TreeMap
        Map<String, String> taskMap = Task.parseTaskMap(payload); 
        System.out.printf(">>> task map: %s\n", taskMap.toString()); 
        
        // Get the number of tasks
        Integer num = (int) taskMap.keySet().stream().filter(t -> t.startsWith("description-")).count();

        // Get userId from username
        Optional<User> opt = userRepo.findUserByUsername(taskMap.get("username")); 
        String userId = ""; 
        if (opt.isPresent()) {
            User user = opt.get(); 
             userId = user.getUserId(); 
        }

        // Create a list of Task objects from the payload
        List<Task> taskList = Task.createTaskList(num, taskMap, userId); 

        // insert single task into database 

        // insert multiple task into database 

        // return if successful 
        return "result.html"; 
        return "error.html";
    }
    
}
