package com.mua.overwatch.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DayPackageUsage {

    @Embedded
    private DayUsage dayUsage;
    @Relation(
            parentColumn = "day_usage_id",
            entityColumn = "package_usage_id"
    )
    private List<PackageUsage> packageUsageList;

}
