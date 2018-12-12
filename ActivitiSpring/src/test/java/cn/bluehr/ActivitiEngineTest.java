package cn.bluehr;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.InputStream;

/**
 * @Auther: fys2000
 * @Date: 2018/12/11 19:19
 * @Description:
 */

public class ActivitiEngineTest {
    @Test
    public void testEngine() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "activiti.cfg.xml" });
        ProcessEngineConfiguration processEngineConfiguration = (ProcessEngineConfiguration) context.getBean("processEngineConfiguration");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        Assert.assertEquals("MyProcessEngine", processEngine.getName());
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Assert.assertNotNull(runtimeService);
        TaskService taskService = processEngine.getTaskService();
        Assert.assertNotNull(taskService);
    }

    @Test
    public void testDefaltEngine(){
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        Assert.assertEquals("MyProcessEngine", processEngine.getName());
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Assert.assertNotNull(runtimeService);
        TaskService taskService = processEngine.getTaskService();
        Assert.assertNotNull(taskService);
    }

    @Test
    public void testGetFile(){
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("activiti.cfg.xml");
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(stream);
        //configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        //configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine.getName());
    }
    @Test
    public void getDefaultProcessEngine(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        Assert.assertNotNull(engine);
    }




}
