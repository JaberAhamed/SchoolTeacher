package com.teacher.modern.modernteacher.models.viewmodels;

import java.util.List;

public class ClassSectionIdsVM {

    public List<Integer> classIds;
    public List<Integer> sectionIds;

    public ClassSectionIdsVM(List<Integer> classIds, List<Integer> sectionIds) {

        this.classIds = classIds;
        this.sectionIds = sectionIds;
    }
}
