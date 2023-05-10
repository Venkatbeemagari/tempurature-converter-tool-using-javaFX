package com.example.temptocels;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class TemperatureConverterApp extends Application {
    private ComboBox<String> inputScaleComboBox;
    private ComboBox<String> outputScaleComboBox;
    private TextField temperatureField;
    private Label resultLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Temperature Converter");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Temperature input field
        Label temperatureLabel = new Label("Temperature:");
        temperatureField = new TextField();
        grid.add(temperatureLabel, 0, 0);
        grid.add(temperatureField, 1, 0);

        // Input scale selection
        Label inputScaleLabel = new Label("Input Scale:");
        inputScaleComboBox = new ComboBox<>();
        inputScaleComboBox.getItems().addAll("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)");
        inputScaleComboBox.getSelectionModel().selectFirst();
        grid.add(inputScaleLabel, 0, 1);
        grid.add(inputScaleComboBox, 1, 1);

        // Output scale selection
        Label outputScaleLabel = new Label("Output Scale:");
        outputScaleComboBox = new ComboBox<>();
        outputScaleComboBox.getItems().addAll("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)");
        outputScaleComboBox.getSelectionModel().selectFirst();
        grid.add(outputScaleLabel, 0, 2);
        grid.add(outputScaleComboBox, 1, 2);

        // Convert button
        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> convertTemperature());
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(convertButton);
        grid.add(buttonBox, 1, 3);

        // Result label
        resultLabel = new Label();
        grid.add(resultLabel, 0, 4, 2, 1);

        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void convertTemperature() {
        String inputTemperatureStr = temperatureField.getText();
        String inputScale = inputScaleComboBox.getSelectionModel().getSelectedItem();
        String outputScale = outputScaleComboBox.getSelectionModel().getSelectedItem();

        try {
            double inputTemperature = Double.parseDouble(inputTemperatureStr);
            double result = convertTemperature(inputTemperature, inputScale, outputScale);
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String formattedResult = decimalFormat.format(result);
            resultLabel.setText("Converted temperature: " + formattedResult + " " + getScaleAbbreviation(outputScale));
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid temperature input.");
        }
    }

    private double convertTemperature(double temperature, String inputScale, String outputScale) {
        double result;

        if (inputScale.equals("Celsius (°C)")) {
            if (outputScale.equals("Fahrenheit (°F)")) {
                result = (temperature * 9 / 5) + 32;
            } else if (outputScale.equals("Kelvin (K)")) {
                result = temperature + 273.15;
            } else {
                result = temperature;
            }
        } else if (inputScale.equals("Fahrenheit (°F)")) {
            if (outputScale.equals("Celsius (°C)")) {
                result = (temperature - 32) * 5 / 9;
            } else if (outputScale.equals("Kelvin (K)")) {
                result = (temperature + 459.67) * 5 / 9;
            } else {
                result = temperature;
            }
        } else if (inputScale.equals("Kelvin (K)")) {
            if (outputScale.equals("Celsius (°C)")) {
                result = temperature - 273.15;
            } else if (outputScale.equals("Fahrenheit (°F)")) {
                result = (temperature * 9 / 5) - 459.67;
            } else {
                result = temperature;
            }
        } else {
            result = temperature;
        }

        return result;
    }

    private String getScaleAbbreviation(String scale) {
        switch (scale) {
            case "Celsius (°C)":
                return "°C";
            case "Fahrenheit (°F)":
                return "°F";
            case "Kelvin (K)":
                return "K";
            default:
                return "";
        }
    }
}


