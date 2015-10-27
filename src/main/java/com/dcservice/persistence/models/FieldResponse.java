package com.dcservice.persistence.models;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dcservice.persistence.models.base.BaseModel;
import com.dcservice.persistence.models.fields.Field;
import com.dcservice.persistence.models.responses.Response;

@Entity
@Table
public class FieldResponse extends BaseModel implements Serializable {

	private static final long serialVersionUID = 4391216983978279275L;

	@Column
	public String answer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Field field;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Response response;

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		setId(ois.readLong());
		setVersion(ois.readLong());
		setCreatedDate((Date)ois.readObject());
		setUpdatedDate((Date)ois.readObject());
		
		setAnswer((String)ois.readObject());
		setField((Field)ois.readObject());
		setResponse((Response)ois.readObject());

	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeLong(getId());
		oos.writeLong(getVersion());
		oos.writeObject(getCreatedDate());
		oos.writeObject(getUpdatedDate());
		
		oos.writeObject(getAnswer());
		oos.writeObject(getField());
		oos.writeObject(getResponse());
	}

	private void readObjectNoData() throws ObjectStreamException {
		throw new InvalidObjectException("Stream data required");
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
