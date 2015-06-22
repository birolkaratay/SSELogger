/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatay.birol.pets.gunici.sselogger;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 * Gün içi uygulamasının kullandığı SSE teknolojisini kullanarak kullanıcının 
 * kendi firmasının yaptığı işlemlerin bildirimlerini alan dinleyici.
 * @author Birol KARATAY 
 */
public class SSELoggerPrivate {
    private static final String URL_NAME = "https://giptest.pmum.gov.tr/gunici/SseServletPrivate";
    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private static final String USER_NAME = "BIROL";
    private static final String USER_PASSWORD = "Dgpys de kullandigim sifrem";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("************************ SSE LOGGER LISTENING *************************");
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        Client client = ClientBuilder.newBuilder()
                .register(SseFeature.class).build();
        WebTarget target = client.target(URL_NAME + "?username=" + USER_NAME + "&password=" + USER_PASSWORD);

        EventInput eventInput = target.request().get(EventInput.class);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();
            if (inboundEvent == null) {
                // connection has been closed
                break;
            }
            System.out.println("-----------" + format.format(System.currentTimeMillis()) + "---------------------\n");
            System.out.println(MessageFormat.format("event : {0}\n id : {1} \n data :{2}", inboundEvent.getName(), inboundEvent.getId(), inboundEvent.readData(String.class)));
        }
        System.out.println("************************ STOP *************************");
    }

}
