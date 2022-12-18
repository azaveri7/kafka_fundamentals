package kafka.orderproducer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class OrderCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        System.out.println(recordMetadata.partition());
        System.out.println(recordMetadata.offset());
        System.out.println("Message Sent Successfully");
        if(e!=null) {
            e.printStackTrace();
        }
    }
}
