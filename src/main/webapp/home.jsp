<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <link rel="icon" type="image/png" href="../assets/img/icon2.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="refresh" content="30; url=/login" />

    <title>Sentiment-analysis</title>


    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />

    <!--    Custom CSS     -->
    <link rel="stylesheet"  href="/assets/css/custom.css"/>

    <!--    Bootstrap core CSS     -->
    <link rel="stylesheet"  href="/assets/css/bootstrap.min.css"/>
    <!--    Light Bootstrap Table core CSS    -->
    <link rel="stylesheet"  href="/assets/css/light-bootstrap-dashboard.css"/>
    <!--    Fonts and icons     -->
    <link rel="stylesheet"  href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"/>
    <link rel="stylesheet"  href='https://fonts.googleapis.com/css?family=Roboto:400,700,300'/>
    <link rel="stylesheet"  href="/assets/css/pe-icon-7-stroke.css"/>

    <!--   Core JS Files and PerfectScrollbar library inside jquery.ui   -->
    <script src="/assets/js/jquery.min.js" type="text/javascript"></script>
    <script src="/assets/js/jquery-ui.min.js" type="text/javascript"></script>
    <script src="/assets/js/bootstrap.min.js" type="text/javascript"></script>
    <!--  Forms Validations Plugin -->
    <script src="/assets/js/jquery.validate.min.js" type="text/javascript"></script>
    <!--  Plugin for Date Time Picker and Full Calendar Plugin-->
    <script src="/assets/js/moment.min.js" type="text/javascript"></script>
    <!--  Date Time Picker Plugin is included in this js file -->
    <script src="/assets/js/bootstrap-datetimepicker.js" type="text/javascript"></script>
    <!--  Select Picker Plugin -->
    <script src="/assets/js/bootstrap-selectpicker.js" type="text/javascript"></script>
    <!--  Checkbox, Radio, Switch and Tags Input Plugins -->
    <script src="/assets/js/bootstrap-checkbox-radio-switch-tags.js" type="text/javascript"></script>
    <!--  Charts Plugin -->
    <script src="/assets/js/chartist.min.js" type="text/javascript"></script>
    <!--  Notifications Plugin    -->
    <script src="/assets/js/bootstrap-notify.js" type="text/javascript"></script>
    <!-- Sweet Alert 2 plugin -->
    <script src="/assets/js/sweetalert2.js" type="text/javascript"></script>
    <!-- Vector Map plugin -->
    <script src="/assets/js/jquery-jvectormap.js" type="text/javascript"></script>
    <!--  Google Maps Plugin    -->
    <script src="https://maps.googleapis.com/maps/api/js" type="text/javascript"></script>
    <!-- Wizard Plugin    -->
    <script src="/assets/js/jquery.bootstrap.wizard.min.js" type="text/javascript"></script>
    <!--  Bootstrap Table Plugin    -->
    <script src="/assets/js/bootstrap-table.js" type="text/javascript"></script>
    <!--  Plugin for DataTables.net  -->
    <script src="/assets/js/jquery.datatables.js" type="text/javascript"></script>
    <!--  Full Calendar Plugin    -->
    <script src="/assets/js/fullcalendar.min.js" type="text/javascript"></script>
    <!-- Light Bootstrap Dashboard Core javascript and methods -->
    <script src="/assets/js/light-bootstrap-dashboard.js" type="text/javascript"></script>

    <!--   Core JS Files and PerfectScrollbar library inside jquery.ui   -->
    <script src="/assets/js/jquery.min.js" type="text/javascript"></script>
    <script src="/assets/js/jquery-ui.min.js" type="text/javascript"></script>
    <script src="/assets/js/bootstrap.min.js" type="text/javascript"></script>


    <!--  Forms Validations Plugin -->
    <script src="/assets/js/jquery.validate.min.js" type="text/javascript"></script>

    <!--  Plugin for Date Time Picker and Full Calendar Plugin-->
    <script src="/assets/js/moment.min.js" type="text/javascript"></script>

    <!--  Date Time Picker Plugin is included in this js file -->
    <script src="/assets/js/bootstrap-datetimepicker.js" type="text/javascript"></script>

    <!--  Select Picker Plugin -->
    <script src="/assets/js/bootstrap-selectpicker.js" type="text/javascript"></script>

    <!--  Checkbox, Radio, Switch and Tags Input Plugins -->
    <script src="/assets/js/bootstrap-checkbox-radio-switch-tags.js" type="text/javascript"></script>

    <!--  Charts Plugin -->
    <script src="/assets/js/chartist.min.js" type="text/javascript"></script>

    <!--  Notifications Plugin    -->
    <script src="/assets/js/bootstrap-notify.js" type="text/javascript"></script>

    <!-- Sweet Alert 2 plugin -->
    <script src="/assets/js/sweetalert2.js" type="text/javascript"></script>

    <!-- Vector Map plugin -->
    <script src="/assets/js/jquery-jvectormap.js" type="text/javascript"></script>

    <!-- Wizard Plugin    -->
    <script src="/assets/js/jquery.bootstrap.wizard.min.js" type="text/javascript"></script>

    <!--  Bootstrap Table Plugin    -->
    <script src="/assets/js/bootstrap-table.js" type="text/javascript"></script>

    <!--  Plugin for DataTables.net  -->
    <script src="/assets/js/jquery.datatables.js" type="text/javascript"></script>

    <!--  Full Calendar Plugin    -->
    <script src="/assets/js/fullcalendar.min.js" type="text/javascript"></script>

    <!-- Light Bootstrap Dashboard Core javascript and methods -->
    <script src="/assets/js/light-bootstrap-dashboard.js" type="text/javascript"></script>

