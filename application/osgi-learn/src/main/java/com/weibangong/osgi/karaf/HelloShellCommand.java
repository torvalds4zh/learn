package com.weibangong.osgi.karaf;

import lombok.extern.slf4j.Slf4j;
import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.karaf.shell.console.OsgiCommandSupport;

/**
 * Created by chenbo on 16/8/23.
 */

/**
 * 继承OsgiCommandSupport或实现gogo的Action都可以自定义karaf命令
 */
@Service
@Slf4j
@Command(scope = "test", name = "hello", description = "Says hello")
public class HelloShellCommand extends OsgiCommandSupport {
//public class HelloShellCommand implements Action {
    @Override
    protected Object doExecute() throws Exception {
        System.out.println("Executing Hello command");
        return null;
    }
}
