package com.weibangong.osgi.karaf;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

/**
 * Created by chenbo on 16/8/23.
 */

@Command(scope = "test", name = "hello", description = "Says hello")
public class HelloShellCommand extends OsgiCommandSupport {
    @Override
    protected Object doExecute() throws Exception {
        System.out.println("Executing Hello command");
        return null;
    }
}
