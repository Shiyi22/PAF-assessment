package ibf2022.paf.assessment.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// TODO: Task 7

@Service
public class TodoService {

    @Autowired
    private TaskRepository taskRepo; 

    @Autowired
    private UserRepository userRepo; 

    // insert a list of tasks for particular user (based on user_id)
    // assume user_id are the same for taskList since they come from one form data with only 1 username 
    @Transactional
    public Boolean upsertTask(List<Task> taskList, String username) {
        
        Boolean bUpserted = false; 

        // if user does not exist, create user in user SQL table before inserting tasks
        Optional<User> opt = userRepo.findUserByUsername(username); 
        User user = new User(); 

        if (opt.isEmpty()) {
            // only username and name required in User class - user_id will be added in insert step 
            user.setUsername(username);
            user.setName(username.substring(0,1).toUpperCase() + username.substring(1)); 
            userRepo.insertUser(user);
        }

        // insert list of tasks 
        List<Integer> rowsAffected = new ArrayList<>(); 
        for (Task t : taskList) {
            Integer iUpdated = taskRepo.insertTask(t);
            rowsAffected.add(iUpdated);
        }

        if (!rowsAffected.contains(0)) {
            return bUpserted = true; 
        }
        return bUpserted; 
        
    }

}
