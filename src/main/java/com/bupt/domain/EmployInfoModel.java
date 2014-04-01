package com.bupt.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: narutolby
 * Date: 13-8-6
 * Time: 下午8:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="EMPLOY_INFO")
public class EmployInfoModel implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column(name = "company_id")
    private String companyId;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "job_name")
    private String jobName;
    @Column(name = "job_link")
    private String jobLink;
    @Column(name = "company_link")
    private String companyLink;
    @Column(name = "employ_num")
    private int employ_num;
    @Column(name = "job_type")
    private String jobType;
    @Column(name = "job_city")
    private String jobCity;
    @Column(name = "pub_date")
    private String pubDate;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobLink() {
        return jobLink;
    }

    public void setJobLink(String jobLink) {
        this.jobLink = jobLink;
    }

    public int getEmploy_num() {
        return employ_num;
    }

    public void setEmploy_num(int employ_num) {
        this.employ_num = employ_num;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobCity() {
        return jobCity;
    }

    public void setJobCity(String jobCity) {
        this.jobCity = jobCity;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCompanyLink() {
        return companyLink;
    }

    public void setCompanyLink(String companyLink) {
        this.companyLink = companyLink;
    }
    @Override
    public String toString(){
       StringBuilder sb = new StringBuilder();
       sb.append("companyName:").append(this.getCompanyName()+" ").append("companyLink:").append(this.getCompanyLink()+" ")
         .append("jobName:").append(this.getJobName()+" ").append("jobLink:").append(this.getJobLink()+" ")
         .append("employNum:").append(this.getEmploy_num()+" ").append("jobType:").append(this.getJobType()+" ")
         .append("jobCity:").append(this.getJobCity()+" ").append("pubDate:").append(this.getPubDate());
        return sb.toString();
    }
}
