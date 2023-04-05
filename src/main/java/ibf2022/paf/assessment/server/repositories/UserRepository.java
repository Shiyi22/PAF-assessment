package ibf2022.paf.assessment.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import ibf2022.paf.assessment.server.models.User;

// TODO: Task 3

public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate; 

    private static final String FIND_USER_BY_USERNAME_SQL = "select * from user where username = ?"; 
    private static final String INSERT_USER_SQL = "insert into user values (?, ?, ?)"; 

    // method 1 
    public Optional<User> findUserByUsername(String username) {
        
        User user = jdbcTemplate.queryForObject(FIND_USER_BY_USERNAME_SQL, BeanPropertyRowMapper.newInstance(User.class), username); 
        if (null == user)
            return Optional.empty(); 
        System.out.printf(">>> user: %s\n", user.toString()); 
        return Optional.of(user); 
    }

    // method 2 
    public String insertUser(User user) {

        // generate random user_id for new user 
        String userId = UUID.randomUUID().toString().substring(0, 8); 
        
        // insert into database 
        jdbcTemplate.update(INSERT_USER_SQL, userId, user.getUsername(), user.getName()); 
        return userId; 
    }
}
