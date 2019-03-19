package com.chenjin.testPCQueue.sys.entity;

import com.chenjin.testPCQueue.common.entity.BasicEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="t_sys_file")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileManagement extends BasicEntity
{
    private static final long serialVersionUID = -8326251862447544117L;
    private String fileName;
    private String fileURL;
    private String keyFlag;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_sys_file_s")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(length=50)
    public String getFileName() { return this.fileName; }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    @Column(length=50)
    public String getFileURL() { return this.fileURL; }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }
    @Column(length=50)
    public String getKeyFlag() { return this.keyFlag; }

    public void setKeyFlag(String keyFlag) {
        this.keyFlag = keyFlag;
    }
}