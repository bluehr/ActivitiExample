package cn.bluehr;

import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.InputStream;
import java.util.Map;

/**
 * @Auther: fys2000
 * @Date: 2018/12/11 19:19
 * @Description:
 */

public class ActivitiEngineTest {

    @Rule
    public ActivitiRule activitiRule  =  new ActivitiRule();

    @Test
    public void oneLineStartActiviti(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP)
                .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
                .buildProcessEngine();
        ManagementService managementService = processEngine.getManagementService();
        Map<String, String> properties = managementService.getProperties();
        for (String s : properties.keySet()) {
            System.out.println(s+":"+properties.get(s));
        }
    }

    @Test
    public void testEngine() {
        /*使用Spring来构建这个类 但是真正和Spring整合配置的时候我们还会更改一下配置文件。*/
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

    @Test
    public void activitiUnitTest(){
        ProcessEngine engine = activitiRule.getProcessEngine();
        System.out.println(engine.getName());
        printCurrentEngineProperty(engine);
    }
    public void printCurrentEngineProperty(ProcessEngine processEngine){
        ManagementService managementService = processEngine.getManagementService();
        Map<String, String> properties = managementService.getProperties();
        for (String s : properties.keySet()) {
            System.out.println(s+":"+properties.get(s));
        }
    }



}
