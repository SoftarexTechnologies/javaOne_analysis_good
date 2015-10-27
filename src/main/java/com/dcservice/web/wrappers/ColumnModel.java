package com.dcservice.web.wrappers;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class ColumnModel implements Serializable {

	private static final long serialVersionUID = 301894056907084981L;
	
	private String header;
	private String property;

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		
		setHeader((String)ois.readObject());
		setProperty((String)ois.readObject());
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		
		oos.writeObject(getHeader());
		oos.writeObject(getProperty());
	}

	private void readObjectNoData() throws ObjectStreamException {
		throw new InvalidObjectException("Stream data required");
	}
	
	public ColumnModel(String header, String property) {
		this.header = header;
		this.property = property;
	}

	public String getHeader() {
		return header;
	}

	public String getProperty() {
		return property;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	
}
