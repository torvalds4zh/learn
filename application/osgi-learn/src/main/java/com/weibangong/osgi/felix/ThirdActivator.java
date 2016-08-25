package com.weibangong.osgi.felix;

import org.osgi.framework.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 * Created by chenbo on 16/8/25.
 */
public class ThirdActivator implements BundleActivator, ServiceListener {
    //Bundle's context
    private BundleContext m_context = null;
    //The service reference being used
    private ServiceReference m_ref = null;
    //The service object being uses
    private DictionaryService m_dictionary = null;

    public void start(BundleContext context) throws Exception {
        m_context = context;

        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("Language", "French");
        context.registerService(DictionaryService.class.getName(), new DictionaryServiceImpl(), props);

        synchronized (this) {
            m_context.addServiceListener(this, "(&(objectClass=" + DictionaryService.class.getName() + ")" + "(Language=*))");
            //Query for any service references matching any language
            ServiceReference[] refs = m_context.getServiceReferences(DictionaryService.class.getName(), "(Language=*)");
            //If we found any dictionary services, then just get a references to the first one so we can use it.
            if (refs != null) {
                m_ref = refs[0];
                m_dictionary = (DictionaryService) m_context.getService(m_ref);
            }
        }
        try {
            System.out.println("Enter a blank to exit.");
            String word = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("Enter word:");
                word = in.readLine();
                if (word.length() == 0) {
                    break;
                } else if (m_dictionary == null) {
                    System.out.println("No dictionary available.");
                } else if (m_dictionary.checkWord(word)) {
                    System.out.println("Correct.");
                } else {
                    System.out.println("Incorrect.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(BundleContext context) throws Exception {

    }

    public void serviceChanged(ServiceEvent event) {
        String[] objectClass = (String[]) event.getServiceReference().getProperty("objectClass");

        //If a dictionary service was registered, see if we need one. If so, get a reference to it.
        if (event.getType() == ServiceEvent.REGISTERED) {
            if (m_ref == null) {
                //Get a reference to the service object.
                m_ref = event.getServiceReference();
                m_dictionary = (DictionaryService) m_context.getService(m_ref);
            }
        }

        //If a dictionary service was unregistered, see if it was the one we are using.
        //If so, unget the service and try to query to get another one.
        else if (event.getType() == ServiceEvent.UNREGISTERING) {
            if (event.getServiceReference() == m_ref) {
                //Unget the Service object and null references.
                m_context.ungetService(m_ref);
                m_ref = null;
                m_dictionary = null;

                //Query to see if we can get another service.
                ServiceReference[] refs = null;
                try {
                    refs = m_context.getServiceReferences(DictionaryService.class.getName(), "(Language=*)");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(refs != null){
                    m_ref = refs[0];
                    m_dictionary = (DictionaryService) m_context.getService(m_ref);
                }
            }
        }
    }

    private static class DictionaryServiceImpl implements DictionaryService{
        String[] m_dictionary = {
                "bienvenue", "au", "tutoriel", "osgi"
        };

        public boolean checkWord(String word) {
            word = word.toLowerCase();
            for (int i = 0;i < m_dictionary.length; i++) {
                if (m_dictionary[i].equals(word)) {
                    return true;
                }
            }
            return false;
        }
    }
}
