// JavaScript Document
//chart js
$('#passedTest').html(barChartValuePassed);
$('#faliedTest').html(barChartValueFailed);
$('#noRunTest').html(barChartValueNoRun);


$(function () {
    Highcharts.chart('container1', {
        chart: {
            type: 'column',
			margin: [10, 0, 50, 60],
			backgroundColor: 'transparent',
        },
	 colors: ['#90ee7e', '#f45b5b', '#2196f3'],

        title: {
            text: null
        },
        subtitle: {
             enabled: false
        },
        xAxis: {
            type: 'category',
			 title: {
                text: 'Status'
            }
        },
        yAxis: {
            title: {
                text: 'Test Cases'
            }

        },
				 exporting: {
            enabled: false
        },
        title: {
            text: null
        },
		 credits: {
            enabled: false
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:.1f}'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}</b> of total<br/>'
        },

        series: [{
            name: 'Test',
            colorByPoint: true,
            data: [{
                name: 'Passed',
                y: barChartValuePassed,
            }, {
                name: 'Failed',
                y: barChartValueFailed,
            }, {
                name: 'No Run',
                y: barChartValueNoRun,
            }]
        }],
    });
});

//====================== Pie Chart ================================//
$(function () {
    Highcharts.chart('container3', {
        chart: {
            type: 'pie',
			backgroundColor: 'transparent',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0,
            }
        },
		  colors: ['#90ee7e', '#f45b5b', '#2196f3'],
		 exporting: {
            enabled: false
        },
		legend: {
			enabled: true
        },
        title: {
            text: null
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
		 credits: {
            enabled: false
        },

        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Test Share',
            data: [
                {
                    name: 'Passed',
                    y: barChartValuePassed,
                    sliced: true,
                    selected: true
                },
				['Failed', barChartValueFailed],
				['No Run', barChartValueNoRun],
            ]
        }]
    });
});
//======================inside popup chart =========================================//



function clickChart() {
// ===========================container4 chart ====================================//

$('#modelpassedcount').html(modelchartpassed);
$('#modelfailedcount').html(modelchartfailed);
$('#modelnoruncount').html(modelchartnorun);

$(function () {
    Highcharts.chart('container4', {
        chart: {
            type: 'column',
			margin: [10, 0, 50, 60],
			backgroundColor: 'transparent',
        },
	 colors: ['#90ee7e', '#f45b5b', '#2196f3'],

        title: {
            text: null
        },
        subtitle: {
             enabled: false
        },
        xAxis: {
            type: 'category',
			 title: {
                text: 'Status'
            }
        },
        yAxis: {
            title: {
                text: 'Test Cases'
            }

        },
				 exporting: {
            enabled: false
        },
        title: {
            text: null
        },
		 credits: {
            enabled: false
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:.1f}'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}</b> of total<br/>'
        },

        series: [{
            name: 'Test',
            colorByPoint: true,
            data: [{
                name: 'Passed',
                y: modelchartpassed,
            }, {
                name: 'Failed',
                y: modelchartfailed,
            }, {
                name: 'No Run',
                y: modelchartnorun,
            }]
        }],
    });
});

// ===========================container5 chart ====================================//
$(function () {
    Highcharts.chart('container5', {
        chart: {
            type: 'pie',
			backgroundColor: 'transparent',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0,
            }
        },
		  colors: ['#90ee7e', '#f45b5b', '#2196f3'],
		 exporting: {
            enabled: false
        },
		legend: {
			enabled: true
        },
        title: {
            text: null
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
		 credits: {
            enabled: false
        },

        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Test Share',
            data: [
                {
                    name: 'Passed',
                    y: modelchartpassed,
                    sliced: true,
                    selected: true
                },
				['Failed', modelchartfailed],
				['No Run', modelchartnorun],
            ]
        }]
    });
});

}

