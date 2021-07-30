package com.ganesh.pojos;

import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Station {
    @NonNull
    private final int stationId;
    private String stationName;

    public String toString(){
        return "[Station ID: "+stationId +" Station Name: "+stationName +"]";
    }
}
