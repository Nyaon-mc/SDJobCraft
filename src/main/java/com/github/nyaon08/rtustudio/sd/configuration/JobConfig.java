package com.github.nyaon08.rtustudio.sd.configuration;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import kr.rtuserver.framework.bukkit.api.configuration.RSConfiguration;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class JobConfig extends RSConfiguration<SDJobCraft> {

    private String jobSelectInventoryTitle = "직업선택";

    private Map<String, Object> jobInfoInventoryTitle = Map.of(
            "0", "직업 정보 1",
            "1", "직업 정보 2"
    );

    private List<String> globalWorld = List.of("");
    private List<String> farmWorld = List.of("");
    private List<String> irisWorld = List.of("");

    private String compressedCrop = "itemsadder:compressed_crop";
    private String organicCrop = "itemsadder:organic_crop";

    public JobConfig(SDJobCraft plugin) {
        super(plugin, "job.yml", 1);
        setup(this);
    }

    private void init() {
        jobSelectInventoryTitle = getString("job-select-inventory-title", jobSelectInventoryTitle, """
                직업선택 인밴토리 타이틀 설정""");

        jobInfoInventoryTitle = getMap("job-info-inventory-title", jobInfoInventoryTitle, """
                직업 정보 인밴토리 타이틀 설정""");

        globalWorld = getList("global-worlds", globalWorld, """
                공용 월드""");

        farmWorld = getList("farm-worlds", farmWorld, """
                농원 월드""");

        irisWorld = getList("iris-worlds", irisWorld, """
                아이리스 월드""");

        compressedCrop = getString("compressed-crop", compressedCrop, """
                농부 압축 작물 설정""");

        organicCrop = getString("organic-crop", organicCrop, """
                농부 유기농 작물 설정""");
    }

}
