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
 * Gün içi uygulamasının kullandığı SSE teknolojisini kullanarak 
 * sistemdeki herkesin gorebildiği bildirimleri alan dinleyici.
 * @author Birol KARATAY 
 */
public class SSELoggerPublic {
    
    private static final String URL_NAME = "https://giptest.pmum.gov.tr/gunici/SseServlet";
    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("************************ SSE LOGGER LISTENING *************************");
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        Client client = ClientBuilder.newBuilder()
                .register(SseFeature.class).build();
        WebTarget target = client.target(URL_NAME);

        EventInput eventInput = target.request().get(EventInput.class);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();
            if (inboundEvent == null) {
                // bağlantı kapandı.
                break;
            }
            System.out.println("-----------" + format.format(System.currentTimeMillis()) + "---------------------\n");
            System.out.println(MessageFormat.format("event : {0}\n id : {1} \n data :{2}", inboundEvent.getName(), inboundEvent.getId(), inboundEvent.readData(String.class)));
        }
        System.out.println("************************ STOP *************************");
    }

}
