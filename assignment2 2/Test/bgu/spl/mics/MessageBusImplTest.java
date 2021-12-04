package bgu.spl.mics;

import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import bgu.spl.mics.example.services.ExampleEventHandlerService;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageBusImplTest {
    MessageBus M=new MessageBusImpl();
    Event<String> event = new ExampleEvent("Room1");
    String[]room1={"roni","nana"};
    String[]room2={"dylen"};
    MicroService mc1 = new ExampleEventHandlerService("Room1",room1);
    MicroService mc2 = new ExampleEventHandlerService("Room2",room2);
    Message b = new ExampleBroadcast("Room1");

    @Test
    public void subscribeEvent() {
        /*      adds Event to the MicroService according to the round count      */

        M.subscribeEvent(event.getClass(),mc1);
        M.sendEvent(event);
        try {
            assertEquals(M.awaitMessage(mc1).toString(), event,
                    "The message M was subscribed to isn't the message M received");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            assertNull(M.awaitMessage(mc2).toString(),
                    "SecondM is not subscribed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void subscribeBroadcast() {
        M.subscribeBroadcast((Class<? extends Broadcast>) b.getClass(),mc1);
        M.sendBroadcast((Broadcast) b);
        try {
            assertEquals(M.awaitMessage(mc1), b);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void complete() { //
    }

    @Test
    public void sendBroadcast() throws InterruptedException {
        M.register(mc1);
        M.register(mc2);
        M.subscribeEvent(event.getClass(),mc1);
        M.sendBroadcast((Broadcast) b);
        M.awaitMessage(mc1);

        assertSame(M.awaitMessage(mc1), b.getClass());
        M.awaitMessage(mc2);
        assertNotSame(M.awaitMessage(mc2),b.getClass());
    }

    @Test
    public void sendEvent() throws InterruptedException {
        M.register(mc1);
        M.register(mc2);
        M.subscribeEvent(event.getClass(),mc1);
        M.subscribeEvent(event.getClass(),mc2);

        M.sendEvent(event);
        Event<String> event2 = new ExampleEvent("Room1");
        M.sendEvent(event2);

        M.awaitMessage(mc1);
        M.awaitMessage(mc2);
        assertSame(M.awaitMessage(mc1),M.awaitMessage(mc2));
    }

    @Test
    public void register() {
        // add a new Queue to the events list
        int eventsBefore = M.getNumberOfEvents();
        M.register(mc1);
        assertSame(eventsBefore,M.getNumberOfEvents()-1);
    }

    @Test
    public void unregister() {
        //clear the MicroService's Queue
        M.register(mc2);
        int eventsBefore = M.getNumberOfEvents();
        M.unregister(mc2);
        assertSame(eventsBefore,M.getNumberOfEvents()+1);
    }

    @Test
    public void awaitMessage() throws InterruptedException {
        M.register(mc1);
        M.awaitMessage(mc1);
        assertSame(M.awaitMessage(mc1).toString(),mc1.toString());
    }
}