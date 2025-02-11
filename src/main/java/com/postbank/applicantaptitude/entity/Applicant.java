package com.postbank.applicantaptitude.entity;

import lombok.Data;
import javax.persistence.*;
@Data
@Entity
@Table(name = "tm_candidate_info_new")
public class Applicant {
    @Id
    @Column(name = "ID_NUMBER_OR_PASSPORT", nullable = false, length = 50)
    private String idNumber;
    private boolean hasCompletedTest;

    public boolean isHasCompletedTest() {
        return hasCompletedTest;
    }

    public void setHasCompletedTest(boolean hasCompletedTest) {
        this.hasCompletedTest = hasCompletedTest;
    }
}