import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchModule {
    private List<String> documents;  // 存储文档
    private Map<String, Double> idfMap;  // 存储idf值

    public SearchModule(List<String> documents) {
        this.documents = documents;
        this.idfMap = new HashMap<>();
    }

    // 计算idf值
    private void calculateIdf() {
        int n = documents.size();
        for (String document : documents) {
            String[] words = document.split(" ");
            for (String word : words) {
                if (!idfMap.containsKey(word)) {
                    int count = 0;
                    for (String doc : documents) {
                        if (doc.contains(word)) {
                            count++;
                        }
                    }
                    double idf = Math.log((double) n / count);
                    idfMap.put(word, idf);
                }
            }
        }
    }

    // 计算tf值
    private Map<String, Integer> calculateTf(String document) {
        Map<String, Integer> tfMap = new HashMap<>();
        String[] words = document.split(" ");
        for (String word : words) {
            if (!tfMap.containsKey(word)) {
                tfMap.put(word, 1);
            } else {
                tfMap.put(word, tfMap.get(word) + 1);
            }
        }
        return tfMap;
    }

    // 计算tf-idf值
    private double calculateTfIdf(String word, Map<String, Integer> tfMap) {
        double tf = (double) tfMap.get(word) / tfMap.size();
        double idf = idfMap.get(word);
        return tf * idf;
    }

    // 搜索匹配的文档
    public List<String> search(String query) {
        calculateIdf();
        Map<String, Integer> queryTfMap = calculateTf(query);
        Map<String, Double> documentTfIdfMap = new HashMap<>();
        for (String document : documents) {
            Map<String, Integer> documentTfMap = calculateTf(document);
            double documentTfIdf = 0.0;
            for (String word : queryTfMap.keySet()) {
                if (documentTfMap.containsKey(word)) {
                    double tfIdf = calculateTfIdf(word, documentTfMap);
                    documentTfIdf += tfIdf;
                }
            }
            documentTfIdfMap.put(document, documentTfIdf);
        }
        List<String> matchedDocuments = new ArrayList<>();
        for (String document : documentTfIdfMap.keySet()) {
            if (documentTfIdfMap.get(document) > 0) {
                matchedDocuments.add(document);
            }
        }
        return matchedDocuments;
    }
}
