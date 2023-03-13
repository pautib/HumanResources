package org.pautib.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class Qualifications {

    private String school;
    @Column(name = "DATE_COMPLETED")
    private LocalDate dateCompleted;
    @Column(name = "QUALIFICATION_AWARDED")
    private String qualificationAwarded;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public LocalDate getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getQualificationAwarded() {
        return qualificationAwarded;
    }

    public void setQualificationAwarded(String qualificationAwarded) {
        this.qualificationAwarded = qualificationAwarded;
    }
}
