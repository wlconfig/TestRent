//package com.example.sens.service.impl;
//
//import com.example.sens.mapper.SearchMapper;
//import com.example.sens.entity.SearchResult;
//
//@Service
//public class SearchService {
//    @Autowired
//    private SearchMapper searchMapper;
//
//    public List<SearchResult> search(String keyword, int pageNum, int pageSize) {
//        List<String> tokens = tokenize(keyword); // 分词
//        Map<String, Double> tfidf = computeTFIDF(tokens); // 计算TF-IDF
//        List<SearchResult> results = searchMapper.search(tfidf, (pageNum - 1) * pageSize, pageSize); // 按TF-IDF查询
//        return results;
//    }
//
//    private List<String> tokenize(String text) {
//        // TODO: 分词实现
//    }
//
//    private Map<String, Double> computeTFIDF(List<String> tokens) {
//        // TODO: 计算TF-IDF实现
//    }
//}
