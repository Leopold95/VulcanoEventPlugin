package me.leopold95.vulcano.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ConfigFiller {
    public static List<Pair> getConfigFills(){
        return Arrays.asList(
                new Pair("test1", "v1"),
                new Pair("test2", "v2")
        );
    }

    public static List<Pair> getMessageFills(){
        return Arrays.asList(
                new Pair("test1", "v1"),
                new Pair("test2", "v2")
        );
    }

}

