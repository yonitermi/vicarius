package com.example.Vicarius.controller;

import com.example.Vicarius.model.BlogPost;
import com.example.Vicarius.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ElasticsearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @PostMapping("/createIndex")
    public String createIndex(@RequestParam String indexName) {
        try {
            return elasticsearchService.createIndex(indexName);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating index";
        }
    }

    @PostMapping("/createDocument")
    public String createDocument(@RequestParam String indexName, @RequestBody BlogPost blogPost) {
        try {
            return elasticsearchService.createDocument(indexName, blogPost);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating document";
        }
    }

    @GetMapping("/getDocument")
    public BlogPost getDocumentById(@RequestParam String indexName, @RequestParam String documentId) {
        try {
            return elasticsearchService.getDocumentById(indexName, documentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // or handle as per your requirement
        }
    }
}
