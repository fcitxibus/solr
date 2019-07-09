package com.lion.solrj.query;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.util.List;
import java.util.Map;

public class SolrBasicQueryComplete {
    // 定义要使用的Solr连接地址，连接的时候明确的连接到对应的Core上
    public static final String SOLR_HOST_URL = "http://192.168.19.138/solr/yu-core" ;
    public static final String USERNAME = "yu" ;   // 用户名
    public static final String PASSWORD = "123" ; // 密码
    public static void main(String[] args) throws Exception {    // 进行Solr数据的查询
        // 0、为了保证服务器的运行安全肯定要在所有的服务器上进行认证的定义
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();// 创建认证对象
        // 认证的时候需要设置有一个认证的主体信息（用户名和密码）
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(USERNAME, PASSWORD);
        // 将认证的信息设置到认证提供者的对象上
        credentialsProvider.setCredentials(AuthScope.ANY,credentials); // 设置认证的处理信息
        // 将认证的信息传入到HttpClient构建对象之中，创建一个Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(credentialsProvider).build() ;
        // 1、SolrJ是基于HTTP的一种访问处理形式，而且Solr整体访问的时候都可以通过HTTP的地址完成
        HttpSolrClient solrClient = new HttpSolrClient.Builder(SOLR_HOST_URL).withHttpClient(httpClient).build() ;
        // 2、获取了一个合格的Solr客户端连接对象实例之后，就可以进行查询的创建
        SolrQuery solrQuery = new SolrQuery() ; // 创建查询对象
        solrQuery.setQuery("solr_keywords:*宝*") ; // 查询全部数据
        solrQuery.setSort("solr_d_price", SolrQuery.ORDER.desc) ; // 采用降序排列
        solrQuery.setHighlight(true) ; // 采取高亮配置
        solrQuery.addHighlightField("solr_s_name") ; // 追加高亮显示字段
        solrQuery.setHighlightSimplePre("<strong>") ;   // 高亮标记开始
        solrQuery.setHighlightSimplePost("</strong>") ;  // 高亮标记结束
        // 3、利用SolrClient发出Solr查询命令，随后获取查询响应结果
        QueryResponse queryResponse = solrClient.query(solrQuery);
        // 4、所有的HTTP服务器的响应信息都保存在了QueryResponse对象实例之中，根据响应获取数据
        SolrDocumentList results = queryResponse.getResults();// 获取相应的信息
        System.out.println("===================== 普通数据查询 ==================");
        // 5、Document保存的是每一个索引数据，而DocumentList返回的是索引集合
        System.out.println("【数据行数】" + results.getNumFound());
        // 6、所有的数据以List集合的形式出现
        for (SolrDocument document : results) { // 文档迭代输出
            System.out.println("【返回信息】id = " + document.get("id") + "、name = " + document.get("solr_s_name") + "、catalog = " + document.get("solr_s_catalog"));
        }
        System.out.println("===================== 显示高亮数据 ==================");
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();// 获取所有的高亮数据
        for (SolrDocument document : results) {
            Map<String,List<String>> resultMap = highlighting.get(document.get("id")) ;
            System.out.println("【高亮显示】" + resultMap.get("solr_s_name"));
        }
        solrClient.close();
    }
}