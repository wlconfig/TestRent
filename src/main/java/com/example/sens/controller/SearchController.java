//package com.example.sens.controller;
//
//import com.example.sens.entity.SearchResult;
//import com.example.sens.service.impl.SearchService;
//
//@Controller
//public class SearchController {
//    @Autowired
//    private SearchService searchService;
//
//    @GetMapping("/search")
//    public String search(@RequestParam("q") String keyword, @RequestParam(value = "page", defaultValue = "1") int pageNum, Model model) {
//        List<SearchResult> results = searchService.search(keyword, pageNum, PAGE_SIZE);
//        model.addAttribute("results", results);
//        return "search";
//    }
//}
