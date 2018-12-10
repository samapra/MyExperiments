import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {


    private final static String TOPIC = "GB-VIDEO-PLAY_VOD";
    private final static String BOOTSTRAP_SERVERS = "h1kka01-v01.activity-capture.stg1.ovp.bskyb.com:9093";

    public static void main(String[] args) throws Exception {
        System.out.println("main = " + args);
        runConsumer();
    }

    //Manually connect to kafka topic via kafka command line tools
    //sh Documents/kafka_2.11-0.10.2.0/bin/kafka-console-consumer.sh --bootstrap-server h1kka01-v01.activity-capture.stg1.ovp.bskyb.com:9093,h1kka02-v01.activity-capture.stg1.ovp.bskyb.com:9093,h1kka03-v01.activity-capture.stg1.ovp.bskyb.com:9093,h1kka04-v01.activity-capture.stg1.ovp.bskyb.com:9093 --topic GB-DCM-STREAM_STOP

    private static Consumer<Long, String> createConsumer() {
        System.out.println("Consumer = " );
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,  "KafkaExampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create the consumer using props.
        final Consumer<Long, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(TOPIC));

        return consumer;
    }

    static void runConsumer() throws InterruptedException {
        System.out.println("runConsumer = " );

        final Consumer<Long, String> consumer = createConsumer();
        final int giveUp = 1;   int noRecordsCount = 0;
        while (true) {
            Duration duration = Duration.ofMinutes(1);

            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(duration);
            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }
            consumerRecords.forEach(record -> {
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());
            });
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }
}