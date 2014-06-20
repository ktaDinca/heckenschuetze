package com.airvals.model;

import com.intervals.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 06/Jun/2014
 */
@Entity
@Table(name = "av_companies")
public class Company extends AbstractEntity{

    @Column(name = "name")
    private String name;

}
