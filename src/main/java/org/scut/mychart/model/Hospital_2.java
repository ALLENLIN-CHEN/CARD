package org.scut.mychart.model;

public class Hospital_2 {

    private Integer  person_num;
    private Integer  person_sum;
    private String   sex;
    private Integer  year;
    private String   age;
    private String   hospital;
    private String   doctor;
    private String   department;
    private Integer  hospital_num;
    private Integer  hospital_sim;
    private Integer  hospital_sum;
    private Integer  rate_desc;

    public Integer getperson_num() {
        return person_num;
    }
    public Integer getperson_sum() {
        return person_sum;
    }
    public Integer getyear() {
        return year;
    }
    public String getsex() {
        return sex;
    }
    public String getage() {
        return age;
    }
    public String gethospital() {
        return hospital;
    }
    public String getdoctor() {
        return doctor;
    }
    public String getdepartment() {
        return department;
    }
    public Integer getHospital_num() {
        return hospital_num;
    }
    public Integer getSim(){
        return hospital_sim;
    }
    public Integer getSum(){
        return hospital_sum;
    }
    public Integer getRate(int days){
        rate_desc = Integer.valueOf(1000*getSim()*days/getSum());
        return rate_desc;
    }
}
