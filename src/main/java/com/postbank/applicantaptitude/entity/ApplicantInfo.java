package com.postbank.applicantaptitude.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tm_recruitment_userinfo")
public class ApplicantInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERINFO_ID")
    private Integer userInfoId;

    @Column(name = "ID_NUMBER_OR_PASSPORT", nullable = false)
    private String idNumberOrPassport;

    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;

    public Integer getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Integer userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getIdNumberOrPassport() {
        return idNumberOrPassport;
    }

    public void setIdNumberOrPassport(String idNumberOrPassport) {
        this.idNumberOrPassport = idNumberOrPassport;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}