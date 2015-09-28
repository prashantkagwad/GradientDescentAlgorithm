package com.gda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Prashant Kagwad [pdk130030]
 * 
 */
public class ReadFile {

	private Data data = new Data();

	public ReadFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public void getAttributeData(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";
		int numberOfAttributes = 0;

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				StringTokenizer st = new StringTokenizer(line);
				numberOfAttributes = st.countTokens();

				String[] attributeNames = new String[numberOfAttributes + 1];
				for (int i = 0; i < numberOfAttributes; i++) {
					attributeNames[i] = st.nextToken();
				}
				attributeNames[numberOfAttributes] = "Class";
				data.setAttributeNames(attributeNames);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void getRowData(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";
		ArrayList<int[]> tempData = new ArrayList<int[]>();

		int numberOfAttributes = 0;
		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {

				StringTokenizer st = new StringTokenizer(line);
				numberOfAttributes = st.countTokens();

				int[] rowData = new int[numberOfAttributes];
				for (int i = 0; i < numberOfAttributes; i++) {

					rowData[i] = Integer.parseInt(st.nextToken());
				}
				tempData.add(rowData);
			}
			data.setRowData(tempData);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void printData() {

		ArrayList<int[]> rowData = data.getRowData();
		System.out.println("Number of Examples : " + rowData.size());
		System.out.println("Attributes/Example : "
				+ (rowData.get(0).length - 1));

		// Printing Attribute Names
		String[] attributeNames = data.getAttributeNames();
		for (int i = 0; i < attributeNames.length; i++) {
			System.out.print(attributeNames[i] + "\t");
		}
		System.out.println();

		// Printing Data
		for (int i = 0; i < rowData.size(); i++) {
			int[] row = rowData.get(i);
			for (int j = 0; j < row.length; j++) {
				System.out.print(row[j] + "\t");
			}
			System.out.println();
		}
	}
}