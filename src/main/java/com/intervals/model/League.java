package com.intervals.model;

import javax.persistence.*;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 06/Apr/2014
 */

//@Entity
//@Table(name = "leagues")
public class League extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "repr_id")
    private Player representative;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
