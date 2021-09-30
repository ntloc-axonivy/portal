package ch.ivy.addon.portalkit.statistics.es.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.ChartModel;
import org.primefaces.model.charts.axes.cartesian.CartesianScaleLabel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

public class ChartJsUtil {

  public static ChartModel createPieModel(Map<String, Number> dataset) {
    ChartModel chartModel;
    var pieModel = new PieChartModel();
    ChartData data = new ChartData();

    PieChartDataSet dataSet = new PieChartDataSet();
    List<Number> values = new ArrayList<>(dataset.values());
    dataSet.setData(values);

    List<String> bgColors = new ArrayList<>();
    bgColors.add("rgb(255, 99, 132)");
    bgColors.add("rgb(54, 162, 235)");
    bgColors.add("rgb(255, 205, 86)");
    bgColors.add("rgb(255, 99, 132)");
    bgColors.add("rgb(54, 162, 235)");
    bgColors.add("rgb(255, 205, 86)");
    bgColors.add("rgb(255, 99, 132)");
    bgColors.add("rgb(54, 162, 235)");
    bgColors.add("rgb(255, 205, 86)");
    dataSet.setBackgroundColor(bgColors);

    data.addChartDataSet(dataSet);
    List<String> labels = new ArrayList<>(dataset.keySet());
    data.setLabels(labels);

    pieModel.setData(data);
    chartModel = pieModel;
    return chartModel;
  }

  public static ChartModel createLineModel() {
    ChartModel chartModel;
    var lineModel = new LineChartModel();
    ChartData data = new ChartData();

    LineChartDataSet dataSet = new LineChartDataSet();
    List<Number> values = new ArrayList<>();
    values.add(65);
    values.add(59);
    values.add(80);
    values.add(81);
    values.add(56);
    values.add(55);
    values.add(40);
    dataSet.setData(values);
    dataSet.setFill(false);
    dataSet.setLabel("My First Dataset");
    dataSet.setBorderColor("rgb(75, 192, 192)");
    dataSet.setLineTension(0.1);
    data.addChartDataSet(dataSet);

    List<String> labels = new ArrayList<>();
    labels.add("January");
    labels.add("February");
    labels.add("March");
    labels.add("April");
    labels.add("May");
    labels.add("June");
    labels.add("July");
    data.setLabels(labels);

    // Options
    LineChartOptions options = new LineChartOptions();
    Title title = new Title();
    title.setDisplay(true);
    title.setText("Line Chart");
    options.setTitle(title);

    lineModel.setOptions(options);
    lineModel.setData(data);
    chartModel = lineModel;
    return chartModel;
  }

