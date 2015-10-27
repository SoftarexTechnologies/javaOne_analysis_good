package com.dcservice.persistence.models;

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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dcservice.persistence.models.base.BaseModel;
import com.dcservice.persistence.models.enums.FieldType;
import com.dcservice.persistence.models.fields.Field;

@Entity
@Table
public class Option extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 4210698261930953201L;

	@Column
	private String label;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Field field;
	
	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		setId(ois.readLong());
		setVersion(ois.readLong());
		setCreatedDate((Date)ois.readObject());
		setUpdatedDate((Date)ois.readObject());
		
		setLabel((String)ois.readObject());
		setField((Field)ois.readObject());
		
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeLong(getId());
		oos.writeLong(getVersion());
		oos.writeObject(getCreatedDate());
		oos.writeObject(getUpdatedDate());
		
		oos.writeObject(getLabel());
		oos.writeObject(getField());
	}

	private void readObjectNoData() throws ObjectStreamException {
		throw new InvalidObjectException("Stream data required");
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
}
