package bgu.spl.mics;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	List<Queue<Message>> events;

	public MessageBusImpl() {
		events = new ArrayList<>();
	}

	public Event getEvent(int i){
		return null;
	}

	public int getNumberOfEvents(){
		return events.size();
	}

	@Override   // i changed it from: Class<? extends Event<T>> type
	public <T> void subscribeEvent(Class<? extends Event> type, MicroService m) {
		// add the event type to the Mc


	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(MicroService m) {
		// create a queue in the Micro-Service (for a new event)

	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
