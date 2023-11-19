package com.example.Vicarius.service;

import com.example.Vicarius.model.BlogPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ElasticsearchService {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    public String createIndex(String indexName) throws Exception {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged() ? "Index created successfully" : "Failed to create index";
    }

    public String createDocument(String indexName, BlogPost blogPost) throws Exception {
        blogPost.setId(UUID.randomUUID().toString());
        Map<String, Object> documentMap = objectMapper.convertValue(blogPost, Map.class);
        IndexRequest indexRequest = new IndexRequest(indexName)
                .id(blogPost.getId())
                .source(documentMap);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.getId();
    }

    public BlogPost getDocumentById(String indexName, String documentId) throws Exception {
        GetRequest getRequest = new GetRequest(indexName, documentId);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        if (getResponse.isExists()) {
            return objectMapper.readValue(getResponse.getSourceAsString(), BlogPost.class);
        }
        return null; // or handle as per your requirement
    }
}
