package com.lion.solr.task;

import com.lion.solr.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/solr.properties")
public class FullImportTask {
    @Value("${solr.full.import.url}")
    private String fullImportUrl ;
    @Scheduled(cron = "0 0 0 1,5,15,20,25 * ?")
    public void startFullImportTask() {
        System.out.println("【Solr-FullImport】进行Solr索引的完全重建。");
        HttpRequestUtil.send(this.fullImportUrl) ;
    }
}