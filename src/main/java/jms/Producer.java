package jms;

import entities.Sensor;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import com.google.gson.*;

import javax.jms.JMSException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Producer {
    static Gson gson = new Gson();
    static Random random;
    public void sendMessage(String topicName) throws JMSException {
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory("failover:tcp://localhost:61616");
        Connection connection=factory.createConnection("root","toor");
        connection.start();
        Session sesion = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Topic topic=sesion.createTopic(topicName);
        MessageProducer producer=sesion.createProducer(topic);
        while(true){
            try {
                TimeUnit.SECONDS.sleep(1);
                random=new Random();
                int id=random.nextInt(2)+1;
                String strReading=nuevaLectura(id);
                TextMessage reading=sesion.createTextMessage(strReading);
                System.out.println("Sent: "+strReading);
                producer.send(reading);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String nuevaLectura(int id) {
        String dateSrt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        double temp = new Random().nextInt(90) + 1;
        double humidity = new Random().nextInt(90) + 1;
        Sensor sensor = new Sensor(dateSrt, id, temp, humidity);
        return gson.toJson(sensor);
    }
}
