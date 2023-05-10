import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextPreprocessing {
    public static void main(String[] args) {
        String text = "北京颐和园2栋10室";
        String[] segs = segment(text);
        String[] filteredSegs = filter(segs);
        String keyword = String.join(" ", filteredSegs);
        System.out.println(keyword);
        // 提交关键词给搜索引擎进行搜索
    }

    public static String[] segment(String text) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> tokens = segmenter.process(text, JiebaSegmenter.SegMode.SEARCH);
        String[] segs = new String[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            segs[i] = tokens.get(i).word;
        }
        return segs;
    }

    public static String[] filter(String[] segs) {
        Set<String> stopWords = new HashSet<>(Arrays.asList("北京", "颐和园", "栋", "室", "号"));
        return Arrays.stream(segs)
                .filter(seg -> !stopWords.contains(seg))
                .toArray(String[]::new);
    }
}