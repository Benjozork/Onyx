package me.benjozork.onyx.event;

import com.badlogic.gdx.utils.ArrayMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.benjozork.onyx.logger.Log;

/**
 * Manages game events and their processing.
 * @author Benjozork
 */
public class EventHandler {

    private static Log log = Log.create("EventHandler");

    private static ArrayMap<EventProcessor,
                            ArrayMap<String, Class<? extends Event>>> eventProcessors = new ArrayMap<EventProcessor,
                                                                                                     ArrayMap<String, Class<? extends Event>>>();

    public static void pushEvent(Event e) {
        for (EventProcessor eventProcessor : eventProcessors.keys()) {
            for (String s : eventProcessors.get(eventProcessor).keys()) {
                if (eventProcessors.get(eventProcessor).get(s).equals(e.getClass())) {
                    String className = eventProcessor.getClass().getSimpleName();
                    try {
                        eventProcessor.getClass().getMethod(s, eventProcessors.get(eventProcessor).get(s)).invoke(eventProcessor, e);
                    } catch (IllegalAccessException e1) {
                        log.fatal("IllegalAccessException while accessing method '%s' of class '%s'!", s,  className);
                    } catch (InvocationTargetException e1) {
                        log.fatal("InvocationTargetException while accessing method '%s' of class '%s'!", s, className);
                    } catch (NoSuchMethodException e1) {
                        log.fatal("NoSuchMethodException while accessing method '%s' of class '%s'!", s, className);
                    }
                }
            }
        }
    }

    public static void subscribeTo(EventProcessor eventProcessor) {
        ArrayMap<String, Class<? extends Event>> eventTypes = new ArrayMap<String, Class<? extends Event>>();
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
                    eventTypes.put(method.getName(), (Class<? extends Event>) c);
                }
            }
        }
        eventProcessors.put(eventProcessor, eventTypes);
    }

    /*public static void subscribeTo(EventProcessor eventProcessor, String... eventIds) {
        ObjectMap.Entry<EventProcessor, ArrayMap<String, SubscribeFilter>> entry = new ObjectMap.Entry<EventProcessor, ArrayMap<String, SubscribeFilter>>();
        ArrayMap<String, SubscribeFilter> filterList = new ArrayMap<String, SubscribeFilter>();
        entry.key = eventProcessor;
        for (String s : eventIds) filterList.put(s, SubscribeFilter.EQUALS);
        entry.value = filterList;
        eventProcessors.put(entry.key, entry.value);
    }*/

}
