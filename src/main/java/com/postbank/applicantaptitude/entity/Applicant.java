package com.postbank.applicantaptitude.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "tm_candidate_info_new")
@Getter
@Setter
public class Applicant {

    @Id
    @Column(name = "ID_NUMBER_OR_PASSPORT", nullable = false, unique = true)
    private String idNumberOrPassport;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "FNAME", nullable = false)
    private String firstName;

    @Column(name = "LNAME", nullable = false)
    private String lastName;

    @Column(name = "has_completed_test", columnDefinition = "BIT(1)")
    private boolean hasCompletedTest;

    public boolean getHasCompletedTest() {
        return hasCompletedTest;
    }

    public void setHasCompletedTest(boolean hasCompletedTest) {
        this.hasCompletedTest = hasCompletedTest;
    }
}