package ch.ivy.addon.portalkit.statistics.es.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;

import ch.ivy.addon.portalkit.statistics.es.search.SearchCriteria;
import ch.ivyteam.ivy.environment.Ivy;

public class ESRestService {

  public static RestHighLevelClient createHighRestClient(int port) {
    return new RestHighLevelClient(createRestBuilder(port));
  }

  public static RestClient createRestClient(int port) {
    return createRestBuilder(port).build();
  }

  protected static RestClientBuilder createRestBuilder(int port) {
    return RestClient.builder(new HttpHost("localhost", port, "http"));
  }

  protected static List<SearchHit> searchByCriteria(RestHighLevelClient client, SearchCriteria criteria) {
    var totalCount = countByCriteria(criteria);
    var request = new SearchRequest(criteria.getIndexName());
    var searchBuilder = new SearchSourceBuilder();
    searchBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
    searchBuilder.from(criteria.getSearchFrom());
    searchBuilder.size((int) totalCount);
    searchBuilder.query(criteria.getQuery());
    searchBuilder.sort(new FieldSortBuilder(criteria.getSortField()).order(criteria.getSortOrder()));
    searchBuilder.fetchSource(true);
    searchBuilder.fetchSource(criteria.getIncludeFields(), criteria.getExcludeFields());
    request.source(searchBuilder);

    Ivy.log().info("** SearchQuery {0}", searchBuilder.toString());
    SearchResponse response = null;
    try {
      response = client.search(request, RequestOptions.DEFAULT);
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<>();
    }

    var result = new ArrayList<SearchHit>();
    Ivy.log().info("** SearchResponse status {0}", response.status());
    if (response.status() == RestStatus.OK) {
      Ivy.log().info("** SearchResponse - How long this search took {0}", response.getTook());
      Ivy.log().info("** SearchResponse - TotalHits {0}", response.getHits().getTotalHits());
      result.addAll(Arrays.asList(response.getHits().getHits()));
    }
    return result;
  }

  private static long countByCriteria(SearchCriteria criteria) {
    CountRequest countRequest = new CountRequest(criteria.getIndexName());
    countRequest.query(criteria.getQuery());
    var totalCount = 1l;
    try {
      var response = createHighRestClient(9200).count(countRequest, RequestOptions.DEFAULT);
      totalCount = response.getCount();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return totalCount;
  }
}
