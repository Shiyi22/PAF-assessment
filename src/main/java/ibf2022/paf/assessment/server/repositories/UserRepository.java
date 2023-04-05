package ibf2022.paf.assessment.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.User;

// TODO: Task 3

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate; 

    private static final String FIND_USER_BY_USERNAME_SQL = "select * from user where username = ?"; 
    private static final String INSERT_USER_SQL = "insert into user values (?, ?, ?)"; 

    // method 1 
    public Optional<User> findUserByUsername(String username) {

        User user = new User(); 
        try {
            user = jdbcTemplate.queryForObject(FIND_USER_BY_USERNAME_SQL, BeanPropertyRowMapper.newInstance(User.class), username); 
        } catch (DataAccessException ex) {
            return Optional.empty(); 
        }
        return Optional.of(user); 
    }

    // method 2 
    public String insertUser(User user) {

        // generate random user_id for new user 
        String userId = UUID.randomUUID().toString().substring(0, 8); 
        
        // insert into database 
        jdbcTemplate.update(INSERT_USER_SQL, userId, user.getUsername(), user.getName()); 

        // System.out.printf(">>> userId: %s\n", userId);
        return userId; 
    }
}
