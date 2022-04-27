package inside.test.services;

import inside.test.data.entities.Role;
import inside.test.data.entities.User;
import inside.test.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис по работе с репозиторием Юзеров, по сути нам нужен только поиск
 * */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Optional<User> findUserByName(String name) {
        return userRepository.findUsersByName(name);
    }

    /**
    * В задании не требовалось, написал для простоты создания пользователей
    */
    @Transactional
    public void createNewUser(User newUser) throws IllegalArgumentException {
        if (findUserByName(newUser.getName()).isPresent()) {
            throw new IllegalArgumentException("User with this name exists");
        }
        User user = new User();
        user.setName(newUser.getName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Collections.singleton(new Role("ROLE_USER")));
        userRepository.save(user);
    }

    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = findUserByName(name).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User '%s' not found", name)));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
