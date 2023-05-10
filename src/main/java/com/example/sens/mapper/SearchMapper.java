package com.example.sens.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.naming.directory.SearchResult;
import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {
    List<SearchResult> search(@Param("tfidf") Map<String, Double> tfidf, @Param("offset") int offset, @Param("limit") int limit);
}
