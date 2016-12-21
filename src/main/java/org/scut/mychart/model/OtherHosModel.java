package org.scut.mychart.model;

public class OtherHosModel {
	private Integer person_num;
	private String sex;
	private String age;
	private String area;
	private String year;
	private String dep_name;
	private String hos_name;
	private Integer total_num;
	private String doc_name;
	
	
	public Integer getPerson_num(){
		return person_num;	
	}
	public String getSex(){
		return sex;
	}
	public String getAge(){
		return age;
	}
	public String getArea(){
		return area;
	}
	public String getYear()
	{
		return year;
	}
	public String getDep_name(){
		return dep_name;
	}
	public Integer getTotal_num(){
		return total_num;
	}
	public String getDoc_name(){
		return doc_name;
	}
	public String getHos_name(){
		return hos_name;
	}
	
	public void setYear(String year){
		this.year=year;
	}
	public void setAge(String age){
		this.age=age;
	}
	public void setPerson_num(Integer person_num){
		this.person_num=person_num;
	}
	

}
