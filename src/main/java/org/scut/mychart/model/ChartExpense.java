package org.scut.mychart.model;

public class ChartExpense {
    private Integer person_num,birth;
    private String social_security_card, person_id;
    private String hospital, department;
    private Integer year, month, day;
    private String operation;
    private String city, area, sex;
    private double coverage;

    public Integer getperson_num() {
        return person_num;
    }

    public Integer getyear() {
        return year;
    }

    public String getsex() {
        return sex;
    }

    public String getarea() {return area;}

    public double getCoverage() {
        return coverage;
    }

    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    public Integer getBirth() {return birth;}

    public String getHospital() {return hospital;}

    public String getDepartment() {return department;}
}
