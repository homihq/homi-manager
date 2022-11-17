package com.homihq.manager.core.aws;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import javax.annotation.PostConstruct;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AwsRegionProvider {

    @Value("classpath:aws-regions.csv")
    private Resource regionListResource;

    private List<AwsRegion> regionList;

    public List<AwsRegion> getRegionList() {
        return this.regionList;
    }

    @SneakyThrows
    @PostConstruct
    public void load() {
        log.info("Loading AWS Regions ----{}", regionListResource.getFilename());
        ICsvBeanReader beanReader = new CsvBeanReader(new InputStreamReader(regionListResource.getInputStream()), CsvPreference.STANDARD_PREFERENCE);
        final String[] header = beanReader.getHeader(true);
        final CellProcessor[] processors = new CellProcessor[] {

                new NotNull(), // value
                new NotNull() // key
        };
        regionList = new ArrayList<>();
        AwsRegion awsRegion;
        while( (awsRegion = beanReader.read(AwsRegion.class, header, processors)) != null ) {
            regionList.add(awsRegion);
        }

        log.info("Aws regions - {}", regionList);
    }
}
