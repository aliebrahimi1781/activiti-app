package demo.service;

import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import demo.rest.Task;
import demo.rest.TaskList;
import org.activiti.engine.form.TaskFormData;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class TaskServiceHandlerImpl implements TaskServiceHandler {

  private final static Logger logger = LoggerFactory.getLogger(ProcessServiceHandlerImpl.class);
  private static final String QUERY_URL = "http://localhost:9000/activiti/service/runtime/tasks?processDefinitionKey=vacationRequest&includeProcessVariables=true";
  private static final String URL="http://localhost:9000/activiti/service/runtime/tasks";
  private final HttpHost host = new HttpHost("localhost",9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);

  @Override
  public TaskList getPool() {
    TaskList result = restTemplate.getForObject(QUERY_URL, TaskList.class);
    logger.info(result.toString());
    return result;
  }

  @Override
  public void claim(Task task, String username) {
    HashMap<String, String> post= new HashMap<>();
    post.put("assignee","kermit");
    restTemplate.put(URL+"/"+task.getId(), post);
  }

  @Override
  public TaskList getTasksFor(String assignee) {
    TaskList result = restTemplate.getForObject(QUERY_URL+"&assignee="+assignee, TaskList.class);
    logger.info(result.toString());
    return result;
  }

  @Override
  public void approveVacationRequest(String taskId) {
    String url="http://localhost:9000/activiti/service/form/form-data?taskId=10025";
    TaskFormData formData= restTemplate.getForObject(url, TaskFormData.class);
    org.activiti.engine.task.Task task=formData.getTask();
  }
}