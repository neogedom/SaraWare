package br.com.saraware.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "MBData")
@ViewScoped
public class DataBean {

	private Date maxDate;

	public Date getMaxDate() {
		maxDate = new Date();
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
}
