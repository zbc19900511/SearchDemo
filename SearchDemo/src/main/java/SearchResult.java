import java.util.ArrayList;
import java.util.List;

/**
 * Created by baidu on 16/7/4.
 * 搜索结果对象
 * 此处只进行了相对简单的对比,并没有根据特别细化的内容进行定义
 */
public class SearchResult {

    public SearchResult(){

    }

    //keyword与返回结果的相关性
    private double similarty = 0;
    //单页左侧搜索结果个数
    private int onePageSearchLeftResultNum = 0;
    //单页右侧搜索结果个数
    private int onePageSearchRightResultNum = 0;
    //相关推荐结果个数
    private int onePageSearchAboutResultNum = 0;
//    //左侧结果list
//    private List<SingleResult> leftResults = new ArrayList<SingleResult>();
//    //右侧结果List
//    private List<SingleResult> rightResults = new ArrayList<SingleResult>();
//    //相关推荐list
//    private List<SingleResult> aboutResults = new ArrayList<SingleResult>();
    //搜索所用时间
    private float searchTime;
    //搜索用的关键词
    private String keyWord;

    //给外部提供读取和修改的接口

    public void setSimilarty(double similarty){
        this.similarty = similarty;
    }
    public double getSimilarty(){
        return this.similarty;
    }
    public void setOnePageSearchLeftResultNum(int onePageSearchLeftResultNum){
        this.onePageSearchLeftResultNum = onePageSearchLeftResultNum;
    }
    public int getOnePageSearchLeftResultNum(){
        return this.onePageSearchLeftResultNum;
    }

    public void setOnePageSearchRightResultNum(int onePageSearchRightResultNum){
        this.onePageSearchRightResultNum = onePageSearchRightResultNum;
    }
    public int getOnePageSearchRightResultNum(){
        return this.onePageSearchRightResultNum;
    }

    public void setOnePageSearchAboutResultNum(int onePageSearchAboutResultNum){
        this.onePageSearchAboutResultNum = onePageSearchAboutResultNum;
    }
    public int getOnePageSearchAboutResultNum(){
        return this.onePageSearchAboutResultNum;
    }

//    public void setLeftResults(List<SingleResult> results){
//        this.leftResults = results;
//    }
//    public List<SingleResult> getLeftResults(){
//        return this.leftResults;
//    }
//    public void setRightResults(List<SingleResult> results){
//        this.rightResults = results;
//    }
//
//    public List<SingleResult> getRightResults(){
//        return this.rightResults;
//    }
//
//    public void setAboutResults(List<SingleResult> results){
//        this.aboutResults = results;
//    }
//    public List<SingleResult> getAboutResults(){
//        return this.aboutResults;
//    }
    public void setSearchTime(float searchTime){
        this.searchTime = searchTime;
    }
    public float getSearchTime(){
        return this.searchTime;
    }
    public void setKeyWord(String keyWord){
        this.keyWord = keyWord;
    }
    public String getKeyWord(){
        return this.keyWord;
    }




}
