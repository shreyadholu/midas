package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BalanceController {

    private final UserRepository userRepository;

    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        // Find user by ID in the database
        Optional<UserRecord> userOptional = userRepository.findById(userId);
        
        // Return the user's balance if found, otherwise return 0
        if (userOptional.isPresent()) {
            return new Balance(userOptional.get().getBalance());
        } else {
            return new Balance(0);
        }
    }
}