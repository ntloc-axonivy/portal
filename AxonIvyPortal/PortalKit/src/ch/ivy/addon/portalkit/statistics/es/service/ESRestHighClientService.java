package ch.ivy.addon.portalkit.statistics.es.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import ch.ivyteam.ivy.environment.Ivy;

public class ESRestHighClientService extends ESRestService {

  public static void test() {
    Ivy.log().info("*** Start ES Rest high client");
    RestHighLevelClient client = createHighRestClient(9200);
    try {
      Ivy.log().info("** Built a client successfully");
      testGet(client);
      client.close();
      Ivy.log().info("** Closed a client successfully");
    } catch (IOException e) {
      Ivy.log().info("** Closed a client failed");
      e.printStackTrace();
    }
  }

  protected static void testGet(RestHighLevelClient client) throws IOException {
    GetRequest request = new GetRequest("portal_task", "li20FXwB1S-EhEqlGGp-");
    request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
    String[] includes = Strings.EMPTY_ARRAY;
    String[] excludes = new String[] {"_type", "_id"};
    FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
    request.fetchSourceContext(fetchSourceContext);
    request.storedFields("applicationid");

    GetResponse response = client.get(request, RequestOptions.DEFAULT);
    Ivy.log().info("** Test get api: found {0}", response.isExists());
    if (response.isExists()) {
      Ivy.log().info("** Test get api: id {0}", response.getId());
      Ivy.log().info("** Test get api: source is empty {0}", response.isSourceEmpty());
      Ivy.log().info("** Test get api: source {0}", response.getSourceAsString());
      Ivy.log().info("** Test get api: fields {0}", response.getFields().keySet());
    }
  }

  protected static List<String> findIndexes() throws IOException {
    var request = new Request("GET", "/_cat/indices");
    request.addParameter("pretty", "true");
    request.setEntity(new NStringEntity("{\"json\":\"text\"}", ContentType.APPLICATION_JSON));
    Response response = createRestClient(9200).performRequest(request);
    response.getEntity().getContent();
    String string = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
    Ivy.log().info("Existing indexes {0}", string);
    return null;
  }

}