//====================global dark theme style for chart ===========================//
Highcharts.theme = {
   colors: ['#2b908f', '#90ee7e', '#f45b5b', '#7798BF', '#aaeeee', '#ff0066', '#eeaaee',
      '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'],
   chart: {
      backgroundColor: {
         linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
         stops: [
            [0, '#2a2a2b'],
            [1, '#3e3e40']
         ]
      },
     
      plotBorderColor: '#606063'
   },
   title: {
      style: {
         color: '#E0E0E3',
         textTransform: 'uppercase',
         fontSize: '20px'
      }
   },
   subtitle: {
      style: {
         color: '#E0E0E3',
         textTransform: 'uppercase'
      }
   },
   xAxis: {
      gridLineColor: '#707073',
      labels: {
         style: {
            color: '#E0E0E3'
         }
      },
      lineColor: '#707073',
      minorGridLineColor: '#505053',
      tickColor: '#707073',
      title: {
         style: {
            color: '#A0A0A3'

         }
      }
   },
   yAxis: {
      gridLineColor: '#707073',
      labels: {
         style: {
            color: '#E0E0E3'
         }
      },
      lineColor: '#707073',
      minorGridLineColor: '#505053',
      tickColor: '#707073',
      tickWidth: 1,
      title: {
         style: {
            color: '#A0A0A3'
         }
      }
   },
   tooltip: {
      backgroundColor: 'rgba(0, 0, 0, 0.85)',
      style: {
         color: '#F0F0F0'
      }
   },
   plotOptions: {
      series: {
         dataLabels: {
            color: '#B0B0B3'
         },
         marker: {
            lineColor: '#333'
         }
      },
      boxplot: {
         fillColor: '#505053'
      },
      candlestick: {
         lineColor: 'white'
      },
      errorbar: {
         color: 'white'
      }
   },
   legend: {
      itemStyle: {
         color: '#E0E0E3'
      },
      itemHoverStyle: {
         color: '#FFF'
      },
      itemHiddenStyle: {
         color: '#606063'
      }
   },
   credits: {
      style: {
         color: '#666'
      }
   },
   labels: {
      style: {
         color: '#707073'
      }
   },

   drilldown: {
      activeAxisLabelStyle: {
         color: '#F0F0F3'
      },
      activeDataLabelStyle: {
         color: '#F0F0F3'
      }
   },

   navigation: {
      buttonOptions: {
         symbolStroke: '#DDDDDD',
         theme: {
            fill: '#505053'
         }
      }
   },

   // scroll charts
   rangeSelector: {
      buttonTheme: {
         fill: '#505053',
         stroke: '#000000',
         style: {
            color: '#CCC'
         },
         states: {
            hover: {
               fill: '#707073',
               stroke: '#000000',
               style: {
                  color: 'white'
               }
            },
            select: {
               fill: '#000003',
               stroke: '#000000',
               style: {
                  color: 'white'
               }
            }
         }
      },
      inputBoxBorderColor: '#505053',
      inputStyle: {
         backgroundColor: '#333',
         color: 'silver'
      },
      labelStyle: {
         color: 'silver'
      }
   },

   navigator: {
      handles: {
         backgroundColor: '#666',
         borderColor: '#AAA'
      },
     outlineColor: '#CCC',
      maskFill: 'rgba(255,255,255,0.1)',
      series: {
         color: '#7798BF',
         lineColor: '#A6C7ED'
      },
      xAxis: {
         gridLineColor: '#505053'
      }
   },

   scrollbar: {
      barBackgroundColor: '#808083',
      barBorderColor: '#808083',
      buttonArrowColor: '#CCC',
      buttonBackgroundColor: '#606063',
      buttonBorderColor: '#606063',
      rifleColor: '#FFF',
      trackBackgroundColor: '#404043',
      trackBorderColor: '#404043'
   },

   // special colors for some of the
   legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
   background2: '#505053',
   dataLabelsColor: '#B0B0B3',
   textColor: '#C0C0C0',
   contrastTextColor: '#F0F0F3',
   maskColor: 'rgba(255,255,255,0.3)'
};


// Apply the theme
Highcharts.setOptions(Highcharts.theme);