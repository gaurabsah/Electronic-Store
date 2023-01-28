package com.store.electronic;

import com.store.electronic.entities.Role;
import com.store.electronic.entities.User;
import com.store.electronic.repositories.RoleRepository;
import com.store.electronic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicStoreApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Value("${normal.role.id}")
    private String role_normal_id;

    @Value("${admin.role.id}")
    private String role_admin_id;

    @Override
    public void run(String... args) throws Exception {

        System.out.println(passwordEncoder.encode("saurav123"));

//        User user = User.builder().email("abc@dev.in").password(passwordEncoder.encode("abcd"))
//                .name("abc").about("abc").gender("MALE").userId("abc1vaj").build();
//        userRepository.save(user);

        try {

            Role role_admin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
            Role role_normal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();
            repository.save(role_admin);
            repository.save(role_normal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
