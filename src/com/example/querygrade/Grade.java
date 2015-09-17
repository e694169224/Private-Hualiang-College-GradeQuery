package com.example.querygrade;

/***
 * TableAdapter的数据的实体类
 * courseName：课程名称
 * teacher：教师
 * usuallyResults：平时成绩
 * finalGrade：期末成绩
 * sumGrade：总评成绩
 * GPA：绩点
 * credit：学分
 * creditGPA：学分绩点
 * @author yin
 *
 */
public class Grade {
	private String courseName;
	private String teacher;
	private String usuallyResults;
	private String finalGrade;
	private String sumGrade;
	private String GPA;
	private String credit;
	private String creditGPA;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getUsuallyResults() {
		if(" ".equals(usuallyResults)){
			return "无";
		}else{
			return usuallyResults;
		}
		
	}
	public void setUsuallyResults(String usuallyResults) {
		this.usuallyResults = usuallyResults;
	}
	public String getFinalGrade() {
		if(" ".equals(finalGrade)){
			return "无";
		}else{
			return finalGrade;
		}
	}
	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}
	public String getSumGrade() {
		return sumGrade;
	}
	public void setSumGrade(String sumGrade) {
		this.sumGrade = sumGrade;
	}
	public String getGPA() {
		return GPA;
	}
	public void setGPA(String gPA) {
		GPA = gPA;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getCreditGPA() {
		return creditGPA;
	}
	public void setCreditGPA(String creditGPA) {
		this.creditGPA = creditGPA;
	}
	
	

}
