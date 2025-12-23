package com.jpmc.midascore;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPopulator {
    @Autowired
    private FileLoader fileLoader;


    @Autowired
    private DatabaseConduit databaseConduit;

    public void populate() {
        // Ensure this path matches exactly where your test files are in src/test/resources
        String[] userLines = fileLoader.loadStrings("/test_data/lkjhgfdsa.hjkl");
        
        if (userLines != null) {
            for (String userLine : userLines) {
                // Using split(",") if the file is CSV or ", " if there is a space
                String[] userData = userLine.split(", ");
                if (userData.length == 2) {
                    UserRecord user = new UserRecord(userData[0], Float.parseFloat(userData[1]));
                    databaseConduit.save(user);
                }
                
            }
        }
    }
}