</head>
<body>

<div class="wrapper">
    <div class="sidebar" data-color="orange" data-image="../assets/img/full-screen-image-3.jpg">

        <div class="sidebar-wrapper">

            <div class="logo">
                <a href="/login" class="logo-text">
                    Gmail Sentiment </br>
                    Analysis
                </a>
            </div>
            <div class="user">
                <div class="photo">
                    <img src="../assets/img/incentro.png" />
                </div>
                <div class="info">
                    <a>${currentUser.email}</a>
                </div>
            </div>
        </div>
    </div>

    <div class="main-panel">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="${logout_url}" class="text-danger">
                                <i class="pe-7s-close-circle"></i>
                                log out
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="content">
            <!--      here you can write your content for the main area                     -->
            <c:if test="${not empty errormessage}">
                <div class="alert alert-danger animated fadeInDown" role="alert">
                    <strong>Oh snap!</strong> ${errormessage}
                </div>
            </c:if>
            <c:if test="${not empty message}">
                <div class="alert alert-success animated fadeInDown" role="alert" data-color="black">
                    <strong>Success!</strong> ${message}
                </div>
            </c:if>
            <!--<div data-notify="container" class="col-xs-11 col-sm-4 alert alert-info alert-with-icon animated fadeInDown" -->
            <div class="container-fluid">



                <div class="row">

                    <div class="col-md-4">
                        <div class="card bordercard">
                            <p class="psubtext">Subscribe to the Gmail Sentiment Analysis tool so we can help you structure and analyse your Gmail account! </br></br>
                                <a href="/subscribe" class="btn btn-warning btn-fill btn-wd" role="button">Subscribe</a></p>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card bordercard">
                            <p class="psubtext">It was nice having this app, but now I would like to structure my own Gmail account again.</br></br>
                                <a href="/unsubscribe" class="btn btn-warning btn-fill btn-wd" role="button">Unsubscribe</a>
                            </p>
                        </div>
                    </div>

                    <c:if test="${not empty mailsToday && mailsToday != 0}">
                        <div class="col-md-4">
                            <div class="card bordercard">
                                <div class="content">
                                    <p class="psubtext "><h5>Emails today : ${mailsToday}</h5>  </p>
                                    <a href="http://www.gmail.com" class="btn btn-simple btn-warning btn-icon">
                                        <i class="allicons pe-7s-mail "></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${currentUser.email == 'admin'}">
                        <a href="/getMail" class="btn btn-warning btn-fill btn-wd" role="button">Filter Email</a>
                    </c:if>

                </div>
                <div class="row">
                    <c:if test="${not empty pieChartValues.allPositiveValues && not empty pieChartValues.allNegativeValues}">
                        <div class="col-md-3">
                            <div class="card bordercard">
                                <div class="header">
                                    Total positive and negative
                                    <p class="category">Bar Chart</p>
                                </div>
                                <div class="content">
                                    <div class="row">
                                        <div id="chartViews" class="ct-chart-bar"></div>
                                    </div>
                                </div>
                                <div class="footer">
                                    <h6>Legend</h6>
                                    <ul>
                                        <c:forEach items="${pieChartValues.allPositiveValues}" var="cv">
                                            <li>${cv.label}: ${cv.value}</li>
                                        </c:forEach>
                                        <c:forEach items="${pieChartValues.allNegativeValues}" var="cv">
                                            <li>${cv.label}: ${cv.value}</li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty pieChartValues.chartValues}">
                        <div class="col-md-5">
                            <div class="card bordercard">
                                <div class="header">
                                    Analysis of Gmail account
                                    <p class="category">Pie Chart</p>
                                </div>
                                <div class="content">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div id="chartPreferences" class="ct-chart "></div>
                                        </div>
                                        <div class="col-md-6">
                                            <h6>Legend</h6>
                                            <ul>
                                                <c:forEach items="${pieChartValues.chartValues}" var="cv">
                                                    <li class="legenda-link"><a href="https://mail.google.com/mail/u/0/#label/${cv.label}">${cv.label}: ${cv.value}</a></li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="footer"></div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty emailContentToday}">
                        <div class="col-md-4">
                            <div class="card bordercard">
                                <div class="content">
                                    <p class="psubtext">Latest analysed e-mails:
                                    <div class="fresh-datatables">
                                        <table id="datatables" class="table table-striped table-no-bordered table-hover" cellspacing="0"
                                               width="100%" style="width:100%">
                                            <thead>
                                            <tr>
                                                <th>From</th>
                                                <th>Subject</th>
                                                <th>Analysed status</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${emailContentToday}" var="mail">
                                                <tr class='clickable-row' data-href='https://mail.google.com/mail/u/0/#label/${mail.statusMail}/${mail.messageID}'>
                                                    <td>
                                                        <c:if test="${not empty mail.fromMail}">
                                                            ${mail.fromMail}
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <c:if test="${not empty mail.subjectMail}">
                                                            ${mail.subjectMail}
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <c:if test="${not empty mail.statusMail}">
                                                            ${mail.statusMail}
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                </div> <!-- END row -->

                <div class="row">
                    <c:if test="${not empty pieChartValues.chartValues}">
                        <div class="col-md-8">
                            <div class="card bordercard">
                                <div class="header">
                                    Last 5 days
                                    <p class="category">Line Chart with the amount of mails received</p>
                                </div>
                                <div class="content">
                                    <div id="chartStock" class="ct-chart ">

                                    </div>

                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <footer class="footer">
            <div class="container-fluid">
                <p class="copyright pull-right">
                    <a href="http://www.incentro.com" class="btn btn-simple btn-warning btn-icon edit" target="_blank">
                    &copy; 2017 Incentro
                </a>
                </p>
            </div>
        </footer>
    </div> <!-- /main panel -->
