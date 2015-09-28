package com.gda;

import java.util.ArrayList;

/**
 * @author Prashant Kagwad [pdk130030]
 * 
 */
public class GradientDescentAlgorithm {

	public static final double e = 2.718281828;
	public static int x0 = 1;
	public static double w0 = 0;
	public static double[] weights;
	public static double learningRate = 0.1;
	public static int iteration = 200;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			if (args.length != 4) {
				System.err
						.println("You must call GradientDescentAlgorithm as "
								+ "follows:\n\njava GradientDescentAlgorithm "
								+ "<trainsetFilename> <testsetFilename> <learning rate> <number of iterations to run the algorithm>\n");
				System.exit(1);
			}

			// Read in the file names.
			// Training Data File
			ReadFile trainingFile = new ReadFile();
			String trainingSetFileName = System.getProperty("user.dir")
					+ "\\Input\\" + args[0];
			trainingFile.getAttributeData(trainingSetFileName);
			trainingFile.getRowData(trainingSetFileName);
			// trainingFile.printData();
			Data trainingData = trainingFile.getData();

			// Test Data File
			ReadFile testFile = new ReadFile();
			String testSetFileName = System.getProperty("user.dir")
					+ "\\Input\\" + args[1];
			testFile.getAttributeData(testSetFileName);
			testFile.getRowData(testSetFileName);
			// testFile.printData();
			Data testData = testFile.getData();

			learningRate = Double.parseDouble(args[2]);
			iteration = Integer.parseInt(args[3]);

			// Number of Weights corresponding to the input attributes of a set.
			int numberOfAttributes = trainingData.getAttributeNames().length;
			weights = new double[numberOfAttributes];

			// Initialize all the network weights to zero
			weights[0] = w0;
			for (int i = 1; i < weights.length; i++) {
				weights[i] = 0;
			}

			int numberOfExamples = trainingData.getRowData().size();
			while (iteration > 0) {

				if (iteration > numberOfExamples) {
					computePerceptron(trainingData, numberOfExamples);
					iteration = iteration - numberOfExamples;
				} else {
					computePerceptron(trainingData, iteration);
					iteration = 0;
				}
			}

			// printPerceptron();
			computeTrainingDataAccuracy(trainingData);
			computeTestDataAccuracy(testData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void computePerceptron(Data trainingData, int runs) {

		try {
			ArrayList<int[]> rowData = trainingData.getRowData();

			// Iterate over the dataset
			for (int rowIterator = 0; rowIterator < runs; rowIterator++) {

				int[] row = rowData.get(rowIterator);

				// Add the product of (W0 * X0) into summation
				double observeSummation = weights[0] * x0;
				double observe = 0;

				// Compute the observed label using summation of products of
				// (respective weights and attributes) in each row.
				for (int column = 0; column < row.length - 1; column++) {
					observeSummation = observeSummation
							+ (weights[column + 1] * row[column]);
				}

				// Sigmoid Function Calculation
				observe = 1 / (1 + (Math.pow(e, (observeSummation * -1))));

				// Compute and display the Error for this row.
				double error = (row[row.length - 1] - observe);
				// System.out.println("Row No. " + (rowIterator + 1) +
				// " Error : "
				// + error + " [ " + row[row.length - 1] + " - " + observe
				// + " ]");
				// System.out.println("observedSummation : " +
				// observeSummation);

				if (error != 0.0) {

					// System.out.println(weights[0]
					// + " = "
					// + weights[0]
					// + " + ("
					// + learningRate
					// + " * (1 - "
					// + observe
					// + ") * "
					// + error
					// + " * "
					// + x0
					// + " ) => "
					// + (weights[0] = weights[0]
					// + (learningRate * (1 - observe) * observe
					// * error * x0)));

					weights[0] = weights[0]
							+ (learningRate * (1 - observe) * observe * error * x0);

					for (int column = 1; column < weights.length; column++) {

						// System.out
						// .println(weights[column]
						// + " = "
						// + weights[column]
						// + " + ("
						// + learningRate
						// + " * (1 - "
						// + observe
						// + ") * "
						// + error
						// + " * "
						// + row[column - 1]
						// + " ) => "
						// + (weights[column] = weights[column]
						// + (learningRate * (1 - observe)
						// * observe * error * row[column - 1])));

						weights[column] = weights[column]
								+ (learningRate * (1 - observe) * observe
										* error * row[column - 1]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printPerceptron() {

		try {
			System.out.print("[ ");
			for (int iterator = 0; iterator < weights.length; iterator++) {
				System.out.print(weights[iterator] + ",");
			}
			System.out.print(" ]");
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void computeTrainingDataAccuracy(Data trainingData) {

		try {
			int ncce = 0;
			ArrayList<int[]> rowData = trainingData.getRowData();

			// Iterate over the training data set
			for (int rowIterator = 0; rowIterator < rowData.size(); rowIterator++) {

				int[] row = rowData.get(rowIterator);

				// Add the product of (W0 * X0) into summation
				double observeSummation = weights[0] * x0;
				int observe = 0;

				// Compute the observed label using summation of products of
				// (respective weights and attributes) in each row.
				for (int column = 0; column < row.length - 1; column++) {
					observeSummation = observeSummation
							+ (weights[column + 1] * row[column]);
				}

				// Compare the observed frequency to the threshold.
				if (observeSummation >= 0.5)
					observe = 1;
				else
					observe = 0;

				// Compute and display the Error for this row.
				// System.out.println("Row No. " + (rowIterator + 1) + " [ "
				// + row[row.length - 1] + " <> " + observe + " ]");
				// System.out.println("observedSummation : " +
				// observeSummation);

				if (row[row.length - 1] == observe) {
					ncce++;
				}
			}

			double percentage = (ncce * 100) / rowData.size();

			System.out.println("Accuracy on training set (" + rowData.size()
					+ " instances) : " + percentage + " %");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void computeTestDataAccuracy(Data testData) {

		try {

			int ncce = 0;
			ArrayList<int[]> rowData = testData.getRowData();

			// Iterate over the training data set
			for (int rowIterator = 0; rowIterator < rowData.size(); rowIterator++) {

				int[] row = rowData.get(rowIterator);

				// Add the product of (W0 * X0) into summation
				double observeSummation = weights[0] * x0;
				int observe = 0;

				// Compute the observed label using summation of products of
				// (respective weights and attributes) in each row.
				for (int column = 0; column < row.length - 1; column++) {
					observeSummation = observeSummation
							+ (weights[column + 1] * row[column]);
				}

				// Compare the observed frequency to the threshold.
				if (observeSummation >= 0.5)
					observe = 1;
				else
					observe = 0;

				// Compute and display the Error for this row.
				// System.out.println("Row No. " + (rowIterator + 1) + " [ "
				// + row[row.length - 1] + " <> " + observe + " ]");
				// System.out.println("observedSummation : " +
				// observeSummation);

				if (row[row.length - 1] == observe) {
					ncce++;
				}
			}

			double percentage = (ncce * 100) / rowData.size();

			System.out.println("Accuracy on test set (" + rowData.size()
					+ " instances) : " + percentage + " %");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
