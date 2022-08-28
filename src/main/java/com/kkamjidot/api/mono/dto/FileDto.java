package com.kkamjidot.api.mono.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class FileDto implements Serializable {
    private final String name;
    private final String type;
    private final String path;
}
