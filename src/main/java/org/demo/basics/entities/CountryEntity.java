/*
 * Created on 21 janv. 2015 ( Time 17:25:52 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package org.demo.basics.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="COUNTRY" )

public class CountryEntity {
	
  @Id
  @Column(name="CODE")
  private String code;

  @Column(name="NAME", length=50)
  private String name;

  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }
  public void setName(String deptName) {
    this.name = deptName;
  }

  public String toString() {
    return "Country : '" + code + "' - " + name;
  }
}

