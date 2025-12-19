package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskThreeTests {
    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRepository userRepository;

    @Test
    void task_three_verifier() throws InterruptedException {
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }
        
        // Wait for Kafka to process all messages
        Thread.sleep(5000);

        logger.info("----------------------------------------------------------");
        
        // Retrieve Waldorf's balance from the database
        UserRecord waldorf = userRepository.findByName("waldorf");
        if (waldorf != null) {
            logger.info("WALDORF FINAL BALANCE: {}", waldorf.getBalance());
        } else {
            logger.error("User 'waldorf' not found in the database.");
        }

        logger.info("----------------------------------------------------------");
        logger.info("The balance is printed above. Round it down to the nearest integer for submission.");
        logger.info("kill this test once you find the answer");
        
        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
}