package producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;


public class DoubleDirectSenderApp {
    private static final String EXCHANGE_NAME = "DoubleDirect";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            System.out.print("Введите сообщение: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            StringBuilder stringBuilder = new StringBuilder(input);
            if (input.startsWith("php")) {
                String message = stringBuilder.substring(4);
                channel.basicPublish(EXCHANGE_NAME, "php", null, message.getBytes("UTF-8"));
            }
            if (input.startsWith("java")) {
                String message = stringBuilder.substring(5);
                channel.basicPublish(EXCHANGE_NAME, "java", null, message.getBytes("UTF-8"));
            }
        }
    }
}