package kafka.orderconsumer.customdeserializers;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

public class OrderConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.deserializer", StringDeserializer.class.getName());
        props.setProperty("value.deserializer", OrderDeserializer.class.getName());
        props.setProperty("group.id", "OrderGroup");
        //props.setProperty("auto.commit.interval.ms", "2000");
        props.setProperty("auto.commit.offset", "false");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        //Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

        KafkaConsumer<String, Order> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("OrderPartitionedTopic"));

        ConsumerRecords<String, Order> records = consumer.poll(Duration.ofSeconds(10));
        for(ConsumerRecord<String, Order> record : records){
            String customerName = record.key();
            Order order = record.value();
            System.out.println("Customer Name: " + customerName);
            System.out.println("Product: " + order.getProduct());
            System.out.println("Quantity: " + order.getQuantity());
            System.out.println("Partition: " + record.partition());
        }
        consumer.close();
    }
}
