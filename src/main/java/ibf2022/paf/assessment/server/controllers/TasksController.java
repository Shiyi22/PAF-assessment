package ibf2022.paf.assessment.server.controllers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.UserRepository;
import ibf2022.paf.assessment.server.services.TodoService;

// TODO: Task 4, Task 8

@Controller
public class TasksController {

    @Autowired
    private UserRepository userRepo; 

    @Autowired
    private TodoService todoSvc; 

    @PostMapping("/task")
    public ModelAndView processSave(@RequestBody String payload) throws ParseException {
        
        Boolean bContinue = true; 
        Boolean bUpserted = false; 

        // >>> payload: username=fred&description-0=exercise&priority-0=1&dueDate-0=2023-04-07&description-1=boxing&priority-1=3&dueDate-1=2023-04-08

        // convert payload to TreeMap
        Map<String, String> taskMap = Task.parseTaskMap(payload); 
        System.out.printf(">>> task map: %s\n", taskMap.toString()); 
        
        // Get the number of tasks
        Integer num = (int) taskMap.keySet().stream().filter(t -> t.startsWith("description-")).count();

        // Get userId from username
        String username = taskMap.get("username"); 

        // check for spaces in username 
        if (username.contains("+"))
            bContinue = false; 

        if (bContinue) {

            Optional<User> opt = userRepo.findUserByUsername(username); 
            String userId = ""; 
            if (opt.isPresent()) {
                User user = opt.get(); 
                 userId = user.getUserId(); 
            }

            // Create a list of Task objects from the payload
            List<Task> taskList = Task.createTaskList(num, taskMap, userId); 

            // insert multiple task into SQL database 
            bUpserted = todoSvc.upsertTask(taskList, username); 
        }
 
        if (bUpserted || bContinue) {
            // add taskCount and username
            Map<String, String> models = new HashMap<>(); 
            models.put("taskCount", String.valueOf(num)); 
            models.put("username", username); 
            return new ModelAndView("/result", models, HttpStatusCode.valueOf(200)); 
        } else {
            return new ModelAndView("/error", HttpStatusCode.valueOf(500)); 
        }
    }
    
}
