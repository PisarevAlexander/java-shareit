package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;


    @Transactional
    @Override
    public User create(UserDto userDto) {
        return repository.save(UserMapper.toUser(userDto));
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(long userId) {
        return repository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Id " + userId + " не найден"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public User update(UserDto userDto, long userId) {
        User user = repository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Id " + userId + " не найден"));
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        return repository.save(user);
    }

    @Transactional
    @Override
    public void delete(long userId) {
        repository.deleteById(userId);
    }
}