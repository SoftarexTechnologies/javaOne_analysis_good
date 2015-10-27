package com.dcservice.persistence.models.fields;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.dcservice.persistence.models.FieldResponse;
import com.dcservice.persistence.models.Option;
import com.dcservice.persistence.models.base.BaseModel;
import com.dcservice.persistence.models.enums.FieldType;
import com.dcservice.persistence.models.responses.Response;

@Entity
@Table
public class Field extends BaseModel implements Serializable {

	private static final long serialVersionUID = -5222710508176943878L;

	@Column(nullable = false)
	public FieldType type;

	@Column(nullable = false)
	public String label;

	@Column(nullable = false)
	public Boolean required = false;

	@Column(nullable = false)
	public Boolean active = true;

	@OneToMany(mappedBy = "field")
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Set<Option> options;

	@OneToMany(mappedBy = "field")
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Set<FieldResponse> responses;

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		setId(ois.readLong());
		setVersion(ois.readLong());
		setCreatedDate((Date)ois.readObject());
		setUpdatedDate((Date)ois.readObject());
		
		setType((FieldType)ois.readObject());
		setLabel((String)ois.readObject());
		setRequired((Boolean)ois.readObject());
		setActive((Boolean)ois.readObject());
		setOptions((Set<Option>)ois.readObject());
		setResponses((Set<FieldResponse>)ois.readObject());
		
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeLong(getId());
		oos.writeLong(getVersion());
		oos.writeObject(getCreatedDate());
		oos.writeObject(getUpdatedDate());
		
		oos.writeObject(getType());
		oos.writeObject(getLabel());
		oos.writeObject(getRequired());
		oos.writeObject(getActive());
		oos.writeObject(getOptions());
		oos.writeObject(getResponses());
	}

	private void readObjectNoData() throws ObjectStreamException {
		throw new InvalidObjectException("Stream data required");
	}
	
	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Option> getOptions() {
		return options;
	}

	public void setOptions(Set<Option> options) {
		this.options = options;
	}

	public Set<FieldResponse> getResponses() {
		return responses;
	}

	public void setResponses(Set<FieldResponse> responses) {
		this.responses = responses;
	}

}