  public static ChartModel createBarModel(String chartName, String xAxisName, String yAxisName,
      Map<String, Number> dataset) {
    ChartModel chartModel;
    var barModel = new BarChartModel();
    ChartData data = new ChartData();

    BarChartDataSet barDataSet = new BarChartDataSet();
    barDataSet.setLabel(chartName);
    List<String> bgColor = new ArrayList<>();
    List<String> borderColor = new ArrayList<>();

    List<Number> values = new ArrayList<>();
    values.addAll(dataset.values());

    barDataSet.setData(values);
    bgColor.add("rgba(255, 99, 132, 0.2)");
    bgColor.add("rgba(255, 159, 64, 0.2)");
    bgColor.add("rgba(255, 205, 86, 0.2)");
    bgColor.add("rgba(75, 192, 192, 0.2)");
    bgColor.add("rgba(54, 162, 235, 0.2)");
    bgColor.add("rgba(153, 102, 255, 0.2)");
    bgColor.add("rgba(201, 203, 207, 0.2)");
    bgColor.add("rgba(255, 99, 132, 0.2)");
    bgColor.add("rgba(255, 159, 64, 0.2)");
    bgColor.add("rgba(255, 205, 86, 0.2)");
    bgColor.add("rgba(75, 192, 192, 0.2)");
    bgColor.add("rgba(54, 162, 235, 0.2)");
    bgColor.add("rgba(153, 102, 255, 0.2)");
    bgColor.add("rgba(201, 203, 207, 0.2)");
    barDataSet.setBackgroundColor(bgColor);

    borderColor.add("rgb(255, 99, 132)");
    borderColor.add("rgb(255, 159, 64)");
    borderColor.add("rgb(255, 205, 86)");
    borderColor.add("rgb(75, 192, 192)");
    borderColor.add("rgb(54, 162, 235)");
    borderColor.add("rgb(153, 102, 255)");
    borderColor.add("rgb(201, 203, 207)");
    borderColor.add("rgb(255, 99, 132)");
    borderColor.add("rgb(255, 159, 64)");
    borderColor.add("rgb(255, 205, 86)");
    borderColor.add("rgb(75, 192, 192)");
    borderColor.add("rgb(54, 162, 235)");
    borderColor.add("rgb(153, 102, 255)");
    borderColor.add("rgb(201, 203, 207)");
    barDataSet.setBorderColor(borderColor);
    barDataSet.setBorderWidth(1);

    data.addChartDataSet(barDataSet);

    List<String> labels = new ArrayList<>();
    labels.addAll(dataset.keySet());
    data.setLabels(labels);
    barModel.setData(data);

    // Options
    BarChartOptions options = new BarChartOptions();
    CartesianScales cScales = new CartesianScales();
    CartesianLinearAxes linearAxes = new CartesianLinearAxes();
    linearAxes.setOffset(true);
    CartesianLinearTicks ticks = new CartesianLinearTicks();
    ticks.setBeginAtZero(true);
    linearAxes.setTicks(ticks);

    cScales.addXAxesData(createLinearAxes("left", xAxisName));
    cScales.addYAxesData(createLinearAxes("bottom", yAxisName));

    options.setScales(cScales);

    Title title = new Title();
    title.setDisplay(true);
    title.setText("Bar Chart");
    options.setTitle(title);

    Legend legend = new Legend();
    legend.setDisplay(true);
    legend.setPosition("top");
    LegendLabel legendLabels = new LegendLabel();
    legendLabels.setFontStyle("bold");
    legendLabels.setFontColor("#2980B9");
    legendLabels.setFontSize(24);
    legend.setLabels(legendLabels);
    options.setLegend(legend);

    barModel.setOptions(options);
    chartModel = barModel;
    return chartModel;
  }

  private static CartesianLinearAxes createLinearAxes(String position, String axesLabel) {
    CartesianLinearAxes newAxesData = new CartesianLinearAxes();
    newAxesData.setPosition(position);
    CartesianLinearTicks ticks = new CartesianLinearTicks();
    ticks.setBeginAtZero(true);
    newAxesData.setTicks(ticks);

    CartesianScaleLabel scaleLabel = new CartesianScaleLabel();
    scaleLabel.setDisplay(true);
    scaleLabel.setLabelString(axesLabel);
    newAxesData.setScaleLabel(scaleLabel);
    return newAxesData;
  }

  public static ChartModel createDonutModel() {
    ChartModel chartModel;
    var donutModel = new DonutChartModel();
    ChartData data = new ChartData();

    DonutChartDataSet dataSet = new DonutChartDataSet();
    List<Number> values = new ArrayList<>();
    values.add(300);
    values.add(50);
    values.add(100);
    dataSet.setData(values);

    List<String> bgColors = new ArrayList<>();
    bgColors.add("rgb(255, 99, 132)");
    bgColors.add("rgb(54, 162, 235)");
    bgColors.add("rgb(255, 205, 86)");
    dataSet.setBackgroundColor(bgColors);

    data.addChartDataSet(dataSet);
    List<String> labels = new ArrayList<>();
    labels.add("Red");
    labels.add("Blue");
    labels.add("Yellow");
    data.setLabels(labels);

    donutModel.setData(data);
    chartModel = donutModel;
    return chartModel;
  }

}
