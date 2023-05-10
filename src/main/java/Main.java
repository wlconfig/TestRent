import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> documents = new ArrayList<>();
        documents.add("北京市 海淀区 西二旗 金隅嘉华大厦 3室2厅 120平米 580万");
        documents.add("北京市 朝阳区 三里屯 丽都水岸 2室1厅 90平米 520万");
        documents.add("上海市 徐汇区 衡山路 泰安公寓 2室1厅 80平米 400万");
        documents.add("上海市 浦东新区 陆家嘴 世纪大道 3室2厅 130平米 850万");
        SearchModule searchModule = new SearchModule(documents);
        List<String> matchedDocuments = searchModule.search("北京市 三室2厅 120平米");
        for (String document : matchedDocuments) {
            System.out.println(document);
        }
    }
}