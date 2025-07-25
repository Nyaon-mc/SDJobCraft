package com.github.nyaon08.rtustudio.sd;

/**
 * 플러그인의 SDJobCraft 시스템과 상호작용할 수 있는 메서드를 제공하는 클래스입니다.
 */
public class SDJobCraftAPI {

    private static SDJobCraft plugin;

    private SDJobCraftAPI() {
        throw new UnsupportedOperationException("SDJobCraftAPI is not instantiable");
    }

    /**
     * 플러그인 인스턴스를 반환합니다.
     *
     * @return 플러그인 인스턴스
     */
    private static SDJobCraft plugin() {
        if (plugin == null) plugin = SDJobCraft.getInstance();
        return plugin;
    }

}
