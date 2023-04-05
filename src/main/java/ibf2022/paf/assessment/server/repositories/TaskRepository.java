package ibf2022.paf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.Task;

// TODO: Task 6

@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate; 

    private static final String INSERT_TASK_SQL = "insert into task (user_id, description, priority, due_date) values (?, ?, ?, ?)"; 

    // method to insert single task into task table (SQL)
    public Integer insertTask(Task task) {
        return jdbcTemplate.update(INSERT_TASK_SQL, task.getUserId(), task.getDescription(), task.getPriority(), task.getDueDate()); 
    }
}
