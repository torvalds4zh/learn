package com.weibangong.osgi.felix;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * Created by chenbo on 16/8/25.
 */
public class SecondActivator implements BundleActivator {


    public void start(BundleContext context) throws Exception {
        System.out.println("DictionaryService registering...");
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("Language", "English");
        context.registerService(DictionaryService.class, new DictionaryServiceImpl(), props);
//        context.registerService(DictionaryService.class.getName(),new DictionaryServiceImpl(), props);
    }

    public void stop(BundleContext context) throws Exception {
        // NOTE: The service is automatically unregistered.
    }

    private static class DictionaryImpl implements DictionaryService {
        String[] m_dictionary =
                {"welcome", "to", "the", "osgi", "tutorial"};

        public boolean checkWord(String word) {
            word = word.toLowerCase();

            // This is very inefficient
            for (int i = 0; i < m_dictionary.length; i++) {
                if (m_dictionary[i].equals(word)) {
                    return true;
                }
            }
            return false;
        }
    }
}
