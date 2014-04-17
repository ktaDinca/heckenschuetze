package com.intervals.model;

import javax.persistence.*;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 05/Apr/2014
 */

//@Entity
//@Table(name = "players")
public class Player extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
