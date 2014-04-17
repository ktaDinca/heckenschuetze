package com.intervals.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 04/Mar/2014
 */
public enum JobType {

    ADMIN, CLERK, DEPARTMENT_MANAGER, DIVISION_MANAGER;

    public static List<String> getAllValues() {
        List<String> values = new ArrayList<String>();
        for (JobType type : JobType.values()) {
            values.add(type.toString());
        }

        return values;
    }

}
