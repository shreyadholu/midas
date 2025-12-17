package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    @KafkaListener(
        topics = "${general.kafka-topic}",
        groupId = "midas-core-group"
    )
    public void listen(Transaction transaction) {
        // Put a breakpoint on the line below when debugging
        // Just receiving the transaction is enough for Task 2
    }
}
