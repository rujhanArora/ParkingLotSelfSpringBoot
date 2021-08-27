package com.machineCoding.parkingLot.repositories;

import com.machineCoding.parkingLot.models.User;
import com.machineCoding.parkingLot.utils.TokenUtil;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private List<User> userList = new ArrayList<>();
    private Map<String, Integer> userIdToUserIndex = new HashMap<>();

    public User addUser(User user) {
        User addedUser = User.builder()
                .id(TokenUtil.generateRandomTokenDefaultLength())
                .email(user.getEmail())
                .name(user.getName())
                .userRoleList(user.getUserRoleList())
                .build();
        userList.add(addedUser);
        userIdToUserIndex.put(addedUser.getId(), userList.size() - 1);
        return addedUser;
    }

    public Optional<User> getUserById(String userId) {
        if (!userIdToUserIndex.containsKey(userId)) {
            return Optional.empty();
        }
        int userInd = userIdToUserIndex.get(userId);
        return Optional.of(userList.get(userInd));
    }
}
