package me.benjozork.onyx.event;

import com.badlogic.gdx.utils.ArrayMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.benjozork.onyx.logger.Log;

/**
 * Manages game events and their processing.
 * @author Benjozork
 */
public class EventManager {

    private static Log log = Log.create("EventHandler");

    private static ArrayMap<EventProcessor, ArrayMap<Method, Class<? extends Event>>> eventProcessors = new ArrayMap<EventProcessor, ArrayMap<Method, Class<? extends Event>>>();

    /**
     * Pushes an {@link Event} to the event bus and notifies concerned {@link EventProcessor} objects
     * @param e the event to push
     */
    public static void pushEvent(Event e) {
        for (EventProcessor eventProcessor : eventProcessors.keys()) {
            ArrayMap<Method, Class<? extends Event>> classes = eventProcessors.get(eventProcessor);
            for (Method m : classes.keys()) {
                String name = m.getName();
                if (classes.get(m).equals(e.getClass())) {
                    String className = eventProcessor.getClass().getSimpleName();
                    try {
                        m.invoke(eventProcessor, e);
                    } catch (IllegalAccessException e1) {
                        log.fatal("IllegalAccessException while accessing method '%s' of class '%s'!", name, className);
                    } catch (InvocationTargetException e1) {
                        log.fatal("InvocationTargetException while accessing method '%s' of class '%s'!", name, className);
                    }
                }
            }
        }
    }

    /**
     * Subscribe an {@link EventProcessor} to the event bus.<br/>
     * All it's methods containing {@link Event} parameters will be invoked when needed.
     * @param eventProcessor the {@link EventProcessor} to subscribe
     */
    public static void subscribe(EventProcessor eventProcessor) {
        ArrayMap<Method, Class<? extends Event>> eventTypes = new ArrayMap<Method, Class<? extends Event>>();
        for (Method method : eventProcessor.getClass().getMethods()) {
            for (Class<?> c : method.getParameterTypes()) {
                String name = method.getName();
                if (name.equals("wait") // Escape native methods with null parameter types
                    || name.equals("equals")
                    || name.equals("clone")
                    || name.equals("finalize")
                    || name.equals("hashCode")
                    || name.equals("toString")
                    || name.equals("notify")
                    || name.equals("notifyAll")) break;
                if (c.getSuperclass().equals(Event.class)) {
                    eventTypes.put(method, (Class<? extends Event>) c);
                }
            }
        }
        eventProcessors.put(eventProcessor, eventTypes);
    }

}
