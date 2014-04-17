
package com.intervals.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since March 3, 2014
 */
//@Entity
//@Table(name = "client")
public class Client extends AbstractEntity {

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}