</div> <!-- /wrapper -->


</body>

<script>
    jQuery(document).ready(function($) {
        $(".clickable-row").click(function() {
            window.open($(this).attr('data-href'));
        });
    });
</script>

<script>
    $().ready(function(){
        charts.initCharts();
    });

    type = ['','info','success','warning','danger'];

    charts = {
        initCharts: function () {
            // Pie Chart
            var dataPreferences = {
                series: [
                    [25, 30, 20, 25]
                ]
            };

            var optionsPreferences = {
                donut: true,
                donutWidth: 50,
                startAngle: 0,
                height: "245px",
                total: 100,
                showLabel: false,
                axisX: {
                    showGrid: false
                }
            };

            Chartist.Pie('#chartPreferences', dataPreferences, optionsPreferences);

            Chartist.Pie('#chartPreferences', {
                labels: ${pieChartValues.stringValues},
                series: ${pieChartValues.intValues}
            });

            // Bar Chart
            var data = {
                labels: ['Values'],
                series: [${pieChartValues.barPValues},${pieChartValues.barNValues}
                ]
            };

            var options = {
                seriesBarDistance: 100,
                axisX: {
                    showGrid: false
                },
                height: "245px"
            };

            var responsiveOptions = [
                ['screen and (max-width: 240px)', {
                    seriesBarDistance: 50,
                    axisX: {
                        labelInterpolationFnc: function (value) {
                            return value[0];
                        }
                    }
                }]
            ];

            Chartist.Bar('#chartViews', data, options, responsiveOptions);

            // **************** ingle line with points ******************** */

            var dataStock = {
                labels: [
                    <c:forEach items="${recentMailValues}" var="cv">
                    '${cv.label}',
                    </c:forEach>],
                series: [ [
                    <c:forEach items="${recentMailValues}" var="cv">
                    ${cv.value},
                    </c:forEach>]
                ]
            };

            var optionsStock = {
                lineSmooth: false,
                height: "260px",
                axisY: {
                    offset: 40,
                    labelInterpolationFnc: function(value) {
                        return value;
                    }

                },
                classNames: {
                    point: 'ct-point ct-green',
                    line: 'ct-line ct-green'
                }
            };

            Chartist.Line('#chartStock', dataStock, optionsStock);

        }
    }
