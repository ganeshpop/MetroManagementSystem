package com.ganesh.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Getter
@AllArgsConstructor
@RequiredArgsConstructor

public class Student {
    private int rollNumber;
    @NonNull
    private final int marksInEnglish;
    @NonNull
    private final int marksInMaths;
    @NonNull
    private final int marksInScience;
    @NonNull
    private final String studentName;

}
