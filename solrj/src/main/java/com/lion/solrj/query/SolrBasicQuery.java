package com.lion.solrj.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;


public class SolrBasicQuery {
    public static final String SOLR_HOST_URL = "http://yu:123@192.168.19.138/solr/yu-core";
    public static void main(String[] args) throws Exception {    // 进行Solr数据的查询
        // 1、SolrJ是基于HTTP的一种访问处理形式，而且Solr整体访问的时候都可以通过HTTP的地址完成
        HttpSolrClient solrClient = new HttpSolrClient.Builder(SOLR_HOST_URL).build() ;
        // 2、获取了一个合格的Solr客户端连接对象实例之后，就可以进行查询的创建
        SolrQuery solrQuery = new SolrQuery() ; // 创建查询对象
        solrQuery.setQuery("*:*") ; // 查询全部数据
        // 3、利用SolrClient发出Solr查询命令，随后获取查询响应结果
        QueryResponse queryResponse = solrClient.query(solrQuery);
        // 4、所有的HTTP服务器的响应信息都保存在了QueryResponse对象实例之中，根据响应获取数据
        SolrDocumentList results = queryResponse.getResults();// 获取相应的信息
        // 5、Document保存的是每一个索引数据，而DocumentList返回的是索引集合
        System.out.println("【数据行数】" + results.getNumFound());
        // 6、所有的数据以List集合的形式出现
        for (SolrDocument document : results) { // 文档迭代输出
            System.out.println("【返回信息】id = " + document.get("id") + "、name = " + document.get("solr_s_name") + "、catalog = " + document.get("solr_s_catalog"));
        }
        solrClient.close();
    }
}