</script>





<!--   Core JS Files and PerfectScrollbar library inside jquery.ui   -->
<script src="../assets/js/jquery.min.js" type="text/javascript"></script>
<script src="../assets/js/jquery-ui.min.js" type="text/javascript"></script>
<script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>


<!--  Forms Validations Plugin -->
<script src="../assets/js/jquery.validate.min.js"></script>

<!--  Plugin for Date Time Picker and Full Calendar Plugin-->
<script src="../assets/js/moment.min.js"></script>

<!--  Date Time Picker Plugin is included in this js file -->
<script src="../assets/js/bootstrap-datetimepicker.js"></script>

<!--  Select Picker Plugin -->
<script src="../assets/js/bootstrap-selectpicker.js"></script>

<!--  Checkbox, Radio, Switch and Tags Input Plugins -->
<script src="../assets/js/bootstrap-checkbox-radio-switch-tags.js"></script>

<!--  Charts Plugin -->
<script src="../assets/js/chartist.min.js"></script>

<!--  Notifications Plugin    -->
<script src="../assets/js/bootstrap-notify.js"></script>

<!-- Sweet Alert 2 plugin -->
<script src="../assets/js/sweetalert2.js"></script>

<!-- Vector Map plugin -->
<script src="../assets/js/jquery-jvectormap.js"></script>

<!-- Wizard Plugin    -->
<script src="../assets/js/jquery.bootstrap.wizard.min.js"></script>

<!--  bootstrap Table Plugin    -->
<script src="../assets/js/bootstrap-table.js"></script>

<!--  Plugin for DataTables.net  -->
<script src="../assets/js/jquery.datatables.js"></script>


<!--  Full Calendar Plugin    -->
<script src="../assets/js/fullcalendar.min.js"></script>

<!-- Light Bootstrap Dashboard Core javascript and methods -->
<script src="../assets/js/light-bootstrap-dashboard.js"></script>

<script src="../assets/js/charts.js"></script>



</html>
