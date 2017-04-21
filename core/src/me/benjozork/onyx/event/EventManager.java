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

    private static ArrayMap<EventListener, ArrayMap<Method, Class<? extends Event>>> eventProcessors = new ArrayMap<EventListener, ArrayMap<Method, Class<? extends Event>>>();

    /**
     * Pushes an {@link Event} to the event bus and notifies concerned {@link EventListener} objects
     * @param e the event to push
     */
    public static void pushEvent(Event e) {
        for (EventListener eventListener : eventProcessors.keys()) {
            ArrayMap<Method, Class<? extends Event>> classes = eventProcessors.get(eventListener);
            for (Method m : classes.keys()) {
                String name = m.getName();
                if (classes.get(m).equals(e.getClass())) {
                    String className = eventListener.getClass().getSimpleName();
                    try {
                        m.invoke(eventListener, e);
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
     * Subscribe an {@link EventListener} to the event bus.<br/>
     * All it's methods containing {@link Event} parameters will be invoked when needed.
     * @param eventListener the {@link EventListener} to subscribe
     */
    public static void subscribe(EventListener eventListener) {
        ArrayMap<Method, Class<? extends Event>> eventTypes = new ArrayMap<Method, Class<? extends Event>>();
        for (Method method : eventListener.getClass().getMethods()) {
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
        eventProcessors.put(eventListener, eventTypes);
    }

}
