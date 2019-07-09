package com.lion.solr.task;

import com.lion.solr.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/solr.properties")
public class DeltaImportTask {
    @Value("${solr.delta.import.url}")
    private String deltaImportUrl ;
    @Scheduled(cron = "0 * * * * ?")    // 每秒执行一次
    public void startDeltaImportTask() {
        System.out.println("【Solr-DeltaImport】进行Solr索引的增量重建。");
        HttpRequestUtil.send(this.deltaImportUrl) ;
    }
}
