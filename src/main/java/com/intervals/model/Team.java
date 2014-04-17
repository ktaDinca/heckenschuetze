package com.intervals.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 05/Apr/2014
 */

//@Entity
//@Table(name = "teams")
public class Team extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
