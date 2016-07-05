import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by baidu on 16/7/4.
 */
public class Util {

    public static final String google = "Google";
    public static final String baidu = "Baidu";

    public Util(){

    }
    /**
     * 下载页面源码
     * @param search "Google or Baidu"
     * @param keyWord 要搜索的关键字
     * @return
     */
    public String getHtml(String search,String keyWord){

        String urlString;
        String Tag = null;
        //用于拼装请求URL ,此处未做健壮性处理。
        // todo:理论上应该使用其他方式模拟请求首页然后模拟输入关键词。
        if(search.equals(google)){
            Tag = "B";
            urlString = "https://www.google.com.hk/search?q=" + keyWord;

        }else if (search.equals(baidu)){
            Tag = "G";
            urlString = "http://www.baidu.com/s?wd=" + keyWord;
        }else {
            System.out.println("无法识别的搜索引擎");
            return null;
        }
        //用于请求页面,由于国内防火墙,导致google获取不稳定。使用前更改host。使用代理无法访问,未做proxy方式请求。
        //todo:设置proxy
        try {
            StringBuffer html = new StringBuffer();
            URL url = new URL(urlString);
            //建立url连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //访问google必须先模拟出浏览器信息
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; " +
                    "zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
            long beginTime = System.currentTimeMillis();
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            long endtime = System.currentTimeMillis();
            long useTime = endtime - beginTime;
            //这个处理方式不好。待优化;
            //存入搜索返回时间
            if(Tag.equals("B")){
                MainClass.baiduSearchResult.setSearchTime(useTime);
            }else if(Tag.equals("G")){
                MainClass.googleSearchResult.setSearchTime(useTime);
            }
            String temp;
            //拼接html结果字符
            while ((temp = br.readLine()) != null) {
                html.append(temp).append("\n");
            }
            br.close();
            isr.close();
            //此处用于测试
            writeFile(html.toString());
            return html.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 用于比较搜索结果
     * @Param baiduResult googleResult
     */

    public void compareResult(SearchResult baiduResult,SearchResult googleResult){

        System.out.println("-------------------------------------搜索引擎对比数据----------关键词:" + baiduResult.getKeyWord()+ "-----------------------");
        System.out.println("搜索引擎      左侧结果数      右侧结果数     相关搜索数           keyword相似度           搜索耗时");
        System.out.println(" 百度          " + baiduResult.getOnePageSearchLeftResultNum() + "               "+baiduResult.getOnePageSearchRightResultNum()
                            + "             " + baiduResult.getOnePageSearchAboutResultNum() + "           " + baiduResult.getSimilarty() + "         "
                            + baiduResult.getSearchTime());
        System.out.println(" 谷歌          " + googleResult.getOnePageSearchLeftResultNum() + "               "+googleResult.getOnePageSearchRightResultNum()
                + "             " + googleResult.getOnePageSearchAboutResultNum() + "           " + googleResult.getSimilarty() + "         "
                + googleResult.getSearchTime());
    }


    /**
     * TestMethod
     */
    private void writeFile(String str){
        File f = new File("/Users/baidu/Downloads/google.html");
        byte bytes[]=new byte[512];
        bytes=str.getBytes();
        int b=str.length();

        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bytes,0,b);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
