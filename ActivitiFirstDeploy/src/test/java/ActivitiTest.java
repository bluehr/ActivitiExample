import org.activiti.engine.ProcessEngine;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @Auther: fys2000
 * @Date: 2018/12/21 15:43
 * @Description:
 */
public class ActivitiTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    public void test(){
        ProcessEngine processEngine = activitiRule.getProcessEngine();
        System.out.println(processEngine.getName());
    }
}
