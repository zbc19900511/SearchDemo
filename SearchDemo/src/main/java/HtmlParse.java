import org.htmlparser.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import org.htmlparser.visitors.TextExtractingVisitor;
import org.htmlparser.Parser;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by baidu on 16/7/4.
 */
public class HtmlParse {
    SearchResult searchResult;

    public HtmlParse(SearchResult searchResult){
        this.searchResult = searchResult;
    }

    private Document initHtmlObject(String html){
        Document doc = Jsoup.parse(html);
        return doc;
    }
    /**
     *@method 统计百度左边及右边搜索结果
     *@params id index
     *@return int count
     * */
    private int findBaiduSearchResult(String id,String index,Document doc){
        int count = 0;
        int countOp = 0;
        //分析搜索结果
        Element searchElement= doc.getElementById(id);
        Elements searchElements = searchElement.children();
        for(Element leftSingleElemet : searchElements){
            //计算搜索结果总个数
            count++;

//            //计算百度相关结果个数  此处暂未加入评比数据
//            if (leftSingleElemet.toString().indexOf(index) != -1){
//                countOp++;
//            }
        }
        //int normalCount = count - countOp;

        return count;
    }
    /**
     * @method 统计百度底部相关搜索个数
     * @params id ,index doc
     * @return 相关搜索数量
     * */
    private int getAboutNum(String id,String index ,Document doc){
        int count = 0;
        Elements elements = doc.select(id).select(index);
        for (Element element:elements){
            // TODO: 16/7/4  此处可以进行更多的单条信息分析
            count ++;
            //System.out.println(element);
        }

        return count;
    }
    /**
     * @method  用于分析百度的搜索结果值
     * @params html result
     * */

    public void parseBaiduResult(String html,SearchResult result) throws ParserException {
        Document doc = initHtmlObject(html);
        try{
            result.setOnePageSearchLeftResultNum(findBaiduSearchResult("content_left","result-op",doc));

            result.setOnePageSearchAboutResultNum(getAboutNum("[id=rs]","a",doc));

            result.setOnePageSearchRightResultNum(findBaiduSearchResult("con-ar","index",doc));

        } catch (NullPointerException e){
            System.out.println("百度文本不存在");
        }


    }

    private int findGoogleSearchResult(String id,String index,Document doc){
        int count = 0;
        Element element = doc.getElementById(id);
        Elements elements = element.select(index);

        for (Element element1:elements){
            // TODO: 16/7/4  此处可以进行更多的单条信息分析
            count ++;
        }
        return count;
    }

    private int getGoogleAboutNum(String index, String Tag, Document doc){
        int count = 0;
        Elements elements = doc.select(index).select(Tag);
        for (Element element:elements){
            count ++;
        }
        return count;
    }
    /**
     * @method 用于分析谷歌搜索结果
     * @params html result
     * @return void
     * */
    public void parseGoogleResult(String html,SearchResult result){

        Document doc = initHtmlObject(html);
        try{
            //Todo:此处可优化为由conf配置查询结果
            //查找对应搜索结果的值
            result.setOnePageSearchLeftResultNum(findGoogleSearchResult("ires","[class=g]",doc));
            result.setOnePageSearchRightResultNum(findGoogleSearchResult("rhs_block","[class=g]",doc));
            result.setOnePageSearchAboutResultNum(getGoogleAboutNum("[id=brs]","[class=g]",doc));

        } catch(NullPointerException e){
            System.out.println("谷歌搜索结果不存在");
        }

    }
}
