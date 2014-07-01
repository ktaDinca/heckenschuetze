package com.airvals.model;

import com.intervals.model.AbstractEntity;
import com.itextpdf.text.Document;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import javax.persistence.*;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 23/Jun/2014
 */
@Entity
@Table(name = "av_ticket")
public class Ticket extends AbstractEntity {

    @Column(name = "path_to_doc")
    private String pathToDocument;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "flight_result_id")
    private FlightResult flightResult;

    @Column(name = "code")
    private String code;

    public String getPathToDocument() {
        return pathToDocument;
    }

    public void setPathToDocument(String pathToDocument) {
        this.pathToDocument = pathToDocument;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public FlightResult getFlightResult() {
        return flightResult;
    }

    public void setFlightResult(FlightResult flightResult) {
        this.flightResult = flightResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        org.springframework.security.authentication.encoding.PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        this.code = passwordEncoder.encodePassword(code, null).substring(0, 15);
    }
}
