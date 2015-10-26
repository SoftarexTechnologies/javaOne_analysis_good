package com.dcservice.persistence.models.responses;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.dcservice.persistence.models.FieldResponse;
import com.dcservice.persistence.models.base.BaseModel;

@Entity
@Table
public class Response extends BaseModel implements Serializable {

	private static final long serialVersionUID = 754140410656222737L;

	@OneToMany(mappedBy = "response")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<FieldResponse> fieldResponses;

	public Set<FieldResponse> getFieldResponses() {
		return fieldResponses;
	}

	public void setFieldResponses(Set<FieldResponse> fieldResponses) {
		this.fieldResponses = fieldResponses;
	}

}
