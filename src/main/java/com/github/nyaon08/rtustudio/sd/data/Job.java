package com.github.nyaon08.rtustudio.sd.data;

public record Job(Type job, int level, int ep, int probability) {

    public enum Type {
        NONE, FARMER, FISHER, MINER, JEWELER
    }

}
