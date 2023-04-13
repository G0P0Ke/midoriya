<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <style><%@include file="/WEB-INF/css/style.css"%></style>
    <div>
        <a class="ghost-button-semi-transparent" onclick="window.location='info'">Info</a>
    </div>
    <hr>
    <p style="text-align: center; font-family:Gill Sans, sans-serif;color: #fff; font-size: 2.0em">Charts</p>
    <div class="button_div">
        <input class="button-next-page" type="button" value="Global export" onclick="window.location='global-export'">
        <input class="button-next-page" type="button" value="Non-primary export" onclick="window.location='non-primary-export'" >
        <input class="button-next-page" type="button" value="Global import" onclick="window.location='global-import'" >
        <input class="button-next-page" type="button" value="Category import" onclick="window.location='category-import'" >
    </div>
    <hr>
</head>
<body>
<div id="chartContainer" class="chart"></div>
<div class="chart">
    <form:form method="post" modelAttribute="selectForm">
        <form:select path="title" cssClass="select">
            <form:options items="${countryList}" itemValue="title" itemLabel="title" />
        </form:select>
        <input type="submit" class="select" value="Choose Country"/>
    </form:form>
</div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script type="text/javascript">
  window.onload = function() {

    let dps = [[]];
    let chart = new CanvasJS.Chart("chartContainer", {
      theme: "dark1", // "light1", "dark1", "dark2"
      animationEnabled: true,
      title: {
        text: "Russia's petroleum export to ${country}",
        fontFamily: "monospace"
      },
      subtitles: [{
        text: "${firstYear}-${lastYear}",
        fontFamily: "monospace"
      }],
      axisX: {
        valueFormatString: "####"
      },
      axisY: {
        title: "USD million",
        fontFamily: "monospace"
      },
      data: [{
        type: "spline",
        xValueFormatString: "####",
        yValueFormatString: "#,##0.0 million dollars",
        dataPoints: dps[0]
      }]
    });

    let xValue;
    let yValue;

    <c:forEach items="${dataPointsList}" var="dataPoints" varStatus="loop">
        <c:forEach items="${dataPoints}" var="dataPoint">
            xValue = parseInt("${dataPoint.x}");
            yValue = parseFloat("${dataPoint.y}");
            dps[parseInt("${loop.index}")].push({
              x : xValue,
              y : yValue
            });
        </c:forEach>
    </c:forEach>

    chart.render();

  }
</script>
</body>
</html>