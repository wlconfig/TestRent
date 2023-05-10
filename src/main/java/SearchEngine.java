import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchEngine {
//    上述代码中，SearchEngine类是一个搜索引擎类，它接收一个文档Map作为参数，
//    其中Key为文档ID，Value为文档内容。
//    在构造函数中，它会根据文档内容建立倒排索引。
//    在search方法中，它接收一个查询字符串、每页大小和页数作为参数，返回查询结果的文档ID列表。
//    在搜索过程中，它会计算查询字符串中每个词的TF-IDF值，并将文档得分相加，最后按得分从高到低排序，并返回指定页数的文档ID列表。
//    在main方法中，我们创建一个SearchEngine对象，并使用search方法搜索包含"Beijing"的文档，并返回第一页的结果。输出结果为：
    private Map<String, Map<Integer, Double>> invertedIndex;//倒排索引
    private Map<Integer, String> documentMap;//文档

    public SearchEngine(Map<Integer, String> documentMap) {
        this.documentMap = documentMap;
        this.invertedIndex = new HashMap<>();
        buildInvertedIndex();
    }


    /*这段代码实现了倒排索引的构建。
    1.具体来说，它首先遍历了一个文档集合，
    2.对于每个文档，它调用了 calculateTermFrequency 方法计算了该文档中每个单词的词频，
    3.然后遍历了该文档的所有单词，对于每个单词，它将该单词在该文档中的词频以及该文档的编号存储到了倒排索引中。
    倒排索引是一个数据结构，它可以根据单词快速查找到包含该单词的所有文档。
    在该代码中，倒排索引是一个 Map<String, Map<Integer, Double>> 类型的变量，其中外层的 Map 存储单词和文档编号之间的映射关系，内层的 Map 存储文档编号和单词在该文档中的词频之间的映射关系。
    * */
    private void buildInvertedIndex() {
        for (Map.Entry<Integer, String> entry : documentMap.entrySet()) {//1.遍历文档集合
            int documentId = entry.getKey();//其中Key为文档ID，为Int类型
            String document = entry.getValue();//Value为文档内容，为String类型
            Map<String, Double> termFrequencyMap = calculateTermFrequency(document);//计算词频TF
            for (Map.Entry<String, Double> tfEntry : termFrequencyMap.entrySet()) {//遍历词频Map
                String term = tfEntry.getKey();//获得文本中split后的的单词,为String类型
                double tf = tfEntry.getValue();//获得TF值，为Double类型
                if (!invertedIndex.containsKey(term)) {
                    invertedIndex.put(term, new HashMap<>());// Map<term, Map<Integer, Double>>
                }
                invertedIndex.get(term).put(documentId, tf);//将该单词在该文档中的词频以及该文档的编号存储到了倒排索引中
            }
        }
    }

    //计算一段文本中每个词语的词频（term frequency）的功能
    private Map<String, Double> calculateTermFrequency(String document) {
        Map<String, Double> termFrequencyMap = new HashMap<>();
        //1.将文档内容全部转为小写字母，并将其拆分为一个字符串数组

        //toLowerCase()会将字符串中的所有大写字母全部转为小写字母，"HELLO WORLD"转为"hello world"
        //这是一个 Java 中 String 类的方法，它将字符串按照空格字符进行分割，返回一个字符串数组。
        // 具体实现是使用正则表达式 "\s+" 进行分割，其中 \s 表示空格字符，+ 表示出现一次或多次。
        // 因此，split("\s+") 可以将一个字符串按照空格字符进行分割，并且忽略连续的空格字符。
        String[] terms = document.toLowerCase().split("\\s+");

        /*  这段代码的功能是将一段文本中出现的单词进行词频统计，即计算每个单词在文本中出现的频率。
            1.代码首先定义了一个Map对象(termFrequencyMap)，用于存储每个单词及其出现的次数。
            2.之后，对于文本中的每个单词，如果该单词不在Map中，就将其添加到Map中，并将其出现次数初始化为0。
            3.然后，无论该单词是否已存在于Map中，都将其出现次数加1。
            4.接着，代码遍历Map中的每个键值对，将每个单词出现的次数除以文本中单词总数，得到该单词在文本中的频率，并更新Map中该单词的值为该频率。
            5.最终，Map中保存了每个单词在文本中的词频。*/
        //term表示文本中的单词，terms表示文本
        for (String term : terms) {//每次循环都会将 terms 中的一个元素赋值给 term 变量，并执行循环体内的代码块
            //containsKey 是一个 Java 中的 Map 接口的方法，用于判断一个 Map 中是否包含指定的键。如果包含，则返回 true，否则返回 false。
            if (!termFrequencyMap.containsKey(term)) {//2.单词不在文本中出现
                termFrequencyMap.put(term, 0.0);//2.将该单词添加到map中，初始化次数为0.0
            }
            termFrequencyMap.put(term, termFrequencyMap.get(term) + 1);//3.无论该单词是否已存在于Map中，都将其出现次数加1
        }
        //entrySet是Java中Map接口的一个方法，它返回Map中包含的所有key-value映射关系的Set视图。
        // 其中每个元素都是一个Map.Entry对象，该对象表示一个key-value映射关系。可以通过遍历这个Set集合来获取Map中的所有键值对。
        for (Map.Entry<String, Double> entry : termFrequencyMap.entrySet()) {//4.遍历键值对
            /*   TF = （某个词在文档中出现的次数）/（文档中总词数）    */
            entry.setValue(entry.getValue() / terms.length);//没有setValue之前Value是次数，setValue之后变为了TF。key一直都是term单词
        //注意，value改变前后都是double类型，次数:0.0,也就是说改变后的TF为Double类型
        }
        return termFrequencyMap;//5.最终termFrequencyMap保存了词频TF
    }
    /*这段代码是一个基于倒排索引实现的简单搜索引擎，接受三个参数：查询字符串query、每页返回结果的数量pageSize、请求的页码pageNum，返回一个整数列表，表示查询结果的文档ID。
具体实现过程如下：
    1.对查询字符串query进行词频统计，得到一个Map（queryTermFrequencyMap），保存查询字符串中每个词出现的频率。
    2.遍历queryTermFrequencyMap中的每个词，如果该词存在于倒排索引中（invertedIndex），
则从倒排索引中获取包含该词的文档列表（documentTfMap），计算该词的IDF值，然后遍历该文档列表，计算每个文档的得分（documentScoreMap）
,得分公式为：query词频 * document词频 * IDF。
    3.将文档得分保存到documentScoreMap中，最终得到一个包含所有文档ID及其得分的Map。
    4.对documentScoreMap按照得分进行降序排序，得到一个按照相关度排序的文档列表（sortedScoreList）。
    5.根据请求的页码pageNum和每页返回结果的数量pageSize，计算出需要返回的结果的起始位置start和结束位置end。
    6.遍历sortedScoreList，取出从start到end位置的文档ID，保存到结果列表中。

返回结果列表。
    * */
    //query为查询字符串；pageSize为每页返回的结果；pageNum为请求的页码，返回的是是int 类型，表示查询结果的文档ID，
    //参考invertedIndex.get(term).put(documentId, tf)，即pageNum为documentId
    public List<Integer> search(String query, int pageSize, int pageNum) {
        List<Integer> results = new ArrayList<>();
        Map<String, Double> queryTermFrequencyMap = calculateTermFrequency(query);//1.词频TF统计,Map的key表示单词，value表示TF值
        Map<Integer, Double> documentScoreMap = new HashMap<>();
        for (Map.Entry<String, Double> queryTfEntry : queryTermFrequencyMap.entrySet()) {//遍历每对单词TF的键值对
            String queryTerm = queryTfEntry.getKey();//每对单词
            double queryTf = queryTfEntry.getValue();//每对单词的TF
            if (invertedIndex.containsKey(queryTerm)) {//2.倒排索引的key有单词,改词存在于倒排索引中
                Map<Integer, Double> documentTfMap = invertedIndex.get(queryTerm);//获取文档列表，(documentId, tf)
                /*  IDF = log（文档总数 / （包含该词的文档数，即该词出现在多少文档中）） */
                double idf = Math.log(documentMap.size() / documentTfMap.size());
                for (Map.Entry<Integer, Double> documentTfEntry : documentTfMap.entrySet()) {//遍历该词出现在的文档列表，(documentId, tf)
                    int documentId = documentTfEntry.getKey();//获取documentId
                    double documentTf = documentTfEntry.getValue();//获取tf
                    if (!documentScoreMap.containsKey(documentId)) {//Map<Integer, Double> documentScoreMap，这里documentId肯定不在documentScoreMap中出现
                        documentScoreMap.put(documentId, 0.0);//所有这里视为该Map存的是：pageNum即请求的页码，也就是documentId
                    }
                    documentScoreMap.put(documentId, documentScoreMap.get(documentId) + queryTf * documentTf * idf);
                }
            }
        }
        List<Map.Entry<Integer, Double>> sortedScoreList = new ArrayList<>(documentScoreMap.entrySet());
        sortedScoreList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        int start = pageSize * (pageNum - 1);
        int end = Math.min(start + pageSize, sortedScoreList.size());
        for (int i = start; i < end; i++) {
            results.add(sortedScoreList.get(i).getKey());
        }
        return results;
    }

    public static void main(String[] args) {
        Map<Integer, String> documentMap = new HashMap<>();
        documentMap.put(1, "Beijing Chaoyang District");
        documentMap.put(2, "Beijing Haidian District");
        documentMap.put(3, "Shanghai Pudong District");
        documentMap.put(4, "Shanghai Xuhui District");
        documentMap.put(5, "Guangzhou Tianhe District");
        documentMap.put(6, "Guangzhou Yuexiu District");
        SearchEngine searchEngine = new SearchEngine(documentMap);
        List<Integer> searchResults = searchEngine.search("Beijing", 2, 1);
        for (int documentId : searchResults) {
            System.out.println(documentMap.get(documentId));
        }
    }
}