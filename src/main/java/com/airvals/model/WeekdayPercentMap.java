package com.airvals.model;

import com.intervals.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 22/Jun/2014
 */

@Entity
@Table(name = "av_weekday_percents_map")
public class WeekdayPercentMap extends AbstractEntity {

    private String day;
    private Float percent;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

}
