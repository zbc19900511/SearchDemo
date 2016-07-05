import org.htmlparser.util.ParserException;

/**
 * Created by baidu on 16/7/4.
 */
public class MainClass {

    public  String baiduHtml = null;
    public  String googleHtml = null;
    public  static SearchResult baiduSearchResult = new SearchResult();
    public  static SearchResult googleSearchResult = new SearchResult();
    MyThread baiduThread;
    MyThread googleThread;
    Util util = new Util();
    //初始化html分析器
    HtmlParse baiduHtmlParse = new HtmlParse(baiduSearchResult);
    HtmlParse googleHtmlParse = new HtmlParse(googleSearchResult);
    private final static String keyWord = "我不知道";


    public static void main(String[] args) throws InterruptedException {

        MainClass main = new MainClass();
        main.startThread();
    }
    /**
     *
     *
     * */
    public void startThread() throws InterruptedException {

        //使用两个线程,节约获取及解析的时间

        baiduThread = new MyThread(Util.baidu,keyWord);
        googleThread = new MyThread(Util.google,keyWord);
        baiduThread.start();
        googleThread.start();
        //主进程等待线程结束
        baiduThread.join();
        googleThread.join();

        if(!googleThread.isAlive() && !baiduThread.isAlive()){
            util.compareResult(baiduSearchResult,googleSearchResult);
        }
    }
    //获取html子线程,未考虑性能
    class MyThread extends Thread {

        String searchOption = null;
        String requestKeyword = null;
        public MyThread(String search,String keyword){
            requestKeyword = keyword;
            searchOption = search;
        }
        public void run() {


            //由于百度与google的页面元素定义不通,所以要分开处理。
            //System.out.println("MyThread.run()=" + searchOption);
            if(searchOption.equals(Util.baidu)){
                baiduSearchResult.setKeyWord(keyWord);
                baiduHtml = util.getHtml(searchOption,requestKeyword);
                //计算keyword与搜索结果相关性
                double baiduSimilar = CosineSimilarAlogrithm.getSimilarity(keyWord ,baiduHtml);
                baiduSearchResult.setSimilarty(baiduSimilar);
                //为baiduresult赋值
                try {
                    baiduHtmlParse.parseBaiduResult(baiduHtml,baiduSearchResult);
                } catch (ParserException e) {
                    e.printStackTrace();
                }


                //System.out.print(baiduHtml);
            }else if(searchOption.equals(Util.google)){

                googleSearchResult.setKeyWord(keyWord);
                googleHtml = util.getHtml(searchOption,requestKeyword);
                //计算keyword与搜索结果相关性
                double googleSimilar = CosineSimilarAlogrithm.getSimilarity(keyWord,googleHtml);
                googleSearchResult.setSimilarty(googleSimilar);
                //为googleResult 赋值
                googleHtmlParse.parseGoogleResult(googleHtml,googleSearchResult);
                //System.out.print(googleHtml);
            }else{
                System.out.println("不支此搜索引擎");
            }

        }
    }
}
