package com.dcservice.common.helpers;

import static com.dcservice.common.helpers.ValidationHelper.isNullOrEmpty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dcservice.persistence.models.base.BaseModel;

public class ListHelper extends BaseHelper {

	public static <T extends BaseModel> void remove(Collection<T> list, T entity) {
		if (!isNullOrEmpty(entity))
			remove(list, entity.getId());
	}

	public static <T extends BaseModel> void remove(Collection<T> list, Long id) {
		if (!isNullOrEmpty(list) && !isNullOrEmpty(id)) {
			for (T item : list) {
				if (item.getId().equals(id)) {
					list.remove(item);
					break;
				}
			}
		}
	}

	public static List<String> fileToList(String fileName)
			throws FileNotFoundException {
		List<String> result = new ArrayList<String>();
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
			while ((line = in.readLine()) != null) {
				result.add(line);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} 
		return null;
	}

	public static List<String> getCopy(List<String> list) {
		return new ArrayList<String>(list);
	}

	public static void printWithStep(List<String> list, int step) {
		if (!isNullOrEmpty(list) && step > 0) {
			int position = 0;
			while (list.size() > position) {
				System.out.println(list.get(position));
				position = position + step;
			}
		}
	}

	public static <T extends BaseModel> void bubbleSort(T[] list) {
		T temp = null;
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < list.length - 1; j++) {
				if (list[j].getId() < list[j + 1].getId()) {
					temp = list[j];
					list[j] = list[j + 1];
					list[j + 1] = temp;
				}
			}
		}
	}

}
