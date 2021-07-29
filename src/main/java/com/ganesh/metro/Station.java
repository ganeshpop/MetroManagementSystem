package com.ganesh.metro;

import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Station {
    @NonNull
    private final int stationId;
    private String stationName;

}
