import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * @Auther: fys2000
 * @Date: 2018/12/21 15:43
 * @Description:
 */
public class ActivitiTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();
    
    private ProcessEngine processEngine;
    private IdentityService identityService;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private ManagementService managementService;
    private FormService formService;


    @Before
    public void setServices() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        processEngine = processEngineConfiguration.buildProcessEngine();
        identityService = processEngine.getIdentityService();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        formService = processEngine.getFormService();
        managementService = processEngine.getManagementService();
        historyService = processEngine.getHistoryService();
    }

    @Test
    public void test() {
        Map<String, String> properties = managementService.getProperties();
        properties.keySet().forEach(s-> System.out.println(properties.get(s)));
    }

    @Test
    public void deployMyProcess() throws FileNotFoundException {
        //几乎所有需要操作到流程文件，或者读取流程相关信息的，都是使用仓库服务repositoryService
        //部署流程 这里跟构建流程引擎的方式差不多，都是使用了构建者模式
        //1. 首先创建DeploymentBuilder对象,通过这个对象，我们可以指定要加载的流程定义文件，以及一些其他属性。
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        //2.1 加载流程文件，从类路径加载
//        deploymentBuilder.addClasspathResource("AskForLeave.bpmn");
        //2.使用InputStream加载
        FileInputStream fileInputStream = new FileInputStream("E:\\YS2000\\DemoProjects\\ActivitiExample\\ActivitiFirstDeploy\\src\\main\\resources\\AskForLeave.bpmn");
        deploymentBuilder.addInputStream("AskForLeave.bpmn", fileInputStream);
        deploymentBuilder.name("测试部署请假流程");
        //还有将多个bpmn文件打包批量，以及字符串这两种部署方式（这里暂时不介绍这两种方式）
        //执行部署，直到调用deploy()方法才是真正的部署到引擎中了
        //同时会在act_ge_bytearray ,act_re_deployment ,act_re_procdef这3个表中插入相关信息
        //与以前的教程不一样的是，别的教程中会经常将流程图片和流程bpmn文件一起部署，但我认为有点多余了，因为在部署bpmn的时候会自动生成流程图片
        Deployment deploy = deploymentBuilder.deploy();
        //这个是创建一个部署查询对象查询，查询的是act_re_deployment表这个表记录的就是deploymentBuilder对象所附加的属性
        long count = repositoryService.createDeploymentQuery().count();
        System.out.println("部署时间："+deploy.getDeploymentTime());
        Assert.assertEquals(1, count);
    }
}
