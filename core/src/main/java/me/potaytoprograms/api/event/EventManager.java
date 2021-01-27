package me.potaytoprograms.api.event;

import com.badlogic.gdx.utils.Array;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

public class EventManager {

    private final HashMap<String, Array<RegisteredMethod>> regMethods = new HashMap<>();
    private final HashMap<String, Event> events = new HashMap<>();

    public void registerEventHandler(Object o){
        Method[] methods = o.getClass().getMethods();

        System.out.println("reading class: " + o.getClass().getName());

        for(Method m : methods){
            registerMethod(m, o);
        }
    }

    private void registerMethod(Method m, Object o){
        if(m.isAnnotationPresent(EventHandler.class)){
            EventHandler e = m.getAnnotation(EventHandler.class);
            if(regMethods.containsKey(e.id())) {
                regMethods.get(e.id()).add(new RegisteredMethod(o, m));
            }else{
                Array<RegisteredMethod> array = new Array<>();
                array.add(new RegisteredMethod(o, m));
                regMethods.put(e.id(), array);
            }
            System.out.println("registered handler method: " + m.getName() + " for: " + e.id());
        }
        Annotation[] annotations = m.getDeclaredAnnotations();
    }

    public void registerEvent(Event e){
        events.put(e.getId(), e);
        System.out.println("registered event: " + e.getId());
    }

    public void callEvent(Event e){
        callEvent(e.getId(), e.getData());
    }

    public void callEvent(String id, HashMap<String, Object> data){
        if(events.containsKey(id) && regMethods.containsKey(id)) {
            System.out.println("called event: " + id);
            Array<RegisteredMethod> array = regMethods.get(id);

            for (int i = 0; i < array.size; i++) {
                try {
                    RegisteredMethod m = array.get(i);
                    m.m.invoke(m.o, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class RegisteredMethod {
        private Object o;
        private Method m;

        public RegisteredMethod(Object o, Method m){
            this.o = o;
            this.m = m;
        }
    }
}
